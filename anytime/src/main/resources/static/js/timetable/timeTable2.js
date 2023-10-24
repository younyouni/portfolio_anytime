$(document).ready(function () {

    $("#settingBtn").click(function() {
        $("#tableSetting").show();
        $('#tableSetting').before('<div class="modalwrap"></div>');
    });

    $(".close").click(function() {
        $("#tableSetting").hide();
        $(".modalwrap").remove();
    });
	

    $("#tableSetting").submit(function(e) {
        e.preventDefault();  // 폼 제출 방지

        var newName = $("input[name='name']").val();  // 새 이름 가져오기
        var timetable_id = $("#timetable").data("id");

        console.log("Current timetable ID: " + timetable_id);
        console.log("Current newName: " + newName);

        $.ajax({
            url: 'changeName',
            type: 'POST',
            data: {
                timetable_id: timetable_id,
                newName: newName
            },
            success: function(response) {
                // 요청 성공 시 처리
                alert('이름 변경 성공');
                
                // 필요한 경우 페이지 리로딩 또는 특정 DOM 업데이트 등의 추가 작업 수행
                
                location.reload();
            },
            error: function(error) {
                // 요청 실패 시 처리
                alert('이름 변경 실패');
            }
        });
    });

});
