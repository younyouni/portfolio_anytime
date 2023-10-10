<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<title>애니타임</title>
<meta charset="utf-8">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.partial.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/calculator/calculator.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery-1.10.2.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/credit/extensions.underscore-min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/credit/common.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery.flot.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery.flot.pie.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery.flot.resize.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/credit/calculator.js"></script>
</head>
<style>
#placeholder {
	width: 500px;
	height: 300px;
}
</style>
<div id="placeholder">
	<div class="flot-text"
		style="position: absolute; inset: 0px; font-size: smaller; color: rgb(84, 84, 84);">
		<div class="flot-x-axis flot-x1-axis xAxis x1Axis"
			style="position: absolute; inset: 0px; display: block;"></div>
		<div class="flot-y-axis flot-y1-axis yAxis y1Axis"
			style="position: absolute; inset: 0px; display: block;">
			<div
				style="position: absolute; top: 70px; font: 400 12px/14px&amp; amp; amp; amp; quot; 맑은 고딕&amp;amp; amp; amp; quot; , 돋움 , &amp;amp; amp; amp; quot; Apple SD Gothic Neo&amp;amp; amp; amp; quot; , tahoma; color: rgb(166, 166, 166); left: 0px; text-align: right;">2.0</div>
		</div>
	</div>
	<canvas class="flot-overlay" width="328" height="116"
		style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 365px; height: 129px;"></canvas>
</div>

<script type="text/javascript">
	$(function() {
		var data = [[3,0],[9,8],[10,8] ];
		$.plot($(".flot-text"), [ data ]);
	});
</script>
