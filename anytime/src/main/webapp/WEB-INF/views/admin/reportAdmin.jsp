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
		$("div.center-block").hide();
		$("div.reportadmin div.menu a").click(function() {
			$("div.reportadmin a").removeClass("active");
			$(this).addClass("active");
		});

		$(".menu li.reportrequest").click(function() {
			$("table.request").show();
			$("table.list").hide();
		});

		$(".menu li.reportlist").click(function() {
			$("table.list").show();
			$("table.request").hide();
			$("div.center-block").show();
		});

		$('button.process').click(function() {
			event.preventDefault();
			$('form#reportprocess').css('display', 'block');
			$('form#reportprocess').before('<div class="modalwrap"></div>');
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
			$('form#reportInfo').css('display', 'block');
			$('form#reportInfo').before('<div class="modalwrap"></div>');

		});
	});
</script>
<body>
	<div id="wrapper">
		<jsp:include page="../common/left_admin.jsp" />
		<div class="reportadmin">
			<div class="menu">
				<ol>
					<li class="reportrequest menu"><a class="active">신고 처리 요청</a></li>
					<li class="reportlist menu"><a>신고 목록</a></li>
				</ol>
				<div class="order">
					<label> <input type="radio" name="latest" value="1" checked>
						최신순
					</label> <label> <input type="radio" name="mostReported" value="2">
						신고순
					</label>
				</div>
			</div>
			<div class="section">
				<table class="request">
					<thead>
						<tr>
							<th colspan="4"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="4">
								<article>
									<a class="article" href="detail?post_id=${post.POST_ID}"> <img
										src="/anytime/resources/image/common/profile.png"
										class="picture">
										<div class="profile">
											<span class="large">우니우닝</span> <span class="large">10/15
												21:02</span>
										</div>
										<button class="process">
											처리 <img src="/anytime/resources/image/admin/process.png"
												class="process">
										</button> <br>
										<h2 class="medium">게시판 이름</h2>
										<p class="small">게시물 내용게시물 내용게시물 내용게시물 내용게시물 내용게시물
											내용sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
											게시물 내용게시물 내용게시물 내용게시물 내용게시물 내용</p>
										<p class="last">게시판 이름</p>
										<ul class="status">
											<li class="attach">1${fileCount}</li>
											<li title="공감" class="vote">1${post.LIKE_COUNT}</li>
											<li title="댓글" class="comment">1${post.COMMENT_COUNT}</li>
										</ul>
									</a>
								</article>
							</td>
						</tr>
						<tr>
							<td>신고 횟수 : 회</td>
							<td>신고사유</td>
							<td>신고자 :</td>
							<td>시간</td>
						</tr>
						<tr>
							<td colspan="4">
								<article>
									<a class="article" href="detail?post_id=${post.POST_ID}"> <img
										src="/anytime/resources/image/common/profile.png"
										class="picture">
										<div class="profile">
											<span class="large">우니우닝</span> <span class="large">10/15
												21:02</span>
										</div>
										<button class="process">
											처리 <img src="/anytime/resources/image/admin/process.png"
												class="process">
										</button> <br>
										<h2 class="medium">게시판 이름</h2>
										<p class="small">게시물 내용게시물 내용게시물 내용게시물 내용게시물 내용게시물
											내용sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
											게시물 내용게시물 내용게시물 내용게시물 내용게시물 내용</p>
										<p class="last">게시판 이름</p>
										<ul class="status">
											<li class="attach">1${fileCount}</li>
											<li title="공감" class="vote">1${post.LIKE_COUNT}</li>
											<li title="댓글" class="comment">1${post.COMMENT_COUNT}</li>
										</ul>
									</a>
								</article>
							</td>
						</tr>
						<tr>
							<td>신고 횟수 : 회</td>
							<td>신고사유</td>
							<td>신고자 :</td>
							<td>시간</td>
						</tr>
					</tbody>
				</table>
				<table class="list" style="display: none;">
					<thead>
						<tr>
							<th class="rownum"></th>
							<th class="writer">작성자</th>
							<th class="count">신고 횟수</th>
							<th class="reason">신고 사유</th>
							<th class="reporter">신고자</th>
							<th class="date">신고 날짜</th>
							<th class="status">처리상태</th>
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
							<td class="continue">처리 진행중</td>
						</tr>
						<tr>
							<td>2</td>
							<td>학교이름</td>
							<td>유형</td>
							<td>게시판이름</td>
							<td>게시판 개설 목적</td>
							<td>운영자</td>
							<td>처리완료</td>
						</tr>
					</tbody>
				</table>
			</div>
			<%-------------------------------- ▼페이지네이션▼ --------------------------------%>
		</div>
		<jsp:include page="../common/footer2.jsp" />
	</div>
	<div id="container">
		<form id="reportprocess" class="modal"
			style="margin-left: -200px; margin-top: -220.5px; display: none; width: 500px;">
			<a title="닫기" class="close"></a> <span class="new">신고 처리</span>
			<p></p>
			<table>
				<tr>
					<td>게시물</td>
					<td class="box">
						<div class="order">
							<label> <input type="radio" name="board_id" value="1">
								반려
							</label> <label> <input type="radio" name="board_id" value="1">
								게시물 비공개
							</label>
						</div>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>비공개 사유</td>
					<td colspan="3" class="box"><input type="text" readOnly
						value="게게시피" style="width: 345px;"></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td class="box"><input type="text" class="first" readOnly
						value="x대"></td>
					<td><div class="order">
							<label> <input type="radio" name="board_id" value="1">
								반려
							</label> <label> <input type="radio" name="board_id" value="1">
								정지
							</label>
						</div></td>
				</tr>
				<tr>
					<td>정지 사유</td>
					<td colspan="5" class="box"><input type="text" class="first"
						readOnly value="x대" style="width: 345px;"></td>
				</tr>
			</table>
			<a class="button" data-toggle="modal">확인</a>
		</form>
		<form id="reportInfo" class="modal"
			style="margin-left: -240px; margin-top: -220.5px; display: none; width: 550px;">
			<a title="닫기" class="close"></a> <span class="info">신고 내용 상세보기</span>
			<p></p>
			<table style="width: 500px;">
				<tr>
					<td>작성자</td>
					<td class="box" style="width: 0px;"><input type="text"
						class="box" readOnly value="누가" style="width: 100px;"></td>
					<td style="width: 75px; padding: 0 7px 0 10px;">신고 날짜</td>
					<td class="box" colspan="2"><input type="text" class="right"
						readOnly value="몇월몇일" style="width: 100px;"></td>
					<td style="width: 50px; padding: 0 2px 0 6px;">신고자</td>
					<td class="box"><input type="text" class="first right"
						readOnly value="누구" style="width: 80px; margin-right: 6px;"></td>
				</tr>
				<tr>
					<td>내 용</td>
					<td colspan="6" class="box"><textarea readOnly
							style="width: 420px;">목적
						</textarea></td>
				</tr>
				<tr>
					<td>신고사유</td>
					<td colspan="6" class="box"><input type="text" class="full"
						readOnly value="비속어사용" style="width: 420px;"></td>
				</tr>
				<tr>
					<td>처리 상태</td>
					<td colspan="4" class="box"><input type="text" class="first"
						readOnly value="누구" style="width: 270px;"></td>
					<td style="padding: 0;">담당자</td>
					<td colspan="2" class="box"><input type="text" class="right"
						readOnly value="몇월몇일" style="width: 80px; margin-right: 10px;"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>