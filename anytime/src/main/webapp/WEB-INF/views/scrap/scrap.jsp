<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>
	<jsp:include page="../common/header.jsp" />
	<jsp:include page="../common/submenu.jsp" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/common.partial.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.article.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.community.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/container.modal.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scrap/scrap.css">
<script src="${pageContext.request.contextPath}/resources/js/scrap/scrap.js"></script>
</head>
<body>
	<div id="container" class="article">

		<aside class="none">
			<div class="title">
				<a class="hamburger"></a>
				<h1>
					<a href="${pageContext.request.contextPath}/myscrap">내 스크랩</a>
				</h1>
			</div>
		</aside>
		<div class="wrap title">
			<h1>
				<a href="${pageContext.request.contextPath}/myscrap">내 스크랩</a>
			</h1>
			<hr>
		</div>
		<div class="wrap bubbles none"></div>
		<div class="wrap articles">
		
			



			
		<div class="clearBothOnly"></div>
			<div class="center-block">
			
						<ul class="pagination justify-content-center">
							<c:if test="${page <= 1 }">
								<li class="page-item">
								<li class="page-item hidden"><a class="page-link gray">이전&nbsp;&nbsp;</a>
								</li>
							</c:if>
							<c:if test="${page > 1 }">
								<li class="page-item"><a
									href="list?board_id=${board_id}&page=${page-1}&search_field=${search_field}&search_word=${search_word}"
									class="page-link">&nbsp;&nbsp;&nbsp;이전</a></li>
							</c:if>


							<c:forEach var="a" begin="${startpage}" end="${endpage}">
								<c:if test="${a == page}">
									<li class="page-item  active"><a class="page-link">${a}</a>
									</li>
								</c:if>
								<c:if test="${a != page }">
									<c:url var="go" value="list">
										<c:param name="board_id" value="${board_id}" />
										<c:param name="search_field" value="${search_field}" />
										<c:param name="search_word" value="${search_word}" />
										<c:param name="page" value="${a}" />
									</c:url>
									<li class="page-item"><a href="${go}" class="page-link">${a}</a>
									</li>
								</c:if>
							</c:forEach>
							
							
							<c:if test="${page >= maxpage }">
								<li class="page-item hidden"><a class="page-link hidden">다음&nbsp;&nbsp;&nbsp;</a>
								</li>
							</c:if>
							<c:if test="${page < maxpage }">
								<c:url var="next" value="list">
									<c:param name="board_id" value="${board_id}" />
									<c:param name="search_field" value="${search_field}" />
									<c:param name="search_word" value="${search_word}" />
									<c:param name="page" value="${page+1}" />
								</c:url>
								<li class="page-item"><a href="${next}" class="page-link">다음&nbsp;&nbsp;&nbsp;</a>
								</li>
							</c:if>
						</ul>
			</div>
		
		<hr>
		

	</div>
	
		<jsp:include page="../common/rightside3.jsp" />

	<div id="bottom">
		<ul class="links">
			<li><a href="/page/serviceagreement">이용약관</a></li>
			<li class="privacy"><a href="/page/privacy">개인정보처리방침</a></li>
			<li><a href="/page/youthpolicy">청소년보호정책</a></li>
			<li><a href="/page/rules">커뮤니티이용규칙</a></li>
			<li><a href="/notice">공지사항</a></li>
			<li><a href="/page/faq">문의하기</a></li>
			<li class="copyright"><a href="/">© 에브리타임</a></li>
		</ul>
		<span>직업정보제공사업 신고번호 : J1204020230008</span>
	</div>



</body>
</html>