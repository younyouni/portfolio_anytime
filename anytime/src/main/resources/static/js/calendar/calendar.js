document.addEventListener('DOMContentLoaded', function() {
  	var calendarEl = document.getElementById('calendar');
  
	
  	var calendar2El = document.getElementById('calendar2');
    
    
   var calendar2 = new FullCalendar.Calendar(calendar2El, {
   headerToolbar: {
	      left: 'title prevYear,nextYear',
    },
   locale: 'ko',
   
  });
    
    // YYYY-MM-DD W HH:mm:ss 형식으로 날씨 변환
	const formatDate = (date) => {
		const year = date.getFullYear();
		const month = (date.getMonth() + 1).toString().padStart(2, '0');
		
		const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
  		const dayOfWeek = daysOfWeek[date.getDay()];
  		
		const day = date.getDate().toString().padStart(2, '0');
		const hours = date.getHours().toString().padStart(2, '0');
		const minutes = date.getMinutes().toString().padStart(2, '0');
		const seconds = date.getSeconds().toString().padStart(2, '0');
	  
	  	return `${year}-${month}-${day} (${dayOfWeek}) ${hours}:${minutes}:${seconds}`;
	};
	
	// YYYY-MM-DD 형식으로 날씨 변환
	const formatDate2 = (date) => {
	  	const year = date.getFullYear();
	  	const month = (date.getMonth() + 1).toString().padStart(2, '0');
	  
	  	const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
		const dayOfWeek = daysOfWeek[date.getDay()];
	  	const day = date.getDate().toString().padStart(2, '0');
	  
	  return `${year}-${month}-${day} ${dayOfWeek}`;
	};
 
 	const formatDate3 = (date) => {
		const year = date.getFullYear();
		const month = (date.getMonth() + 1).toString().padStart(2, '0');
  		
		const day = date.getDate().toString().padStart(2, '0');
		const hours = date.getHours().toString().padStart(2, '0');
		const minutes = date.getMinutes().toString().padStart(2, '0');
		const seconds = date.getSeconds().toString().padStart(2, '0');
	  
	  	return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
	};
  
  var calendar = new FullCalendar.Calendar(calendarEl, {
	  customButtons: {
		    addCalendar: {
		      text: '일정 등록',
		      click: function() {
		      		var calendar = document.getElementById('calendar_setting');
					calendar.textContent = "일정 추가";
					var calendar2 = document.getElementById('calendarModalFormButton');
					calendar2.value = "일정 등록";
		      		
		      		$("form#calendarModal").show();
		      		$('form#calendarModal').css('display', 'block');
					$('form#calendarModal').before('<div class="modalwrap"></div>');

		      }
		    }
		  },
	headerToolbar: {
	      left: 'prevYear,prev,next,nextYear today',
	      center: 'title',
	      right: 'addCalendar',
	      
    },
    initialView: 'dayGridMonth',
    navLinks: true,
    editable: true,
    dayMaxEvents: true,
    locale: 'ko',
    droppable: true,
    selectable: true,
    eventRender: function(info) {
 		
    },


    events: function(info, successCallback, failureCallback) {
      $.ajax({
        url: "calendarlist",
        dataType: "json",
        success: function(Result) {
          var events = [];
          
          if(Result != null){
          $.each(Result, function(index, data) {
            //if (data.pValue != null && data.pValue === 'private') {
              events.push({
            	id: data.id,
                title: data.title,
                start: data.start,
                end: data.end,
                description: data.description,
                borderColor: data.color,
                backgroundColor: '#7869e6',
                allday: data.allday
              });
            //}
          });
          } else {
          	console.log("값이 없어요");
          }
          console.log(events)
          successCallback(events);
        },
        error: function(Result) {
          console.log("에러가 발생했습니다.");
          failureCallback(Result);
        }
      });
    },
    
    eventDrop: function(info){
    	console.log(info);
    },
    
    select: function(arg){
		var start = arg.start;
		//var end = arg.end;
		
		// YYYY-MM-DDTHH:MM 형식으로 변환
		//var startdate = start.toISOString().slice(0, 16);
		
		// 년, 월, 일 부분만 추출
		var year = start.getFullYear();
	    var month = (start.getMonth() + 1).toString().padStart(2, '0');
	    var day = start.getDate().toString().padStart(2, '0');
	    var hours = start.getHours().toString().padStart(2, '0');
	    var minutes = start.getMinutes().toString().padStart(2, '0');
		
		// YYYY-MM-DD 형식으로 조합
		var formattedDate = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
		
	    $("form#calendarModal").show();
  		$('form#calendarModal').css('display', 'block');
		$('form#calendarModal').before('<div class="modalwrap"></div>');
		
		// 입력 요소에 값을 할당
		document.getElementsByName('calendar_date').value = formattedDate;
		
    },
    
    eventClick: function(info) {
	     var event = info.event;
		 console.log('클릭한 이벤트 고유 ID:', event.id);
	 	 console.log('클릭한 이벤트 제목:', event.title);
		 console.log('클릭한 이벤트 시작 시간:', event.start);
		 console.log('클릭한 이벤트 종료 시간:', event.end);
		 console.log('클릭한 이벤트 설명:', event.description);
		 console.log('클릭한 이벤트 색상:', event.borderColor);
		 console.log('클릭한 이벤트 하루 종일:', event.allDay);
		 console.log('클릭한 이벤트 이건뭐야:', event.extendedProps);
		 
		console.log('이건됨? = ' , event.extendedProps.description);
		
		/*쓰는거
		event.id
		event.title
		event.borderColor
		formattedStartDate
		formattedEndDate
		event.allDay
		event.extendedProps.description
		*/
		
		$("form#calendarDetail").show();
  		$('form#calendarDetail').css('display', 'block');
		$('form#calendarDetail').before('<div class="modalwrap"></div>');
		
		//디테일 모달창 제목 변경
		const calendarDetailTitle = document.getElementById('calendar_detail_title');
		calendarDetailTitle.textContent = event.title;
		
		//디테일 타입 변경
		
		
		//디테일 모달창 날짜 변경
		const calendarDetailDate = document.getElementById('calendar_detail_date');
		if(event.allDay == 'false') {
			const eventStartDate = new Date(event.start);
			const eventEndDate = new Date(event.end);	
			const formattedStartDate = formatDate(eventStartDate);
			const formattedEndDate = formatDate(eventEndDate);
				console.log('Start Date:', formattedStartDate);
				console.log('End Date:', formattedEndDate);
						if(event.end != null){
							calendarDetailDate.textContent = formattedStartDate + "~" + formattedEndDate;
						} else {
							calendarDetailDate.textContent = formattedStartDate;
						}
				
		} else {
			const eventStartDate = new Date(event.start);
			const eventEndDate = new Date(event.end);	
			const formattedStartDate = formatDate(eventStartDate);
			const formattedEndDate = formatDate(eventEndDate);
				console.log('Start Date:', formattedStartDate);
				console.log('End Date:', formattedEndDate);
								if(event.end != null){
									calendarDetailDate.textContent = formattedStartDate + " ~ " + formattedEndDate;
								} else {
									calendarDetailDate.textContent = formattedStartDate;
								}
		}
		
		//디테일 설명 변경
		const calendarDetailDescription = document.getElementById('calendar_detail_description');
		calendarDetailDescription.textContent = event.extendedProps.description;
		
		//디테일 색상 변경
		const calendarDetailSpanStyle = document.querySelector('#calendar_detail_title');
		calendarDetailSpanStyle.style.borderLeft = '20px solid' + event.borderColor;

	
		const id = event.id;
		const title = event.title;
		const borderColor = event.borderColor;
		const description = event.extendedProps.description;
	
		
		$('input[name="calendar_title"]').val(title);
	    	if(borderColor != null){
    			$('input[name="color"]:checked').val();
	    		$('input[name="custom_color"]').val(borderColor);
	    	} else {
	    		
	    	}
    	
    	
    	
    	const eventStartDate = new Date(event.start);
		const eventEndDate = new Date(event.end);
		
		//당일 체크 ( 당일이면 end값이 없어야 함 )

    	
		if (event.allDay) {
		const start = formatDate3(eventStartDate);
		  $('input[name="allday"]').prop('checked', true);
		  $('input[name="calendar_date"]').val(start);

			if(event.end != null){
			const end = formatDate3(eventEndDate);
		  	$('input[name="calendar_date2"]').val(end);
			}
		  
		} else {
		const start = formatDate2(eventStartDate);
		  $('input[name="allday"]').prop('checked', false);
		  $('input[name="calendar_date"]').val(start);
		  
			if(event.end != null){
			const end = formatDate2(eventEndDate);
		  	$('input[name="calendar_date2"]').val(end);
			}
			
		}

   		
    	$('textarea[name="calendar_description"]').val(description);
	
	
	
	
	
	
		
		
     },
  
  }); // calendar 끝
  calendar.render();
  calendar2.render();
  
	// 종일 선택 여부
	document.getElementById('allday_check').addEventListener('change', function() {
	  const datetimeField = document.getElementById('all_check'); // 날짜 및 시간 입력 필드
	  const datetimeField2 = document.getElementById('all_check2'); // 날짜 및 시간 입력 필드
	
	  // 기존 값 저장
	  var datetimeFieldValue = datetimeField.value;
	  var datetimeField2Value = datetimeField2.value;
	
	  // 체크박스가 체크된 경우 datetime 입력 필드를 날짜 선택 모드로 변경
	  if (this.checked) {
	    datetimeField.type = 'date';
	    datetimeField2.type = 'date';
	
	    // 이전 값을 복원
	 //   datetimeField.value = datetimeFieldValue.split("T")[0];
	  //  datetimeField2.value = datetimeField2Value.split("T")[0];
	  } else {
	    datetimeField.type = 'datetime-local';
	    datetimeField2.type = 'datetime-local';
	
	 //   datetimeField.value = datetimeFieldValue;
	 //   datetimeField2.value = datetimeField2Value;
	  }
	  
	});


	//calendar1 , 2 연동
	//< 달
	var calendarButton = document.querySelector('#calendar .fc-prev-button');
	var calendar2Button = document.querySelector('#calendar2 .fc-prev-button');
	//> 달
	var calendarButton2 = document.querySelector('#calendar .fc-next-button');
	var calendar2Button2 = document.querySelector('#calendar2 .fc-next-button');
	//< 년
	var calendarButton3 = document.querySelector('#calendar .fc-prevYear-button');
	var calendar2Button3 = document.querySelector('#calendar2 .fc-prevYear-button');
	//> 년
	var calendarButton4 = document.querySelector('#calendar .fc-nextYear-button');
	var calendar2Button4 = document.querySelector('#calendar2 .fc-nextYear-button');
	//투데이
	var calendarButton5 = document.querySelector('#calendar .fc-today-button');
	var calendar2Button5 = document.querySelector('#calendar2 .fc-today-button');

	//버튼 동시 기능
	  calendarButton.addEventListener('click', function() {
	    var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton2.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button2.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton3.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button3.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton4.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button4.dispatchEvent(clickEvent);
	  });
	  
	  calendarButton5.addEventListener('click', function() {
	  	var clickEvent = new Event('click', { bubbles: true });
	    calendar2Button5.dispatchEvent(clickEvent);
	  });
	  


// 모달 창 닫기
$('a.close').click(function() {
	$('#calendarModal').css('display', 'none');
	$('#calendarDetail').css('display', 'none');
	
	$('div.modalwrap').remove();
});

//일정 등록
$("#calendarModal").submit(function(event) {
	     event.preventDefault();
	
	// 보안 토큰
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
    // 폼 요소 내의 필드 값을 가져오기
    var title = $('input[name="calendar_title"]').val();
    var color = $('input[name="color"]:checked').val();
    var start = $('input[name="calendar_date"]').val();
    var end = $('input[name="calendar_date2"]').val();
    var allday = $('input[name="allday"]:checked').val();
    var description = $('textarea[name="calendar_description"]').val();
	
	// 커스텀 컬러 선택
	if(color == 1){
		color = $('input[name="custom_color"]').val();
	}			
	
    $.ajax({
    	url: "calendaradd",
    	type: "POST",
    	data:{
    		title: title,
    		color: color,
    		start: start,
    		end: end,
    		allday: allday,
    		description: description
    	},
    	beforeSend: function(xhr)
		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
    	dataType: "json",
    	success: function(Result) {
        	if(Result == 1){
        		alert("일정이 등록 되었습니다.");
        		location.reload();
        	} else {
        		alert("일정 등록에 실패했습니다.");
        	}
    	}
    })

    // 예시: 콘솔에 데이터 출력
    console.log("제목:", title);
    console.log("색상:", color);
    console.log("시작 일자:", start);
    console.log("종료 일자:", end);
    console.log("하루 종일 여부:", allday);
    console.log("설명:", description);
            
});

//일정 수정
$("#calendar_update").click(function(event) {
	event.preventDefault();	  
	$('#calendarDetail').css('display', 'none');
	$("form#calendarModal").show();
	
	var calendar = document.getElementById('calendar_setting');
	calendar.textContent = "일정 수정";
	var calendar2 = document.getElementById('calendarModalFormButton');
	calendar2.value = "일정 수정";
	
});

//일정 삭제
$("#calendar_delete").click(function(event) {
		event.preventDefault();
});





/*
	//좌표
	addEventListener('click', (e) => {
    const x = e.clientX;
    const y = e.clientY;
    console.log("X 좌표: " + x);
    console.log("Y 좌표: " + y);
    // 모달 열기 등 다른 작업 수행
  });
		  
*/
});//ready