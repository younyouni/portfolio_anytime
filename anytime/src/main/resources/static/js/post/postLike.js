$(document).ready(function () {
    $(".posvote").one('click', function(e) { // one() 함수 사용
        e.preventDefault();

        var confirmResult = confirm('이 글에 공감하십니까?');
        
        if(confirmResult){
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            
            var post_id= $(this).data('post_id');

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
                        $('.vote').text(data.like_count); 
                    } else if(data.statusCode == 2){
                        alert('공감 취소!');
                        $('.vote').text(data.like_count); 
                    } else if(data.statusCode == 3){ // 이미 공감한 경우
                        alert('이미 공감하셨습니다.');
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
