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

    $("div.menu ol").on("click","li.extension", function() {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        semester = $("#semesters option:selected").text();
        output = "";

        $.ajax({
            url: 'createNewTimeTable',
            type: 'POST',
            data: { 
                'semester': semester  // 여기에 실제 값을 넣으세요.
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
                    alert(response.name);
                    alert(response.timetable_DATE);
                    $('div.menu ol li').removeClass('active');
                    output += '<li class="active"><a href="/timetable/2018/1/8599353">' + response.name + '</a></li>'                    
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
  
});

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
                            if(this.status ==1){
                                output += '<li class="active"><a href="/'+this.timetable_ID+'" class="primary">';
                                $('#tableUpdatedAt').text(this.timetable_DATE)
                            }else{
                                output += '<li><a href="/'+this.timetable_ID+'">';
                            }
                            output += this.name+'</a></li>';
                        })
                        output += '<li class="extension"><a class="create">새 시간표 만들기</a></li>'
                        
                        $("div.menu ol").append(output);
                        $('#tableName').text($("div.menu li.active a").text());
                        

                }
            }
    })



}
