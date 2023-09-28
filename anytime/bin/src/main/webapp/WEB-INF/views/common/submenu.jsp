<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css" href="/Anytime/css/submenu.css" rel="stylesheet">
</head>
<body style="">
	<div id="submenu">
		<div class="wrap">

			<c:set var="itemsPerGroup" value="3" />
			<%-- type 1, 2, 3, 4이 순차적으로 변경됨 --%>
			<c:forEach var="type" begin="1" end="4">
				<div class="divider"></div>
				<%-- type을 나누는 구분 --%>
				<div class="group">
					<%-- 게시판을 3개씩 출력하기 위한 구분 --%>
					<ul>
						<%-- 게시판을 3줄씩 출력하기 위한 count, 다음 type때 0으로 초기화 --%>
						<c:set var="itemCount" value="0" />
						<c:forEach var="menu" items="${boardlist}" varStatus="status">
							<%-- type이 일치할때, 승인을 받았을때, --%>
							<c:if test="${menu.type == type && menu.approval eq 'Y'}">
								<%-- 게시판 3개 출력했을때, --%>
								<c:if test="${itemCount % itemsPerGroup == 0 && itemCount != 0}">
					</ul>
				</div>
				<%-- 한 그룹에 3개의 게시판이 출력되면 다음 게시판을 위한 그룹 태그 --%>
				<div class="group">
					<ul>
						</c:if>
						<li><a href="PostList.bo?board_num=${menu.board_num}">${menu.name}</a></li>
						<c:set var="itemCount" value="${itemCount + 1}" />
						</c:if>
			</c:forEach>
			</ul>
		</div>
		</c:forEach>


		<div class="divider"></div>
		<hr>
	</div>
	<input type="hidden" id="communityCampusId" value="37">
	</div>