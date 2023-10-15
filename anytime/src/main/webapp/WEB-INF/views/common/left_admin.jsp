<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div class="left_admin">
	<div class="card">
		<form class="admin">
			<img
				src="${pageContext.request.contextPath}/resources/image/common/profile.png"
				class="picture">
			<p class="nickname">수정필요테스트${member.nickname}</p>
			<p class="userid">관리자아이디출력${member.login_id}</p>
			<p class="email"><img
				src="${pageContext.request.contextPath}/resources/image/admin/email.png"
				class="picture">관리자이메일출력${member.login_id}</p>
			<ul class="buttons">
				<li class="info"><a href="account.com">내 정보</a></li>
				<li class="logout"><a href="logout.com">로그아웃</a></li>
			</ul>
			<hr>
		</form>
	</div>
</div>