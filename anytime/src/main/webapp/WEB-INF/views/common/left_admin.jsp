<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style>
a {
	text-decoration: none;
}

ul {
	padding-left: 0px;
}
</style>
<div class="left_admin">
	<div class="card">
		<form class="admin">
			<img
				src="${pageContext.request.contextPath}/resources/image/common/profile.png"
				class="picture">
			<p class="nickname">관리자</p>
			<p class="userid">${member.login_id}</p>
			<p class="email">
				<img
					src="${pageContext.request.contextPath}/resources/image/admin/email.png"
					class="picture">${member.email}</p>
			<ul class="buttons">
				<li class="info"><a href="account.com">내 정보</a></li>
				<li class="logout"><a href="logout.com">로그아웃</a></li>
			</ul>
			<hr>
		</form>
	</div>
</div>