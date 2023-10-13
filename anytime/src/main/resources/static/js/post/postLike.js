$(document).ready(function () {
    $(".posvote").on('click', function(e) {
        var confirmResult = confirm('이 글에 공감하십니까?');
        
        if(confirmResult){
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            
            let postId= $(this).data('post_id'); // 게시글의 ID를 얻습니다. 이 값은 HTML에서 data-postId 속성으로 설정되어야 합니다.

            $.ajax({
                url: 'likePost',
                type: 'POST',
                data: {post_id: post_id},
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token)
                },
                success:function(data){
                    if(data.statusCode == 1){
                        alert('공감 완료!');
                        $('.vote').text(data.like_count); // Update Like Count on Page.
                    } else if(data.statusCode == 2){
                        alert('공감 취소!');
                        $('.vote').text(data.like_count); // Update Like Count on Page.
                    } else{
                        alert('오류 발생 : ' + data.errorMessage);
                    }
                },
                error:function(e){
                    console.log(e);
                    alert('오류 발생!');
                }
            });
        }
    });
});
