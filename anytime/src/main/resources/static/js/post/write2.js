$(document).ready(function(){

	$("#writeBoardContainer").prepend(
	`<form class="write" id ="writeBoard" enctype="multipart/form-data">
	
	<p><input id="title" placeholder="글 제목" class="title"></p>
	<p style="margin-bottom: 0px !important;">
	<textarea id="content" placeholder="애니타임은 누구나 기분 좋게 참여할 수 있는 커뮤니티를 만들기 위해 커뮤니티 이용규칙을 제정하여 운영하고 있습니다. 
위반 시 게시물이 삭제되고 서비스 이용이 일정 기간 제한될 수 있습니다. 
	
아래는 이 게시판에 해당하는 핵심 내용에 대한 요약 사항이며, 게시물 작성 전 커뮤니티 이용규칙 전문을 반드시 확인하시기 바랍니다. 

※ 정치·사회 관련 행위 금지 
- 국가기관, 정치 관련 단체, 언론, 시민단체에 대한 언급 혹은 이와 관련한 행위 
- 정책·외교 또는 정치·정파에 대한 의견, 주장 및 이념, 가치관을 드러내는 행위 
- 성별, 종교, 인종, 출신, 지역, 직업, 이념 등 사회적 이슈에 대한 언급 혹은 이와 관련한 행위 
- 위와 같은 내용으로 유추될 수 있는 비유, 은어 사용 행위 

※ 홍보 및 판매 관련 행위 금지 
- 영리 여부와 관계 없이 사업체·기관·단체·개인에게 직간접적으로 영향을 줄 수 있는 게시물 작성 행위 
- 위와 관련된 것으로 의심되거나 예상될 수 있는 바이럴 홍보 및 명칭·단어 언급 행위 

※ 불법촬영물 유통 금지
불법촬영물등을 게재할 경우 전기통신사업법에 따라 삭제 조치 및 서비스 이용이 영구적으로 제한될 수 있으며 관련 법률에 따라 처벌받을 수 있습니다.

※ 그 밖의 규칙 위반 
- 타인의 권리를 침해하거나 불쾌감을 주는 행위 
- 범죄, 불법 행위 등 법령을 위반하는 행위 
- 욕설, 비하, 차별, 혐오, 자살, 폭력 관련 내용을 포함한 게시물 작성 행위 
- 음란물, 성적 수치심을 유발하는 행위 
- 스포일러, 공포, 속임, 놀라게 하는 행위 " class="smallplaceholder large">
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
<li title="첨부" class="attach">
  <label class="file-label">
    <span class="file-name" id="file-name"></span> 파일 선택
    <input type="file" id="post_file" name="post_file" class="file-input">
  </label>
</li>
<li title="완료" class="submit" id="writePost"></li>
<!-- ▼익명체크박스 미사용 주석처리▼
<li class="anonym">
<input type="checkbox" class="form-check-input" id="isAnonymous" name="isAnonymous">
 <label class="form-check-label" for="exampleCheck1"> 익명 </label>
</li>
-->

</ul>
<div class="clearBothOnly"></div>
</form>
`);


$("#writeBoard").hide(); // Hide
var toggle = false;

$("#writeArticleButton").on("click", function(e) {	
	
	toggle = !toggle;
	if(toggle == true){
		
		$("#writeBoard").show(); //Show
	}else{		
		
		$("#writeBoard").hide(); // Hide
	}
});

/*글쓰기 완료*/
var filesToUpload = []; // 업로드할 파일들을 저장하는 배열

$(document).on('change', '#post_file', function() {
    var newFiles = this.files;

    // 이미 업로드된 파일의 개수
    var uploadedFileCount = $('.thumbnails li').length - 1; // 'new' li 제외

    if (uploadedFileCount + newFiles.length > 5) {
        alert('첨부파일은 최대 5개까지만 가능합니다.');
        this.value = ''; // 현재 입력 필드 초기화
        return;
    }

    for (var i = 0; i < newFiles.length; i++) {
        filesToUpload.push(newFiles[i]); // 새롭게 추가된 파일들도 배열에 포함시킵니다.

        var objectUrl = URL.createObjectURL(newFiles[i]);

        // 이미지 업로드 시 미리보기 썸네일 추가
        var thumbnailItem = $('<li><img src="' + objectUrl + '" /></li>');
        
	thumbnailItem.find('img').on('load', function () { 
		URL.revokeObjectURL(this.src);   // Here we are revoking the object URL after the image has loaded.
	});

        thumbnailItem.find('img').css('max-width', '100px'); // 썸네일 이미지 크기 조절

        $('.thumbnails .new').before(thumbnailItem);
    }
});



    $("#writePost").on("click", function(e) {
        e.preventDefault();

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

	    var board_id = document.getElementById('board_id').textContent;

	    var formData=new FormData();
	    formData.append('BOARD_ID', board_id);
	    formData.append('LOGIN_ID', $('#login_id').val());
	    formData.append('SUBJECT', $("#title").val());
	    formData.append('CONTENT', $("#content").val());

	    for (var i=0; i<filesToUpload.length; i++) { 
		    // 모든 첨부파일을 formData에 추가합니다.
		    formData.append("file[]", filesToUpload[i]);
	    }

	  	$.ajax({
	    	type: "POST",
	    	url: "write",
	    	data: formData,
	    	processData: false,  
	  		contentType: false,
	  		beforeSend: function(xhr) {
	  			xhr.setRequestHeader(header, token);
	  		},
	  	}).done(function(res) {
	      	if (res.statusCode == 1){
	          	alert("게시글 작성에 성공하였습니다.");
	          	location.reload();
	      	} else {
	          	alert("게시글 작성에 실패하였습니다. 오류: " + res.errorMessage);
	      	}
	  	});
    });

function displayThumbnails(files) {
	var fileList = files;
	var anyWindow = window.URL || window.webkitURL;

	for(var i = 0; i < fileList.length; i++){
		var objectUrl = anyWindow.createObjectURL(fileList[i]);
		$('.thumbnails .new').before('<li><img src="' + objectUrl + '" /></li>');
		
		window.URL.revokeObjectURL(fileList[i]);
	}
  }


// $(document).on('change', '#post_file', function() {
//     var fileList = this.files;
//     var anyWindow = window.URL || window.webkitURL;

//     // 이미 업로드된 파일의 개수
//     var uploadedFileCount = $('.thumbnails li').length - 1; // 'new' li 제외

//     if(uploadedFileCount + fileList.length > 5) {
//         alert('첨부파일은 최대 5개까지만 가능합니다.');
//         this.value = ''; // 현재 입력 필드 초기화
//         return;
//     }

//     for(var i = 0; i < fileList.length; i++){
//     //이미지 만들기
//       var objectUrl = anyWindow.createObjectURL(fileList[i]);
//       $('.thumbnails .new').before('<li><img src="' + objectUrl + '" /></li>');
      
//       window.URL.revokeObjectURL(fileList[i]);
//     }
// });

/*글쓰기 완료*/
})