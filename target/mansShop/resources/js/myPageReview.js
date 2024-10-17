const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

function handleReviewSubmit(obj) {
    const orderDetailId = obj.value;

    const content = {
        content: $('.qna-content').val(),
    }

    $.ajax({
        url: `/my-page/review/${orderDetailId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(content),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(data === 'SUCCESS')
                location.href='/my-page/review';
            else
                alert('오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요');
        }
    })
}