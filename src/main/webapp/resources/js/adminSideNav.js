$(function() {
    const path = window.location.pathname;
    const defaultPath = '/admin'
    const productPath = `${defaultPath}/product`;
    const orderPath = `${defaultPath}/order`;
    const qnaPath = `${defaultPath}/qna`;
    const salesPath = `${defaultPath}/sales`;
    const memberPath = `${defaultPath}/member`;
    const date = new Date();
    const year = date.getFullYear();
    $('#sales-li a').attr('href', '/admin/sales/period/' + year);

    if(!path.startsWith(memberPath)){
        console.log('side nav');
        let str = '';
        let elements = '';
        if(path.startsWith(productPath)){
            str = "<ul class=\"admin-nav-ul fs-l\">" +
                "<li>" +
                "<a href=\"/admin/product\">상품 목록</a>" +
                "</li>" +
                "<li>" +
                "<a href=\"/admin/product/stock\">재고 관리</a>" +
                "</li>" +
                "<li>" +
                "<a href=\"/admin/product/discount\">할인 설정</a>" +
                "</li>" +
                "</ul>";
            elements = $("#product-li");
        }else if(path.startsWith(orderPath)) {
            str = "<ul class=\"admin-nav-ul fs-l\">" +
                "<li>" +
                "<a href=\"/admin/order/new\">미처리 목록</a>" +
                "</li>" +
                "<li>" +
                "<a href=\"/admin/order/all\">전체 목록</a>" +
                "</li>" +
                "</ul>";
            elements = $("#order-li");
        }else if(path.startsWith(qnaPath)) {
            str = "<ul class=\"admin-nav-ul fs-l\">" +
                "<li>" +
                "<a href=\"/admin/qna/product/new\">상품 문의</a>" +
                "</li>" +
                "<li>" +
                "<a href=\"/admin/qna/member/new\">회원 문의</a>" +
                "</li>" +
                "<li>" +
                "<a href=\"/admin/qna/classification\">문의 카테고리</a>" +
                "</li>" +
                "</ul>";
            elements = $("#qna-li");
        }else if(path.startsWith(salesPath)) {
            str = "<ul class=\"product-nav-ul fs-l\">" +
                "<li>" +
                "<a href=\"/admin/sales/period/" + year + "\">기간별 매출</a>" +
                "</li>" +
                "<li>" +
                "<a href=\"/admin/sales/product\">상품별 매출</a>" +
                "</li>" +
                "</ul>";
            elements = $("#sales-li");
        }

        elements.append(str);
    }
})