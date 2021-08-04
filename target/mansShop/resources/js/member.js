var idPattern = /^[A-Za-z0-9]{5,15}$/;
var pwPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
var emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
var phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function(){

    $("#userPw").on("propertychange change keyup paste input", function(){
        if($("#userPw").val().length < 8){
            $("#pwOverlap").text("비밀번호는 8자리 이상입니다.");
            $("#pwStat").val("");
        }else if(pwPattern.test($("#userPw").val()) == false){
            $("#pwOverlap").text("비밀번호는 영어,특수문자,숫자가 포함되어야합니다.");
            $("#pwStat").val("");
        }else{
            $("#pwOverlap").text("");
            $("#pwStat").val("");
        }
    })

    $("#checkUserPw").on("propertychange change keyup paste input", function(){
        if($("#userPw").val() != $("#checkUserPw").val()) {
            $("#pwCheckOverlap").text("비밀번호가 일치하지 않습니다.");
            $("#pwStat").val("");
        }else{
            $("#pwCheckOverlap").text("");
            $("#pwStat").val("1");
        }
    })

    $("#userEmail").on("propertychange change keyup paste input", function(){
        if(emailPattern.test($("#userEmail").val()) == false){
            $("#emailOverlap").text("유효한 주소가 아닙니다.");
            $("#mailStat").val("");
        }else{
            $("#emailOverlap").text("");
            $("#mailStat").val("1");
        }
    })

    $("#userPhone").on("propertychange change keyup paste input", function(){
        if(phonePattern.test($("#userPhone").val()) == false){
            $("#phoneOverlap").text("유효한 번호가 아닙니다.");
            $("#phoneStat").text("");
        }else{
            $("#phoneOverlap").text("");
            $("#phoneStat").val("1");
        }
    })


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
                            $("#idStat").val("0");
                        }else{
                            $("#idOverlap").text("사용가능한 아이디입니다.");
                            $("#idStat").val("1");
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

    $("#select_year").on("propertychange change keyup paste input", function(){
        console.log("select Year");
        lastday();
    })

    $("#select_month").on("propertychange change keyup paste input", function(){
        lastday();
    })

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

$(function(){
    $("#join").click(function() {
        var formObj = $("form[role='form']");
        var year = $("#select_year").val();
        var month = $("#select_month").val();
        var day = $("#select_day").val();
        var select_date = [year, month, day].join('-');
        var str = "<input type='hidden' name='userBirth' value='" + select_date + "'>";


        formObj.append(str);

        console.log("birth : " + $('input[name=userBirth]').val());


        if($("#userId").val() == ""){
            console.log("id null");
            $("#idOverlap").text("아이디를 입력하세요");
            $("#userId").focus();
        }else if($("#idStat").val() == ""){
            console.log("id stat null");
            $("#idOverlap").text("아이디 중복체크를 해주세요");
            $("#userId").focus();
        }else if($("#idStat").val() == "0"){
            console.log("id stat 0");
            $("#userId").focus();
        }else if($("#userPw").val() == ""){
            console.log("pw null");
            $("#pwOverlap").text("비밀번호를 입력하세요");
            $("#userPw").focus();
        }else if($("#checkUserPw").val() == ""){
            console.log("checkPw null");
            $("#pwCheckOverlap").text("비밀번호를 다시 입력하세요");
            $("#checkUserPw").focus();
        }else if($("#pwStat").val() != "1"){
            console.log("pwOverlap not null");
            $("#userPw").focus();
        }else if($("#userName").val() == ""){
            console.log("Name Null");
            $("#nameOverlap").text("이름을 입력하세요");
            $("#userName").focus();
        }else if($("#userEmail").val() == "") {
            console.log("Email null || overlap not null");
            $("#emailOverlap").text("이메일을 입력하세요");
            $("#userEmail").focus();
        }else if($("#mailStat").val() != "1"){
            $("#userEmail").focus();
        }else if($("#userPhone").val() == "") {
            console.log("Phone null || overlap not null");
            $("#phoneOverlap").text("연락처를 입력하세요");
            $("#userPhone").focus();
        }else if($("#phoneStat").val() != "1"){
            $("#userPhone").focus();
        }else{
            alert("가입 고고");
        }

        formObj.submit();

    });

    $(".loginBtn").on("click", function(e){
        e.preventDefault();
        $("#loginForm").submit();
    });
});
