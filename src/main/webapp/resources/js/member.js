var idPattern = /^[A-Za-z0-9]{5,15}$/;
var pwPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
var emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
var phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var searchType = "phone";
var timer = true;

$(document).ready(function(){

    $(".searchIdBtn").click(function(){
        var name = $('input[name="userName"]').val();
        var phone = null;
        var mail = null;

        $('.nameOverlap').text('');
        $('.overlap').text('');

        if(searchType == "phone") {
            phone = $('input[name="userPhone"]').val();
        }else if(searchType == "mail") {
            mail = $('input[name="userEmail"]').val();
        }

        if(name == null && name == undefined && name == '') {
            $('.nameOverlap').text("이름을 입력해주세요");
            $('input[name="userName"]').focus();
        }else if(searchType == "phone" && phonePattern.test(phone) == false) {
            $('.overlap').text("유효한 번호가 아닙니다.");
            $('input[name="userPhone"]').focus();
        }else if(searchType == "mail" && emailPattern.test(mail) == false){
            $('.overlap').text("유효한 메일 주소가 아닙니다.");
            $('input[name="userEmail"]').focus();
        }else{
            var formData = {
                userName : name,
                userPhone : phone,
                userEmail : mail,
            }

            $.ajax({
                url: '/member/searchId',
                method : 'post',
                data: formData,
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success : function(data){
                    if(data == '')
                        $('.searchOverlap').text("일치하는 정보가 없습니다.");
                    else{
                        successSearchId(data);
                    }
                }
            })
        }
    })

    $('.searchPwBtn').click(function(){
        var uid = $('input[name="userId"]').val();
        var name = $('input[name="userName"]').val();
        var mail = $('input[name="userEmail"]').val();
        $('.idOverlap').text('');
        $('.nameOverlap').text('');
        $('.overlap').text('');

        if(uid == null || idPattern.test(uid) == false) {
            $('.idOverlap').text("유효한 아이디가 아닙니다.");
            $('input[name="userId"]').focus();
        }else if(name == null && name == undefined && name == '') {
            $('.nameOverlap').text("이름을 입력해주세요");
            $('input[name="userName"]').focus();
        }else if(searchType == "mail" && emailPattern.test(mail) == false){
            $('.overlap').text("유효한 메일 주소가 아닙니다.");
            $('input[name="userEmail"]').focus();
        }else{
            var formData = {
                userId : uid,
                userName : name,
                userEmail : mail,
            }

            $.ajax({
                url: '/member/searchPw',
                method: 'post',
                data: formData,
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    if(data == '')
                        $(".searchOverlap").text("일치하는 정보가 없습니다.");
                    else if(data == 'error')
                        alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요');
                    else if(data == 'success'){

                        document.getElementById('userId').disabled = true;
                        document.getElementById('userName').disabled = true;
                        document.getElementById('userEmail').disabled = true;


                        var str = "<div class=\"certification\">" +
                            "<div class=\"form-group certifi-input-area\">" +
                            "<div><label>인증번호</label></div>" +
                            "<input class=\"form-control\" type=\"text\" name=\"certifi-input\">" +
                            "</div>" +
                            "<div class=\"certifi-overlap\"></div>" +
                            "<div class=\"form-group timer\">" +
                            "<div class=\"timer-area\" id=\"timerArea\">" +
                            "</div>" +
                            "<div class=\"timer-reset-btn-area\">" +
                            "<button id=\"reset-timer-btn\" onclick=\"extensionTimer()\">시간 연장</button>" +
                            "</div>" +
                            "</div>" +
                            "<div class=\"input-btn-area\">" +
                            "<button class=\"certifi-btn\" id=\"certifi-btn\">인증하기</button>" +
                            "</div>" +
                            "</div>";

                        $('.search_pw_form').append(str);

                        setTimer();
                    }
                }
            })
        }
    })

    $('input[name="searchId"]').change(function(){
        var value = $(this).val();

        var str = getSearchString(value);

        $('.form-area').append(str);

    });

    $('input[name="searchPw"]').change(function(){
        var value = $(this).val();

        var str = getSearchString(value);

        $('.form-area').append(str);

    })



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

    $(".joinBtn").on('click', function(e){
        e.preventDefault();
        location.href='/member/join';
    })

    $(".searchId").on('click', function(e){
        e.preventDefault();
        location.href = '/member/searchId';
    })

    $(".searchPw").on('click', function(e){
        e.preventDefault();
        location.href = '/member/searchPw';
    })

    $(".resetPwBtn").on('click', function(e){
        e.preventDefault();
        $("#resetPwForm").submit();
    })
});

