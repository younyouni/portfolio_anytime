<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">		
<head>
<title>애니타임</title>
<meta charset="utf-8">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/calculator/calculator.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/credit/extensions.underscore-min.js"></script>
<script>
$(document).ready(function(){
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	
	
	// 메뉴 선택시 기본 semester_detail이랑 semester_id 불러오기
	var semester_id
	$(".menu li").click(function(){
			var $li = $(this);
			if ($li.data('id')) {
				$li.addClass('active').siblings().removeClass('active');
			scrollToActiveMenu();
			}
			
		semester_id = $(this).data('id');
	$.ajax({
	url: 'getsemester_detail',
	type: 'GET',
	data: {semester_id: semester_id},
	success: function(data) {
		
		var totalCredit = 0;
        var totalMajorCredit = 0;
        var weightedSum = 0;
        var weightedMajorSum = 0;

     // 성적에 대응하는 숫자 값 맵
        var gradeValuesMap = {
            "A+":4.5, "A0":4.3, "A-":4,
            "B+":3.5, "B0":3.3, "B-":3,
            "C+":2.5, "C0":2.3, "C-":2,
            "D+":1.5, "D0":1.3, "D-":1,
            "F": 0, "P": 0, "NP": 0
        };

        $.each(data, function(i, detail) {
    		var credit = detail.credit;
    		
    		// 해당 성적에 대응하는 숫자 값 가져오기
    		var gradeValue = gradeValuesMap[detail.grade];

    		totalCredit += credit;
    		weightedSum += gradeValue * credit;

    		if (detail.major) {
    			totalMajorCredit += credit;
    			weightedMajorSum += gradeValue * credit;
    		}
    	});

    	var gpa = (totalCredit > 0) ? (weightedSum / totalCredit).toFixed(2) : "-";
    	
        var majorGpa = (totalMajorCredit > 0) ? (weightedMajorSum / totalMajorCredit).toFixed(2) : "-";

        $('dd.gpa').text(gpa);
        $('dd.major').text(majorGpa);
        $('dd.acquisition').text(totalCredit);
		
		
		
		
        // 받아온 데이터로 subject테이블 만들기 
        var tbody = $('.subjects tbody');
        tbody.empty();  // 기존 행들 삭제
        
        var grades = ["A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-", "D+", "D0", "D-", "F", "P", "NP"];
        
        $.each(data, function(i, detail) {
        $('h3').text(detail.semester_name);  
        	var gradeOptions = '';
            $.each(grades, function(i, grade) {
                gradeOptions += '<option value="' + grade + '"' + (grade === detail.grade ? ' selected' : '') + '>' + grade + '</option>';
            });
            var row = '<tr data-id="' + detail.semester_detail_id + '">' +
                '<td><input name="name" maxlength="50" value="' + detail.subject + '"></td>' +
                '<td><input name="credit" type="number" maxlength="4" min="0" value ="' + detail.credit + '" ></td>' +
                '<td><select name="grade"><option value="' + detail.grade + '" selected="selected">'+ gradeOptions + '</select></td>' +
                '<td><label><input name="major" type="checkbox"' +(detail.major ? ' checked' : '')+'><span></span></label></td>' +
            '</tr>';
            tbody.append(row);
	         });
            }
         });
	   });
	
  $(".menu li").first().trigger('click');
	
  // 더 입력하기 클릭
  $(".new").click(function() {
	    var tbody = $('.subjects tbody');
	    var grades = ["A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-", 
	                  "D+", "D0",  "D-", "F", "P", "NP"];
	    
	    var gradeOptions = '';
	    $.each(grades, function(i, grade) {
	        gradeOptions += '<option value="' + grade + '">' + grade + '</option>';
	    });
	    
	    var row = '<tr>' +
	        '<td><input name="name" maxlength="50"></td>' +
	        '<td><input name="credit" type="number" maxlength="4" min="0" value ="0"></td>' +
	        '<td><select name="grade">' + gradeOptions + '</select></td>' +
	        '<td><label><input name="major" type ="checkbox"><span></span></label></td>' +
	    '</tr>';
	    
	    tbody.append(row);
	});	
	
   
  // subject 항목 변경될때 
  function updateField(e) {
      var $target = $(e.target);
      var $row = $target.closest('tr');
      
      var semester_detail_id = $row.data('id');
      
      var subject = $row.find('input[name="name"]').val();
      var credit = $row.find('input[name="credit"]').val();
      var grade = $row.find('select[name="grade"]').val();
      var major = $row.find('input[name="major"]').prop('checked');
      
      $.ajax({
          url: 'updatesemester_detail',
          type: 'POST',
          data: {
        	  semester_detail_id: semester_detail_id,
              semester_id: semester_id,
              subject: subject,
              credit: credit,
              grade: grade,
              major: major
          },
          beforeSend: function(xhr)
          { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
            xhr.setRequestHeader(header, token);
          },
          success: function(data) {
              console.log(data);
          }
     });
  }
  // 각 입력 필드에 change 이벤트 핸들러 추가
  $('.subjects').on('change', 'input, select', updateField);

});
	
	function scrollToActiveMenu() {
        console.log("scrollTo");
        var $menu = $('.menu');
        if (!$menu.is(':has(li.active)')) {
            return false;
        }
        $menu.scrollLeft(0);
       var left = Math.floor($menu.find('li.active').position().left) - 50;
       $menu.scrollLeft(left);
   }

	
