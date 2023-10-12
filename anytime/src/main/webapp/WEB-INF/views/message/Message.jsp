<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>애니타임</title>
	<jsp:include page="../common/header.jsp" />
	
<%-- <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.partial.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/container.modal.css"> --%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/message/message.css">
<link href="/favicon.ico" rel="shortcut icon">
<script src="https://code.jquery.com/jquery-latest.js"></script>
</head>

<body>
  
<div id="container" class="message" data-box-id="0">
    <aside class="none">
      <div class="title">
        <a class="hamburger"></a>
        <h1>쪽지함</h1>
      </div>
    </aside>
    <div class="messageboxes">
      <h2>쪽지함</h2>
      <div class="items" id="laft">
      			

        <div class="loading" style="display: none;"></div>
      </div>
    </div>
    
    <div class="messages visible">
    	<div class="title">
    		<a class="back">뒤로</a>
    			<h2>익명</h2>
    				<a class="send" data-modal="messageSend" data-box-id="26589680" data-is-anonym="1">쪽지 보내기</a>
    				<a class="refresh">새로고침</a>
    				<a class="more">더보기</a>
    	</div>
	    <div class="items" id="right">
	    	
	    	
    	</div>
    </div>
    
    <form id="messageSend" class="modal">
      <a title="닫기" class="close"></a>
      <h3>쪽지 보내기</h3>
      <p>
        <textarea name="message" class="text" placeholder="내용을 입력해주세요."></textarea>
      </p>
      <input type="submit" value="전송" class="button">
    </form>
    
    <form id="messageMore" class="modal">
      <a title="닫기" class="close"></a>
      <h3>쪽지함</h3>
      <ul>
        <li><a data-menu="removeAll">전체 삭제</a></li>
        <li><a data-menu="block">차단</a></li>
        <li><a data-menu="reportSpam">스팸 신고</a></li>
      </ul>
    </form>
    
  </div>

  
</body>

<script>
$(document).ready(function() {
		getMessageListAjax();
		getMessageLastListAjax();
});

function getMessageListAjax() {
    $.ajax({
        url: "${pageContext.request.contextPath}/messagelist",
        dataType: "json",
        success: function (messageList) {
        	console.log("Ajax 요청 성공: ", messageList);
        	
            // 메시지 목록을 담을 HTML 요소를 선택합니다.
            var messageListItems = $("#right");
		
            let messageItem = "";
            
            if(messageList != null){
            
            $.each(messageList, function (index, ms) {
                // 각 메시지 항목을 동적으로 생성하고 추가합니다.
                messageItem += '<div class="item">';
                messageItem += '<time>' + ms.message_DATE + '</time>';
                	if(ms.direction == 2){
                		messageItem += '<p class="type type2">보낸 쪽지</p>';
                	}else if(ms.direction == 1){
                		messageItem += '<p class="type type1">받은 쪽지</p>';
                	}else if(ms.direction == 3){
                		messageItem += '<p class="type type0">안내</p>';
                	}
                messageItem += '<p class="text">' + ms.content + '</p>';
                messageItem += '</div>';

                // messageListItems에 메시지 아이템을 추가합니다.
                messageListItems.append(messageItem);
                
                messageItem = "";
            });
            
            } else {
            	
            	
            }
            
        }
    });
}

function getMessageLastListAjax() {
    $.ajax({
        url: "${pageContext.request.contextPath}/messagelastlist",
        dataType: "json",
        success: function (messageLastList) {
        	console.log("Ajax 요청 성공: ", messageLastList);
        	
            // 메시지 목록을 담을 HTML 요소를 선택합니다.
            var messageLastListItems = $("#laft");
		
            let messageItem = "";
            
            if(messageLastList != null){
            
            $.each(messageLastList, function (index, ms) {
                  	
                // 각 메시지 항목을 동적으로 생성하고 추가합니다.
                messageItem += '<a class="item" href="/message/고유값들가야함">';
                messageItem += '<time>' + ms.message_DATE + '</time>';
                messageItem += '<h3>' + '익명' + '</h3>';
                messageItem += '<p class="text">' + ms.content + '</p>';
                messageItem += '</a>';

                // messageListItems에 메시지 아이템을 추가합니다.
                messageLastListItems.append(messageItem);
                
                messageItem = "";
            });
            
            } else {
            	
            	
            }
            
        }
    });
}

</script>

</html>