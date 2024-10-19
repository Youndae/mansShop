const idPattern = /^[A-Za-z0-9]{5,15}$/;
const pwPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
const emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let joinId = '';
let joinNickname = '';

$(function() {
    const currentYear = new Date().getFullYear();
    let options = '';
    for(let year = currentYear; year >= currentYear - 100; year--){
        options += "<option value=\"" + year + "\">" + year + "</option>";
    }
    $('#year').append(options);

    options = '';
    for(let month = 1; month < 13; month++)
        options += "<option value=\"" + month + "\">" + month + "</option>";
    $('#month').append(options);
    updateBirthDayOptions();

    $('#year, #month').change(updateBirthDayOptions);

    $('.email-select').change(function() {
        const selectValue = $('.email-select option:selected').val();
        if(selectValue === 'none')
            $('.mail-suffix').attr('type', 'text');
        else
            $('.mail-suffix').attr('type', 'hidden');
    })

    $("#userPw").on("propertychange change keyup paste input", function(){
        if($("#userPw").val().length < 8){
            $(".join-pw-overlap").text("비밀번호는 8자리 이상입니다.");
            $('#userPw').focus();
        }else if(pwPattern.test($("#userPw").val()) == false){
            $(".join-pw-overlap").text("비밀번호는 영어,특수문자,숫자가 포함되어야합니다.");
            $('#userPw').focus();
        }else
            $(".join-pw-overlap").text("");

    })

    $("#checkPw").on("propertychange change keyup paste input", function(){
        if($("#userPw").val() !== $("#checkPw").val()) {
            $('.join-checkPw-overlap').text("비밀번호가 일치하지 않습니다.");
            $('#checkPw').focus();
        }else{
            $('.join-checkPw-overlap').text("");
        }
    })
})

function updateBirthDayOptions() {
    const selectYear = parseInt($('#year option:selected').val());
    const selectMonth = parseInt($('#month option:selected').val());
    const lastDay = new Date(selectYear, selectMonth, 0).getDate();

    $('#day').empty();
    let dayOptions = '';
    for(let day = 1; day <= lastDay; day++)
        dayOptions += "<option value=\"" + day + "\">" + day + "</option>";

    $('#day').append(dayOptions);
}

function handleIdCheck() {
    const userId = $('#userId').val();
    $('.join-id-overlap').text('');
    if(!idPattern.test(userId)){
        $('.join-id-overlap').text('아이디는 5자 이상 15자 이하여야 합니다.');
        $('#userId').focus();
    }else {
        $.ajax({
            url: `/member/check-id?userId=${userId}`,
            type: 'GET',
            success: function (data) {
                if(data === 'OK'){
                    alert('사용할 수 있는 닉네임입니다.');
                    joinId = userId;
                }else if(data === 'DUPLICATE'){
                    $('.join-id-overlap').text('사용중인 아이디입니다.');
                    $('#userId').focus();
                }
            }
        })
    }
}

function handleNicknameCheck() {
    const nickname = $('#nickname').val();
    $('.join-nickname-overlap').text('');
    if(nickname === '' || nickname === ' ') {
        $('.join-nickname-overlap').text('닉네임을 입력해주세요');
        $('#nickname').focus();
    }else {
        $.ajax({
            url: `/member/check-nickname?nickname=${nickname}`,
            type: 'GET',
            success: function(data) {
                if(data === 'OK'){
                    alert('사용할 수 있는 닉네임입니다.');
                    joinNickname = nickname;
                }else if(data === 'DUPLICATE'){
                    $('.join-nickname-overlap').text('사용중인 닉네임입니다.');
                    $('#nickname').focus();
                }
            }
        })
    }
}

function handleJoinBtnOnClick() {
    const userId = $('#userId').val();
    const userPw = $('#userPw').val();
    const checkPw = $('#checkPw').val();
    const username = $('#username').val();
    const nickname = $('#nickname').val();
    const phone = $('#phone').val();
    const birthYear = $('#year option:selected').val();
    const birthMonth = $('#month option:selected').val();
    const birthDay = $('#day option:selected').val();
    const emailPrefix = $('#email').val();
    const emailSuffixSelectValue = $('.email-select option:selected').val();
    let email = `${emailPrefix}@${emailSuffixSelectValue}`;
    if(emailSuffixSelectValue === 'none'){
        const emailSuffix = $('.mail-suffix').val();
        email = `${emailPrefix}@${emailSuffix}`;
    }

    clearAllJoinOverlap();
    if (userId === '' || userId === ' ') {
        $('.join-id-overlap').text('아이디를 입력하세요');
        $('#userId').focus();
    } else if (userId !== joinId) {
        $('.join-id-overlap').text('아이디 중복체크를 해주세요');
        $('#userId').focus();
    } else if (userPw.length < 8) {
        $(".join-pw-overlap").text("비밀번호는 8자리 이상입니다.");
        $('#userPw').focus();
    } else if (!pwPattern.test(userPw)) {
        $(".join-pw-overlap").text("비밀번호는 영어,특수문자,숫자가 포함되어야합니다.");
        $('#userPw').focus();
    } else if (userPw !== checkPw) {
        $(".join-checkPw-overlap").text("비밀번호가 일치하지 않습니다.");
        $("#checkPw").focus();
    }else if(username === '' || username === ' '){
        $('.join-username-overlap').text('이름을 입력해주세요');
        $('#username').focus();
    }else if(nickname !== '' && nickname !== joinNickname) {
        $('.join-nickname-overlap').text('닉네임 중복 체크를 해주세요');
        $('#nickname').focus();
    }else if(phone === '') {
        $('.join-phone-overlap').text('연락처를 입력해주세요');
        $('#phone').focus();
    }else if(!phonePattern.test(phone)){
        $('.join-phone-overlap').text('유효한 연락처가 아닙니다.');
        $('#phone').focus();
    }else if(emailPrefix === '') {
        $('.join-email-overlap').text('이메일을 입력해주세요');
        $('#email').focus();
    }else if(emailSuffixSelectValue === 'none' && $('.mail-suffix').val() === '') {
        $('.join-email-overlap').text('이메일 주소를 입력해주세요');
        $('.mail-suffix').focus();
    }else if(!emailPattern.test(email)){
        $('.join-email-overlap').text('유효한 이메일 주소가 아닙니다.');
        $('.mail-suffix').focus();
    }else {
        const birth = `${birthYear}/${birthMonth}/${birthDay}`;
        const data = {
            userId: userId,
            userPw: userPw,
            username: username,
            nickname: nickname || null,
            phone: phone,
            birth: birth,
            email: email,
        };

        $.ajax({
            url: '/member/join',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if(data === 'SUCCESS')
                    location.href='/member/login';
                else
                    alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요');
            }
        })
    }
}

function clearAllJoinOverlap() {
    $('.join-id-overlap').empty();
    $('.join-pw-overlap').empty();
    $('.join-checkPw-overlap').empty();
    $('.join-username-overlap').empty();
    $('.join-nickname-overlap').empty();
    $('.join-phone-overlap').empty();
    $('.join-email-overlap').empty();
}