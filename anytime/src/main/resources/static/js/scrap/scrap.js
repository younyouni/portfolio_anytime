$(document).ready(function () {
	
	var page = 1;
	
	myScrapListAjax()

	function myScrapListAjax(){
		
		// 보안 토큰
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url: "scraplist",
			data:{
				page: page,
			},
			dataType: "json",
			success: function (response){
			console.log(response);
				
			var scrapArticles = $(".wrap.articles");
			scrapArticles.children().not(".clearBothOnly, .center-block, .pagination justify-content-center").remove();
			
			let scrapList = "";
			
			$.each(response.list, function (index, s) {	
				
				scrapList += '<article class="list">';
				scrapList += '<a class="article" href="post/detail?post_id='+ s.post_ID + '">';
				scrapList += '<div class="desc">';
				scrapList += '<p class="boardname" href="post/list?board_id=' + s.board_ID + '">' + s.boardname + '</p>';
				scrapList += '<h2 class="medium bold">' + s.subject + '</h2>';
				scrapList += '<p class="medium">' + s.content + '</p>';
				scrapList += '<div class="info">';
				scrapList += '<ul class="status">';
				scrapList += '<li title="공감" class="vote">' + s.like_COUNT + '</li>';
				scrapList += '<li title="댓글" class="comment">' + s.comment_COUNT + '</li>';
				scrapList += '</ul>';
				scrapList += '<time class="small">' + s.post_DATE + '</time>';
				scrapList += '<h3 class="small">' + s.nickname + '</h3>';
				scrapList += '</div><hr></div></a><div class="comments"></div></article>';
				
			});
			
			scrapArticles.prepend(scrapList);
			console.log("ajax 실행 완료");

			}

			
		})
		
	}


});
