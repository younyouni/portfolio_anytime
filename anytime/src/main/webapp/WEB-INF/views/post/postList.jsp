<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/post/postlist.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/post/postwrite.css">
<script src="https://code.jquery.com/jquery-latest.js"></script>
</head>

<body>

	<div id="container" class="article">
<!-- 		<input type="hidden" id="isUser" value="1"> 
		<input type="hidden" id="boardId" value="385967">
		<input type="hidden" id="board_id" value="2"> -->
		
		<%-------------------------------- ▼제목▼ --------------------------------%> 
		
		 <div class="wrap title">
			<h1>
				<c:choose>
					<c:when test="${allsearchcheck == 0}">
						<a href="list?board_id=${board_id}">${boardname}</a>
					</c:when>
					<c:when test="${allsearchcheck == 1}">
				'${search_word}'에 대한 검색 결과입니다.
				</c:when>
				</c:choose>
			</h1>
			<hr>
		</div>
		
		<%-------------------------------- ▼글쓰기▼ --------------------------------%>
			
			<div class="wrap articles">
			<c:if test="${allsearchcheck == 0}">	<!-- 천제 검색이 아닐 경우 -->
			<label class="postwriteform" style="display: none;">
				<jsp:include page="postlist_write.jsp" />
			</label>	
			<a id="writeArticleButton">새 글을 작성해주세요! 
			<img src="${pageContext.request.contextPath}/resources/image/post/write.button.png">

			</a>
			</c:if>
		
		
		<%-------------------------------- ▼테스트 게시물 출력▼ --------------------------------%>
		
		<c:choose>
       	<c:when test="${allsearchcheck == 0}">	
		<c:forEach var="post" items="${postlist}">
         	<article>
         		<a class="article" href="detail?post_id=${post.POST_ID}">
         			<h2 class="medium">${post.SUBJECT}</h2>
         			<p class="small">${post.CONTENT}</p> 
         			
         			<time class="small">${post.POST_DATE}</time>
					<h3 class="small">${post.NICKNAME}</h3>
			<ul class="status">
			
				<c:set var="fileCount" value="0" />
               <%--  <c:forEach var="file" items="${post.POST_FILE.split(',')}">
                    <!-- 파일이 존재하면 fileCount 증가 -->
                    <c:if test="${not empty file}">
                        <c:set var="fileCount" value="${fileCount + 1}" />
                    </c:if>
                </c:forEach> --%>
                <li class="attach">${fileCount}</li>
                
				<li title="공감" class="vote">${post.LIKE_COUNT}</li>
				<li title="댓글" class="comment">${post.COMMENT_COUNT}</li>
			</ul>
			<hr>
			</a>
				<div class="comments"></div>
         	</article>
      	</c:forEach>
      	</c:when>
      	</c:choose>

<%--  	
		------------------------------ ▼제목▼ ------------------------------ 
		
		 <div class="wrap title">
			<h1>
				<c:choose>
					<c:when test="${allsearchcheck == 0}">
						<a href="PostList.bo?board_num=${board_num}">${board_name}</a>
					</c:when>
					<c:when test="${allsearchcheck == 1}">
				'${search_word}'에 대한 검색 결과입니다.
				</c:when>
				</c:choose>
			</h1>
			<hr>
		</div>
		
		------------------------------ ▼글쓰기▼ ------------------------------
			
			<div class="wrap articles">
			<c:if test="${allsearchcheck == 0}">	<!-- 천제 검색이 아닐 경우 -->
			<label class="postwriteform" style="display: none;">
				<jsp:include page="postlist_write.jsp" />
			</label>	
			<a id="writeArticleButton">새 글을 작성해주세요! 
			<img src="/Anytime/image/boardlist/write.button.png">

			</a>
			</c:if>
		
		
	
		------------------------------ ▼게시물▼ 메인검색 아닐떄, ------------------------------
		
		<c:choose>
       	<c:when test="${allsearchcheck == 0}">
      	<c:forEach var="post" items="${postlist}">
         	<article>
         		<a class="article" href="PostDetailAction.bo?post_num=${post.post_id}">
         			<h2 class="medium">${post.subject}</h2>
         			<p class="small">${post.content}</p> 
         			<c:if test="${allsearchcheck == 1}"><a class="boardname">${post.boardname}</a></c:if>	<!-- 전체검색일시 보드네임 추가 -->
         			<time class="small">${post.post_date}</time>
					<h3 class="small">${post.nickname}</h3>
			<ul class="status">
				<li class="attach">${post.post_file}</li>
				<li title="공감" class="vote">${post.like_count}</li>
				<li title="댓글" class="comment">${post.comment_count}</li>
			</ul>
			<hr>
			<input type="hidden" name="311208877_comment_anonym" value="0">
			</a>
				<div class="comments"></div>
         	</article>
      	</c:forEach>
  			</c:when>
      	
      	------------------------------ ▼게시물▼ 메인검색 일떄, ------------------------------
      	
      	<c:when test="${allsearchcheck == 1}">
      	<c:forEach var="post" items="${postlist}">
			<article>
				<a class="article" href="PostDetailAction.bo?post_num=${post.post_num}">
				<img src="https://cf-fpi.everytime.kr/0.png" class="picture medium">
				<h3 class="medium">${post.nickname}</h3> 
				<time class="medium">${post.post_date}</time>
				<hr>
				<h2 class="medium bold">${post.subject}</h2>
				<p class="medium">
					${post.content}<br>
				</p>
				<a href="PostList.bo?board_num=${post.board_num}" class="boardname" style="padding-left:15px; padding-bottom:10px;">${post.boardname}</a>
				<ul class="status" style="padding-right:15px; padding-bottom:10px;">
					<li class="attach">${post.file_count}</li>
					<li title="공감" class="vote">${post.like_count}</li>
					<li title="댓글" class="comment">${post.comment_count}</li>
				</ul>
				
				<hr> <input type="hidden" name="311145444_comment_anonym" value="0">
				</a>
				<div class="comments"></div>
			</article>
			</c:forEach>
      	</c:when>
      	</c:choose> 
      	
