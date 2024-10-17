const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

function reviewElementsOnClick(obj) {
    const reviewId = $(obj).attr('data-review-id');
    location.href=`/admin/review/${reviewId}`;
}

function handleReviewReplySubmit(obj) {
    const reviewId = obj.value;
    const content = {
        content: $('#qna-reply').val(),
    }

    $.ajax({
        url: `/admin/review/reply/${reviewId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(content),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            if(data === 'SUCCESS')
                location.reload();
            else
                alert('오류가 발생했습니다.');
        }
    })
}