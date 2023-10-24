$(document).ready(function () {
    // 설정버튼 -> Modal
    $("#settingBtn").click(function() {
        $("#tableSetting").show();
        $('#tableSetting').before('<div class="modalwrap"></div>');
    });

    $(".close").click(function() {
        $("#tableSetting").hide();
        $(".modalwrap").remove();
    });
	
    //시간표 이름변경 및 날짜변경
    $("#tableSetting").submit(function(e) {
        e.preventDefault();  

        var newName = $("input[name='name']").val();  
        var timetable_id = $("#tableName").data("id");

        console.log("Current timetable ID: " + timetable_id);
        console.log("Current newName: " + newName);
        
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: 'changeName',
            type: 'POST',
            data: {
                timetable_id: timetable_id,
                newName: newName
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token)
            },
            success: function(response) {
                alert('이름 변경 성공');
                console.log(response);
                $("#tableName").text(newName);

                // 서버에서 보낸 새로운 TIMETABLE_DATE를 화면에 반영
                //$("#tableUpdatedAt").text(response.newDate);
                location.reload();
            },
            error: function(error) {
                alert('이름 변경 실패');
                console.log(error);
            }
        });
    });

    $(".create").click(function() {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        
        $.ajax({
            url: 'createNewTimeTable',
            type: 'POST',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token)
            },
            success: function(response) {
                // 서버에서 보낸 새로운 시간표 정보를 화면에 추가
                var newTimeTable = '<li><a href="/timetable/' + response.year + '/' + response.semester + '/' + response.id + 
                                   '">' + response.name + '</a></li>';
                $(".menu ol").append(newTimeTable);
                alert('새 시간표 생성 성공');
            },
            error: function(error) {
                alert('새 시간표 생성 실패');
                console.log(error);
            }
        });
    });

});
