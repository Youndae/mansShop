$(document).ready(function(){

    //adminQnA
    var qno = $("#qno").val();
    (function(){
        console.log("replyList get");
        $.getJSON("/admin/getQnAReply", {qno:qno}, function(arr){
            var str = "";
            $(arr).each(function(i, reply){
                console.log("reply get");
                str += "<div class=\"QnAReplyContent\">" +
                        "<label>"+reply.userId+"</label>" +
                        "<span>"+reply.qrContent+"</span>" +
                        "<button type=\"button\" onclick=\"reply_del(this)\" value=\""+reply.replyNo+"\">삭제</button>" +
                        "</div>";
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







    var actionForm = $("#orderActionForm");
    var ordersearchForm = $("#orderSearchForm");

    $(".paginate_button a").on('click', function(e){
        e.preventDefault();

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    })

    $("#orderSearchForm button").on('click', function(e){
        if(!ordersearchForm.find("input[name='keyword']").val()){
            alert('키워드 입력');
        }

        ordersearchForm.find("input[name='pageNum']").val("1");
        e.preventDefault();

        ordersearchForm.submit();
    })


    $("#modalShow").on('click', function(e){

        e.preventDefault();
        var orderNo = $(this).text();

        var str = "";

        getInfo(orderNo, function(info){
           /*alert("data : " + info.orderNo + ", " + info.userId + ", " + info.orderPhone + ", " + info.addr + ", " + info.orderMemo);*/
            $(".modal-body span[name=orderNo]").text(info.orderNo);
            $(".modal-body span[name=recipient]").text(info.recipient);
            $(".modal-body span[name=userId]").text(info.userId);
            $(".modal-body span[name=orderPhone]").text(info.orderPhone);
            $(".modal-body span[name=addr]").text(info.addr);
            $(".modal-body span[name=orderMemo]").text(info.orderMemo);



            if(info.orderStat == 1){
                $("#shipping").hide();
            }

        });

        /*getInfoTable(orderNo, function(orderInfo){
            /!*alert("tableData : " + orderInfo.pclassification + ", " + orderInfo.pname + ", " + orderInfo.psize + ", " + orderInfo.pcolor + ", " + orderInfo.popNo + ", " + orderInfo.orderNo);*!/
            var str = "<thead>" +
                        "<tr>" +
                        "<th>옵션번호</th>" +
                        "<th>분류</th>" +
                        "<th>상품명</th>" +
                        "<th>사이즈</th>" +
                        "<th>컬러</th>" +
                        "<th>수량</th>" +
                        "</tr>"+
                        "</thead>";


        });*/

        (function(){
            $.getJSON("/admin/orderInfoTable", {orderNo:orderNo}, function(arr){
                str = "<thead>" +
                    "<tr>" +
                    "<th>옵션번호</th>" +
                    "<th>분류</th>" +
                    "<th>상품명</th>" +
                    "<th>사이즈</th>" +
                    "<th>컬러</th>" +
                    "<th>수량</th>" +
                    "</tr>"+
                    "</thead>";

                str += getInfoTable(arr);

                $(".order_detail_list").append(str);



             $(".modal").show();
            })
        })();
    });

    $(".modalClose").on('click', function(){
        $(".modal").hide();
    });


    $(".shipping_btn button").on('click', function(){
        console.log("hi");
        var orderNo = $('span[name=orderNo]').text();

        console.log("orderNo : " + orderNo);

        $.ajax({
            url: '/admin/shippingProcess',
            type: 'post',
            data: {orderNo:orderNo},
            dataType: 'json',
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
            success: function(data){
                alert("댓글등록 성공!");//alert 삭제할것.
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
    console.log("order Info get");
    var str = "";
    $(arr).each(function(i, info){
        str += "<tr>" +
                "<td>"+info.popNo+"</td>" +
                "<td>"+info.pclassification+"</td>" +
                "<td>"+info.pname+"</td>" +
                "<td>"+info.psize+"</td>" +
                "<td>"+info.pcolor+"</td>" +
                "<td>"+info.orderCount+"</td>" +
                "</tr>";
    })

    return str;
}