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
                <h1>${list.term}</h1>
            </div>
            <div class="admin-content-content">
                <div class="content-period-detail-month">
                    <div class="form-group">
                        <label>월 매출 : </label>
                        <span><fmt:formatNumber value="${list.sales}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>월 판매량 : </label>
                        <span><fmt:formatNumber value="${list.salesQuantity}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>월 주문량 : </label>
                        <span><fmt:formatNumber value="${list.orderQuantity}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>전년 동월 대비 매출 : </label>
                        <span><fmt:formatNumber value="${list.lastYearComparison}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>전년 동월 매출 : </label>
                        <span><fmt:formatNumber value="${list.lastYearSales}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>전년 동월 판매량 : </label>
                        <span><fmt:formatNumber value="${list.lastYearSalesQuantity}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>전년 동월 주문량 : </label>
                        <span><fmt:formatNumber value="${list.lastYearOrderQuantity}" pattern="#,###"/></span>
                    </div>
                </div>
            </div>
            <div class="content-period-detail-best-product">
                <div class="admin-period-detail-best-product-header">
                    <h3>매출 베스트 5</h3>
                </div>
                <c:forEach items="${list.bestProduct}" var="best">
                    <div class="best-product-content">
                        <div class="form-group best-product-content-header">
                            <label>상품명 : </label>
                            <span>${best.productName}</span>
                        </div>
                        <div class="form-group best-product-content-header">
                            <label>매출 : </label>
                            <span><fmt:formatNumber value="${best.sales}" pattern="#,###"/></span>
                        </div>
                        <div class="form-group best-product-content-header">
                            <label>판매량 : </label>
                            <span><fmt:formatNumber value="${best.salesQuantity}" pattern="#,###"/></span>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="content-period-detail-classification">
                <div class="admin-period-detail-classification-header">
                    <h3>상품 분류별 매출</h3>
                </div>
                <c:forEach items="${list.classificationSales}" var="classification">
                    <div class="classification-sales">
                        <div class="classification-sales-header">
                            <button class="default-btn classification-sales-btn"
                                    value="${classification.classification}" onclick="handleClassificationDetailBtnOnClick(this)">
                                상세 내역
                            </button>
                        </div>
                        <div class="form-group">
                            <label>분류 : </label>
                            <span>${classification.classification}</span>
                        </div>
                        <div class="form-group">
                            <label>매출 : </label>
                            <span><fmt:formatNumber value="${classification.sales}" pattern="#,###"/></span>
                        </div>
                        <div class="form-group">
                            <label>판매량 : </label>
                            <span><fmt:formatNumber value="${classification.salesQuantity}" pattern="#,###"/></span>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="modal-background modal period-classification-modal">
                <div class="admin-modal-content period-classification-modal-content">

                </div>
            </div>
            <div class="content-period-detail-daily">
                <div class="admin-period-detail-daily-header">
                    <h3>일 매출</h3>
                </div>
                <table class="admin-content-table admin-period-table">
                    <thead>
                        <tr>
                            <th>일</th>
                            <th>매출</th>
                            <th>판매량</th>
                            <th>주문량</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list.dailySales}" var="daily">
                            <tr onclick="handlePeriodDailyOnClick(this)" data-term="${list.term}-${daily.month}">
                                <td>${daily.month}</td>
                                <td><fmt:formatNumber value="${daily.sales}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${daily.salesQuantity}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${daily.orderQuantity}" pattern="#,###"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-background modal period-daily-modal">
                <div class="admin-modal-content period-daily-modal-content">

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
