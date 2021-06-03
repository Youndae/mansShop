$(document).ready(function(){

    $(function(){
        $("#IdCheck").click(function(){
            var userId = {userId : $("#userId").val(),};

            console.log("userId : " + userId);
            if(userId.userId == ""){
                $("#overlap").text("아이디를 입력하세요.");

            }else if(userId.userId != "" && (userId.userId < "0" || userId.userId > "9") && (userId.userId < "A" || userId.userId > "Z") && (userId.userId < "a" || userId.userId > "z")) {
                $("#overlap").text("한글 및 특수문자는 사용하실 수 없습니다.");

            }else{
                $.ajaxSettings.traditional = true;
                $.ajax({
                    url: "/member/CheckUserId",
                    type: "post",
                    data: userId,
                    success : function(data){
                        if(data == 1){
                            $("#overlap").text("사용중인 아이디입니다.");
                        }else{
                            $("#overlap").text("사용가능한 아이디입니다.");
                        }

                    },
                    error : function(request, status, error){
                        alert("code:" + request.status + "\n"
                            + "message : " + request.responseText
                            + "\n" + "error : " +error);
                    }
                });
            }
        })
    });


    var today = new Date();
    var today_year = today.getFullYear();
    var start_year = today_year - 100;
    var index = 0;

    for(var y = today_year; y >= start_year; y--){
        document.getElementById('select_year').options[index] = new Option(y, y);
        index++;
    }

    index = 0;

    for(var m = 1; m <= 12; m++){
        document.getElementById('select_month').options[index] = new Option(m, m);
        index++;
    }

    lastday();

});

function lastday(){
    var Year = $("#select_year").val();
    var Month = $("#select_month").val();
    var day = new Date(new Date(Year, Month, 1) - 86400000).getDate();
    var dayIndex_len = document.getElementById('select_day').length;
    if(day > dayIndex_len){
        for(var i = (dayIndex_len+1); i <= day; i++){
            document.getElementById('select_day').options[i - 1] = new Option(i, i);
        }
    }else if(day < dayIndex_len){
        for(var i = dayIndex_len; i >= day; i--){
            document.getElementById('select_day').options[i] = null;
        }
    }
}

function checkPassword(){
    var originPw = $("#userPw").val();
    var checkPw = $("#checkUserPw").val();

    if(originPw != checkPw){
        //같지 않다는 메세지 출력.
    }
}

