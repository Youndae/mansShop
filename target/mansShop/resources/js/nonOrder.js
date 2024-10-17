$(function() {
    $(".paging a").on('click', function(e) {
        e.preventDefault();
        $("#page").val($(this).attr('href'));
        actionFormSubmit();
    })
})

function orderSelectOnChange() {
    const selectValue = $("#mypage-order-select").val();
    $("#term").val(selectValue);

    actionFormSubmit();
}

function actionFormSubmit() {
    const recipient = $("#recipient").val();
    const form = $("#pageActionForm");

    if(recipient !== undefined)
        form.attr('action', '/non/order');
    else
        form.attr('action', '/my-page/order');


    form.submit();
}

function insertReview(obj) {
    const detailId = obj.value;

    location.href = `/my-page/review/post/${detailId}`;
}