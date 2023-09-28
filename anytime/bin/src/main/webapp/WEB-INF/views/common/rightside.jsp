<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link type="text/css" href="/Anytime/css/rightside1.css" rel="stylesheet">
<script>
$(function() {
    document.getElementById("searchButton").addEventListener("click", function() {
        document.getElementById("searchArticleForm").submit();
    });
});
</script>
</head>
<body>
	<div class="rightside">
	<!-- <div class="card"> -->
		<div class="rightside1">
		<form id="searchArticleForm" class="search" action="MainSearch.bo" method="post">
				<input name="search_word" placeholder="전체 게시판의 글을 검색하세요." value="${search_word}" type="text">
				<button type="button" id="searchButton">&nbsp;&nbsp;</button>
		</form>
		</div>


		<div class="card">
	    <div class="board">
	        <c:choose>
	            <c:when test="${not empty userid}">
	                <h3>
	                    <a href="/Anytime/HotListView.bo">HOT 게시물<span>더 보기</span></a>
	                </h3>
	               
	                <c:forEach var="post" items="${sessionScope.hotlist}" varStatus="status">
	                    <c:if test="${status.index < 4}">
	                        <a class="list" href="PostDetailAction.bo?post_num=${post.post_num}">                          
	                            <time>${post.post_date}</time>
	                            <p>${post.subject}</p>
	                        </a>
	                    </c:if>
	                </c:forEach>
	            </c:when>
	            <c:otherwise>
	                <h3>
	                    <a href="/Anytime/login.com">HOT 게시물<span>더 보기</span></a>
	                </h3>
	                <c:forEach var="i" begin="1" end="4">
	                    <a class="list" href="login.com">
	                    	<time>08/12 18:51</time>
	                        <p>로그인 후 이용가능</p>
	                    </a>
	                </c:forEach>
	            </c:otherwise>
	        </c:choose>
	    </div>
	</div>
		<div class="card">
		    <div class="board">
			    <c:if test="${not empty userid}">
			        <h3>
			            <a href="/Anytime/BestListView.bo">BEST 게시판<span>더 보기</span></a>
			        </h3>
			    </c:if>
		    
			    <c:if test="${empty userid}">
			        <h3>
			            <a href="/Anytime/login.com">BEST 게시판<span>더 보기</span></a>
			        </h3>
			    </c:if>
		    </div>
	</div>
        
	</div>
</body>
</html>