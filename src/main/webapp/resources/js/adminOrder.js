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

function handleOrderElementsOnClick(obj) {
    const orderId = $(obj).attr('id');

    $.getJSON(`/admin/order/detail/${orderId}`, function(data) {
        console.log('order Detail responseData : ', data);

        const details = data.detailList;
        let detailStr = "<div class=\"admin-order-detail\">";
        const isOrderComplete = data.status === '배송 완료';
        for(let i = 0; i < details.length; i++) {
            let reviewForm = '';
            if(isOrderComplete){
                let reviewText = details[i].reviewStatus ? '작성' : '미작성';
                reviewForm += "<div class=\"form-group\">" +
                    "<label>리뷰 작성 여부 : </label>" +
                    "<span>" + reviewText + "</span>" +
                    "</div>"
            }

            detailStr += "<div class=\"admin-order-detail-form\">" +
                            "<h3>" + details[i].productName + "</h3>" +
                            "<div class=\"admin-order-detail-info\">" +
                                "<label>분류 : </label>" +
                                "<span>" + details[i].classification + "</span>" +
                            "</div>" +
                            "<div class=\"detail-info-form-group\">" +
                                "<div class=\"form-group\">" +
                                    "<label>사이즈 : </label>" +
                                    "<span>" + details[i].productSize + "</span>" +
                                "</div>" +
                                "<div class=\"form-group\">" +
                                    "<label>컬러 : </label>" +
                                    "<span>" + details[i].productColor + "</span>" +
                                "</div>" +
                                "<div class=\"form-group\">" +
                                    "<label>수량 : </label>" +
                                    "<span>" + details[i].count + "</span>" +
                                "</div>" +
                                "<div class=\"form-group\">" +
                                    "<label>금액 : </label>" +
                                    "<span>" + details[i].price + "</span>" +
                                "</div>" +
                                    reviewForm +
                            "</div>" +
                        "</div>";
        }
        detailStr += "</div>";

        const path = window.location.pathname;
        let detailCheckElements = '';
        if(path.startsWith('/admin/order/new')){
            detailCheckElements += "<div class=\"admin-order-detail-check-btn\">" +
                                        "<button type=\"button\" class=\"default-btn\" value=\"" + orderId + "\" onclick=\"adminOrderCheckBtn(this)\">상품 준비</button>" +
                                    "</div>"
        }

        let str = "<div class=\"modal-content-content\">" +
                        "<div class=\"admin-order-info\">" +
                            "<div class=\"form-group\">" +
                                "<label>받는 사람 : </label>" +
                                "<span>" + data.recipient + "</span>" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                                "<label>사용자 아이디 : </label>" +
                                "<span>" + data.userId + "</span>" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                                "<label>연락처 : </label>" +
                                "<span>" + data.phone + "</span>" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                                "<label>주문일 : </label>" +
                                "<span>" + parseDate(data.createdAt) + "</span>" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                                "<label>배송지 : </label>" +
                                "<span>" + data.address + "</span>" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                                "<label>배송 상태 : </label>" +
                                "<span>" + data.status + "</span>" +
                            "</div>" +
                        "</div>" +
                        "<div class=\"admin-order-detail\">" +
                            detailStr +
                        "</div>" +
                        detailCheckElements +
                    "</div>";

        $(".admin-modal-content").append(str);
    })

    $(".modal-background").fadeIn();
}

function parseDate(date) {
    const year = date.year.toString().slice(-2);
    const month = ('0' + (date.monthValue)).slice(-2);
    const day = ('0' + date.dayOfMonth).slice(-2);
    const hours = ('0' + date.hour).slice(-2);
    const minute = ('0' + date.minute).slice(-2);

    return `${year}-${month}-${day} ${hours} : ${minute}`
}

function adminOrderCheckBtn(obj) {
    const orderId = obj.value;

    $.ajax({
        url: `/admin/order/order-check/${orderId}`,
        type: 'PATCH',
        beforeSend: function (xhr) {
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