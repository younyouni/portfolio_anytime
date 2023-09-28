<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
</head>
<body>
	<div id="wrapper">
		<div class="left_admin">
			<div class="card">
				<form class="admin">
					<img src="${pageContext.request.contextPath}/resources/image/common/0.png"
						class="picture">
					<p class="userid">${member.nickname}admin</p>
					<p class="nickname">${member.userid}관리자</p>
					<p class="email">${member.email}admin@anytime.com</p>
					<ul class="buttons">
						<li><a href="account.com">내 정보</a></li>
						<li><a href="logout.com">로그아웃</a></li>
					</ul>
					<hr>
				</form>
				<form class="login">
					<h3>
						커뮤니티 이용을 위해<br> <strong>로그인</strong>이 필요합니다!
					</h3>
					<a href="login.com" class="button login">로그인</a> <a
						href="register.com" class="button register">애니타임 회원가입</a>
				</form>
			</div>
		</div>
		<form action="memberDeleteProcess.com" class="container">
			<section>
				<h1>회원 탈퇴</h1>
				<div class="input">
					<div class="label">
						<label>계정 비밀번호</label>
					</div>
					<input type="password" name="password" maxlength="20"
						placeholder="계정 비밀번호">
				</div>
				<div class="rules">
					<!---->
					<p>
						※ 탈퇴 후 개인 정보, 시간표 등의 데이터가 삭제되며, 복구할 수 없습니다.<br> ※ 다시 가입하여도,
						게시판 등 이용 제한 기록은 초기화되지 않습니다.<br> ※ 작성한 게시물은 삭제되지 않으며, (알수없음)으로
						닉네임이 표시됩니다.<br>
					</p>
				</div>
				<input type="submit" value="회원 탈퇴" id="withdrawButton">
			</section>
		</form>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}">
		<jsp:include page="../common/footer2.jsp" />
	</div>
</body>
</html>