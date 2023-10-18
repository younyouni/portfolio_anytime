document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');
  
  var calendar = new FullCalendar.Calendar(calendarEl, {
	  customButtons: {
		    myCustomButton: {
		      text: '일정 등록',
		      click: function() {
		      		$("form#calendarModal").show();
		      		$('form#calendarModal').css('display', 'block');
					$('form#calendarModal').before('<div class="modalwrap"></div>');
					
					$('a.close').click(function() {
						$('#calendarModal').css('display', 'none');

						$('div.modalwrap').remove();
					});
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
    eventRender: function(info) {
 		
    },
    
/*
    events: [
          {
          		id: '1',
                title: '테스트1',
                start: '2023-10-18T12:09',
                type: '테스트타입1',
                end: '2023-10-18T18:00',
                description: '18일 12시 부터 6시까지',
                color: '#E60000'
          },
          {
          		id: '2',
                title: '테스트2',
                start: '2023-10-20T12:00',
                type: '테스트타입2',
                description: '20일 12시 하루만',
                color: '#1C06E6'
          },
          {
          		id: '3',
                title: '테스트3',
                start: '2023-10-23T13:00',
                type: '테스트타입3',
                end: '2023-10-23T17:00',
                description: '23일 1시 부터 5시까지',
                color: '#0DE606'
          },
          {
          		id: '4',
                title: '테스트4',
                start: '2023-10-25T14:00',
                type: '테스트타입4',
                end: '2023-10-26T18:00',
                description: '25일 2시 부터 6시까지 하루종일?',
                color: '#0DE606',
                allday: 'true'
          },
          ]
*/ 

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
                color: data.color,
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

  
  });
  calendar.render();
});


$(document).ready(function() {
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
        var description = $('input[name="calendar_description"]').val();
		
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

});