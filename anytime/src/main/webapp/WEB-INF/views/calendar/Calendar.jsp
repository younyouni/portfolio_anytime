<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <meta charset='utf-8' />
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/index.global.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/calendar/calendar.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/calendar.js"></script>

<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

</style>
</head>
<body>
  	<jsp:include page="../common/header.jsp" />
  	
  	<div class="container">
	  <div class="top">상단 내용</div>
	  <div class="content">
	  	<div class="left">
	  	
	  	</div>

	  	<div class="right">
	  		<div id='calendar'></div> 
	    </div>
	  </div>
	</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');
  
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  
  var calendar = new FullCalendar.Calendar(calendarEl, {
    headerToolbar: {
      left: 'prevYear,prev,next,nextYear today',
      center: 'title',
      right: 'dayGridMonth,dayGridWeek,dayGridDay'
    },
    initialView: 'dayGridMonth',
    navLinks: true,
    editable: true,
    dayMaxEvents: true,
    locale: 'ko',
    events: function(info, successCallback, failureCallback) {
      $.ajax({
        url: "calendarlist",
        method: 'POST',
        dataType: "json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(header, token);
        },
        success: function(Result) {
          var events = [];
          $.each(Result, function(index, data) {
            //if (data.pValue != null && data.pValue === 'private') {
              events.push({
            	id: data.id,
                title: data.title,
                start: data.start,
                end: data.end,
                description: data.description
                //color: 'red'
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
    }
  });
  calendar.render();
});
</script>
  

</body>
</html>