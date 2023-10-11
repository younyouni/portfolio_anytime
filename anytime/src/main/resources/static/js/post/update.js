$(document).ready(function(){ 
 
    $("#writeBoardContainer").prepend( 
    `<form class="write" id ="writeBoard"> 
     
    <p><input id="title" placeholder="글 제목" class="title"></p> 
    <p style="margin-bottom: 0px !important;"> 
    <textarea id="content" placeholder="" class="smallplaceholder large"> 
    </textarea></p> 
     
     </form>`); 
 
 
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
 
/*글 수정 시작*/ 
$("#updateButton").on("click", function(e) { 
	e.preventDefault(); 
	 
	let token = $("meta[name='_csrf']").attr("content"); 
	let header = $("meta[name='_csrf_header']").attr("content"); 
 
	var postId = e.target.getAttribute("post_id"); 
    var boardId = e.target.getAttribute("board_id");  
 
	$.ajax({ 
	type: "GET", 
	url: "detail", 
	data: { post_id: postId }, 
	 
	beforeSend: function(xhr) { 
	xhr.setRequestHeader(header, token); 
		 }, 
}).done(function(res) { 
if(res.statusCode == 1){ 
let post=res.data; 
$("#title").val(post.SUBJECT); 
$("#content").val(pos.CONTENT); 
 
$("#writeBoard").show(); 
} else { 
alert("게시글 불러오기에 실패하였습니다. 오류: " + res.errorMessage); 
} 
}); 
 
}); 
/*글 수정 종료*/ 
 
/*글쓰기 완료*/ 
  $("#submitPostButtonIdHere") .on("click", function(e) { 
	e.preventDefault(); 
	 
	let token = $("meta[name='_csrf']").attr("content"); 
	let header = $("meta[name='_csrf_header']").attr("content"); 
	 
	var board_id = document.getElementById('board_id').textContent; 
	 
	console.log($("#title").val()); 
	console.log($("#content").val());  
	 
  	let data = { 
	BOARD_ID: board_id, 
	LOGIN_ID: $('#login_id').val(), 
	SUBJECT: $("#title").val(), 
	CONTENT: $("#content").val(), 
}; 
       
console.log(data); 
    
$.ajax({ 
type: "POST", 
url:"/posts/write", 
data:data, 
beforeSend:function(xhr){ 
xhr.setRequestHeader(header,token) 
}, 
success:function(data){ 
if(data.status == "success"){ 
alert("게시글 작성에 성공하였습니다."); 
location.reload(); 
} else { 
alert("게시글 작성에 실패하였습니다. 오류: " + data.errorMessage); 
} 
}, 
error:function(e){ 
console.log(e); 
} 
}); 
 
}); 
/*글쓰기 완료*/ 
 
}); 
`` 
