<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>내 정보 - 애니타임</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/info/body.css">
<style>
nav {
	border-bottom: 0 !important;
}
</style>
<jsp:include page="../common/header.jsp" />
</head>
<body id="my">
	<div>
		<div class="container">
			<section>
				<div class="title">
					<h1>내 정보</h1>
					<a href="logout.com" class="logout">로그아웃</a>
				</div>
				<div class="profile">
					<img src="https://cf-fpi.everytime.kr/0.png">
					<h3>${member.userid}</h3>
					<p>
						<span>${member.nickname}</span>
					</p>
					<p>
						<span>${school.school_name}</span> <span
							>${member.admission_year}</span> <span>학번</span>
					</p>
				</div>
			</section>
			<section>
				<h2>계정</h2>
				<a href="certificate.com" class="item">학교 인증</a>
				<a href="${pageContext.request.contextPath}/youn/password" class="item">비밀번호
					변경</a> <a href="${pageContext.request.contextPath}/youn/info" class="item">내정보
					변경</a>
			</section>
			<section>
				<h2>커뮤니티</h2>
				<a href="${pageContext.request.contextPath}/youn/boardlist" class="item">게시판 관리</a>
			</section>

			<section>
				<h2>기타</h2>
				<a href="${pageContext.request.contextPath}/youn/delete" class="item">회원 탈퇴</a>
			</section>
		</div>
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<jsp:include page="../common/footer2.jsp" />
</body>
</html>