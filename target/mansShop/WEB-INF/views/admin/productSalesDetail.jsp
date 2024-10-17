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
                <h1>${detail.productName} 매출</h1>
            </div>
            <div class="admin-content-content">
                <div class="content-period-detail-month">
                    <div class="form-group">
                        <label>총 매출 : </label>
                        <span><fmt:formatNumber value="${detail.totalSales}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>총 판매량 : </label>
                        <span><fmt:formatNumber value="${detail.totalSalesQuantity}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>${detail.thisYear}년 매출 : </label>
                        <span><fmt:formatNumber value="${detail.yearSales}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>${detail.thisYear}년 판매량 : </label>
                        <span><fmt:formatNumber value="${detail.yearSalesQuantity}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>전년 대비 매출 : </label>
                        <span><fmt:formatNumber value="${detail.lastYearComparison}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>${detail.thisYear - 1}년 매출 : </label>
                        <span><fmt:formatNumber value="${detail.lastYearSales}" pattern="#,###"/></span>
                    </div>
                    <div class="form-group">
                        <label>${detail.thisYear - 1}년 판매량 : </label>
                        <span><fmt:formatNumber value="${detail.lastYearSalesQuantity}" pattern="#,###"/></span>
                    </div>
                </div>
                <div class="content-product-detail-month mt-5-pe">
                    <div class="admin-product-sales-detail-header">
                        <h3>월별 매출</h3>
                    </div>
                    <table class="admin-cotnent-table mt-5-pe">
                        <thead>
                        <tr>
                            <th>월</th>
                            <th>매출</th>
                            <th>판매량</th>
                            <th>주문량</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${detail.monthSales}" var="month">
                            <tr>
                                <td>${month.month}</td>
                                <td><fmt:formatNumber value="${month.sales}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${month.salesQuantity}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${month.orderQuantity}" pattern="#,###"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="content-product-detail-option">
                    <div class="content-product-detail-option-content mt-5-pe">
                        <div class="product-detail-option-content-header mt-5-pe">
                            <h3>옵션별 총 매출 정보</h3>
                        </div>
                        <div class="content-product-detail-option-content-content">
                            <c:forEach items="${detail.optionTotalSales}" var="total">
                                <c:set var="option" value=""/>
                                <c:if test="${total.productSize ne null}">
                                    <c:set var="option" value="사이즈 : ${total.productSize}"/>
                                </c:if>
                                <c:if test="${total.productColor ne null}">
                                    <c:if test="${not empty option}">
                                        <c:set var="option" value="${option}, "/>
                                    </c:if>
                                    <c:set var="option" value="${option}컬러 : ${total.productColor}, "/>
                                </c:if>

                                <div class="product-sales-detail-option b1-s-lg mt-5-p">
                                    <span>${option} 매출 : <fmt:formatNumber value="${total.optionSales}" pattern="#,###"/>, 판매량 : <fmt:formatNumber value="${total.optionSalesQuantity}" pattern="#,###"/></span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="content-product-detail-option-content mt-5-pe">
                        <div class="product-detail-option-content-header mt-5-pe">
                            <h3>옵션별 ${detail.thisYear}년 매출 정보</h3>
                        </div>
                        <div class="content-product-detail-option-content-content">
                            <c:forEach items="${detail.optionYearSales}" var="total">
                                <c:set var="option" value=""/>
                                <c:if test="${total.productSize ne null}">
                                    <c:set var="option" value="사이즈 : ${total.productSize}"/>
                                </c:if>
                                <c:if test="${total.productColor ne null}">
                                    <c:if test="${not empty option}">
                                        <c:set var="option" value="${option}, "/>
                                    </c:if>
                                    <c:set var="option" value="${option}컬러 : ${total.productColor}, "/>
                                </c:if>

                                <div class="product-sales-detail-option b1-s-lg mt-5-p">
                                    <span>${option} 매출 : <fmt:formatNumber value="${total.optionSales}" pattern="#,###"/>, 판매량 : <fmt:formatNumber value="${total.optionSalesQuantity}" pattern="#,###"/></span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="content-product-detail-option-content mt-5-pe">
                        <div class="product-detail-option-content-header mt-5-pe">
                            <h3>옵션별 ${detail.thisYear - 1}년 매출 정보</h3>
                        </div>
                        <div class="content-product-detail-option-content-content">
                            <c:forEach items="${detail.optionLastYearSales}" var="total">
                                <c:set var="option" value=""/>
                                <c:if test="${total.productSize ne null}">
                                    <c:set var="option" value="사이즈 : ${total.productSize}"/>
                                </c:if>
                                <c:if test="${total.productColor ne null}">
                                    <c:if test="${not empty option}">
                                        <c:set var="option" value="${option}, "/>
                                    </c:if>
                                    <c:set var="option" value="${option}컬러 : ${total.productColor}, "/>
                                </c:if>

                                <div class="product-sales-detail-option b1-s-lg mt-5-p">
                                    <span>${option} 매출 : <fmt:formatNumber value="${total.optionSales}" pattern="#,###"/>, 판매량 : <fmt:formatNumber value="${total.optionSalesQuantity}" pattern="#,###"/></span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
