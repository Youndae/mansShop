var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function(){

    //productQnADetail
    $("#pQnAReplyForm_btn").on('click', function(){
        $("#pQnAReplyForm").attr('action', '/admin/productQnAReply');
        $("#pQnAReplyForm").submit();
    })

    //adminQnA
    var qno = {
        qno : $("#qno").val(),
    };
    (function(){
        $.getJSON("/admin/getQnAReply", qno, function(arr){
            var str = "<ul>";
            $(arr).each(function(i, reply){
                console.log("reply get");

                var date = new Date(reply.qrRegDate);

                var regDate = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();

                str += "<li class=\"QnADetail_reply_content\">" +
                        "<div class=\"QnADetail_reply_header\">" +
                        "<strong class=\"replyer st_7\">" + reply.userId + "</strong>" +
                        "<small class=\"pull-right text-muted sm_ml_10\">" + regDate + "</small>" +
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
            type: 'patch',
            data: JSON.stringify(qno),
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
        var orderNo = $(this).text();
        var str = "";

        console.log('tbl_orderList');
        getInfo(orderNo, function(info){
            console.log('getInfo in');
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
                console.log('orderInfoTable in');
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

        var orderNo = {
            orderNo : $('span[name=orderNo]').text(),
        }

        $.ajax({
            url: '/admin/shippingProcess',
            type: 'patch',
            data: JSON.stringify(orderNo),
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
    var replyNo = obj.attributes['value'].value;

    $.ajax({
        url: '/admin/QnAReplyDel',
        type: 'delete',
        data: {replyNo : replyNo},
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
        var form = $("#replyForm").serialize();

        $.ajax({
            url: '/admin/QnAReply',
            type: 'post',
            data: form,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1){
                    location.reload();
                }else{
                    alert("오류가 발생했습니다.");
                }
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
    console.log('getInfo');
    $.get("/admin/orderInfo/"+orderNo + ".json", function(result){
        if(callback)
            callback(result);
    }).fail(function(xhr, status, er){
        if(error)
            error(er);
    });
}

function getInfoTable(arr){
    var str = "";
    $(arr).each(function(i, info){
        str +=
            "<tr class=\"tbl_tr_m_10\">" +
            "<td>"+info.popNo+"</td>" +
            "<td>"+info.pclassification+"</td>" +
            "<td>"+info.pname+"</td>";

        if(info.psize != null){
            str += "<td>"+info.psize+"</td>";
        }else{
            str += "<td></td>";
        }

        if(info.pcolor != null){
            str += "<td>"+info.pcolor+"</td>";
        }else{
            str += "<td></td>";
        }

        str += "<td>"+info.orderCount+"</td></tr>";

    })
    return str;
}