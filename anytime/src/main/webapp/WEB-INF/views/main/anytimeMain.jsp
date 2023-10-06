<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>
<link href="${pageContext.request.contextPath}/resources/css/main/main.css" type="text/css" rel="stylesheet">

 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
 <script>
        $(document).ready(function() {
            $("#searchInput").on("input", function() {
                var searchKeyword = $(this).val().toLowerCase();

                $(".campuslist a").each(function() {
                    var schoolName = $(this).find(".name").text().toLowerCase();
                    if (schoolName.includes(searchKeyword)) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                });
            });a
        });
    </script>
    
</head>
<body>
	<aside>
		<div class="login">
			<a href="/" class="logo"><img src="${pageContext.request.contextPath}/resources/image/common/logo.png"
				alt="에브리타임" width="28px" height="40px"></a>
				<a href="${pageContext.request.contextPath}/member/login" class="button login">로그인</a>
				<a href="${pageContext.request.contextPath}/member/register" class="button register"> 애니타임 회원가입</a>
			<p class="find">
				<a href="${pageContext.request.contextPath}/member/forgotid">아이디/비밀번호 찾기</a>
			</p>
			<hr>
		</div>
		<form class="search">
			<p>우리 학교 커뮤니티 둘러보기</p>
			<input type="text" name="name" placeholder="찾으시는 캠퍼스를 검색하세요."
				autocomplete="off" id="searchInput">
					
		</form>
		<div class="campuslist">
        <%-- 학교 리스트를 가져와서 출력 --%>
        <c:forEach var="school" items="${schoolList}">
            <a href="${pageContext.request.contextPath}/${school.domain}">
                <span class="name">${school.name}</span>
                <span class="count">${school.total_count} 명</span>
            </a>
        </c:forEach>
    </div>
	</aside>
	<section class="community">
		<h2>
			<strong>재학생 커뮤니티 애니타임</strong>
		</h2>
		<div class="paragraph">
			<p>
				학교 인증을 거친 재학생들의 안전한 대화를 위한 <strong>익명 시스템</strong>과<br>학생들이 직접
				게시판을 개설하여 운영하는 <strong>커뮤니티 플랫폼</strong>을 통해<br>많은 대학교에서 가장 활발히
				이용하는 재학생 커뮤니티로 자리잡았습니다.
			</p>
		</div>
		<div class="figures">
			<div>
				<p class="icon">
					<img src="${pageContext.request.contextPath}/resources/image/main/icon.authorized.png">
				</p>
				<p class="description">철저한 학교 인증</p>
			</div>
			<div>
				<p class="icon">
					<img src="${pageContext.request.contextPath}/resources/image/main/icon.anonymous.png">
				</p>
				<p class="description">완벽한 익명 시스템</p>
			</div>
			<div>
				<p class="icon">
					<img src="${pageContext.request.contextPath}/resources/image/main/icon.users.png">
				</p>
				<p class="description">재학생 운영 게시판</p>
			</div>
		</div>
	</section>
	<section class="footer white">
		<ul class="links">
			<li><a href="/page/serviceagreement">이용약관</a></li>
			<li class="privacy"><a href="/page/privacy">개인정보처리방침</a></li>
			<li><a href="/page/faq">문의하기</a></li>
			<li class="copyright"><a href="/">© 애니타임</a></li>
		</ul>
	</section>
</body>
</html>