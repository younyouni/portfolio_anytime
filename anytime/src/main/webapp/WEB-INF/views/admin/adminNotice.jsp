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
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postlist.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/post/postwrite.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/post/write.js"></script>
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
		<div id="container" class="article" style="margin-top: 85px;">
			<input type="hidden" id="isUser" value="1"> <input
				type="hidden" id="boardId" value="1">
			<aside class="none">
				<div class="title">
					<a class="hamburger"></a>
					<h1>
						<a href="/1">공지사항</a>
					</h1>
				</div>
			</aside>
			<div class="wrap title">
				<h1>
					<a href="/1">공지사항</a>
				</h1>
				<hr>
			</div>
			<div class="wrap articles" id="writeBoardContainer">
				<label class="postwriteform"> <!-- style="display: none;" -->
					<%-- <jsp:include page="postlist_write.jsp" /> --%>
				</label> <a id="writeArticleButton">새 글을 작성해주세요! <img
					src="${pageContext.request.contextPath}/resources/image/post/write.button.png">
				</a>
				<div class="wrap bubbles none"></div>
				<article class="list">
					<a class="article" href="/1/v/40942051"><div class="desc">
							<h2 class="medium bold">3/26(월) 로그인 해제 (사용자 인증 정보 오류) 안내</h2>
							<p class="medium">
								안녕하세요, 에브리타임입니다.<br>3월 26일(월) 15:45분 경 "사용자 인증 정보" 문구와 함께
								로그인이 해제되는 오류가 있었습니다.
							</p>
							<div class="info">
								<ul class="status">
									<li title="공감" class="vote">11</li>
								</ul>
								<time class="small">03/26</time>
								<h3 class="admin bold small">애니타임</h3>
							</div>
							<hr>
						</div> <input type="hidden" name="40942051_comment_anonym" value="0"></a>
					<div class="comments"></div>
				</article>

				<div class="wrap articles">

					<div class="clearBothOnly"></div>
					<div class="pagination">
						<a href="/1/p/1" class="prev">이전</a><a href="/1/p/3" class="next">다음</a>
					</div>
				</div>
				<hr>


			</div>
			<form id="abuseForm" class="modal">
				<a title="닫기" class="close"></a>
				<h3>신고 사유 선택</h3>
				<ul>
					<li><a data-reason="1">게시판 성격에 부적절함</a></li>
					<li><a data-reason="2">욕설/비하</a></li>
					<li><a data-reason="3">음란물/불건전한 만남 및 대화</a></li>
					<li><a data-reason="4">상업적 광고 및 판매</a></li>
					<li><a data-reason="5">유출/사칭/사기</a></li>
					<li><a data-reason="6">낚시/놀람/도배</a></li>
					<li><a data-reason="7">정당/정치인 비하 및 선거운동</a></li>
				</ul>
			</form>
			<form id="manageMoimForm" class="modal">
				<a title="닫기" class="close"></a>
				<h3>게시판 설정</h3>
				<p>
					<label>소개</label> <input type="text" name="info" class="text">
				</p>
				<p class="hide">
					<label>인기 글 금지</label> <input type="checkbox"
						id="manageMoimForm_is_not_selected_hot_article"
						name="is_not_selected_hot_article"><label
						for="manageMoimForm_is_not_selected_hot_article" class="checkbox">글이
						공감을 받아도 인기 글 및 HOT 게시물에 선정되지 않음</label>
				</p>
				<input type="button" value="게시판 양도" class="button light floatLeft">
				<input type="button" value="게시판 삭제" class="button light floatLeft">
				<input type="submit" value="수정" class="button">
			</form>
			<form id="transferMoimForm" class="modal"
				data-gtm-form-interact-id="0">
				<a title="닫기" class="close"></a>
				<h3>게시판 양도</h3>
				<p>
					<label>양도인 비밀번호</label> <input type="password"
						name="transferer_password" class="text"
						data-gtm-form-interact-field-id="1">
				</p>
				<p>
					<label>피양도인 아이디</label> <input type="text" name="transferee_userid"
						class="text" data-gtm-form-interact-field-id="0">
				</p>
				<input type="submit" value="양도 요청" class="button">
			</form>
			<form id="attachThumbnailForm" class="modal">
				<a title="닫기" class="close"></a>
				<h3>첨부된 이미지</h3>
				<p>
					<label>설명 추가</label>
					<textarea name="caption" class="text"
						placeholder="이 이미지에 대한 설명을 입력하세요."></textarea>
				</p>
				<input type="button" value="첨부 삭제" class="button light floatLeft">
				<input type="submit" value="설명 저장" class="button">
			</form>
			<form id="messageSend" class="modal">
				<a title="닫기" class="close"></a>
				<h3>쪽지 보내기</h3>
				<p>
					<textarea name="message" class="text" placeholder="내용을 입력해주세요."></textarea>
				</p>
				<input type="submit" value="전송" class="button">
			</form>
		</div>
	</div>
</body>
</html>