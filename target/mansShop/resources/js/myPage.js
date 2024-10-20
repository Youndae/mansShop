const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
const emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;
let modifyDefaultNickname = '';

$(function() {
    modifyDefaultNickname = $("#nickname").val();

    $(window).click(function (e) {
        if($(e.target).is(".review-modal")) {
            $(".review-modal").fadeOut();
            $(".modal-content").empty();
        }
    });

    $("#select-email-suffix").change(function () {
        const selectValue = $("#select-email-suffix option:selected").val();
        const suffixInput = $(".mail-suffix");
        if(selectValue === 'none')
            suffixInput.attr('type', 'text');
        else {
            suffixInput.attr('type', 'hidden');
            suffixInput.val('');
        }
    })
})


function deleteProductQnA() {
    const qnaId = getQnAId();

    $.ajax({
        url: `/my-page/qna/product/${qnaId}`,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.href = '/my-page/qna/product';
            else
                alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function productPageLink(obj) {
    const productId = obj.value;
    location.href = `/product/${productId}`;
}

function getQnAId() {
    return $("#qnaId").val();
}

function insertMemberQnA() {
    location.href='/my-page/qna/member/insert';
}

function handleInsertMemberQnABtn() {
    const title = $("#title").val();
    const content = $("#content").val();
    const classification = $("#member-qna-select-box option:selected").val();

    const data = {
        title: title,
        content: content,
        classificationId: classification,
    }

    $.ajax({
        url:'/my-page/qna/member',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            location.href=`/my-page/qna/member/${data}`;
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function patchMemberQnA() {
    const qnaId = getQnAId();

    location.href = `/my-page/qna/member/patch/${qnaId}`;
}

function deleteMemberQnA() {
    const qnaId = getQnAId();

    $.ajax({
        url: `/my-page/qna/member/${qnaId}`,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.href=`/my-page/qna/member`;
            else
                alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요.');
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function patchMemberQnAReplyBtnOnClick(obj) {
    const replyId = obj.value;
    const elements = $(`.member-reply-${replyId}`);

    const str = "<div class=\"qna-reply-modify-input\">" +
                    "<textarea class=\"qna-reply-modify-text\"></textarea>" +
                    "<div class=\"qna-reply-modify-btn\">" +
                        "<button class=\"default-btn\" value=\"" + replyId + "\" onclick=\"patchQnAReply(this)\">수정</button>" +
                    "</div>" +
                "</div>";

    const modifyBtn = $(`.member-reply-${replyId} button`);
    modifyBtn.attr('onclick', 'closeModifyInput(this)');
    modifyBtn.text('닫기');

    elements.append(str);
}

function closeModifyInput(obj) {
    const replyId = obj.value;

    const modifyBtn = $(`.member-reply-${replyId} button`);
    modifyBtn.attr('onclick', 'patchMemberQnAReplyBtnOnClick(this)');
    modifyBtn.text('댓글 수정');

    $(".qna-reply-modify-input").remove();
}

function patchQnAReply(obj) {
    const replyId = obj.value;
    const content = {
        content: $(".qna-reply-modify-text").val()
    };

    $.ajax({
        url: `/my-page/qna/member/reply/${replyId}`,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(content),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log('result data : ', data);
            if(data === 'SUCCESS')
                location.reload();
            else
                alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function insertMemberQnAReply() {
    const qnaId = getQnAId();
    const inputText = $("#reply-input").val();
    const data = {
        id: qnaId,
        content: inputText,
    };

    $.ajax({
        url: `/my-page/qna/member/reply`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log('result data : ', data);
            if(data === 'SUCCESS')
                location.reload();
            else
                alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function handlePatchMemberQnABtn() {
    const qid = $("#qid").val();
    const title = $("#title").val();
    const content = $("#content").val();
    const classification = $("#member-qna-select-box option:selected").val();
    const data = {
        title: title,
        content: content,
        classificationId: classification,
    }

    $.ajax({
        url: `/my-page/qna/member/${qid}`,
        type: 'PATCH',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            location.href=`/my-page/qna/member/${data}`;
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function handleReviewOnClick(obj) {
    const rid = $(obj).attr('id');

    console.log('review modal id : ', rid);

    $.getJSON(`/my-page/review/${rid}`, function(data) {
        console.log('review getJSON data : ', data);
        let str = "<div class=\"modal-content-header\">" +
                        "<h2>" +
                            data.productName +
                        "</h2>" +
                        "<button type=\"button\" value=\"" + data.id + "\" class=\"default-btn mypage-modal-header-btn\" onclick=\"handlePatchReview(this)\"'>" +
                            "수정" +
                        "</button>" +
                    "</div>" +
                    "<div class=\"modal-content-content\">" +
                        "<div class=\"modal-review\">" +
                            data.content +
                        "</div>";

        if(data.replyContent !== null) {
            str += "<div class=\"modal-review-reply-content\">" +
                        "<div class=\"modal-reply-header\">" +
                            "<strong>관리자</strong>" +
                            "<small>" +
                                parseDate(data.replyUpdatedAt) +
                            "</small>" +
                        "</div>" +
                        "<div class=\"modal-reply-content\">" +
                            "<span>" +
                                data.replyContent +
                            "</span>" +
                        "</div>" +
                    "</div>";
        }

        str += "</div>";

        $(".modal-content").append(str);
    })

    $(".review-modal").fadeIn();
}

function parseDate(date) {
    return date.year + "-" +
        padToTwoDigits(date.monthValue) + "-" +
        padToTwoDigits(date.dayOfMonth);
}

function padToTwoDigits(number) {
    return number.toString().padStart(2, '0');
}

function handlePatchReviewBtnOnClick(obj) {
    const reviewId = obj.value;
    const content = {
        content: $("#content").val(),
    };

    $.ajax({
        url: `/my-page/review/${reviewId}`,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(content),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(data === 'SUCCESS')
                location.href='/my-page/review';
            else
                alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
        },
        error: function(request, status, error) {
            alert('code : ' + request.status + '\n'
            + 'message : ' + request.responseText
            + '\n' + 'error : ' + error);
        }
    })
}

function handlePatchReview(obj) {
    const rid = obj.value;

    location.href = `/my-page/review/patch/${rid}`;
}

function deleteReviewBtnOnClick(obj) {
    const rid = $(obj).attr('id');

    $.ajax({
        url: `/my-page/review/${rid}`,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
            else
                alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
        },
        error: function(request, status, error) {
            alert('code : ' + request.status + '\n'
                + 'message : ' + request.responseText
                + '\n' + 'error : ' + error);
        }
    })
}

function memberCheckBtnOnClick() {
    const userId = $("#username").val();
    const pwElements = $("#userPw");
    const userPw = pwElements.val();
    const pwPattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{8,16}$/;
    const overlapElements = $(".info-check-password .overlap");

    if(userPw === '') {
        overlapElements.text('비밀번호를 입력해주세요.')
        pwElements.focus();
    }else if(!pwPattern.test(userPw)){
        overlapElements.text('유효한 비밀번호가 아닙니다.');
        pwElements.focus();
    }else if(userPw.length > 8 && pwPattern.test(userPw)){
        const data = {
            userId : userId,
            userPw: userPw,
        };

        $.ajax({
            url: '/my-page/member-check',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if(data === 'SUCCESS')
                    location.href='/my-page/member/patch-info';
                else{
                    overlapElements.text('유효한 비밀번호가 아닙니다.');
                    pwElements.focus();
                }
            },
            error: function(request, status, error) {
                alert('code : ' + request.status + '\n'
                    + 'message : ' + request.responseText
                    + '\n' + 'error : ' + error);
            }
        })
    }

}

let modifyInfoNicknameCheck = false;

function checkNickname() {
    const nickname = $("#nickname").val();

    if(nickname === '') {
        $(".nickname-overlap").text('닉네임을 입력해주세요')
        $("#nickname").focus();
    }else {
        $.ajax({
            url: '/my-page/member/nickname-check',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({nickname: nickname}),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if(data === 'OK'){
                    $(".nickname-overlap").text('사용할 수 있는 닉네임입니다.');
                    modifyInfoNicknameCheck = true;
                }else if(data === 'DUPLICATE') {
                    $(".nickname-overlap").text('사용할 수 없는 닉네임입니다.');
                    modifyInfoNicknameCheck = false;
                }else
                    alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
            },
            error: function(request, status, error) {
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })
    }
}

function modifyInfoSubmit() {
    const nickname = $("#nickname").val();
    const phone = $("#phone").val();
    const emailPrefix = $("#email").val();
    const emailSelectValue = $("#select-email-suffix option:selected").val();
    const emailSuffix = $(".mail-suffix").val();

    clearModifyInfoOverlap();

    if(!modifyInfoNicknameCheck && nickname !== '' && nickname !== modifyDefaultNickname){
        $(".nickname-overlap").text('중복체크를 해주세요')
        $("#nickname").focus();
    }else if(!phonePattern.test(phone)){
        $(".phone-overlap").text('유효하지 않은 연락처입니다.');
        $("#phone").focus();
    }else if(emailPrefix === ''){
        $(".email-overlap").text('이메일을 입력해주세요');
        $("#email").focus();
    }else if(emailSelectValue === 'none' && emailSuffix === ''){
        $(".email-overlap").text('이메일 주소를 입력해주세요');
        $(".mail-suffix").focus();
    }else if(emailSelectValue === 'none' && !emailPattern.test(emailSuffix)){
        $(".email-overlap").text('정상적이지 않은 이메일주소입니다.');
        $(".mail-suffix").focus();
    }else {
        let suffix = emailSelectValue;
        if(emailSelectValue === 'none')
            suffix = $(".mail-suffix").val();
        const email = `${emailPrefix}@${suffix}`;

        const data = {
            nickname: nickname === '' ? null : nickname,
            phone: phone,
            email: email,
        };

        $.ajax({
            url: '/my-page/member',
            type: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if(data === 'SUCCESS'){
                    alert('수정이 완료되었습니다.');
                    location.href='/my-page/member/info';
                }else
                    alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
            },
            error: function(request, status, error) {
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        });
    }
}

function clearModifyInfoOverlap() {
    $(".nickname-overlap").text('')
    $(".phone-overlap").text('');
    $(".email-overlap").text('');
}

function deleteLikeBtnOnClick(obj) {
    const likeId = $(obj).attr('id');

    $.ajax({
        url: `/my-page/like/${likeId}`,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(data === 'SUCCESS')
                location.reload();
            else
                alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
        }
    })
}