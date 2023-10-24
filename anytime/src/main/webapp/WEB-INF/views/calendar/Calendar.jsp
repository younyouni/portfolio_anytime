<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <meta charset='utf-8' />
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/calendar/index.global.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/calendar/calendar.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/calendar/calendar.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/calendar/jscolor.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/calendar/moment.js"></script>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/modal.css">
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
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
	  <div class="content">
	  	<div class="left">
	  		<div id='calendar2'></div> 
	  	</div>

	  	<div class="right">
	  		<div id='calendar'></div> 
	    </div>
	  </div>
	</div>
	
	<form id="calendarModal" class="modal" style="margin-left: -300px; margin-top: -400px; display: none;">
		<a title="닫기" class="close"></a>
		<span id="calendar_setting">일정 추가</span>       
		<p>
			<label id="calendar_label">일정 제목</label> 
			<input type="text" class="text" name="calendar_title" required>
		</p>
		<p>
			<label id="calendar_label">구분</label>
			<input id="color" name="custom_color" value="#7869e6" data-jscolor="{}">
			<input type="radio" name="color" id="color_check" value="1" style="width: 10%; height: 20px;" checked>
		</p>
			빨강<input type="radio" name="color" id="color_check" value="#FF0000" style="width: 10%; height: 20px;">
			주황<input type="radio" name="color" id="color_check" value="#FFA500" style="width: 10%; height: 20px;">
			노랑<input type="radio" name="color" id="color_check" value="#FFFF00" style="width: 10%; height: 20px;">
			파랑<input type="radio" name="color" id="color_check" value="#0000FF" style="width: 10%; height: 20px;">
		<p>
			<label id="calendar_label">일정 시작</label> 
			<input type="datetime-local" class="text" name="calendar_date" id="all_check" required>
			<label id="calendar_label">일정 종료</label> 
			<input type="datetime-local" class="text" name="calendar_date2" id="all_check2">
			종일
			<input type="checkbox" name="allday" id="allday_check" value="1" style="width: 10%; height: 20px;">
		</p>
		<p>
			<label id="calendar_label">설명</label> 
			<textarea type="text" class="text" name="calendar_description"></textarea>
		</p>
		<input type="submit" id="calendarModalFormButton" value="일정 등록" class="button">
	</form>
	
	<form id="calendarUpdateModal" class="modal" style="margin-left: -300px; margin-top: -400px; display: none;">
		<a title="닫기" class="close"></a>
		<span id="calendar_setting">일정 수정</span>       
		<p>
			<label id="calendar_label">일정 제목</label> 
			<input type="text" class="text" id="calendar_title_update" required>
		</p>
		<p>
			<label id="calendar_label">구분</label>
			<input id="color_update" name="custom_color_update" value="#7869e6" data-jscolor="{}">
			<input type="radio" name="color_update" id="color_check" value="1" style="width: 10%; height: 20px;" checked>
		</p>
			빨강<input type="radio" name="color_update" id="color_check" value="#FF0000" style="width: 10%; height: 20px;">
			주황<input type="radio" name="color_update" id="color_check" value="#FFA500" style="width: 10%; height: 20px;">
			노랑<input type="radio" name="color_update" id="color_check" value="#FFFF00" style="width: 10%; height: 20px;">
			파랑<input type="radio" name="color_update" id="color_check" value="#0000FF" style="width: 10%; height: 20px;">
		<p>
			<label id="calendar_label">일정 시작</label> 
			<input type="datetime-local" class="text" name="calendar_date_update" id="all_check_update" required>
			<label id="calendar_label">일정 종료</label> 
			<input type="datetime-local" class="text" name="calendar_date2_update" id="all_check2_update2">
			종일<input type="checkbox" name="allday_update" id="allday_check_update" value="1" style="width: 10%; height: 20px;">
		</p>
		<p>
			<label id="calendar_label">설명</label> 
			<textarea type="text" class="text" id="calendar_description_update"></textarea>
		</p>
		<input type="submit" id="calendarUpdateButton" value="일정 수정" class="button">
	</form>
	
	
	<form id="calendarDetail" class="modal" style="margin-left: -300px; margin-top: -200px; display: none;">
		<a title="닫기" class="close"></a>
		<p>
		<span id="calendar_detail_title"></span>
		</p>
		<p>
		<div id="calendar_detail_type"></div>
		</p>
		<p>
		<div id="calendar_detail_date"></div>
		</p>
		<p>
		<textarea type="text" class="text" id="calendar_detail_description" readonly></textarea>
		</p>
		<input type="button" id="calendar_update" value="수정" class="button">
		<input type="button" id="calendar_delete" value="삭제" class="button">
	</form>

</body>
</html>