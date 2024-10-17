<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/js/orderPayment.js"></script>
<script type="text/javascript" src="/js/default.js"></script>
<link rel="stylesheet" href="/css/order.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="order">
        <div class="order-header">
            <h1>상품 결제</h1>
        </div>
        <div class="order-content">
            <div class="order-form">
                <div class="form-content">
                    <div class="form-content-label">
                        <label>수령인</label>
                    </div>
                    <div class="form-content-input">
                        <input type="text" id="recipient">
                    </div>
                    <div class="recipient-overlap overlap">

                    </div>
                </div>
                <div class="form-content">
                    <div class="form-content-label">
                        <label>연락처</label>
                    </div>
                    <div class="form-content-input">
                        <input type="text" id="phone" placeholder="-를 제외한 숫자만 입력">
                    </div>
                    <div class="phone-overlap overlap">

                    </div>
                </div>
                <div class="form-content">
                    <div class="form-content-label">
                        <label>배송지</label>
                    </div>
                    <div class="form-content-input-postcode">
                        <input type="text" id="postCode" placeholder="우편번호" readonly>
                        <button type="button" id="searchAddr">우편번호 찾기</button>
                    </div>
                    <div class="form-content-input-address">
                        <input style="width: 700px" type="text" id="address" placeholder="주소" readonly>
                    </div>
                    <div class="form-content-input-detail-address">
                        <input type="text" id="addrDetail" placeholder="상세주소">
                    </div>
                </div>
                <div class="addr-overlap overlap">

                </div>
                <div class="form-content">
                    <div class="form-content-label">
                        <label>배송 메모</label>
                    </div>
                    <div class="form-content-input">
                        <input type="text" id="orderMemo">
                    </div>
                </div>
            </div>
            <table class="order-table" border="1">
                <thead>
                    <tr>
                        <th>상품명</th>
                        <th>옵션</th>
                        <th>수량</th>
                        <th>가격</th>
                    </tr>
                </thead>
                <tbody class="order-table-body">
                    <c:forEach items="#{order.products}" var="product">
                        <c:set var="result" value=""/>
                        <c:if test="${product.PSize ne null}">
                            <c:set var="result" value="사이즈 : ${product.PSize}"/>
                        </c:if>
                        <c:if test="${product.PColor ne null}">
                            <c:if test="${not empty result}">
                                <c:set var="result" value="${result}, "/>
                            </c:if>
                            <c:set var="result" value="${result}컬러 : ${product.PColor}"/>
                        </c:if>

                        <tr
                            data-option-id="${product.productOptionId}"
                            data-count="${product.count}"
                            data-price="${product.productTotalPrice}"
                            data-product-id="${product.productId}"
                        >
                            <td>${product.productName}</td>
                            <td>${result}</td>
                            <td>${product.count}</td>
                            <td>
                                <c:if test="${product.discount > 0}">
                                    <span class="discount-value">${product.discount}%</span>
                                </c:if>
                                <span class="price"><fmt:formatNumber value="${product.productTotalPrice}" pattern="#,###"/> 원</span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="order-price">
                <span class="deliver-fee">배송비 : ${order.deliveryFee}</span>
                <span class="total-price">총 주문 금액 : <fmt:formatNumber value="${order.finalTotalPrice}" pattern="#,###"/> 원</span>
            </div>
            <div class="order-payment">
                <div class="form-content-label">
                    <label>결제 수단</label>
                </div>
                <div>
                    <input type="radio" name="select-payment" value="card">신용카드
                    <input type="radio" name="select-payment" value="cash">무통장 입금
                </div>
            </div>
            <div class="order-payment-btn">
                <button type="button" class="default-btn" id="payment">결제하기</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
