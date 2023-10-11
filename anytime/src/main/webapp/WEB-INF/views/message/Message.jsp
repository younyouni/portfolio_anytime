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

<!-- 					<script type="text/javascript" src="/js/extensions.jquery-1.10.2.min.js"></script>
					<script type="text/javascript" src="/js/extensions.underscore-min.js"></script>
					<script type="text/javascript" src="/js/common.js"></script>
					<script type="text/javascript" src="/js/message.index.js"></script>
					<script type="text/javascript" src="/js/message.send.js"></script> -->
</head>

<body>

<!-- <nav>
    <div class="wrap">
      <div id="logo">
        <a href="/"><img src="/images/new/nav.logo.png"></a>
        <p><span class="name multiple">에브리타임</span><span class="subname">숭실대</span></p>
      </div>
      <div id="account">
        <a href="/message" title="쪽지함" class="icon message">쪽지함</a>
        <a href="/my" title="내 정보" class="icon my">내 정보</a>
        <input type="hidden" id="userUserid" value="rainbow281">
        <input type="hidden" id="userSchool" value="21">
        <input type="hidden" id="userCampus" value="37">
      </div>
      <ul id="menu">
        <li><a href="/">게시판</a></li>
        <li><a href="/timetable">시간표</a></li>
        <li><a href="/lecture">강의실</a></li>
        <li><a href="/calculator">학점계산기</a></li>
        <li><a href="/friend">친구</a></li>
        <li><a href="https://bookstore.everytime.kr/">책방</a></li>
        <li><a href="https://www.campuspick.com/">캠퍼스픽</a></li>
      </ul>
    </div>
  </nav> -->
  
<div id="container" class="message" data-box-id="0">
    <aside class="none">
      <div class="title">
        <a class="hamburger"></a>
        <h1>쪽지함</h1>
      </div>
    </aside>
    <div class="messageboxes">
      <h2>쪽지함</h2>
      <div class="items">
        <a class="item active" href="/message/26589680">
        	<time>20/10/20 22:29</time>
        		<h3>익명</h3>
        			<p class="text">01093010366 이거에요!  감사해요정말ㅜㅜ</p>
        </a>
        <a class="item" href="/message/26588813">
        	<time>20/10/20 22:01</time>
        		<h3>익명</h3>
        			<p class="text">그러니까요 누락된 손유진씨도 제 글보고 쪽지 보냈으면 좋겠어요 ㅠㅠ 진짜 왜 학생들이 스트레스 받으면서 이짓을 해야되는건지.. 기말 강의평가만 기다립니다..</p>
        </a>
        <a class="item" href="/message/26586495">
        	<time>20/10/20 21:20</time>
        		<h3>익명</h3>	
        			<p class="text">정말 정말 감사해요 ㅠㅠ 진짜 지금 너무 열받아서 머리가 아프고 ㅠㅠㅠ님 아녔으면 ㅠㅠ 계속 몰랐을거고 ㅠㅠ 저 손유진씨는 또 어떡해요 ㅠㅠ 하아... 진짜 이 교수님 너무 짜증나네요</p>
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
        </a>
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
	    	<div class="items">
	    		<div class="item">
	    			<time>20/10/20 22:29</time>
	    				<p class="type type1">받은 쪽지</p>
	    					<p class="text">01093010366 이거에요!  감사해요정말ㅜㅜ</p>
	    		</div>
	    	<div class="item">
	    		<time>20/10/20 22:26</time>
	    			<p class="type type2">보낸 쪽지</p>
	    				<p class="text">유진씨랑 저만 누락됐더라고요 ㅠㅠ 번호알려주시면 초대해드릴게요</p>
	    	</div>
	    	<div class="item">
	    		<time>20/10/20 22:22</time>
	    			<p class="type type1">받은 쪽지</p>
	    				<p class="text">앗 네네</p>
	    	</div>
	    	<div class="item">
	    		<time>20/10/20 22:15</time>
	    			<p class="type type2">보낸 쪽지</p>
	    				<p class="text">혹시 손유진씨???</p>
	    	</div>
	    	<div class="item">
	    		<time>20/10/20 22:15</time>
	    			<p class="type type0">안내</p>
	    				<p class="text">자유게시판에 작성된 익명1의 댓글을 통해 시작된 쪽지입니다.<br>글 내용: 스포츠와 건강관리 ㅂㅅㅇ교수님</p>
	    	</div>
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
  
  
  
  
  
<!--   <script type="text/javascript">
    var _serverTime = 1696983543459;
    var _clientTime = new Date().getTime();
    var _diffTime = _clientTime - _serverTime;
    var _apiServerUrl = 'https://api.everytime.kr';
    window._screenName = '알림/쪽지 - 알림 탭 (쪽지함 목록)';
  </script>
  <script async="" src="https://www.googletagmanager.com/gtag/js?id=G-85ZNEFVRGL"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'G-85ZNEFVRGL', { 'send_page_view': false });
    _gfn.logPageView();
  </script> -->

</body>

</html>