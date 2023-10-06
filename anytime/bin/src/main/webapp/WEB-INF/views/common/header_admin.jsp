<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/image/common/nav.logo.png">
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
	href="${pageContext.request.contextPath}/resources/css/admin/container.community.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/container.modal.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/common/post/postlist.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.7.0.js"></script>
<script>
	$(function() {

		var currentPage = location.pathname;
		var newPath = currentPage.replace('/Anytime/', ''); // 나중에 설정 바꾸고서 날리기
		console.log("currentPage" + currentPage);
		console.log("newPath" + newPath);

		if (newPath.includes('.bo')) {
			$(".bo").addClass("active");
		} else {
			$(".bo").removeClass("active");
		}
		$("li").on("click", function() {
			if (!userid) {
				event.preventDefault();
				alert("로그인이 필요합니다.");
				location.href = "login.com";
			}
		});
	});
</script>
<nav>
	<div class="wrap">
		<div id="logo">
			<a href="MainBoard.bo"><img
				src="${pageContext.request.contextPath}/resources/image/common/nav.logo.png"></a>
			<p>
				<span class="name multiple">애니타임~~~</span><span class="subname">Anytime</span>
			</p>
		</div>
		<div id="account">
			<a href="/message" title="쪽지함" class="icon message">쪽지함</a> <a
				href="account.com" title="내 정보" class="icon my">내 정보</a> <input
				type="hidden" id="userid" value="${userid}"> <input
				type="hidden" id="userSchool" value="21"> <input
				type="hidden" id="userCampus" value="37">
		</div>
		<ul id="menu">
			<li class="bo"><a href="MainBoard.bo">게시판 관리</a></li>
			<li><a href="/timetable">신고 관리</a></li>
			<li><a href="/lecture">공지사항</a></li>
		</ul>
	</div>
</nav>