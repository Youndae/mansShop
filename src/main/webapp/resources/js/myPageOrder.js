var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var pOpNoArr = new Array();
var cdNoArr = new Array();
$(document).ready(function(){
    //orderComplete
    $("#orderList").on('click', function(){
       location.href='/myPage/memberOrderList';
    });

    $("#orderInfoCheck").on('click', function(){
        $("#orderInfoCheckForm").attr("action", "/nonOrderList");
        $("#orderInfoCheckForm").submit();
    })

    //cart
    $("#select_order").on('click', function(){
        var noArr = new Array();
        var nameArr = new Array();
        var sizeArr = new Array();
        var colorArr = new Array();
        var countArr = new Array();
        var priceArr = new Array();
        var pnoArr = new Array();
        var cdArr = new Array();

        $("input[name=check]:checked").each(function(){
            noArr.push($(this).attr("value"));
            nameArr.push($(this).attr("data_pName"));
            sizeArr.push($(this).attr("data_pSize"));
            colorArr.push($(this).attr("data_pColor"));
            countArr.push($(this).attr("data_cCount"));
            priceArr.push($(this).attr("data_cPrice"));
            pnoArr.push($(this).attr("data_pno"));
            cdArr.push($(this).attr("data_cdNo"))
        });

        $("input[name=pOpNo]").val(noArr);
        $("input[name=pName]").val(nameArr);
        $("input[name=pSize]").val(sizeArr);
        $("input[name=pColor]").val(colorArr);
        $("input[name=cCount]").val(countArr);
        $("input[name=cPrice]").val(priceArr);
        $("input[name=pno]").val(pnoArr);
        $("input[name=cdNo]").val(cdArr);

        $("#order_form").attr("action", "/order/orderPayment");
        $("#order_form").submit();

    })

    $("button[name=up]").on('click', function(){

        var cdNo = $(this).siblings("input").val();
        var pPrice = $(this).parent('td').next().text().replace(/\D/g, '');
        pPrice = parseInt(pPrice) / parseInt(count);
        var countType = "up";

        $.ajaxSettings.traditional = true;
        $.ajax({
            url: '/myPage/cartCount',
            type: 'post',
            data: {cdNo: cdNo, cPrice: pPrice, countType: countType},
            beforeSend:function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                location.reload();
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })


    });

    $("button[name=down]").on('click', function(){

        var cdNo = $(this).siblings("input").val();
        var count = $(this).siblings("span").text();
        var pPrice = $(this).parent('td').next().text().replace(/\D/g, '');
        pPrice = parseInt(pPrice) / parseInt(count);
        var countType = "down";

        if(count == "1"){
            alert("?????? ???????????????.");
        }else{
            $.ajaxSettings.traditional = true;
            $.ajax({
                url: '/myPage/cartCount',
                type: 'post',
                data: {cdNo: cdNo, cPrice: pPrice, countType: countType},
                beforeSend:function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    location.reload();
                },
                error: function(request, status, error){
                    alert("code : " + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            })
        }



    });

    $("#select_delete").on('click', function(){
        console.log("select_delete");
        /*var pOpNoArr = new Array();*/
        $("input[name=check]:checked").each(function(){
            cdNoArr.push($(this).attr("data_cdNo"));
        });

        $.ajaxSettings.traditional = true;
        $.ajax({
            url : '/myPage/deleteCart',
            type: 'post',
            data: {cdNo: cdNoArr},
            beforeSend:function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                location.reload();
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })
    });

    //memberOrderList

    var date = new Date();
    date.setMonth(date.getMonth() - 3);

    var month = 0;

    if((date.getMonth() + 1) >= 10){
        month = date.getMonth() + 1;
    }else{
        month = '0' + (date.getMonth() + 1);
    }

    var regDate = date.getFullYear() + "/" + month + "/01";

    console.log("order RegDate : " + regDate);

    getList(regDate);
});

$(function(){
    $("#select_orderList_term").change(function(){
        console.log("orderList term change");
        var val = $("#select_orderList_term option:selected").val();

        if(val == "all"){
            var regDate = new Date(1990,1,1);

            var date = regDate.getFullYear() + "/" + (regDate.getMonth() + 1) + "/" + regDate.getDate();
        }else{
            var regDate = new Date();
            regDate.setMonth(regDate.getMonth() - val);

            var date = regDate.getFullYear() + "/" + (regDate.getMonth() + 1) + "/01";
        }




        getList(date);
    })

})



function getList(regDate){
    $(".memberOrderList div").remove();
    $.getJSON("/myPage/selectOrderList", {regDate: regDate}, function(arr){
        console.log("getMemberOrderList");
        var str = "";
        var date = "";
        var totalPrice = 0;

        $(arr).each(function(i, attach){

            if(i == 0){
                date = attach.orderDate;

                var od = new Date(attach.orderDate);
                attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();
                str += "<div class=\"orderList_tbl\">" +
                    "<label>" + attach.orderDate + "</label>" +
                    "<table border=\"1\">" +
                    "<tr>" +
                    "<th>?????????</th>" +
                    "<th>??????</th>" +
                    "<th>??????</th>" +
                    "<th>??????</th>" +
                    "<th>????????????</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<a class=\"tbl_order_a\" href=\"/" + attach.pno + "\">" +
                    "<div class=\"tbl_firstThumbnail\">" +
                    "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                    "</div>" +
                    "<div class=\"tbl_productName\">"+
                    attach.pname +
                    "</div></a></td>";
                if(attach.psize != null){
                    if(attach.pcolor != null){
                        str += "<td>????????? : " + attach.psize + "  ?????? : " + attach.pcolor + "</td>";
                    }else if(attach.pcolor == null){
                        str += "<td>????????? : " + attach.psize + "</td>";
                    }
                }else if(attach.pcolor != null){
                    str += "<td>?????? : " + attach.pcolor + "</td>";
                }
                str += "<td>" + attach.orderCount + "</td>" +
                    "<td>" + attach.odPrice + "</td>";

                totalPrice = attach.orderPrice;

                if(attach.orderStat == 0){
                    str += "<td>???????????????</td>";
                }else if(attach.orderStat == 1){
                    str += "<td>?????????</td>";
                }else if(attach.orderStat == 2){
                    str += "<td>????????????";
                    if(attach.reviewStat == 0){
                        str += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.popNo + "/" + attach.pno + "/" + attach.orderNo + "\" onclick=\"insertReview(this)\">??????????????????</button></td>";
                    }else{
                        str += "</td>";
                    }
                }
                str += "</tr>";
            }else{
                if(date == attach.orderDate){
                    str += "<tr>" +
                        "<td>" +
                        "<a class=\"tbl_order_a\" href=\"/" + attach.pno + "\">" +
                        "<div class=\"tbl_firstThumbnail\">" +
                        "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                        "</div>" +
                        "<div class=\"tbl_productName\">"+
                        attach.pname +
                        "</div></a></td>";
                    if(attach.psize != null){
                        if(attach.pcolor != null){
                            str += "<td>????????? : " + attach.psize + "  ?????? : " + attach.pcolor + "</td>";
                        }else if(attach.pcolor == null){
                            str += "<td>????????? : " + attach.psize + "</td>";
                        }
                    }else if(attach.pcolor != null){
                        str += "<td>?????? : " + attach.pcolor + "</td>";
                    }
                    str += "<td>" + attach.orderCount + "</td>" +
                        "<td>" + attach.odPrice + "</td>";

                    if(attach.orderStat == 0){
                        str += "<td>???????????????</td>";
                    }else if(attach.orderStat == 1){
                        str += "<td>?????????</td>";
                    }else if(attach.orderStat == 2){
                        str += "<td>????????????";
                        if(attach.reviewStat == 0){
                            str += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.popNo + "/" + attach.pno + "/" + attach.orderNo + "\" onclick=\"insertReview(this)\">??????????????????</button></td>";
                        }else{
                            str += "</td>";
                        }
                    }
                    str += "</tr>";
                }else{
                    date = attach.orderDate;

                    var od = new Date(attach.orderDate);
                    attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();

                    str +=  "</tbody></table><span>??? ?????? : " + totalPrice.toLocaleString() + " ???</span></div>"+
                        "<div class=\"orderList_tbl\">" +
                        "<label>" + attach.orderDate + "</label>" +
                        "<table border=\"1\">" +
                        "<tr>" +
                        "<th>?????????</th>" +
                        "<th>??????</th>" +
                        "<th>??????</th>" +
                        "<th>??????</th>" +
                        "<th>????????????</th>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>" +
                        "<tr>" +
                        "<td>" +
                        "<a class=\"tbl_order_a\" href=\"/" + attach.pno + "\">" +
                        "<div class=\"tbl_firstThumbnail\">" +
                        "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                        "</div>" +
                        "<div class=\"tbl_productName\">"+
                        attach.pname +
                        "</div></a></td>";
                    if(attach.psize != null){
                        if(attach.pcolor != null){
                            str += "<td>????????? : " + attach.psize + "  ?????? : " + attach.pcolor + "</td>";
                        }else if(attach.pcolor == null){
                            str += "<td>????????? : " + attach.psize + "</td>";
                        }
                    }else if(attach.pcolor != null){
                        str += "<td>?????? : " + attach.pcolor + "</td>";
                    }
                    str += "<td>" + attach.orderCount + "</td>" +
                        "<td>" + attach.odPrice + "</td>";

                    totalPrice = attach.orderPrice;

                    if(attach.orderStat == 0){
                        str += "<td>???????????????</td>";
                    }else if(attach.orderStat == 1){
                        str += "<td>?????????</td>";
                    }else if(attach.orderStat == 2){
                        str += "<td>????????????";
                        if(attach.reviewStat == 0){
                            str += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.popNo + "/" + attach.pno + "/" + attach.orderNo + "\" onclick=\"insertReview(this)\">??????????????????</button></td>";
                        }else{
                            str += "</td>";
                        }
                    }
                    str += "</tr>";
                }
            }

            console.log("i : " + i);

            if(i == arr.length - 1){
                str +=  "</tbody></table><span>??? ?????? : " + totalPrice.toLocaleString() + " ???</span></div>";
            }

        })

        $(".memberOrderList").append(str);
    })
}

function insertReview(obj){
    var val = obj.attributes['value'].value;

    var no = val.split("/");

    location.href="/myPage/orderReview/"+no[0] + "/" + no[1] + "/" + no[2];

};


//orderReview
$(function(){
    $("#reviewInsert").on('click',function(){
        $("#reviewInsertForm").attr("action", "/myPage/orderReview");
        $("#reviewInsertForm").submit();
    })
})