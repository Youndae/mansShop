const idPattern = /^[A-Za-z0-9]{5,15}$/;
const emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let searchType = "phone";
let timer = true;

$(document).ready(function(){
    $(".join-btn").on('click', function(e) {
        e.preventDefault();
        location.href='/member/join';
    })

    $(".searchIdBtn").click(function(){
        const name = $('input[name="userName"]').val();
        let phone = null;
        let mail = null;

        $('.nameOverlap').text('');
        $('.overlap').text('');

        if(searchType === "phone") {
            phone = $('input[name="userPhone"]').val();
        }else if(searchType === "mail") {
            mail = $('input[name="userEmail"]').val();
        }

        if(name == null && name === undefined && name === '') {
            $('.nameOverlap').text("이름을 입력해주세요");
            $('input[name="userName"]').focus();
        }else if(searchType === "phone" && !phonePattern.test(phone)) {
            $('.overlap').text("유효한 번호가 아닙니다.");
            $('input[name="userPhone"]').focus();
        }else if(searchType === "mail" && !emailPattern.test(mail)){
            $('.overlap').text("유효한 메일 주소가 아닙니다.");
            $('input[name="userEmail"]').focus();
        }else{
            const data = {
                                userName : name,
                                userPhone : phone || null,
                                userEmail : mail || null,
                            }

            $.ajax({
                url: '/member/search-id',
                method : 'post',
                contentType: 'application/json',
                data: JSON.stringify(data),
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success : function(data){
                    if (data === 'FAIL' || data === 'NOTFOUND')
                        $('.searchOverlap').text("일치하는 정보가 없습니다.");
                    else {
                        successSearchId(data);
                    }
                }
            })
        }
    })

    $('.searchPwBtn').click(function(){
        const uid = $('input[name="userId"]').val();
        const name = $('input[name="userName"]').val();
        const mail = $('input[name="userEmail"]').val();
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
            const data = {
                userId : uid,
                userName : name,
                userEmail : mail,
            }

            $.ajax({
                url: '/member/search-pw',
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(data),
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    if(data === 'FAIL')
                        $(".searchOverlap").text("일치하는 정보가 없습니다.");
                    else if(data === 'ERROR')
                        alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요');
                    else if(data === 'SUCCESS'){
                        document.getElementById('userId').disabled = true;
                        document.getElementById('userName').disabled = true;
                        document.getElementById('userEmail').disabled = true;

                        const str = "<div class=\"certification\">" +
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
        const value = $(this).val();

        const str = getSearchString(value);

        $('.form-area').append(str);

    });

    $('input[name="searchPw"]').change(function(){
        const value = $(this).val();

        const str = getSearchString(value);

        $('.form-area').append(str);
    })
});

$(function(){
    $(".loginBtn").on("click", function(e){
        e.preventDefault();
        $("#loginForm").submit();
    });

    $(".joinBtn").on('click', function(e){
        e.preventDefault();
        location.href='/member/join';
    })

    $(".search-id-btn").on('click', function(e){
        e.preventDefault();
        location.href = '/member/search-id';
    })

    $(".search-pw-btn").on('click', function(e){
        e.preventDefault();
        location.href = '/member/search-pw';
    })

    $(".resetPwBtn").on('click', function(e){
        e.preventDefault();
        $("#resetPwForm").submit();
    })
});

$(document).on('click', "#certifi-btn", function(){
    const uid = $('#userId').val();
    const certify = $('input[name="certifi-input"]').val();

    if(certify == '' || certify == null){
        $('.certifi-overlap').text('인증번호를 입력해주세요.');
    }else if(certify < 100000){
        $('.certifi-overlap').text('유효하지 않은 인증번호입니다.');
    }else{
        const data = {
                            userId : uid,
                            cno : certify,
                        };

        $.ajax({
            url: '/member/certify-pw',
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data === 'FAIL')
                    $('.certifi-overlap').text('유효하지 않은 인증번호입니다.');
                else if(data === 'ERROR')
                    alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요');
                else if(data === 'SUCCESS'){
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

    const str = "<div class=\"search-id-result\">" +
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
    location.href= '/member/search-pw';
}

function getSearchString(value){
    let str = "";

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

let x;

function setTimer(){
    let time = 300;
    let min = "";
    let sec = "";

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