</script>


</head>
<body style="">

	<jsp:include page="../common/header.jsp" />

	<div id="container" class="calculator">
		<input type="hidden" id="userId" value="542509"> <input
			type="hidden" id="userRequiredCredit" value="150"> <input
			type="hidden" id="userGradeType" value="">
		<aside class="none">
			<div class="title">
				<a class="hamburger"></a>
				<h1>학점 계산기</h1>
			</div>
		</aside>
		<div class="section">
			<div class="chart">
				<article class="overview">
					<div class="column gpa">
						<h4>전체 평점</h4>
						<p class="value">3.81</p>
						<p class="total">/ 4.5</p>
					</div>
					<div class="column major">
						<h4>전공 평점</h4>
						<p class="value">3.9</p>
						<p class="total">/ 4.5</p>
					</div>
					<div class="column acquisition">
						<h4>취득 학점</h4>
						<p class="value">90</p>
						<p class="total" title="졸업 학점 설정">/ 150</p>
					</div>
				</article>

			</div>
			<div class="menu">
				<ol>
					<c:forEach var="semester" items="${semesters}">
						<li class="" data-id="${semester.semester_id}"><a>${semester.semester_name}</a></li>
					</c:forEach>
				</ol>
			</div>
			<table class="subjects">
				<caption>
					<h3></h3>
					<dl class="information">
						<dt>평점</dt>
						<dd class="gpa"></dd>
						<dt>전공</dt>
						<dd class="major"></dd>
						<dt>취득</dt>
						<dd class="acquisition"></dd>
					</dl>
					<a class="import" style="display: inline;">시간표 불러오기</a>
				</caption>
				<thead>
					<tr>
						<th class="name">과목명</th>
						<th class="credit">학점</th>
						<th class="grade">성적</th>
						<th class="major">전공</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
				<tfoot>
					<tr>
						<td colspan="4"><a class="new">더 입력하기</a> <a class="reset">초기화</a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<hr>
		<form id="importForm" class="modal"
			style="margin-left: -200px; margin-top: -93px; display: none;"
			data-gtm-form-interact-id="0">
			<a title="닫기" class="close"></a>
			<h3>내 시간표 가져오기</h3>
			<p>
				<label>시간표 선택</label> <select name="semester"
					data-gtm-form-interact-field-id="0"><option
						value="42644511">2023년 겨울학기 (시간표 1)</option>
					<option value="42662455">2023년 2학기 (시간표 1)</option>
					<option value="40480064">2023년 여름학기 (시간표 1)</option>
					<option value="40480070">2023년 1학기 (시간표 1)</option>
					<option value="42644989">2022년 1학기 (시간표 1)</option>
					<option value="42644991">2021년 여름학기 (시간표 1)</option>
					<option value="24372243">2021년 1학기 (시간표)</option>
					<option value="24372222">2020년 겨울학기 (시간표)</option>
					<option value="22897232">2020년 2학기 (플랜B)</option>
					<option value="42743666">2015년 여름학기 (시간표 1)</option>
					<option value="42785680">2015년 1학기 (시간표 1)</option>
					<option value="42644986">2013년 여름학기 (시간표 1)</option>
					<option value="42743665">2012년 여름학기 (시간표 1)</option>
					<option value="42644510">2011년 1학기 (시간표 1)</option></select>
			</p>
			<input type="submit" value="가져오기" class="button">
		</form>
		<form id="requiredCreditForm" class="modal">
			<a title="닫기" class="close"></a>
			<h3>졸업 학점 설정</h3>
			<p>
				<label>졸업 학점</label> <input type="number" name="required_credit"
					maxlength="3" class="text">
			</p>
			<input type="submit" value="저장" class="button">
		</form>
	</div>
	<div id="bottom">
		<ul class="links">
			<li><a href="/page/serviceagreement">이용약관</a></li>
			<li class="privacy"><a href="/page/privacy">개인정보처리방침</a></li>
			<li><a href="/page/youthpolicy">청소년보호정책</a></li>
			<li><a href="/page/rules">커뮤니티이용규칙</a></li>
			<li><a href="/notice">공지사항</a></li>
			<li><a href="/page/faq">문의하기</a></li>
			<li class="copyright"><a href="/">© 애니타임</a></li>
		</ul>
		<span>직업정보제공사업 신고번호 : J1204020230008</span>
	</div>

</body>
</html>