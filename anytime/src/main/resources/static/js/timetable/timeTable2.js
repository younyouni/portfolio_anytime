var timetables = {}; // 시간표 데이터를 학기별로 저장할 객체
var selectedDay = '월';
var defalut_id = 1;
var timetableData = {};

$(document).ready(function () {
    var semester = '2023년 2학기';

    getTimetableList(semester);

    timetables = JSON.parse(localStorage.getItem('timetables')) || {};

    $('.weeks li').click(function() {
        selectedDay = $(this).text();
        $('.weeks li').removeClass('active');
        $(this).addClass('active');
    });


    // var selectedSemester = $("#semesters option:selected").text();

    // 설정버튼 -> Modal
    $("#settingBtn").click(function() {
        $("#tableSetting").show();
        $('#tableSetting').before('<div class="modalwrap"></div>');
    });

    $(".close").click(function() {
        $("#tableSetting").hide();
        $(".modalwrap").remove();
    });

    // 삭제 버튼 클릭 이벤트
    $("#deleteBtn").click(function() {
        var timetable_id = $("#tableName").attr("data-id");

        if (confirm("시간표를 삭제하시겠습니까?")) {
            deleteTimetable(timetable_id);
        }
    });

    // 시간표 삭제 함수
    function deleteTimetable(timetable_id) {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        var user_id = $('body').data('user-id');
        var status;
        if( $("#tableSetting_is_primary").is(':checked')){
            status = 1;
        }else{
            status = 0;
        }

        $.ajax({
            url: 'deleteTimetable', 
            type: 'POST',
            data: {
                timetable_id: timetable_id,
                user_id: user_id,
                status: status
            },
            dataType : "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token)
            },
            success: function(response) {
                alert(response.message);  
                console.log(response);
                if (response.message == '시간표 삭제 성공') 
                location.reload(); 
            },
            error: function(error) {
                alert('시간표 삭제 실패');
                console.log(error.responseJSON.message);  
            }
        });
    }

    // 시간표 이름 변경 및 날짜 변경
    $("#tableSetting").submit(function(e) {
        e.preventDefault();

        var newName = $("input[name='name']").val();
        var timetable_id = $("#tableName").attr("data-id");
        var status;

        if( $("#tableSetting_is_primary").is(':checked')){
            status = 1;
        }else{
            status = 0;
        }
        

        console.log("Current timetable ID: " + timetable_id);
        console.log("Current newName: " + newName);
        console.log("Current status : " + status);

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: 'updateTimetable',
            type: 'POST',
            data: {
                timetable_id: timetable_id,
                newName: newName,
                status: status
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token)
            },
            success: function(response) {
                alert('이름 변경 성공');
                console.log(response);
                // $("#tableName").text(newName);

                // if (response.TIMETABLE_DATE) {
                //     $('#tableUpdatedAt').text(response.TIMETABLE_DATE);
                // }
                
                location.reload();
            },
            error: function(error) {
                alert('이름 변경 실패');
                console.log(error);
            }
        });
        
    });

    // 설정 모달창 이름 데이터값 불러오기
    $("#settingBtn").click(function() {
        $("input[name='name']").val($("#tableName").text());  
        $("#tableSetting").show();
        $('#tableSetting').before('<div class="modalwrap"></div>');

        var isPrimary = $('li.active a').hasClass('primary');
        if (isPrimary) {
            $('#tableSetting_is_primary').prop('checked', true);
            $('#tableSetting_is_primary').attr('disabled', 'disabled');
        } else {
            $('#tableSetting_is_primary').prop('checked', false);
            $('#tableSetting_is_primary').removeAttr('disabled');
        }
     });

     // 새 시간표 만들기
    $("div.menu ol").on("click","li.extension", function() {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        semester = $("#semesters option:selected").text();
        output = "";

        $.ajax({
            url: 'createNewTimeTable',
            type: 'POST',
            data: { 
                'semester': semester  
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token)
            },
            success: function(response) {
                console.log('새 시간표 생성 응답:', response);
                if(response != null ){
                    alert('새 시간표 생성 성공');

                    $('#tableName').text(response.name);
                    $('#tableUpdatedAt').text(response.timetable_DATE);
                    $('#tableName').attr('data-id', response.timetable_ID);

                    timetables[response.timetable_ID] = [];
                    console.log('새 시간표 생성 후 timetables 상태:', timetables);

                    $('div.menu ol li').removeClass('active');
                    output += '<li class="active"><a href="javascript:loadTimetableDetails('+response.timetable_ID+')">' + response.name + '</a></li>'                    
                }
                $('li.extension').before(output);
                
            },
            error: function(error) {
                alert('새 시간표 생성 실패');
                console.log(error);
            }
        });
    });
    

    $("#semesters").change(function () {
        var selectedSemester = $("#semesters option:selected").text();
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        semester =  $("#semesters option:selected").text();
        getTimetableList(semester);

    });

    // 새 수업추가버튼 -> Modal
    $(".button.custom.only").click(function() {
        $("#customsubjects").show();
    });

    $(".close").click(function() {
        $("#customsubjects").hide();
    });
    
