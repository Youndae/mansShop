const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

$(function () {
    $(".admin-qna-select-box").change(function () {
        const type = $(".admin-qna-select-box option:selected").val();
        const path = window.location.pathname;

        if(path.startsWith('/admin/qna/product'))
            location.href = `/admin/qna/product/${type}`;
        else if(path.startsWith('/admin/qna/member'))
            location.href = `/admin/qna/member/${type}`;
    })
})

function productQnAListOnClick(obj) {
    const qnaId = $(obj).attr('data-qna-id');

    location.href = `/admin/qna/product/detail/${qnaId}`;
}

function memberQnAListOnClick(obj) {
    const qnaId = $(obj).attr('data-qna-id');

    location.href = `/admin/qna/member/detail/${qnaId}`;
}

function handleProductQnAReplyModifyBtn(obj) {
    handleModifyBtn(obj, 'modifyProductQnAReply(this)', 'handleProductQnAModifyCloseBtn(this)');
}

function handleMemberQnAReplyModifyBtn(obj) {
    handleModifyBtn(obj, 'modifyMemberQnAReply(this)', 'handleMemberQnAModifyCloseBtn(this)');
}

function handleModifyBtn(obj, onclickText, closeText) {
    const replyId = obj.value;

    const content = $(`.detail-reply-${replyId} .qna-reply-content p`).text();
    const inputElements = $(`.detail-reply-${replyId} .qna-reply-modify-input`);
    const str = "<div class=\"qna-reply-modify-input\">" +
                    "<textarea class=\"qna-reply-modify-text\">" +
                        content +
                    "</textarea>" +
                    "<div class=\"qna-reply-modify-btn\">" +
                        "<button type=\"button\" class=\"default-btn\" value=\"" + replyId + "\" onclick=\"" + onclickText + "\">수정</button>" +
                    "</div>" +
                "</div>";
    inputElements.append(str);
    $(`.detail-reply-${replyId} .qna-reply-content button`).attr('onclick', closeText).text('닫기');
}

function handleProductQnAModifyCloseBtn(obj) {
    handleModifyCloseBtn(obj, 'handleProductQnAReplyModifyBtn(this)');
}

function handleMemberQnAModifyCloseBtn(obj) {
    handleModifyCloseBtn(obj, 'handleMemberQnAReplyModifyBtn(this)')
}

function handleModifyCloseBtn(obj, onclickText) {
    const replyId = obj.value;
    $(`.detail-reply-${replyId} .qna-reply-content button`).attr('onclick', onclickText).text('댓글 수정');
    $(`.detail-reply-${replyId} .qna-reply-modify-input`).empty();
}

function modifyProductQnAReply(obj) {
    const replyId = obj.value;
    const data = createModifyReplyObject(replyId);

    $.ajax({
        url: `/admin/qna/product/reply/${replyId}`,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function modifyMemberQnAReply(obj) {
    const replyId = obj.value;
    const data = createModifyReplyObject(replyId);

    $.ajax({
        url: `/admin/qna/member/reply/${replyId}`,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function createModifyReplyObject(replyId) {
    return {
        content: $(`.detail-reply-${replyId} .qna-reply-modify-input textarea`).val(),
    };
}

function handleProductQnAReplySubmit(obj) {
    const data = createQnAReplyObject(obj);

    $.ajax({
        url: '/admin/qna/product/reply',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function handleMemberQnAReplySubmit(obj) {
    const data = createQnAReplyObject(obj);

    $.ajax({
        url: '/admin/qna/member/reply',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function createQnAReplyObject(obj) {
    return {
        id: obj.value,
        content: $("#qna-reply").val(),
    };
}

function handleProductQnACompleteBtn(obj) {
    const qnaId = obj.value;

    $.ajax({
        url: `/admin/qna/product/${qnaId}`,
        type: 'PATCH',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function handleMemberQnACompleteBtn(obj) {
    const qnaId = obj.value;

    $.ajax({
        url: `/admin/qna/member/${qnaId}`,
        type: 'PATCH',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function qnaClassificationAddBtnOnclick() {
    $('.admin-qna-classification-add-btn').attr('onclick', 'qnaClassificationAddFormClose()').text('닫기');
    const str = "<div class=\"admin-qna-classification-input\">" +
                    "<div class=\"classification-input-content\">" +
                        "<label>카테고리명 : </label>" +
                        "<input type=\"text\" class=\"admin-classification-input\">" +
                    "</div>" +
                    "<div class=\"classification-input-btn\">" +
                        "<button type=\"button\" class=\"classification-submit-btn\" onclick=\"addClassification()\">추가</button>" +
                    "</div>" +
                "</div>";
    $('.admin-qna-classification-content').append(str);
}

function qnaClassificationAddFormClose() {
    $('.admin-qna-classification-add-btn').attr('onclick', 'qnaClassificationAddBtnOnclick()').text('카테고리 추가');
    $('.admin-qna-classification-content').empty();
}

function deleteClassificationBtn(obj) {
    const classificationId = obj.value;

    $.ajax({
        url: `/admin/qna/classification/${classificationId}`,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}

function addClassification() {
    const data = {
        content: $('.admin-classification-input').val(),
    };

    $.ajax({
        url: '/admin/qna/classification',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
        }
    })
}