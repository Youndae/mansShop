<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminProduct.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="admin-content">
            <div class="admin-content-header">
                <h1>할인 설정</h1>
                <button type="button" class="default-btn" onclick="handleProductDiscount()">할인 적용</button>
            </div>
            <div class="admin-content-content">
                <div class="discount-content-header">
                    <div class="discount-classification-select">
                        <label>상품 분류 : </label>
                        <select class="discount-classification-select-box">
                            <option value="default" selected hidden>상품 분류를 선택해주세요</option>
                            <c:forEach items="${classification}" var="category">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="discount-product-select"></div>
                    <div class="discount-content-content"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
