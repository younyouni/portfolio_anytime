<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header_admin.jsp" />
<head>
<title>관리자 페이지 - 애니타임</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/common.css">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/admin/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common/modal.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>

</head>
<script>
	$(function() {
		$("form#searchBoard").hide();
		$("div.center-block").hide();
		$("div.boardadmin div.menu a").click(function() {
			$("div.boardadmin a").removeClass("active");
			$(this).addClass("active");
		});

		$(".menu li.boardrequest").click(function() {
			$("table.request").show();
			$("table.list").hide();
			$("form#searchBoard").hide();
		});

		$(".menu li.boardlist").click(function() {
			$("table.list").show();
			$("table.request").hide();
			$("form#searchBoard").show();
			$("div.center-block").show();
		});

		$('a.approval').click(function() {
			$('form#approvalreview').css('display', 'block');
			$('form#approvalreview').before('<div class="modalwrap"></div>');
		});

		// 모달 닫기 버튼을 클릭할 때
		$('a.close').click(function() {
			$('form.modal').css('display', 'none');
			$('div.modalwrap').remove();
		});

		$('#approvalreview > input.button').click(function() {
			let checkreason = $('input[name="rejectreason"]').val();
			if ($.trim(checkreason) == "") {
				alert("게시판 승인 거부 사유를 입력하세요.");
				$('input[name="rejectreason"]').val('').focus();
				return false;
			}
		});

		$('.list tbody td:not(.switch)').click(function() {
			$('form#boardInfo').css('display', 'block');
			$('form#boardInfo').before('<div class="modalwrap"></div>');

		});
	});
</script>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<div class="boardadmin">
			<div class="menu">
				<ol>
					<li class="boardrequest"><a class="active">게시판 승인 요청</a></li>
					<li class="boardlist"><a>게시판 목록</a></li>
					<li>
						<form id="searchBoard" class="search"
							action="/anytime/post/search" method="get" accept-charset="UTF-8">
							<input type="hidden" name="board_id" value="1"> <select
								name="search_field" id="search_field">
								<option value="0">전체</option>
								<option value="1">학교명</option>
								<option value="2">게시판명</option>
							</select> <input name="search_word" id="search_word"
								placeholder="검색어를 입력하세요." value="" type="text">
							<button type="button" id="searchButton">&nbsp;&nbsp;</button>
						</form>
					</li>
				</ol>
			</div>
			<div class="section">
				<table class="request">
					<thead>
						<tr>
							<th class="toggle"></th>
							<th class="school">학교명</th>
							<th class="type">유형</th>
							<th class="name">게시판 이름</th>
							<th class="purpose">게시판 개설 목적</th>
							<th class="manager">운영자</th>
							<th class="approval"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><label class="switch"> <input type="checkbox">
									<span class="slider round"></span>
							</label></td>
							<td>학교이름</td>
							<td>유형</td>
							<td>게시판이름</td>
							<td>게시판 개설 목적</td>
							<td>운영자</td>
							<td class="complete"><p>승인완료</p></td>
						</tr>
						<tr>
							<td><label class="switch"> <input type="checkbox">
									<span class="slider round"></span>
							</label></td>
							<td>학교이름</td>
							<td>유형</td>
							<td>게시판이름</td>
							<td>게시판 개설 목적</td>
							<td>운영자</td>
							<td class="pending"><p>
									승인보류<a class="approval"><img
										src="${pageContext.request.contextPath}/resources/image/admin/approval.cancel.png"></a>
								</p></td>
						</tr>
						<tr>
							<td><label class="switch"> <input type="checkbox">
									<span class="slider round"></span>
							</label></td>
							<td>학교이름</td>
							<td>유형</td>
							<td>게시판이름</td>
							<td>게시판 개설 목적</td>
							<td>운영자</td>
							<td class="reject"><p>승인거부</p></td>
						</tr>
					</tbody>
				</table>
				<table class="list" style="display: none;">
					<thead>
						<tr>
							<th class="rownum"></th>
							<th class="school">학교명</th>
							<th class="type">유형</th>
							<th class="name">게시판 이름</th>
							<th class="purpose">게시판 개설 목적</th>
							<th class="manager">운영자</th>
							<th class="toggle"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>학교이름</td>
							<td>유형</td>
							<td>게시판이름</td>
							<td>게시판 개설 목적</td>
							<td>운영자</td>
							<td class="switch"><label class="switch"> <input
									type="checkbox"> <span class="slider round"></span>
							</label></td>
						</tr>
					</tbody>
				</table>
			</div>
			<%-------------------------------- ▼페이지네이션▼ --------------------------------%>
		</div>
		<jsp:include page="../common/footer2.jsp" />
	</div>
	<div id="container">
		<form id="approvalreview" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none;">
			<a title="닫기" class="close"></a> <span class="new">게시판 승인 심사</span>
			<p></p>
			<label id="rejectreason">승인 거부 사유</label> <input type="text"
				class="info" name="reason" placeholder="승인 거부 사유"
				class="text rejectreason"> <input type="submit" value="확인"
				class="button">
		</form>
		<form id="boardInfo" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none; width: 490px;">
			<a title="닫기" class="close"></a> <span class="info">게시판 정보</span>
			<p></p>
			<table>
				<tr>
					<td>학교명</td>
					<td class="box"><input type="text" class="first" readOnly
						value="x대"></td>
					<td style="padding: 0 20px;">유형</td>
					<td class="box"><input type="text" class="first" readOnly
						value="단체"></td>
					<td style="padding: 0 20px;">운영자</td>
					<td class="box"><input type="text" class="first" readOnly
						value="youni"></td>
				</tr>
				<tr>
					<td>게시판 이름</td>
					<td colspan="5" class="box"><input type="text" readOnly
						value="게게시피" style="width: 360px;"></td>
				</tr>
				<tr>
					<td>개설 목적</td>
					<td colspan="5" class="box"><textarea readOnly>목적
						</textarea></td>
				</tr>
			</table>
			<label class="switch"> <input type="checkbox"> <span
				class="slider round"></span>
			</label>
		</form>
	</div>
</body>
</html>