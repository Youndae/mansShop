$(function() {
    const path = window.location.pathname;
    const year = Number(path.substring(20));
    const thisYear = new Date().getFullYear();
    console.log('thisYear : ', thisYear);
    console.log('year : ', year);
    let periodSelectBoxOptionStr = '';

    for(let i = thisYear; i >= thisYear - 3; i--) {
        if(i === year)
            periodSelectBoxOptionStr += "<option value=\"" + i + "\" selected>" + i + "</option>";
        else
            periodSelectBoxOptionStr += "<option value=\"" + i + "\">" + i + "</option>";
    }
    $('.admin-period-select-box').append(periodSelectBoxOptionStr);

    $(window).click(function (e) {
        if($(e.target).is('.period-classification-modal')) {
            $('.period-classification-modal').fadeOut();
            $(".period-classification-modal-content").empty();
        }
    })

    $(window).click(function (e) {
        if($(e.target).is('.period-daily-modal')) {
            $('.period-daily-modal').fadeOut();
            $(".period-daily-modal-content").empty();
        }
    })

    $('.admin-period-select-box').change(function() {
        const year = $('.admin-period-select-box option:selected').val();
        location.href = `/admin/sales/period/${year}`;
    })

})

function handlePeriodMonthOnClick(obj) {
    const yearMonth = $(obj).attr('data-term');

    location.href=`/admin/sales/period/detail/${yearMonth}`;
}

function handleClassificationDetailBtnOnClick(obj) {
    const classificationId = obj.value;
    const term = $('.admin-content-header h1').text();
    $.getJSON(`/admin/sales/period/detail/classification?term=${term}&classification=${classificationId}`, function(data) {
        let str = "<div class=\"modal-content-header\">" +
                    "<h1>" + classificationId + " 매출 정보</h1>" +
                    "</div>" +
                    "<div class=\"modal-content-content\">" +
                        "<div class=\"admin-period-classification-modal\">" +
                            "<div class=\"admin-period-modal-content-sales\">" +
                                "<div class=\"form-group\">" +
                                    "<label>매출 : </label>" +
                                    "<span>" + data.totalSales.toLocaleString() + "</span>" +
                                "</div>" +
                                "<div class=\"form-group\">" +
                                    "<label>판매량 : </label>" +
                                    "<span>" + data.totalSalesQuantity.toLocaleString() + "</span>" +
                                "</div>" +
                            "</div>" +
                            "<div class=\"admin-period-modal-content-table\">" +
                                "<table class=\"admin-period-modal-table\">" +
                                    "<thead>" +
                                        "<tr>" +
                                            "<th>상품명</th>" +
                                            "<th>옵션</th>" +
                                            "<th>매출</th>" +
                                            "<th>판매량</th>" +
                                        "</tr>" +
                                    "</thead>" +
                                    "<tbody>";

        const productList = data.productSales;

        for(let i = 0; i < productList.length; i++) {
            const product = productList[i];
            let optionText = '';
            const size = `사이즈 : ${product.productSize}`;
            const color = `컬러 : ${product.productColor}`;
            if(product.productSize === null) {
                if(product.productColor !== null)
                    optionText = color;
            }else {
                if(product.productColor !== null)
                    optionText = `${size}, ${color}`;
                else
                    optionText = size;
            }

            str += "<tr>" +
                "<td>" + product.productName + "</td>" +
                "<td>" + optionText + "</td>" +
                "<td>" + product.productSales.toLocaleString() + "</td>" +
                "<td>" + product.productQuantity.toLocaleString() + "</td>" +
                "</tr>"
        }


       str += "</tbody>" +
            "</table>" +
           "</div>" +
        "</div>" +
       "</div>";

        $('.period-classification-modal-content').append(str);
    })

    $('.period-classification-modal').fadeIn();
}

function handlePeriodDailyOnClick(obj) {
    const term = $(obj).attr('data-term');

    $.getJSON(`/admin/sales/period/detail/day?term=${term}`, function (data) {
        let str = "<div class=\"modal-content-header\">" +
                        "<h1>" + data.term + " 매출 정보</h1>" +
                    "</div>" +
                    "<div class=\"modal-content-content\">" +
                        "<div class=\"admin-period-classification-modal\">" +
                            "<button type=\"button\" class=\"default-btn admin-period-daily-header-btn\" " +
                                "value=\"" + data.term + "\" onclick=\"handleDailyOrderListBtnOnClick(this)\">" +
                            "주문 내역" +
                            "</button>" +
                            "<div class=\"admin-period-modal-content-sales\">" +
                                "<div class=\"form-group\">" +
                                    "<label>매출 : </label>" +
                                    "<span>" + data.sales.toLocaleString() + "</span>" +
                                "</div>" +
                                "<div class=\"form-group\">" +
                                    "<label>판매량 : </label>" +
                                    "<span>" + data.salesQuantity.toLocaleString() + "</span>" +
                                "</div>" +
                                "<div class=\"form-group\">" +
                                    "<label>주문량 : </label>" +
                                    "<span>" + data.orderQuantity.toLocaleString() + "</span>" +
                                "</div>" +
                            "</div>" +
                        "</div>";

        const contentList = data.content;
        for(let i = 0; i < contentList.length; i++) {
            const content = contentList[i];
            str += "<div class=\"classification-sales daily-classification-sales\">" +
                        "<div class=\"form-group\">" +
                            "<label>분류 : </label>" +
                            "<span>" + content.classification + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>매출 : </label>" +
                            "<span>" + content.sales.toLocaleString() + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>판매량 : </label>" +
                            "<span>" + content.salesQuantity.toLocaleString() + "</span>" +
                        "</div>" +
                    "</div>";
        }

        str += "</div>";

        $('.period-daily-modal-content').append(str);
    })

    $('.period-daily-modal').fadeIn();
}

function handleDailyOrderListBtnOnClick(obj) {
    const term = obj.value;

    location.href = `/admin/sales/period/detail/date/${term}`;
}

function handleProductSalesElementsOnClick(obj) {
    const productId = $(obj).attr('data-product-id');

    location.href = `/admin/sales/product/detail/${productId}`;
}