/*$(function(){
    $("#certifi-btn").click(function(){
        console.log("certify btn click");
        console.log("certify No is " + $('input[name="certifi-input"]').val());
    })
})*/

$(document).on('click', "#certifi-btn", function(){
    var uid = $('#userId').val();
    var certify = $('input[name="certifi-input"]').val();

    if(certify == '' || certify == null){
        $('.certifi-overlap').text('인증번호를 입력해주세요.');
    }else if(certify < 100000){
        $('.certifi-overlap').text('유효하지 않은 인증번호입니다.');
    }else{
        var formData = {
            userId : uid,
            cno : certify,
        };

        $.ajax({
            url: '/member/certifyPw',
            method: 'post',
            data: formData,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                console.log("certify data : " + data);
                if(data == 'fail')
                    $('.certifi-overlap').text('유효하지 않은 인증번호입니다.');
                else if(data == 'error')
                    alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요');
                else if(data == 'success'){
                    $('#formId').val(uid);
                    $('#formCno').val(certify);

                    $('#pwForm').submit();
                }

            }
        })
    }
})


function successSearchId(uid){
    $('.search-radio-area').remove();
    $('.search_id_form').remove();

    var str = "<div class=\"search-id-result\">" +
        "<h2>아이디는 <br/>" + uid + "<br/>입니다.</h2>" +
        "</div>" +
        "<div class=\"btn-area\">" +
        "<button class=\"loginFormBtn\" onclick=\"loginFormBtn()\">로그인</button>" +
        "<button class=\"searchPw\" onclick=\"searchPwBtn()\">비밀번호 찾기</button>" +
        "</div>";

    $('.login_content').append(str);
}

function loginFormBtn(){
    location.href = '/member/login';
}

function searchPwBtn(){
    location.href= '/member/searchPw';
}

function getSearchString(value){
    var str = "";

    if(value == "phone"){
        $('.userEmail').remove();

        str = "<div class=\"form-group userPhone\">" +
            "<div><label>휴대폰번호</label></div>" +
            "<input class=\"form-control\" placeholder=\"-를 제외한 숫자만 입력해주세요\" type=\"text\" name=\"userPhone\">" +
            "<div class=\"overlap\"></div>" +
            "</div>";

        searchType = "phone";
    }else if(value == "email"){
        $('.userPhone').remove();

        str = "<div class=\"form-group userEmail\">" +
            "<div><label>이메일</label></div>" +
            "<input class=\"form-control\" type=\"text\" id=\"userEmail\" name=\"userEmail\">" +
            "<div class=\"overlap\"></div>" +
            "</div>";

        searchType = "mail";
    }

    return str;
}

var x;

function setTimer(){
    var time = 300;
    var min = "";
    var sec = "";

    x = setInterval(function(){
        min = parseInt(time / 60);
        sec = time % 60;

        document.getElementById('timerArea').innerHTML = min + "분 " + sec + "초";
        time --;

        if(time < 0){
            clearInterval(x);
            document.getElementById('timerArea').innerHTML = "시간 초과";
            timer = false;
        }
    }, 1000);
}

function extensionTimer(){
    clearInterval(x);
    setTimer();

}