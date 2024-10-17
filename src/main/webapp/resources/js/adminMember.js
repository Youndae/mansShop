const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

$(function() {
    $(window).click(function (e) {
        if($(e.target).is('.modal-background')) {
            $('.modal-background').fadeOut();
            $(".admin-modal-content").empty();
        }
    })
})

function adminMemberContentOnClick(obj) {
    const userId = $(obj).attr('data-member-id');

    memberModalFadeIn(userId);
}

function memberModalFadeIn(userId) {
    $.getJSON(`/admin/member/${userId}`, function(data) {
        const str = "<div class=\"admin-member-info\">" +
                        "<div class=\"form-group admin-member-id\">" +
                            "<label>아이디 : </label>" +
                            "<span>" + data.userId + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>이름 : </label>" +
                            "<span>" + data.username + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>닉네임 : </label>" +
                            "<span>" + data.userId + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>연락처 : </label>" +
                            "<span>" + data.phone + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>이메일 : </label>" +
                            "<span>" + data.email + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>생년월일 : </label>" +
                            "<span>" + parseDate(data.birth) + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>보유 포인트 : </label>" +
                            "<span>" + data.point + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>가입일 : </label>" +
                            "<span>" + parseDate(data.createdAt) + "</span>" +
                        "</div>" +
                    "</div>" +
                    "<div class=\"admin-member-proc\">" +
                        "<div class=\"admin-member-point-input\">" +
                            "<div class=\"form-group\">" +
                                "<label>포인트 지급 : </label>" +
                                "<input type=\"number\" class=\"member-point-input\">" +
                                "<button type=\"button\" class=\"default-btn member-point-btn\" onclick=\"handleMemberPoint()\">지급</button>" +
                            "</div>" +
                        "</div>" +
                        "<div class=\"admin-member-btn\">" +
                            "<button type=\"button\" class=\"default-btn admin-member-order\" onclick=\"handleModalMemberOrder()\">주문 정보</button>" +
                            "<button type=\"button\" class=\"default-btn admin-member-order\" onclick=\"handleModalProductQnA()\">상품 문의 내역</button>" +
                            "<button type=\"button\" class=\"default-btn admin-member-order\" onclick=\"handleModalMemberQnA()\">문의 내역</button>" +
                        "</div>" +
                    "</div>";

        $(".admin-modal-content").append(str);
    })

    $(".modal-background").fadeIn();
}

function parseDate(date) {
    const year = date.year.toString().slice(-4);
    const month = ('0' + (date.monthValue)).slice(-2);
    const day = ('0' + date.dayOfMonth).slice(-2);

    return `${year}-${month}-${day}`
}

function handleMemberPoint() {
    const pointValue = $('.member-point-input').val();
    const userId = $('.admin-member-id span').text();
    const data = {
        userId: userId,
        point: pointValue,
    };

    $.ajax({
        url: '/admin/member/point',
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(data),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS'){
                alert('포인트 지급이 완료되었습니다.');
                $(".admin-modal-content").empty();
                memberModalFadeIn(userId);
            }
        }
    })
}

function handleModalMemberOrder() {
    const userId = $('.admin-member-id span').text();

    location.href = `/admin/order/all?keyword=${userId}&searchType=userId`;
}

function handleModalProductQnA() {
    const userId = $('.admin-member-id span').text();

    location.href = `/admin/qna/product/all?keyword=${userId}`;
}

function handleModalMemberQnA() {
    const userId = $('.admin-member-id span').text();

    location.href = `/admin/qna/member/all?keyword=${userId}`;
}