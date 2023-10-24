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
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<div
			style="width: 300px; height: 300px; margin-top: 200px; margin-left: 150px;">
			<canvas id="pieChart"></canvas>
		</div>
		<div
			style="width: 300px; height: 300px; margin-top: 200px; margin-left: 150px;">
			<canvas id="lineChart"></canvas>
		</div>
	</div>
</body>
<script>
    var reportReason = [
        <c:forEach items="${reportCount}" var="reportData">
            '${reportData.REASON}',
        </c:forEach>
    ];

    var reportReason_text = reportReason.map(function(value) {
        if (value === '1') {
            return '게시판 성격에 부적절함';
        } else if (value === '2') {
            return '욕설/비하';
        } else if (value === '3') {
            return '음란물/불건전한 만남 및 대화';
        } else if (value === '4') {
            return '상업적 광고 및 판매';
        } else if (value === '5') {
            return '유출/사칭/사기';
        } else if (value === '6') {
            return '낚시/놀람/도배';
        } else if (value === '7') {
            return '정당/정치인 비하 및 선거운동';
        }
    });

    var reportCount = [
        <c:forEach items="${reportCount}" var="reportData">
            ${reportData.COUNT_BY_REASON},
        </c:forEach>
    ];

    var pieCanvas = document.getElementById('pieChart');
    var pieChart = new Chart(pieCanvas, {
        type: 'pie',
        data: {
            labels: reportReason_text,
            datasets: [{
                data: reportCount,
                backgroundColor: [
                    '#5470c6', '#91cc75', '#fac858', '#ee6666',
                    '#73c0de', '#3ba272', '#fc8452'
                ]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            legend: {
                display: false,
            }
        }
    });

    // Line Chart
    const lineCanvas = document.getElementById('lineChart');
    const lineChart = new Chart(lineCanvas, {
        type: 'line',
        data: {
            labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9'],
            datasets: [
                {
                    label: 'Dataset 1',
                    backgroundColor: 'red',
                    borderColor: 'red',
                    data: [10, 41, 35, 51, 49, 62, 69, 91, 148],
                    fill: false
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    type: 'category',
                    display: true,
                }
            }
        }
    });
</script>
</html>
