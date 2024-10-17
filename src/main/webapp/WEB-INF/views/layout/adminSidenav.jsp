<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<link rel="stylesheet" href="/css/sidenav.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSideNav.js"></script>
<body>
    <div class="side-nav">
        <ul class="side-nav-category-ul">
            <li id="product-li">
                <a href="/admin/product">상품 관리</a>
            </li>
            <li id="order-li">
                <a href="/admin/order/new">주문 관리</a>
            </li>
            <li id="qna-li">
                <a href="/admin/qna/product/new">문의 관리</a>
            </li>
            <li id="review-li">
                <a href="/admin/review">리뷰 관리</a>
            </li>
            <li><a href="/admin/member">회원 관리</a></li>
            <li id="sales-li">
                <a href="/admin/sales/period">매출 관리</a>
            </li>
        </ul>
    </div>
</body>
</html>
