jQuery.orderListStr = function (arr, type) {
    let str = '';
    let date = '';
    let totalPrice = 0;

    $(arr).each(function(i, attach) {
        if (i == 0) {
            date = attach.orderDate;
            totalPrice = attach.orderPrice;
            str += orderListTablePrefix(attach);
        } else {
            if (date != attach.orderDate) {
                date = attach.orderDate;
                str += orderListTableSuffix(totalPrice);
                str += orderListTablePrefix(attach);
                totalPrice = attach.orderPrice;
            }
        }

        str += orderListTableTd(attach);
        str += orderOptionStr(attach);

        if (type === 'user')
            str += orderStatus(attach);
        else if (type === 'Anonymous')
            str += nonOrderStatus(attach);

        if (i === arr.length - 1)
            str += orderListTableSuffix(totalPrice);
    });

    return str;
}

function orderListTableSuffix(totalPrice) {
    return "</tbody></table><span>총 금액 : " + totalPrice.toLocaleString() + " 원</span></div>";
}

function orderListTableTd(attach) {
    let str = "<tr>" +
        "<td>" +
        "<a class=\"tbl_order_a\" href=\"/product/" + attach.pno + "\">" +
        "<div class=\"tbl_firstThumbnail\">" +
        "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
        "</div>" +
        "<div class=\"tbl_productName\">"+
        attach.pname +
        "</div>" +
        "</a>" +
        "</td>";

    return str;
}

function orderListTablePrefix(attach) {
    const od = new Date(attach.orderDate);
    attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();

    return "<div class=\"orderList_tbl\">" +
        "<label>" + attach.orderDate + "</label>" +
        "<table class=\"tbl_h_2\" border=\"1\">" +
        "<thead>" +
        "<tr>" +
        "<th>상품명</th>" +
        "<th>옵션</th>" +
        "<th>수량</th>" +
        "<th>가격</th>" +
        "<th>배송현황</th>" +
        "</tr>" +
        "</thead>";
}

function orderOptionStr (attach) {
    let sizeAndColorStatus = '';
    let orderCount = attach.orderCount;
    let orderPrice = attach.odPrice;

    if(attach.psize != null){
        if(attach.pcolor != null)
            sizeAndColorStatus = "사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor;
        else
            sizeAndColorStatus = "사이즈 : " + attach.psize;

    }else if(attach.pcolor != null)
        sizeAndColorStatus = "컬러 : " + attach.pcolor;

    return "<td>" + sizeAndColorStatus + "</td>" +
        "<td>" + orderCount + "</td>" +
        "<td>" + orderPrice + "</td>";
}

function nonOrderStatus (attach) {
    let orderStatus = '';
    if(attach.orderStat == 0){
        orderStatus = "배송준비중";
    }else if(attach.orderStat == 1){
        orderStatus = "배송중";
    }else if(attach.orderStat == 2){
        orderStatus = "배송완료(비회원은 리뷰를 작성하실 수 없습니다.)";
    }

    return "<td>" + orderStatus + "</td>";
}

function orderStatus (attach) {
    let orderStatus = '';

    if(attach.orderStat == 0)
        orderStatus = "배송준비중";
    else if(attach.orderStat == 1)
        orderStatus = "배송중";
    else if(attach.orderStat == 2){
        orderStatus = "배송완료";
        if(attach.reviewStat == 0)
            orderStatus += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.pno + "/" + attach.orderNo + "\"" +
                " onclick=\"insertReview(this)\">리뷰작성하기</button>";
    }

    return "<td>" + orderStatus + "</td>";
}