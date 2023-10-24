$(document).ready(function () {

	var urlParams = new URLSearchParams(window.location.search);
	var post_id = urlParams.get('post_id');
	
	function reportAjax(post_id){
		$.ajax({
			url: "scrap",
			data:{
				"post_id": post_id,
			},
			success: function (scrapResult){
				if(scrapResult == 1){
					alert("스크랩이 완료 되었습니다.");
				} else if(scrapResult == 2) {
					alert("스크랩이 취소 되었습니다.");
				} else {
					alert("스크랩중 오류가 발생했습니다.");
				}
			}	
		})
	}

});
