var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function(){

    //productQnADetail
    $("#pQnAReplyForm_btn").on('click', function(){
        $("#pQnAReplyForm").attr('action', '/admin/productQnAReply');
        $("#pQnAReplyForm").submit();
    })

    //adminQnA
    var qno = $("#qno").val();
    (function(){
        console.log("replyList get");
        $.getJSON("/admin/getQnAReply", {qno:qno}, function(arr){
            var str = "<ul>";
            $(arr).each(function(i, reply){
                console.log("reply get");

                var date = new Date(reply.qrRegDate);

                var regDate = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();

                str += "<li class=\"QnADetail_reply_content\">" +
                        "<div class=\"QnADetail_reply_header\">" +
                        "<strong class=\"replyer\">" + reply.userId + "</strong>" +
                        "<small class=\"pull-right text-muted\">" + regDate + "</small>" +
                        "</div>" +
                        "<div class=\"QnADetail_reply_content_content\">" +
                        "<p>" + reply.qrContent + "</p>" +
                        "</div></li>";
            })
            $(".QnAReply").append(str);
        })
    })();

    $("#QnAComplete").on('click', function(){
        $.ajax({
           url: '/admin/adminQnAComplete',
            type: 'post',
            data: {qno:qno},
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
               console.log("complete success");
               if(data == 1){
                   location.reload();
               }else{
                   alert("처리 실패");
               }
            }
        });
    });

    $("#tbl_orderList a").on('click', function(e){

        e.preventDefault();
        var orderNo = $(this).text();
        var str = "";



        getInfo(orderNo, function(info){

            $(".modal-body span[name=orderNo]").text(info.orderNo);
            $(".modal-body span[name=recipient]").text(info.recipient);
            $(".modal-body span[name=orderPhone]").text(info.orderPhone);
            $(".modal-body span[name=addr]").text(info.addr);
            $(".modal-body span[name=orderMemo]").text(info.orderMemo);

            if(info.userId == "Anonymous"){
                $(".modal-body span[name=userId]").text("비회원");
            }else{
                $(".modal-body span[name=userId]").text(info.userId);
            }

            if(info.orderStat == 1){
                $("#shipping").hide();
            }

        });

        (function(){
            $.getJSON("/admin/orderInfoTable", {orderNo:orderNo}, function(arr){


                str += getInfoTable(arr);

                $(".order_detail_info_list").append(str);


             $(".modal").show();
            })
        })();
    });

    $(".modalClose").on('click', function(){
        $(".order_detail_info_list td").remove();
        $(".modal").hide();
    });


    $(".shipping_btn button").on('click', function(){

        var orderNo = $('span[name=orderNo]').text();

        console.log("orderNo : " + orderNo);

        $.ajax({
            url: '/admin/shippingProcess',
            type: 'post',
            data: {orderNo:orderNo},
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){

                console.log("return data : " + data);

                if(data == 1){
                    $(".modal").hide();
                    location.reload();
                }else{
                    alert("오류발생");
                }

            }
        })
    })

});

function reply_del(obj){
    console.log("reply del");
    var replyNo = obj.attributes['value'].value;
    console.log("rno : " + replyNo);

    $.ajax({
        url: '/admin/QnAReplyDel',
        type: 'post',
        data: {replyNo : replyNo},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            console.log("reply delete complete");
            location.reload();
        }
    })
}



$(function(){
    $("#replyForm_submit").on('click', function(){
        console.log("reply submit");
        var form = $("#replyForm").serialize();

        $.ajax({
            url: '/admin/QnAReply',
            type: 'post',
            data: form,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                location.reload();
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
    console.log("order Info get");

    $.get("/admin/orderInfo/"+orderNo + ".json", function(result){
        if(callback)
            callback(result);
    }).fail(function(xhr, status, er){
        if(error)
            error(er);
    });
}

function getInfoTable(arr){
    console.log("order Info Table get");
    var str = "";
    $(arr).each(function(i, info){
        str +=
            "<tr>" +
            "<td>"+info.popNo+"</td>" +
            "<td>"+info.pclassification+"</td>" +
            "<td>"+info.pname+"</td>" +
            "<td>"+info.psize+"</td>" +
            "<td>"+info.pcolor+"</td>" +
            "<td>"+info.orderCount+"</td></tr>";

    })

    return str;
}