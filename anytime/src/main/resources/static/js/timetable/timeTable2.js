var timetables = {};
var selectedDay = '월';

$(document).ready(function () {
    var semester = '2023년 2학기';

    getTimetableList(semester);

    const savedTimetableData = JSON.parse(localStorage.getItem('timetableData'));
    if (savedTimetableData) {
        timetableData = savedTimetableData;
        drawTimetable(timetableData);
    }

    $('.weeks li').click(function() {
        selectedDay = $(this).text();  // 클릭한 요일의 텍스트 값을 가져옵니다.
        $('.weeks li').removeClass('active');  // 모든 요일에서 active 클래스를 제거합니다.
        $(this).addClass('active');  // 클릭한 요일에만 active 클래스를 추가합니다.
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
$("#customsubjects").submit(function(e) {
    e.preventDefault();

    var timetable_id = $("#tableName").attr("data-id");
    var subject = $("input[name='subject']").val();
    var professor = $("input[name='professor']").val();
    var day = $(".weeks .active").text(); // 요일은 선택을 해야합니다.
    var start_time = $(".starthour option:selected").val();
    var end_time = $(".endhour option:selected").val();
    var classroom = $(".place").val();

    console.log('수업 추가 요청 전, timetable_id:', timetable_id);
    console.log('수업 추가 요청 전, timetables 상태:', timetables);

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
            classroom : classroom,
            professor: professor
        },
        dataType : "json",
        beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token)
        },
    //     success:function(response){

    //         var newClass ={
    //             subject: subject,
    //             professor: professor,
    //             day: day,
    //             start_time: start_time,
    //             end_time: end_time,
    //             classroom : classroom,
    //          }

    //         // 추가: 시간표 데이터에 새 수업 정보 추가
    //         timetableData.push(newClass);

    //         // 추가: Canvas에 시간표 다시 그리기
    //         drawTimetable(timetableData);

    //          alert('수업 추가 성공');
    //          console.log(response);
    //         //  location.reload(); // canvas 로직을 방해할 위험이 있습니다.
    //      },
    //      error:function(error){
    //          alert('수업 추가 실패');
    //          console.log(error);
    //      }
    //  });
    success:function(response){
        if (response.status === 'success') {

            var newClass = response.newClass;

            if (!timetables[timetable_id]) {  // 추가: timetableData 배열 초기화 코드
                     timetables[timetable_id] = [];
            }

            timetables[timetable_id].push(newClass);

            drawTimetable(timetables[timetable_id]);

            alert('수업 추가 성공');
        } else {
            alert('수업 추가 실패');
        }
        console.log(response.message);
    },
});
});







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
                                $('#tableUpdatedAt').text(this.timetable_DATE)
                                output += '<li class="active"><a href="javascript:loadTimetableDetails('+this.timetable_ID+')" class="primary">'+this.name+'</a>';
                            }else{
                                output += '<li><a href="javascript:loadTimetableDetails('+this.timetable_ID+')">'+this.name+'</a>';
                            }
                            output += '</li>';
                        })
                        output += '<li class="extension"><a class="create">새 시간표 만들기</a></li>'
                        
                        $("div.menu ol").append(output);
                        

                }
            }
    })

}



// 생성 시간표 클릭시 정보 가져오기
function loadTimetableDetails(timetable_id){
    
    // 시간표 클릭시 active 효과 변경
    $("div.menu ol").on("click", "li:not(.extension) a", function(e) {
        $('div.menu ol li.active').removeClass('active');
    
        $(this).parent().addClass('active');
    });


    $.ajax({
        type:"GET",
        url: "loadTimetableDetails",
        data: {
            timetable_id : timetable_id
        },
        dataType : "json",
        success : function(rdata) {
            console.log('응답 데이터:', rdata);

            $('#tableName').text(rdata.timetable.name);
            $('#tableUpdatedAt').text(rdata.timetable.timetable_DATE);
            $('#tableName').attr('data-id',rdata.timetable.timetable_ID);
            
            var classes  = rdata.timetableDetails;

            timetables[timetable_id] = [];

            if (!timetables[timetable_id]) {  
                timetables[timetable_id] = [];
            }

            if (Array.isArray(classes)) {  // 추가: classes가 배열인지 확인
                timetables[timetable_id].push(...classes);
                drawTimetable(timetables[timetable_id]);
            }

            drawTimetable(timetables[timetable_id]);

        }
    })
}


// // canvas 작업코드 진행중
// const canvas =  document.getElementById('canvas');
// const ctx = canvas.getContext('2d');

// function draw(timestamp){
//     // const x = timestamp / 10 % 600;
//     ctx.clearRect(0, 0, 600, 50);
//     ctx.fillStyle = 'green';
//     ctx.fillRect(0, 0, 100, 50);
//     window.requestAnimationFrame(draw); 
// }

// window.requestAnimationFrame(draw);

// * 캔버스 진행중 *
function drawTimetable(timetableData) {
    // Canvas 설정
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');

    // 캔버스 초기화
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // 일주일 동안의 요일 설정
    const daysOfWeek = ['월', '화', '수', '목', '금'];

    // 각 요일에 대한 배경색 지정
	const subjectColors = ['#f2e8e8', '#ffe9e9', '#eff9cc', '#dcf2e9', '#dee8f6'];

    // 모든 수업 데이터를 반복하여 캔버스에 그림
    timetableData.forEach((classItem) => {
        // 요일별로 x좌표 설정 (여기서는 단순화하기 위해 요일마다 동일 간격으로 배치)
        const x = daysOfWeek.indexOf(classItem.day) * (canvas.width / daysOfWeek.length);

        // 시작 및 종료 시간 기준으로 y좌표 설정 (8시~20시 사이라고 가정)
        const yStart = (classItem.start_time - 8) * (canvas.height / 12);
        const yEnd = (classItem.end_time - 8) * (canvas.height / 12);

        // 배경색상 설정 후 사각형 그리기
        ctx.fillStyle = subjectColors[daysOfWeek.indexOf(classItem.day)];
        ctx.fillRect(x + 10, yStart +10 , canvas.width/daysOfWeek.length -20 , yEnd-yStart -20);

        // 텍스트 색상 설정 후 텍스트 그리기
        ctx.fillStyle = '#000000';
        
		// 과목명, 교수 이름 및 장소 출력 
		ctx.fillText(classItem.subject, x + 20, yStart + 30);
		ctx.fillText(classItem.professor, x + 20, yStart + 50);
		ctx.fillText(classItem.classroom, x + 20, yStart + 70);
		
        localStorage.setItem('timetableData', JSON.stringify(timetableData));
    });
}
