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
      			
      			   			
		<a class="item active" href="/message/26589680">
        	<time>20/10/20 22:29</time>
        		<h3>익명</h3>
        			<p class="text"> 이거에요!  감사해요정말ㅜㅜ</p>
        </a>
        <a class="item" href="/message/26588813">
        	<time>20/10/20 22:01</time>
        		<h3>익명</h3>
        			<p class="text">그러니까요 누락된 qwe 제 글보고 쪽지 보냈으면 좋겠어요 ㅠㅠ 진짜 왜 학생들이 스트레스 받으면서 이짓을 해야되는건지.. 기말 강의평가만 기다립니다..</p>
        </a>
		
		
		
		
		
        
        
        <!-- <a class="item active" href="/message/26589680">
        	<time>20/10/20 22:29</time>
        		<h3>익명</h3>
        			<p class="text">qwe 이거에요!  감사해요정말ㅜㅜ</p>
        </a>
        <a class="item" href="/message/26588813">
        	<time>20/10/20 22:01</time>
        		<h3>익명</h3>
        			<p class="text">그러니까요 누락된 qwe씨도 제 글보고 쪽지 보냈으면 좋겠어요 ㅠㅠ 진짜 왜 학생들이 스트레스 받으면서 이짓을 해야되는건지.. 기말 강의평가만 기다립니다..</p>
        </a>
        <a class="item" href="/message/26586495">
        	<time>20/10/20 21:20</time>
        		<h3>익명</h3>	
        			<p class="text">정말 정말 감사해요 ㅠㅠ 진짜 지금 너무 열받아서 머리가 아프고 ㅠㅠㅠ님 아녔으면 ㅠㅠ 계속 몰랐을거고 ㅠㅠ 저 qwe 또 어떡해요 ㅠㅠ 하아... 진짜 이 교수님 너무 짜증나네요</p>
        </a>
        <a class="item" href="/message/20211474">
        	<time>20/03/31 14:35</time>
        		<h3>익명</h3>
        			<p class="text">저도 놀행초대해주세요 ㅠㅠ</p>
        </a>
        <a class="item" href="/message/16812901">
        	<time>19/11/26 22:43</time>
        		<h3>익명</h3>
        			<p class="text">네 그럼 그렇게 할게요!!</p>
        </a> -->

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
        success: function (messageList) {
        	console.log("Ajax 요청 성공: ", messageList);
        	
            // 메시지 목록을 담을 HTML 요소를 선택합니다.
            var messageLastListItems = $("#left");
		
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

</script>

</html>