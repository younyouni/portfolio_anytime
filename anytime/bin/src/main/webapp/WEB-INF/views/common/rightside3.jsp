<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" type="image/x-icon" href="/Anytime/image/new/nav.logo.png">
    <link type="text/css" href="/Anytime/css/rightside3.css" rel="stylesheet">
</head>
<body style="">
    <div class="rightside">
        <div class="card">
            <div class="board">
            	    <c:if test="${not empty userid}">
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
