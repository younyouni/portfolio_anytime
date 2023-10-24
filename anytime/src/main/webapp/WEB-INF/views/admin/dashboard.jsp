<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header_admin.jsp" />
<head>
<title>관리자 페이지 - 애니타임</title>
<!-- 
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/total.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/admin/changePwd.css">
 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/admin.css">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawReportReasonChart);
      google.charts.setOnLoadCallback(drawVisualization);

      function drawReportReasonChart() {

        var reportReasonData = google.visualization.arrayToDataTable([
          ['신고 사유', '일별 신고 건수'],
        	<c:forEach items="${reportCount}" var="reportData" varStatus="loop">
          	[ <c:choose>
             <c:when test ="${reportData.REASON eq '1'}">'게시판 성격에 부적절함'</c:when>
             <c:when test ="${reportData.REASON eq'2'}">'욕설/비하'</c:when>
             <c:when test ="${reportData.REASON eq'3'}">'음란물/불건전한 만남 및 대화'</c:when>
             <c:when test ="${reportData.REASON eq'4'}">'상업적 광고 및 판매'</c:when>
             <c:when test ="${reportData.REASON eq'5'}">'유출/사칭/사기'</c:when>
             <c:when test ="${reportData.REASON eq'6'}">'낚시/놀람/도배'</c:when>
             <c:when test ="${reportData.REASON eq'7'}">'정당/정치인 비하 및 선거운동'</c:when>
             </c:choose>
        	  , ${reportData.COUNT_BY_REASON}] <c:if test="${!loop.last}">, </c:if>
          </c:forEach>
        ]);

        var reportReasonOptions = {
          title: 'My Daily Activities'
        };

        var reportReasonChart = new google.visualization.PieChart(document.getElementById('piechart'));

        reportReasonChart.draw(reportReasonData, reportReasonOptions);
      }
      
      ////////////////////////////////////////////////////////////////////////////////////////////////

     function drawVisualization() {
        // Some raw data (not necessarily accurate)
        var data = google.visualization.arrayToDataTable([
          ['Day', 'Bolivia', 'Ecuador','total'],
          ['23/10/23',  165,      938, 800],
          ['23/10/24',  135,      1120,700]
        ]);

        var options = {
          title : 'Monthly Coffee Production by Country',
          vAxis: {title: 'Cups'},
          hAxis: {title: 'Month'},
          seriesType: 'bars',
          series: {2: {type: 'line'}}
        };

        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      
     ////////////////////////////////////////////////////////////////////////////////////////////////
    </script>
  </head>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		  <div id="piechart" style="width: 1000px; height: 500px;"></div>
		   <div id="chart_div" style="width: 1000px; height: 500px;"></div>
	</div>
</body>
<script>
   
</script>
</html>
