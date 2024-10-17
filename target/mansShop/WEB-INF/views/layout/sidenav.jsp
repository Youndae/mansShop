<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<link rel="stylesheet" href="/css/sidenav.css">
<script>
    $(function() {
        const path = window.location.pathname;
        if(path.startsWith('/my-page/qna')){
            const str = "<ul class=\"qna-nav-ul fs-l\">" +
                        "<li>" +
                            "<a href=\"/my-page/qna/product\">상품 문의</a>" +
                        "</li>" +
                        "<li>" +
                            "<a href=\"/my-page/qna/member\">문의 사항</a>" +
                        "</li>" +
                    "</ul>";

            $("#my-page-qna-li").append(str);
        }

    })
</script>
<body>
    <div class="side-nav">
        <ul class="side-nav-category-ul">
            <li><a href="/my-page/order">주문 내역</a></li>
            <li><a href="/my-page/like">관심 상품</a></li>
            <li id="my-page-qna-li">
                <a href="/my-page/qna/product">문의 내역</a>
            </li>
            <li><a href="/my-page/review">리뷰 내역</a></li>
            <li><a href="/my-page/member/info">정보 수정</a></li>
        </ul>
    </div>
</body>
</html>
