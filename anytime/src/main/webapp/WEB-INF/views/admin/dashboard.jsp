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
		<jsp:include page="../common/left_admin.jsp" />
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