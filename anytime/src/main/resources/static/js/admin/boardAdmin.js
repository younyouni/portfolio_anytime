$(function() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");

	var approvalStatus = 0;
	var page = 1; //페이지 변수를 1로 초기화
	var searchKey = 0;//검색기능을 위해 검색 변수 초기화
	var keyword ='';
	
	$("form#searchBoard").hide();
	$("div.center-block").hide();
	
	$("div.boardadmin div.menu a").click(function() {
		$("div.boardadmin a").removeClass("active");
		$(this).addClass("active");
	});

	$(".menu li.boardrequest").click(function() {
		$("table.request").show();
		$("table.list").hide();
		$("form#searchBoard").hide();
	});

	$(".menu li.boardlist").click(function() {
		$("table.list").show();
		$("table.request").hide();
		$("form#searchBoard").show();
		$("div.center-block").show();
		boardTotalList(page, searchKey)
	});

	$(document).on("click","a.approval",function() {
		$('form#approvalreview').css('display', 'block');
		$('form#approvalreview').before('<div class="modalwrap"></div>');
		var board_id = $(this).parent().parent().parent().find('input.approval').val();
		$('form#approvalreview input[name="board_id"]').val(board_id);
	});

	// 모달 닫기 버튼을 클릭할 때
	$('a.close').click(function() {
		$('form.modal').css('display', 'none');
		$('div.modalwrap').remove();
	});

	$('#approvalreview > input.button').click(function() {
		let checkreason = $('input[name="rejectreason"]').val();
		
		if ($.trim(checkreason) == "") {
			alert("게시판 승인 거부 사유를 입력하세요.");
			$('input[name="rejectreason"]').val('').focus();
			return false;
		}
	});

	$('.list tbody td:not(.switch)').click(function() {
		$('form#boardInfo').css('display', 'block');
		$('form#boardInfo').before('<div class="modalwrap"></div>');
	});

	$("#approvalreview").submit(function() {
		var approvalStatus = 4;
		var rejectionreason = $("form#approvalreview input.rejectreason").val();
		var board_id = $('#approvalreview input[name="board_id"]').val();
		updateBoardStatus(board_id, approvalStatus,	rejectionreason);
		location.reload();
		e.preventDefault();
	});// $("#approvalreview").submit end

	$("input.approval").on("change",function() {
		var rejectionreason = ''
		var $this = $(this).parent().parent().siblings('.approvalprocess');
		if (this.checked) {
			approvalStatus = 3;
			var board_id = $(this).val();
			$this.removeClass('pending reject');
			$this.find('p').text('승인완료');
			$this.find('img').remove();
			$this.addClass('complete');
		} else {
			approvalStatus = 0;
			var board_id = $(this).val();
			$this.removeClass('complete');
			$this.find('p').text('승인보류');
			var img = new Image();
   			img.src = "${pageContext.request.contextPath}/resources/image/admin/approval.cancel.png";
   			img.onload = function() {
    			$this.find('p').append('<a class="approval"><img src="' + img.src + '"></a>');
    		};
    		$this.addClass('pending');
		}
		updateBoardStatus(board_id, approvalStatus,	rejectionreason);
	});
});

function updateBoardStatus(board_id, approvalStatus, rejectionreason) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type : "post",
		url : 'updateBoardStatus',
		data : {
			board_id : board_id,
			approvalStatus : approvalStatus,
			rejectionreason : rejectionreason
		},
		beforeSend : function(xhr) { // 데이터를 전송하기 전에 헤더에 csrf 값을 설정합니다.
			xhr.setRequestHeader(header, token);
		},
		success : function(rdata) {
			if (rdata == 1) {
				alert('승인처리완료');
			}
		}
	});
}

function boardTotalList(page, searchKey) {
	$.ajax({
		type : "get",
		url : 'boardtotal',
		data : {
			page : page,
			searchKey : searchKey,
			keyword : keyword
		},
		dataType: "json",
		success : function(rdata) {
			let output = "";
			let rownum =1;
			if(rdata.boardTotal.length>0){
				$(rdata.boardTotal).each(function(){
					let board_type='';
					if(this.type ==1){
						board_type='일반';
					}else if(this.type==2){
						board_type='단체';
					}else if(this.type==3){
						board_type='학과';
					}else{
						board_type='커스텀';
					}
					output += '<tr><td>'+rownum+'</td><td>'+this.school_NAME
					+'</td><td>'+board_type+'</td><td>'+this.name+'</td><td>'
					if(this.purpose!=null){
					output+=this.purpose;
					}else{
					output+='';
					}
					output+='</td><td>'+this.login_ID+'</td><td class="switch"><label class="switch">';
					if(this.status ==1){
						output+='<input type="checkbox" class="approval" checked>' 
					}else if(this.status ==1){
						output+='<input type="checkbox" class="approval">';
					}
					output+='<span class="slider round"></span></label></td></tr>'
					rownum++;
				})//each end
				$('table.list tbody').after(output);
			}
		}
	});
}