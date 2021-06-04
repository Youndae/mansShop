var idPattern = /^[A-Za-z0-9]{5,15}$/;
var pwPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
var emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
var phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;

$(document).ready(function(){

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(function(){
        $("#IdCheck").click(function(){
            var UserId ={
                UserId : $("#userId").val(),
            };

            if(UserId.UserId == ""){
                $("#idOverlap").text("아이디를 입력하세요");
            }else if(UserId.UserId != "" && idPattern.test(UserId.UserId) == false){
                $("#idOverlap").text("영문자와 숫자를 사용한 5~15 자리만 가능합니다.");

            }else{
                $.ajax({
                    url: "/member/checkUserId",
                    type: "post",
                    data: UserId,
                    dataType: "json",
                    beforeSend : function(xhr){
                        xhr.setRequestHeader(header, token);
                    },
                    success : function(data){
                        console.log("data : " + data);
                        if(data == 1){
                            $("#idOverlap").text("사용중인 아이디입니다.");
                        }else{
                            $("#idOverlap").text("사용가능한 아이디입니다.");
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

    if(originPw.length < 8){
        $("#pwOverlap").text("비밀번호는 8자리 이상입니다.");
    }else if(originPw != checkPw){
        $("#pwOverlap").text("비밀번호가 일치하지 않습니다.");
    }else if(pwPattern.test(originPw) == false){
        $("#pwOverlap").text("비밀번호는 영어,특수문자,숫자가 포함되어야합니다.");
    }else{
        $("#pwOverlap").text("");
    }
}

function checkEmail(){
    var email = $("#userEmail").val();

    if(emailPattern.test(email) == false){
        $("#emailOverlap").text("유효한 주소가 아닙니다.");
    }else{
        $("#emailOverlap").text("");
    }
}

function checkPhone(){
    var phoneNo = $("#userPhone").val();

    if(phonePattern.test(phoneNo) == false){
        $("#phoneOverlap").text("유효한 번호가 아닙니다.");
    }else{
        $("#phoneOverlap").text("");
    }
}