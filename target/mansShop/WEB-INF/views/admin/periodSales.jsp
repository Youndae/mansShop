<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSales.js"></script>
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
                <h1>기간별 매출</h1>
            </div>
            <div class="admin-content-content">
                <div class="admin-content-content-header admin-period-list-header">
                    <h3>${list.term}년 매출</h3>
                    <select class="admin-period-select-box">

                    </select>
                </div>
                <table class="admin-content-table admin-period-table">
                    <thead>
                        <tr>
                            <th>월</th>
                            <th>매출</th>
                            <th>상품 출고량</th>
                            <th>주문량</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list.content}" var="sales">
                            <tr onclick="handlePeriodMonthOnClick(this)" data-term="${list.term}-${sales.month}">
                                <td>${sales.month}</td>
                                <td><fmt:formatNumber value="${sales.sales}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${sales.salesQuantity}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${sales.orderQuantity}" pattern="#,###"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="admin-content-year-sales">
                    <p>${list.term}년 매출 : <fmt:formatNumber value="${list.sales}" pattern="#,###"/></p>
                    <p>${list.term}년 판매량 : <fmt:formatNumber value="${list.salesQuantity}" pattern="#,###"/></p>
                    <p>${list.term}년 주문량 : <fmt:formatNumber value="${list.orderQuantity}" pattern="#,###"/></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