// 새 수업 추가
$("#customsubjects").submit(function (e) {
    e.preventDefault();

    var timetable_id = $("#tableName").attr("data-id");
    var subject = $("input[name='subject']").val();
    var professor = $("input[name='professor']").val();
    var day = $(".weeks .active").text();
    var start_time = parseInt($(".starthour option:selected").val(), 10);
    var end_time = parseInt($(".endhour option:selected").val(), 10);
    var classroom = $(".place").val();

    var newClass = {
        subject: subject,
        professor: professor,
        day: day,
        start_time: start_time,
        end_time: end_time,
        classroom: classroom,
    };

    // 현재 선택한 시간표에 수업 추가
    if (timetable_id in timetableData) {
        timetableData[timetable_id].push(newClass);
    } else {
        timetableData[timetable_id] = [newClass];
    }
    // Canvas에 시간표 다시 그리기
    drawTimetable(timetableData[timetable_id]);

    // 기존 저장 방식에 따라 저장
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: 'addSubject',
        type: 'POST',
        data: {
            timetable_id: timetable_id,
            subject: subject,
            day: day,
            start_time: start_time,
            end_time: end_time,
            classroom: classroom,
            professor: professor
        },
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        success: function (response) {
            if (response.status === 'success') {
                alert('수업 추가 성공');
            } else {
                alert('수업 추가 실패');
            }
            console.log(response.message);
        },
        error: function (error) {
            alert('수업 추가 실패');
            console.log(error);
        }
    });
});



loadTimetableDetails(defalut_id);
}); // (document).ready(function() end


function getTimetableList(semester){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    console.log(semester)

    $.ajax({
        type: "POST",
                url: "getTimetableByUserIdAndSemester",
                data: {
                    semester : semester
                },
                async: false,
                dataType : "json",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success : function(rdata) {
                    if(rdata!=null){
                        var output="";
                        $("div.menu ol").empty();
                        
                        $(rdata).each(function() {                        
                            if(this.status == 1){
                                $('#tableName').text(this.name);
                                $('#tableName').attr('data-id', this.timetable_ID);
                                defalut_id = this.timetable_ID;
                                console.log("2 = "+defalut_id);
                                $('#tableUpdatedAt').text(this.timetable_DATE)
                                output += '<li class="active"><a href="javascript:loadTimetableDetails('+this.timetable_ID+')" class="primary">'+this.name+'</a>';
                            }else{
                                output += '<li><a href="javascript:loadTimetableDetails('+this.timetable_ID+')">'+this.name+'</a>';
                            }
                            output += '</li>';
                        })
                        output += '<li class="extension"><a class="create">새 시간표 만들기</a></li>'
                        console.log("3 = "+output);
                        $("div.menu ol").append(output);
                        

                }
            }
    })

}

// var timetableData = {};

