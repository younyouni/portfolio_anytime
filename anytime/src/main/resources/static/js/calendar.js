document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');
  
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  
  var calendar = new FullCalendar.Calendar(calendarEl, {
	  customButtons: {
		    myCustomButton: {
		      text: '일정 등록',
		      click: function() {
		      		$("form#calendarModalForm").show();
		      		$('form#calendarModalForm').css('display', 'block');
					$('form#calendarModalForm').before('<div class="modalwrap"></div>');
					
					$('a.close').click(function() {
						$('#calendarModalForm').css('display', 'none');

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
    events: function(info, successCallback, failureCallback) {
      $.ajax({
        url: "calendarlist",
        dataType: "json",
        success: function(Result) {
          var events = [];
          $.each(Result, function(index, data) {
            //if (data.pValue != null && data.pValue === 'private') {
              events.push({
            	id: data.id,
                title: data.title,
                start: data.start,
                type: data.type,
                end: data.end,
                description: data.description,
                color: 'red'
              });
            //}
          });
          console.log(events)
          successCallback(events);
        },
        error: function(Result) {
          alert("에러가 발생했습니다.");
          failureCallback(Result);
        }
      });
    },
  
  });
  calendar.render();
});