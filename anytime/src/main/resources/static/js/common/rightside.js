$(document).ready(function () {

	mainRightSideAjax();

	function mainRightSideAjax(){

	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: "hotpostlist_sample",
		dataType: "json",
		success: function (response){
		console.log(response);
		
		var rightside = $(".rightside");
		
		rightside.empty();
		
		let rightsideList = "";
		
		rightsideList += '<div class="rightside1">';
		rightsideList += '<form id="searchArticleForm" class="search" action="${pageContext.request.contextPath}/post/search" method="get">';
		rightsideList += '<input type="hidden" name="search_field" value="4">';
		rightsideList += '<input name="search_word" placeholder="전체 게시판의 글을 검색하세요." value="' + ${rsponse.search_word} + '" type="text">';
		rightsideList += '<button type="button" id="searchButton">&nbsp;&nbsp;</button>';
		rightsideList += '</form></div>';
		
		rightsideList += '<div class="card">';
		rightsideList += '<div class="board">';
		rightsideList += '<h3><a href="${pageContext.request.contextPath}/hotpost">HOT 게시물<span>더 보기</span></a></h3>';
		
		$.each(response.list, function (index, hot) {	
			
			rightsideList += '<a class="list" href="post/detail?post_id='+ hot.post_ID +'">';
			rightsideList += '<time>' + hot.formattedPOST_DATE +'</time>';
			rightsideList += '<p>' + hot.subject + '</p>';
			rightsideList += '</a>';
			
		});
		
		rightsideList += '<h3><a href="${pageContext.request.contextPath}/bestpost">BEST 게시판<span>더 보기</span></a></h3>';
		rightsideList += '</div></div>';
		
		rightside.append(rightsideList);			
		
		console.log("mainRightSideAjax 실행 완료");
		
		}
	});
	
	}

});