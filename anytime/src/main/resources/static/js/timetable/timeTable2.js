$(document).ready(function () {
    var semester = '2023년 2학기';
    
    getTimetableList(semester);

   


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
                console.log(response);
                if(response != null ){
                    alert('새 시간표 생성 성공');

                    $('#tableName').text(response.name);
                    $('#tableUpdatedAt').text(response.timetable_DATE);
                    $('#tableName').attr('data-id', response.timetable_ID);
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
        success:function(response){
             alert('수업 추가 성공');
             console.log(response);
             location.reload(); 
         },
         error:function(error){
             alert('수업 추가 실패');
             console.log(error);
         }
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
            $('#tableName').text(rdata.timetable.name);
            $('#tableUpdatedAt').text(rdata.timetable.timetable_DATE);
            $('#tableName').attr('data-id',rdata.timetable.timetable_ID);
        }
    })
}


