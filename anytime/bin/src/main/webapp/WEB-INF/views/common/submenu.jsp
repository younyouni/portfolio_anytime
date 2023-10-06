<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common/submenu.css">
<script src="https://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
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
							<c:if test="${menu.type == type && menu.status == 1}">
								<%-- 게시판 3개 출력했을때, --%>
								<c:if test="${itemCount % itemsPerGroup == 0 && itemCount != 0}">
					</ul>
				</div>
				<%-- 한 그룹에 3개의 게시판이 출력되면 다음 게시판을 위한 그룹 태그 --%>
				<div class="group">
					<ul>
						</c:if>
						<li><a href="${pageContext.request.contextPath}/post/list?board_id=${menu.board_id}">${menu.name}</a></li>
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
</body>
<script>
function sendAjaxRequest() {
  var NAME = "some_value"; // NAME 변수를 정의하고 값 할당
	
  $.ajax({
    url: "${pageContext.request.contextPath}/board/list",
    data: {
      "NAME": NAME
    },
    dataType: "json", // JSON 데이터로 응답을 예상함
    success: function (responseData) {
		// 서버로부터의 응답 데이터는 response 매개변수에 JSON 객체로 저장됨
	    console.log("Ajax 요청 성공: ", responseData);
	
	  	// JSON 데이터를 JavaScript 객체로 파싱
	    var boardlist = responseData;
	
	    // submenu 요소 선택
	    var submenu = $("#submenu");
	    var wrap = $("#wrap");
	 
	
	    // submenu 초기화 (기존 내용 삭제)
	    submenu.empty();
	    submenu.append('<div class="wrap">');
		
	    // type (1~4)를 나눠서 출력
	    for (var type = 1; type <= 4; type++) {
	    	// type을 나누는 구분
	    	submenu.find('.wrap').append('<div class="divider"></div>');
	    	// 3개씩 출력을 위해 그룹을 나누는 구분
	    	submenu.find('.wrap').append('<div class="group"><ul>');
	    	// 게시판 카운터 초기화
	    	var itemCount = 0;
	    	
	    	// boardlist를 반복하고 메뉴 생성
	    	for (var i = 0; i < boardlist.length; i++){
	    		var menu = boardlist[i];
	    		
	    		// type이 일치하고 status가 1인 경우
	    		if (menu.type == type && menu.status == 1){
	                
	                // itemConut가 3의 배수일 때 ul 요소 닫기
	                if (itemCount % 3 == 0 && itemCount != 0){
	                	submenu.find('.group:last').append('</ul></div>'); // 그룹 닫기
	                    submenu.find('.wrap').append('<div class="group"><ul>');                
	    		}
	    			// boardlist 출력
	    			submenu.find('.group:last ul').append('<li><a href="${pageContext.request.contextPath}/post/list?board_id=' + menu.board_ID + '">' + menu.name + '</a></li>');
	                itemCount++;
	    	}	    
	    		submenu.append('</ul></div>'); // 그룹 닫기
	    }
	    	submenu.append('<hr></div>');	// 타입 닫기
	    }
    	submenu.find('.wrap').append('<div class="divider"></div>');	// 마지막 닫기
	    
    },
    error: function (error) {
      alert("Ajax 요청 실패: " + error);
    }
  });
}

sendAjaxRequest();

</script>
</html>