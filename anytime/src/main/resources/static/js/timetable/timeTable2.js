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

    $('#semesters').change(function() {
        var selectedSemester = $(this).val();  // 선택된 학기 값을 가져옴

        $.ajax({
            type: 'GET',
            url: 'timetable',  // 요청 URL
            data: { semester: selectedSemester },  // 요청 데이터
            dataType: 'json',  // 응답 데이터 타입
            success: function(response) {  
                if (response.status == "OK") {
                    var timetables = response.body;
                    $('.menu ol').empty();  // 기존에 출력되던 시간표 목록 제거

                    for (var i=0; i<timetables.length; i++) {
                        var timetable = timetables[i];
                        $('.menu ol').append('<li><a href="/timetable/2018/1/' + timetable.TIMETABLE_ID + '" class="primary">' + timetable.NAME + '</a></li>');
                        $('#tableName').text(timetable.NAME);
                        $('#tableUpdatedAt').text(timetable.TIMETABLE_DATE);
                    }
                } else {
                    alert("학기별 시간표 조회 실패");
                }
            },
            error: function(request, status, error) {  
                alert("서버와의 통신 중 에러가 발생하였습니다.");
            }
        });
    });

    // // 시간표 이름 변경 및 날짜 변경
    // $("#tableSetting").submit(function(e) {
    //     e.preventDefault();

    //     var newName = $("input[name='name']").val();
    //     var timetable_id = $("#tableName").data("id");

    //     console.log("Current timetable ID: " + timetable_id);
    //     console.log("Current newName: " + newName);

    //     let token = $("meta[name='_csrf']").attr("content");
    //     let header = $("meta[name='_csrf_header']").attr("content");

    //     $.ajax({
    //         url: 'changeName',
    //         type: 'POST',
    //         data: {
    //             timetable_id: timetable_id,
    //             newName: newName
    //         },
    //         beforeSend: function (xhr) {
    //             xhr.setRequestHeader(header, token)
    //         },
    //         success: function(response) {
    //             alert('이름 변경 성공');
    //             console.log(response);
    //             $("#tableName").text(newName);

    //             // 서버에서 보낸 새로운 TIMETABLE_DATE를 화면에 반영
    //             //$("#tableUpdatedAt").text(response.newDate);
    //             location.reload();
    //         },
    //         error: function(error) {
    //             alert('이름 변경 실패');
    //             console.log(error);
    //         }
    //     });
    // });

    // $(".create").click(function() {
    //     let token = $("meta[name='_csrf']").attr("content");
    //     let header = $("meta[name='_csrf_header']").attr("content");

    //     $.ajax({
    //         url: 'createNewTimeTable',
    //         type: 'POST',
    //         beforeSend: function(xhr) {
    //             xhr.setRequestHeader(header, token)
    //         },
    //         success: function(response) {
    //             // 서버에서 보낸 새로운 시간표 정보를 화면에 추가
    //             var newTimeTable = '<li><a href="/timetable/' + response.semester + '/' + response.id +
    //                                '">' + response.name + '</a></li>';
    //             $(".menu ol").append(newTimeTable);
    //             alert('새 시간표 생성 성공');
    //         },
    //         error: function(error) {
    //             alert('새 시간표 생성 실패');
    //             console.log(error);
    //         }
    //     });
    // });

    // $("#semesters").change(function () {
    //     var selectedSemester = $("#semesters option:selected").text();
    //     let token = $("meta[name='_csrf']").attr("content");
    //     let header = $("meta[name='_csrf_header']").attr("content");
    
    //     // AJAX 요청을 통해 선택한 학기와 유저 정보를 서버로 전달
    //     $.ajax({
    //         url: 'getTimetableByUserIdAndSemester',
    //         type: 'POST',
    //         beforeSend: function(xhr) {
    //             xhr.setRequestHeader(header, token);
    //         },
    //         data: {
    //             semester: selectedSemester,
    //         },
    //         success: function (timetables) {
    //             var timetableList = $(".menu ol");
    //             timetableList.empty(); // 시간표 목록을 비워줍니다.
    //             for (var i = 0; i < timetables.length; i++) {
    //                 var timetable = timetables[i];
    //                 console.log("Received timetable ID: " + timetable_id); // 각 시간표의 id를 출력해봅니다.
    //                 var timetableItem = '<li><a href="/timetable/' + timetable.semester + '/' + timetable.id + '">' + timetable.name + '</a></li>';
    //                 timetableList.append(timetableItem); // 비어있는 목록에 새로운 항목을 추가합니다.
    //             }
    //         },
            
            
    //         error: function (error) {
    //             console.log(error);
    //         }
    //     });
    // });
    
    


});
