$(document).ready(function(){

    var recipient = $("#recipient").val();
    var orderPhone = $("#orderPhone").val();

    $.getJSON("/getNonOrderList", {recipient: recipient, orderPhone: orderPhone}, function(arr){
        var str = "";
        var date = "";
        var totalPrice = 0;

        $(arr).each(function(i, attach){

            if(i == 0){
                date = attach.orderDate;

                var od = new Date(attach.orderDate);
                attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();
                str += "<div>" +
                    "<label>" + attach.orderDate + "</label>" +
                    "<table border=\"1\">" +
                    "<thead>" +
                    "<tr>" +
                    "<th>상품명</th>" +
                    "<th>옵션</th>" +
                    "<th>수량</th>" +
                    "<th>가격</th>" +
                    "<th>배송현황</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<a class=\"tbl_order_a\" href=\"/product/" + attach.pno + "\">" +
                    "<div class=\"tbl_firstThumbnail\">" +
                    "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                    "</div>" +
                    "<div class=\"tbl_productName\">"+
                    attach.pname +
                    "</div></a></td>";
                if(attach.psize != null){
                    if(attach.pcolor != null){
                        str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                    }else if(attach.pcolor == null){
                        str += "<td>사이즈 : " + attach.psize + "</td>";
                    }
                }else if(attach.pcolor != null){
                    str += "<td>컬러 : " + attach.pcolor + "</td>";
                }
                str += "<td>" + attach.orderCount + "</td>" +
                    "<td>" + attach.odPrice + "</td>";

                totalPrice = attach.orderPrice;

                if(attach.orderStat == 0){
                    str += "<td>배송준비중</td>";
                }else if(attach.orderStat == 1){
                    str += "<td>배송중</td>";
                }else if(attach.orderStat == 2){
                    str += "<td>배송완료(비회원은 리뷰를 작성하실 수 없습니다.)</td>";

                }
                str += "</tr>";
            }else{
                if(date == attach.orderDate){
                    str += "<tr>" +
                        "<td>" +
                        "<a class=\"tbl_order_a\" href=\"/product/" + attach.pno + "\">" +
                        "<div class=\"tbl_firstThumbnail\">" +
                        "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                        "</div>" +
                        "<div class=\"tbl_productName\">"+
                        attach.pname +
                        "</div></a></td>";
                    if(attach.psize != null){
                        if(attach.pcolor != null){
                            str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                        }else if(attach.pcolor == null){
                            str += "<td>사이즈 : " + attach.psize + "</td>";
                        }
                    }else if(attach.pcolor != null){
                        str += "<td>컬러 : " + attach.pcolor + "</td>";
                    }
                    str += "<td>" + attach.orderCount + "</td>" +
                        "<td>" + attach.odPrice + "</td>";

                    if(attach.orderStat == 0){
                        str += "<td>배송준비중</td>";
                    }else if(attach.orderStat == 1){
                        str += "<td>배송중</td>";
                    }else if(attach.orderStat == 2){
                        str += "<td>배송완료(비회원은 리뷰를 작성하실 수 없습니다.)</td>";
                    }
                    str += "</tr>";
                }else{
                    date = attach.orderDate;

                    var od = new Date(attach.orderDate);
                    attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();

                    str +=  "</tbody></table><span>총 금액 : " + totalPrice + " 원</span></div>"+
                        "<div>" +
                        "<label>" + attach.orderDate + "</label>" +
                        "<table border=\"1\">" +
                        "<thead>" +
                        "<tr>" +
                        "<th>상품명</th>" +
                        "<th>옵션</th>" +
                        "<th>수량</th>" +
                        "<th>가격</th>" +
                        "<th>배송현황</th>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>" +
                        "<tr>" +
                        "<td>" +
                        "<a class=\"tbl_order_a\" href=\"/product/" + attach.pno + "\">" +
                        "<div class=\"tbl_firstThumbnail\">" +
                        "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                        "</div>" +
                        "<div class=\"tbl_productName\">"+
                        attach.pname +
                        "</div></a></td>";
                    if(attach.psize != null){
                        if(attach.pcolor != null){
                            str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                        }else if(attach.pcolor == null){
                            str += "<td>사이즈 : " + attach.psize + "</td>";
                        }
                    }else if(attach.pcolor != null){
                        str += "<td>컬러 : " + attach.pcolor + "</td>";
                    }
                    str += "<td>" + attach.orderCount + "</td>" +
                        "<td>" + attach.odPrice + "</td>";

                    totalPrice = attach.orderPrice;

                    if(attach.orderStat == 0){
                        str += "<td>배송준비중</td>";
                    }else if(attach.orderStat == 1){
                        str += "<td>배송중</td>";
                    }else if(attach.orderStat == 2){
                        str += "<td>배송완료(비회원은 리뷰를 작성하실 수 없습니다.)</td>";
                    }
                    str += "</tr>";
                }
            }

            if(i == arr.length - 1){
                str +=  "</tbody></table><span>총 금액 : " + totalPrice + " 원</span></div>";
            }

        })

        $(".memberOrderList").append(str);
    })
})
