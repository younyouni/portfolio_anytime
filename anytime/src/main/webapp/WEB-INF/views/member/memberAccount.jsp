<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>내 정보 - 애니타임</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/info/total.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/member/info/body.css">
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<style>
nav {
	border-bottom: 0 !important;
}
</style>
<sec:authorize access="isAnonymous()">
   <script>
     location.href = "${pageContext.request.contextPath}/member/login";
   </script>
</sec:authorize>
<jsp:include page="../common/header.jsp" />
</head>
<body id="my">
	<div>
		<div class="container">
			<section>
				<div class="title">
					<h1>내 정보</h1>
					<a href="${pageContext.request.contextPath}/logout" class="logout">로그아웃</a>
				</div>
				<sec:authorize access="isAuthenticated()">
				<div class="profile">
					<img src="${pageContext.request.contextPath}/resources/image/common/profile.png">
					<h3>${member.login_id}</h3>
					<p>
						<span>${member.nickname}</span>
					</p>
					<p>
						<span>${school.name}</span> <span>${member.admission_year}</span>
						<span>학번</span>
					</p>
				</div>
				</sec:authorize>
			</section>
			<section>
				<h2>계정</h2>
				<a href="${pageContext.request.contextPath}/certificate" class="item">학교 인증</a> <a
					href="${pageContext.request.contextPath}/password" class="item">비밀번호
					변경</a> <a href="${pageContext.request.contextPath}/info" class="item">내정보
					변경</a>
			</section>
			<section>
				<h2>커뮤니티</h2>
				<a href="${pageContext.request.contextPath}/boardlist" class="item">게시판
					관리</a>
			</section>

			<section>
				<h2>기타</h2>
				<a href="${pageContext.request.contextPath}/delete" class="item">회원
					탈퇴</a>
			</section>
		</div>
	</div>
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}">
	<jsp:include page="../common/footer2.jsp" />
</body>
</html>