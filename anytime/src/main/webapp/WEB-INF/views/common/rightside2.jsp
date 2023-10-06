<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link type="text/css" href="${pageContext.request.contextPath}/resources/css/common/rightside2.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script>
$(function() {
    $("#searchButton").click(function() {
        $("#searchArticleForm").submit();
    });
});
</script>
</head>
<body>
	<div class="rightside">
	<!-- <div class="card"> -->
		<div class="rightside2">
		
		 <form id="searchArticleForm" class="search" 
                action="${pageContext.request.contextPath}/post/search" method="get" accept-charset="UTF-8">
                <input type="hidden" name="board_id" value="${board_id}">
                <select name="search_field" id="search_field">
                    <option value="0">전체</option>
                    <option value="1">글 제목</option>
                    <option value="2">글 내용</option>
                </select> 
                <input name="search_word" id="search_word" placeholder="검색어를 입력하세요." 
                       value="${search_word}" type="text">
                <button type="button" id="searchButton">&nbsp;&nbsp;</button>
            </form>
		
		</div>


		<div class="card">
            <div class="board">
            
            	<c:if test="${!empty userid}">
                <h3>
                    <a href="/Anytime/HotListView.bo">HOT 게시물<span>더 보기</span></a>
                </h3>
                
                <c:forEach var="hl" items="${sessionScope.hotlist}" varStatus="status">
                    <c:if test="${status.index < 4}">
                        <a class="list" href="PostDetailAction.bo?post_num=${hl.post_num}">
                            <time>${hl.post_date}</time>
                            <p>${hl.subject}</p>
                        </a>
                    </c:if>
                </c:forEach>
                </c:if>
                
                <c:if test="${empty userid}">
                <h3>
                    <a href="/Anytime/login.com">HOT 게시물<span>더 보기</span></a>
                </h3>
                <% if (session.getAttribute("hotlist") == null) {
                    session.setAttribute("hotlist", request.getAttribute("hotlist"));
                } %>
                <c:forEach var="hl" items="${hotlist}" varStatus="status">
                    <c:if test="${status.index < 4}">
                        <a class="list" href="#">
                            <time>${hl.post_date}</time>
                            <p>로그인 후 이용가능</p>
                        </a>
                    </c:if>
                </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="card">
            <div class="board">
                <h3>
                    <a href="/Anytime/BestListView.bo">BEST 게시판<span>더 보기</span></a>
                </h3>
            </div>
        </div>
        
	</div>
</body>
</html>