$(document).ready(function () {
	$("#container > div.articles > form.write ul.option li.attach label").css({
        "display": "block",
        "width": "100%",
        "height": "100%",
        "cursor": "pointer"
    });

    $(".file-name").css({
        "display": "inline-block",
        "margin-left": "23px",
        width: '650px',
    });

	$("#container > div.articles > form.write ul.option li.attach").css({
		float: 'left',
		backgroundImage: 'url(/anytime/resources/image/post/addFile.png)',
		backgroundSize: 'contain',
		backgroundRepeat: 'no-repeat',
		width: '20px',
		height: '20px',
		marginLeft: '10px',
		marginTop: '10px'
	});


	$("#writeBoardContainer").prepend(
		`<form class="write" id ="writeBoard"> 
     
    <p><input id="title" placeholder="글 제목" class="title"></p> 
    <p style="margin-bottom: 0px !important;"> 
    <textarea id="content" placeholder="" class="smallplaceholder large"> 
    </textarea></p> 
     <input class="file" type="file" name="file" multiple="multiple">
<ol class="thumbnails">
<li class="new"></li>
</ol>
<div class="clearBothOnly"></div>
<ul class="option">

<!-- ▼해시태그 미사용 주석처리▼ 
	<li title="해시태그" class="hashtag"></li> 
-->
<li title="첨부" class="attach"></li>
  <label class="file-label">
    <span class="file-name" id="file-name"></span> 파일 선택
    <input type="file" id="post_file" name="post_file" class="file-input">
  </label>
	
<li title="완료" class="submit" id="writePost"></li>
</ul>
<div class="clearBothOnly"></div>
     </form>`);


	$("#writeBoard").hide(); // Hide 
	var toggle = false;

	$("#writeArticleButton").on("click", function (e) {

		toggle = !toggle;
		if (toggle == true) {

			$("#writeBoard").show(); //Show 
		} else {

			$("#writeBoard").hide(); // Hide 
		}
	});
	
	/*글 수정 시작 => 기존 정보 데이터값 불러오기*/
	$("#updateButton").on("click", function (e) {
		e.preventDefault();

		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");

		let POST_ID = $(this).attr('POST_ID');
		let SUBJECT = $(this).attr('SUBJECT');
		let CONTENT = $(this).attr('CONTENT');

		$.ajax({
			url: 'updatePost',
			type: 'GET',
			data: {
				post_id: POST_ID,
			},
			async: false,
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token)
			},
			success: function (data) {
				console.log(data);

				// 값 가져오기
				let SUBJECT = data.SUBJECT;
				let CONTENT = data.CONTENT;

				// 가져온 값으로 입력 필드 업데이트
				$('#title').val(SUBJECT);
				$('#content').val(CONTENT);

				$("#writeBoard").show();
				$("#boardInfo").hide();

			},
			error: function (e) {
				alert("게시글 불러오기에 실패하였습니다. 오류 : " + e.errorMessage);
				console.log(e);
			}
		});
	});


	/*글 수정 종료 => 기존 정보 데이터값 불러오기*/

	/*글쓰기 완료*/
	$("#writePost").on("click", function (e) {
		e.preventDefault();

		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");

		var POST_ID = document.getElementById('POST_ID');

		console.log($("#title").val());
		console.log($("#content").val());

		let data = {
			post_id: POST_ID,
			LOGIN_ID: $('#login_id').val(),
			SUBJECT: $("#title").val(),
			CONTENT: $("#content").val(),
		};

		console.log(data);

		$.ajax({
			type: "PUT",
			url: "write",
			data: data,
			beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token)
			},
			success: function (data) {
				if (data.status == "success") {
					alert("게시글 작성에 성공하였습니다.");
					location.reload();
				} else {
					alert("게시글 작성에 실패하였습니다. 오류: " + data.errorMessage);
				}
			},
			error: function (e) {
				console.log(e);
			}
		});

	});
	/*글쓰기 완료*/

});
`` 
