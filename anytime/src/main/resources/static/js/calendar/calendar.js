document.addEventListener('DOMContentLoaded', function() {
  	var calendarEl = document.getElementById('calendar');
  
	
  	var calendar2El = document.getElementById('calendar2');
    
    
   var calendar2 = new FullCalendar.Calendar(calendar2El, {
   headerToolbar: {
	      left: 'title prevYear,nextYear',
    },
   locale: 'ko',
   
  });
    

      
  
  
  var calendar = new FullCalendar.Calendar(calendarEl, {
	  customButtons: {
		    myCustomButton: {
		      text: '일정 등록',
		      click: function() {
		      		$("form#calendarModal").show();
		      		$('form#calendarModal').css('display', 'block');
					$('form#calendarModal').before('<div class="modalwrap"></div>');

		      }
		    }
		  },
	headerToolbar: {
	      left: 'prevYear,prev,next,nextYear today',
	      center: 'title myCustomButton',
	      right: 'dayGridMonth,dayGridWeek,dayGridDay',
	      
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
                type: data.type,
                end: data.end,
                description: data.description,
                borderColor: data.color,
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
		document.getElementById('all_check').value = formattedDate;
		
    },
    
    eventClick: function(info) {
	     var event = info.event;
		 console.log('클릭한 이벤트 고유 ID:', event.id);
	 	 console.log('클릭한 이벤트 제목:', event.title);
		 console.log('클릭한 이벤트 시작 시간:', event.start);
		 console.log('클릭한 이벤트 타입:', event.type);
		 console.log('클릭한 이벤트 종료 시간:', event.end);
		 console.log('클릭한 이벤트 설명:', event.description);
		 console.log('클릭한 이벤트 색상:', event.color);
		 console.log('클릭한 이벤트 하루 종일:', event.allDay);
		 console.log('클릭한 이벤트 이건뭐야:', event.extendedProps);
		 
		console.log('이건됨? = ' , event.extendedProps.type);
		
		$("form#calendarDetail").show();
  		$('form#calendarDetail').css('display', 'block');
		$('form#calendarDetail').before('<div class="modalwrap"></div>');
     	
     },
  
  });
  calendar.render();
  calendar2.render();
  
	document.getElementById('allday_check').addEventListener('change', function() {
	  var datetimeField = document.getElementById('all_check'); // 날짜 및 시간 입력 필드
	  var datetimeField2 = document.getElementById('all_check2'); // 날짜 및 시간 입력 필드
	
	  // 기존 값 저장
	  var datetimeFieldValue = datetimeField.value;
	  var datetimeField2Value = datetimeField2.value;
	
	  // 체크박스가 체크된 경우 datetime 입력 필드를 날짜 선택 모드로 변경
	  if (this.checked) {
	    datetimeField.type = 'date';
	    datetimeField2.type = 'date';
	
	    // 이전 값을 복원
	    datetimeField.value = datetimeFieldValue.split("T")[0];
	    datetimeField2.value = datetimeField2Value.split("T")[0];
	  } else {
	    datetimeField.type = 'datetime-local';
	    datetimeField2.type = 'datetime-local';
	
	    datetimeField.value = datetimeFieldValue;
	    datetimeField2.value = datetimeField2Value;
	  }
	});
	
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
	  
	
});


$(document).ready(function() {
	
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
        var type = $('input[name="calendar_type"]').val();
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
        		type: type,
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
        console.log("범주:", type);
        console.log("색상:", color);
        console.log("시작 일자:", start);
        console.log("종료 일자:", end);
        console.log("하루 종일 여부:", allday);
        console.log("설명:", description);
            
});





});//ready