// 생성 시간표 클릭시 정보 가져오기
function loadTimetableDetails(timetable_id) {
    // 시간표 클릭시 active 효과 변경
    $("div.menu ol li:not(.extension) a").on("click", function (e) {
        $("div.menu ol li.active").removeClass("active");
        $(this).parent().addClass("active");
    });

    $.ajax({
        type: "GET",
        url: "loadTimetableDetails",
        data: {
            timetable_id: timetable_id,
        },
        async: false,
        dataType: "json",
        success: function (rdata) {
            console.log("응답 데이터:", rdata);

            $("#tableName").text(rdata.timetable.name);
            $("#tableUpdatedAt").text(rdata.timetable.timetable_DATE);
            $("#tableName").attr("data-id", rdata.timetable.timetable_ID);

            // var newClass = rdata.timetalbeDetails;

            // // DB에서 가져온 데이터를 직접 drawTimetable() 함수에 전달합니다.
            // drawTimetable(newClass); 

            // DB에서 가져온 데이터를 직접 drawTimetable() 함수에 전달합니다.
            drawTimetable(rdata.timetalbeDetails); 

            // 가져온 시간표 데이터를 timetableData에 저장합니다.
            timetableData[timetable_id] = rdata.timetalbeDetails;
        },
    });
}





// 시간표별로 캔버스 그리기
function drawTimetable(timetableData) {
    // Canvas 설정
    const canvas = document.getElementById("canvas");
    const ctx = canvas.getContext("2d");

    // 캔버스 초기화
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // 일주일 동안의 요일 설정
    const daysOfWeek = ["월", "화", "수", "목", "금"];

    // 각 요일에 대한 배경색 지정
    const subjectColors = ["#f2e8e8", "#ffe9e9", "#eff9cc", "#dcf2e9", "#dee8f6"];

    // 요일과 시간 텍스트 크기 및 글꼴 설정
    ctx.font = '12px "맑은 고딕", 돋움,  "Apple SD Gothic Neo", tahoma';
    ctx.textBaseline = "middle";

    // 요일별 선 그리기
    for (let i = 0; i <= daysOfWeek.length; i++) {
        let x = (i + 1) * (canvas.width / (daysOfWeek.length + 1));
        ctx.moveTo(x, 0);
        ctx.lineTo(x, canvas.height);

        if (i < daysOfWeek.length) {
            ctx.fillText(daysOfWeek[i], x + 10, 20);
        }
    }

    for (let j = 0; j <= 14; j++) {
        let y = (j + 1) * (canvas.height / (21 - 7));
        ctx.moveTo(50, y);
        ctx.lineTo(canvas.width, y);

        if (j < 14) {
            let hour = j + 8;
            let ampm = hour >= 12 ? "오후" : "오전";
            hour = hour > 12 ? hour - 12 : hour;
            ctx.fillText(ampm + " " + hour + "시", 50, y + 15);
        }
    }

    // 왼쪽 경계선 추가
    ctx.moveTo(50, 0);
    ctx.lineTo(50, canvas.height);

    ctx.strokeStyle = "grey";
    ctx.stroke();

    // 더 많은 라인을 추가하여 맨 위 라인을 막습니다.
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(canvas.width, 0);
    ctx.strokeStyle = "grey";
    ctx.stroke();

    ctx.closePath();

    console.log(timetableData);
    if (timetableData) {
        timetableData.forEach((classItem) => {
            const dayIndex = daysOfWeek.indexOf(classItem.day);

            // 요일에 따른 가로 위치 계산
            const xStart = (dayIndex + 1) * (canvas.width / (daysOfWeek.length + 1)) + 1.25;

            // 시간에 따른 세로 위치 계산
            const yStart = (classItem.start_time - 8) * (canvas.height / 14) + 30 + 18 + 15;

            const blockHeight = (classItem.end_time - classItem.start_time) * (canvas.height / 14) - 3;

            ctx.fillStyle = subjectColors[dayIndex];
            ctx.fillRect(xStart, yStart, (canvas.width / (daysOfWeek.length + 1)) - 2, blockHeight);

            ctx.fillStyle = "#000";

            // 과목명, 교수 이름 및 장소 출력
            ctx.fillText(classItem.subject, xStart + 10, yStart + 10);
            ctx.fillText(classItem.professor, xStart + 10, yStart + 28);
            ctx.fillText(classItem.classroom, xStart + 10, yStart + 45);
        });
    }
}


