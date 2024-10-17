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





/*

$(document).ready(function(){

    //productQnADetail
    $("#pQnAReplyForm_btn").on('click', function(){
        $("#pQnAReplyForm").attr('action', '/admin/product/qna/reply');
        $("#pQnAReplyForm").submit();
    })

    //adminQnA
    const qno = $("#qno").val();
    /!*const qno = {
        qno : $("#qno").val(),
    };*!/
    (function(){
        $.getJSON("/admin/qna/reply/" + qno, function(arr){
            let str = "<ul>";
            $(arr).each(function(i, reply){
                console.log("reply get");

                const date = new Date(reply.qrRegDate);

                const regDate = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();

                str += "<li class=\"QnADetail_reply_content\">" +
                        "<div class=\"QnADetail_reply_header\">" +
                        "<strong class=\"replyer st_7\">" + reply.userId + "</strong>" +
                        "<small class=\"pull-right text-muted sm_ml_10\">" + regDate + "</small>" +
                        "</div>" +
                        "<div class=\"QnADetail_reply_content_content\">" +
                        "<p>" + reply.qrContent + "</p>" +
                        "<button class=\"reply_del_btn\" onclick='reply_del(this)' value=\"" + reply.replyNo + "\">삭제</button>" +
                        "</div></li>";
            })
            $(".QnAReply").append(str);
        })
    })();

    $("#QnAComplete").on('click', function(){
        $.ajax({
           url: '/admin/qna/complete/' + qno,
            type: 'patch',
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
               if(data == 1){
                   location.reload();
               }else{
                   alert("오류가 발생했습니다.");
               }
            }
        });
    });

    $("#tbl_orderList a").on('click', function(e){
        e.preventDefault();
        const orderNo = $(this).text();
        let str = "";

        getInfo(orderNo, function(info){
            $(".modal-body span[name=orderNo]").text(info.orderNo);
            $(".modal-body span[name=recipient]").text(info.recipient);
            $(".modal-body span[name=orderPhone]").text(info.orderPhone);
            $(".modal-body span[name=addr]").text(info.addr);
            $(".modal-body span[name=orderMemo]").text(info.orderMemo);

            if(info.userId == "Anonymous")
                $(".modal-body span[name=userId]").text("비회원");
            else
                $(".modal-body span[name=userId]").text(info.userId);

            if(info.orderStat == 1)
                $("#shipping").hide();
        });

        (function(){
            $.getJSON("/admin/order-info-data/" + orderNo, function(arr){
                str += getInfoTable(arr);
                $(".order_detail_info_list").append(str);
                $(".modal").show();
            })
        })();
    });

    $(".modalClose").on('click', function(){
        $(".order_detail_info_list tr").remove();
        $(".modal").hide();
    });

    $(".shipping_btn button").on('click', function(){
        const orderNo = $('span[name=orderNo]').text();

        $.ajax({
            url: '/admin/shipping/' + orderNo,
            type: 'patch',
            dataType: 'json',
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1){
                    $(".modal").hide();
                    location.reload();
                }else{
                    alert("오류가 발생했습니다.");
                }
            }
        })
    })
});

function reply_del(obj){
    const replyNo = obj.attributes['value'].value;

    $.ajax({
        url: '/admin/qna/reply/' + replyNo,
        type: 'delete',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            if(data == 1){
                console.log("reply delete complete");
                location.reload();
            }else{
                alert("오류가 발생했습니다.");
            }
        }
    })
}

$(function(){
    $("#replyForm_submit").on('click', function(){
        const form = $("#replyForm").serialize();

        $.ajax({
            url: '/admin/qna/reply',
            type: 'post',
            data: form,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1)
                    location.reload();
                else
                    alert("오류가 발생했습니다.");

            },
            error : function(request, status, error){
                alert("code:" + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " +error);
            }
        })
    });
});

function getInfo(orderNo, callback, error){
    $.get("/admin/order/"+orderNo + ".json", function(result){
        if(callback)
            callback(result);
    }).fail(function(xhr, status, er){
        if(error)
            error(er);
    });
}

function getInfoTable(arr){
    let str = "";
    $(arr).each(function(i, info){
        str +=
            "<tr class=\"tbl_tr_m_10\">" +
            "<td>"+info.popNo+"</td>" +
            "<td>"+info.pclassification+"</td>" +
            "<td>"+info.pname+"</td>";

        if(info.psize != null)
            str += "<td>"+info.psize+"</td>";
        else
            str += "<td></td>";

        if(info.pcolor != null)
            str += "<td>"+info.pcolor+"</td>";
        else
            str += "<td></td>";

        str += "<td>"+info.orderCount+"</td></tr>";
    })
    return str;
}*/
