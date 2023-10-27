$(document).ready(function () {

	mainRightSideAjax();

	function mainRightSideAjax(){

	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	
	var contextPath = window.location.origin + '/anytime';
	
	$.ajax({
		url: contextPath + "/hotpostlist_sample",
		dataType: "json",
		success: function (response){
		console.log(response);
		
		var hotlist = $(".card.rightside");
		
		hotlist.empty();
		
		let rightsideList = "";
		
		rightsideList += '<div class="board">';
		rightsideList += '<h3><a href="' + contextPath + '/hotpost">HOT 게시물<span>더 보기</span></a></h3>';
				
			$.each(response.list, function (index, hot) {	
				if(index < 4){
				rightsideList += '<a class="list" href="' + contextPath + '/post/detail?post_id='+ hot.post_ID +'">';
				rightsideList += '<time>' + hot.formattedPOST_DATE +'</time>';
				rightsideList += '<p>' + hot.subject + '</p>';
				rightsideList += '</a>';
				}
			});
			
		if (response.list.length === 0) {
		    rightsideList += '<a class="list">핫 게시물이 없습니다</a>';
		}
		
		rightsideList += '<h3><a href="' + contextPath + '/bestpost">BEST 게시판<span>더 보기</span></a></h3>';
		rightsideList += '</div>';
		
		hotlist.append(rightsideList);			
		console.log("mainRightSideAjax 실행 완료");
		
		}
	});
	
	}

});