--%>

      	<%-------------------------------- ▼글이 없을때▼ --------------------------------%>
      	
      	<c:if test="${empty postlist}">
      		<c:choose>
      			<c:when test="${emptycheck == 1}">
      				<article>
    					<div class="empty_postlist">
    					<p>글이 없습니다 지금 작성 해보세요.</p>
    					</div>
    				</article>
      			</c:when>
      			<c:when test="${emptycheck == 2}">
      				<article>
    					<div class="empty_postlist">
    					<p>검색된 결과가 없습니다.</p>
    				</div>
    				</article>
      			</c:when>
      		</c:choose>
		</c:if>


		<%-------------------------------- ▼페이지네이션▼ 메인검색 아닐떄, --------------------------------%>
		
			<div class="clearBothOnly"></div>
			<div class="center-block">
				<c:choose>
					<c:when test="${allsearchcheck == 0 && searchcheck != 1}">
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
					</c:when>
		
		<%-------------------------------- ▼페이지네이션▼ 포스트검색 일떄, --------------------------------%>
					
					<c:when test="${allsearchcheck == 0 && searchcheck == 1}">
						<ul class="pagination justify-content-center">

							<c:if test="${page <= 1 }">
								<li class="page-item">
								<li class="page-item hidden"><a class="page-link gray">이전&nbsp;&nbsp;</a>
								</li>
							</c:if>
							<c:if test="${page > 1 }">
								<li class="page-item"><a
									href="search?board_id=${board_id}&page=${page-1}&search_field=${search_field}&search_word=${search_word}"
									class="page-link">&nbsp;&nbsp;&nbsp;이전</a></li>
							</c:if>


							<c:forEach var="a" begin="${startpage}" end="${endpage}">
								<c:if test="${a == page}">
									<li class="page-item  active"><a class="page-link">${a}</a>
									</li>
								</c:if>
								<c:if test="${a != page }">
									<c:url var="go" value="search">
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
								<c:url var="next" value="search">
									<c:param name="board_id" value="${board_id}" />
									<c:param name="search_field" value="${search_field}" />
									<c:param name="search_word" value="${search_word}" />
									<c:param name="page" value="${page+1}" />
								</c:url>
								<li class="page-item"><a href="${next}" class="page-link">다음&nbsp;&nbsp;&nbsp;</a>
								</li>
							</c:if>
						</ul>
					</c:when>		
		
		<%-------------------------------- ▼페이지네이션▼ 메인검색 일떄, --------------------------------%>
		
					<c:when test="${allsearchcheck == 1}">
						<ul class="pagination justify-content-center">

							<c:if test="${page <= 1 }">
								<li class="page-item">
								<li class="page-item hidden"><a class="page-link gray">이전&nbsp;</a>
								</li>
							</c:if>
							<c:if test="${page > 1 }">
								<li class="page-item"><a
									href="MainSearch.bo?&page=${page-1}&search_field=${search_field}&search_word=${search_word}"
									class="page-link">&nbsp;&nbsp;이전</a></li>
							</c:if>

							<c:forEach var="a" begin="${startpage}" end="${endpage}">
								<c:if test="${a == page }">
									<li class="page-item  active"><a class="page-link">${a}</a>
									</li>
								</c:if>
								<c:if test="${a != page }">
									<c:url var="go" value="MainSearch.bo">
										<c:param name="search_field" value="${search_field}" />
										<c:param name="search_word" value="${search_word}" />
										<c:param name="page" value="${a}" />
									</c:url>
									<li class="page-item"><a href="${go}" class="page-link">${a}</a>
									</li>
								</c:if>
							</c:forEach>

							<c:if test="${page >= maxpage }">
								<li class="page-item hidden"><a class="page-link hidden">다음&nbsp;&nbsp;</a>
								</li>
							</c:if>
							<c:if test="${page < maxpage }">
								<c:url var="next" value="MainSearch.bo">
									<c:param name="search_field" value="${search_field}" />
									<c:param name="search_word" value="${search_word}" />
									<c:param name="page" value="${page+1}" />
								</c:url>
								<li class="page-item"><a href="${next}" class="page-link">다음&nbsp;&nbsp;</a>
								</li>
							</c:if>
						</ul>
					</c:when>
				</c:choose>

			</div>
		</div>


		<hr>

		<%-------------------------------- ▼라이트사이드▼ 메인아닐때, 메인일떄, --------------------------------%>

		<c:choose>
			<c:when test="${allsearchcheck == 0}">
				<jsp:include page="../common/rightside2.jsp" />
			</c:when>
			<c:when test="${allsearchcheck == 1}">
				<jsp:include page="../common/rightside.jsp" />
			</c:when>
		</c:choose>


		<%-------------------------------- ▼모달▼ --------------------------------%>

		<form id="abuseForm" class="modal">
			<a title="닫기" class="close"></a>
			<h3>신고 사유 선택</h3>
			<ul>
				<li><a data-reason="1">게시판 성격에 부적절함</a></li>
				<li><a data-reason="2">욕설/비하</a></li>
				<li><a data-reason="3">음란물/불건전한 만남 및 대화</a></li>
				<li><a data-reason="4">상업적 광고 및 판매</a></li>
				<li><a data-reason="5">유출/사칭/사기</a></li>
				<li><a data-reason="6">낚시/놀람/도배</a></li>
				<li><a data-reason="7">정당/정치인 비하 및 선거운동</a></li>
			</ul>
		</form>

		<form id="manageMoimForm" class="modal">
			<a title="닫기" class="close"></a>
			<h3>게시판 설정</h3>
			<p>
				<label>소개</label> <input type="text" name="info" class="text">
			</p>
			<p class="hide">
				<label>인기 글 금지</label> <input type="checkbox"
					id="manageMoimForm_is_not_selected_hot_article"
					name="is_not_selected_hot_article"><label
					for="manageMoimForm_is_not_selected_hot_article" class="checkbox">글이
					공감을 받아도 인기 글 및 HOT 게시물에 선정되지 않음</label>
			</p>
			<input type="button" value="게시판 양도" class="button light floatLeft">
			<input type="button" value="게시판 삭제" class="button light floatLeft">
			<input type="submit" value="수정" class="button">
		</form>
		<form id="transferMoimForm" class="modal"
			data-gtm-form-interact-id="0">
			<a title="닫기" class="close"></a>
			<h3>게시판 양도</h3>
			<p>
				<label>양도인 비밀번호</label> <input type="password"
					name="transferer_password" class="text"
					data-gtm-form-interact-field-id="1">
			</p>
			<p>
				<label>피양도인 아이디</label> <input type="text" name="transferee_userid"
					class="text" data-gtm-form-interact-field-id="0">
			</p>
			<input type="submit" value="양도 요청" class="button">
		</form>

		<form id="attachThumbnailForm" class="modal">
			<a title="닫기" class="close"></a>
			<h3>첨부된 이미지</h3>
			<p>
				<label>설명 추가</label>
				<textarea name="caption" class="text"
					placeholder="이 이미지에 대한 설명을 입력하세요."></textarea>
			</p>
			<input type="button" value="첨부 삭제" class="button light floatLeft">
			<input type="submit" value="설명 저장" class="button">
		</form>

		<form id="messageSend" class="modal">
			<a title="닫기" class="close"></a>
			<h3>쪽지 보내기</h3>
			<p>
				<textarea name="message" class="text" placeholder="내용을 입력해주세요."></textarea>
			</p>
			<input type="submit" value="전송" class="button">
		</form>
	</div>

	<jsp:include page="../common/footer.jsp" />
	
	<script>
	$(document).ready(function() {
		$('#writeArticleButton').click(function() {
			$(this).hide();	
			$(".postwriteform").show();
			
		});
		
	});

	function validateForm() {
	    var subject = document.querySelector('.write .title').value;
	    var content = document.querySelector('.write .smallplaceholder').value;
	    
	    if (subject.trim() === '' || content.trim() === '') {
	        alert('제목과 내용을 모두 입력해주세요.');
	        return false;
	    }
	}

	$(document).ready(function() {
	const fileInput = document.getElementById('post_file');
	const fileNameElement = document.getElementById('file-name');

	fileInput.addEventListener('change', (event) => {
	  const selectedFile = event.target.files[0];
	  if (selectedFile) {
	    fileNameElement.textContent = selectedFile.name;
	  } else {
	    fileNameElement.textContent = '';
	  }
		});

	});

	</script>
</body>
</html>