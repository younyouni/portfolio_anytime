$(document).ready(function () {
    // 공감 버튼 클릭 이벤트
    $(".posvote").on("click", function() {
        var result = confirm("이 글에 공감하십니까?");
        
        if (result) { // 확인 버튼 클릭 시
            let token = $("meta[name='_csrf']").attr("content");
			let header = $("meta[name='_csrf_header']").attr("content");

            let POST_ID = $(this).attr('POST_ID');

            let data = {
                post_id: POST_ID
            };

            $.ajax({
                url: 'likePost',
                type: 'POST',
                data: data,
				beforeSend: function (xhr) {
					xhr.setRequestHeader(header, token)
				},
                success: function(data) {
                    if(data.statusCode == 1){
                        alert('공감이 추가되었습니다.');
                        $('.vote').text(data.likeCount); // 변경된 공감 수 반영
                    } else {
                        alert('공감 추가에 실패하였습니다.');
                    }
                },
                error: function(err) {
                    console.log(err);
                    alert('오류가 발생했습니다.');
                }
            });
        }
    });
});
