<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/common.partial.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.article.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.community.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/post/postlist.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script>
	$(function() {
		var currentPage = location.pathname;
		var newPath = currentPage.replace('/anytime/', ''); // 나중에 설정 바꾸고서 날리기
		console.log("currentPage" + currentPage);
		console.log("newPath" + newPath);
	
		if (newPath.includes("my")) {
			$(".board").removeClass("active");
		} else if (newPath.includes('post')
				|| newPath.includes("${schoolDomain}")) {
			$("#menu li").removeClass("active");
			$(".board").addClass("active");
		}
		/*$("li").on("click", function() {
			if (!userid) {
				event.preventDefault();
				alert("로그인이 필요합니다.");
				location.href = "${pageContext.request.contextPath}/member/login";
			}
		});*/
	});
</script>


<nav>
	<div class="wrap">
		<div id="logo">
			<a href="${pageContext.request.contextPath}/${school.domain}"><img
				src="${pageContext.request.contextPath}/resources/image/common/nav.logo.png"></a>
			<p>
				<span class="name multiple">애니타임</span><span class="subname">
					${school.name}</span>
			</p>
		</div>
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="pinfo" />
			<div id="account">
				<a href="/message" title="쪽지함" class="icon message">쪽지함</a> <a
					href="${pageContext.request.contextPath}/my" title="내 정보"
					class="icon my">내 정보</a> <input type="hidden" id="login_id"
					value="${pinfo.username}"> <input type="hidden"
					id="school_id" value="${school.school_id}"> 
			</div>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
			<div id="account">
				<a href="${pageContext.request.contextPath}/member/login"
					class="button">로그인</a> <a
					href="${pageContext.request.contextPath}/member/register"
					class="button red">회원가입</a>`
			</div>
		</sec:authorize>
		<ul id="menu">
			<li class="board"><a
				href="${pageContext.request.contextPath}/${school.domain}">게시판</a></li>
			<li><a href="/timetable">시간표</a></li>
			<li><a href="calculator">학점계산기</a></li>
			<li><a href="/lecture">캘린더 </a></li>
		</ul>
	</div>
</nav>