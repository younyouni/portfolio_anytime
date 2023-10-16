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
      navLinks: true, // can click day/week names to navigate views
      editable: true,
      dayMaxEvents: true, // allow "more" link when too many events
      locale: 'ko',
      events: {
          url: "calendarlist", // 데이터를 가져올 URL을 지정
          method: 'POST', // GET 또는 POST를 사용하여 데이터를 가져옴
          beforeSend: function(xhr)
	  		{	// 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
	  			xhr.setRequestHeader(header, token);
	  		},
  		dataType: "json",
        failure: function() {
            // 데이터 가져오기 실패 시 처리
            alert('Failed to fetch events!');
          }
        }
 
    });

    calendar.render();
  });
  

</script>

</body>
</html>