<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <meta charset='utf-8' />
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/index.global.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/calendar/calendar.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/calendar.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/modal.css">

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
	
	<form id="calendarModalForm" class="modal" data-gtm-form-interact-id="0" style="margin-left: -200px; margin-top: -92.5px; display: none;">
		<a title="닫기" class="close"></a>
		<h3>일정 등록</h3>
                title: data.title,
                start: data.start,
                end: data.end,
                description: data.description
		<p>
			<label>일정 제목</label> 
			<input type="text" class="text" name="calendar_title">
		</p>
		<p>
			<label>일정 날짜</label> 
			<input type="date" class="text" name="calendar_date">
		</p>
		<p>
			<label>설명</label> 
			<input type="text" class="text" name="calendar_title">
		</p>

		<input type="submit" id="calendarModalFormButton" value="일정 등록" class="button">
	</form>

</body>
</html>