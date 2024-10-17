<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/cart.js"></script>
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/cart.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <div class="cart">
            <div class="cart-content">
                <div class="cart-header">
                    <h1>장바구니</h1>
                </div>
                <div class="cart-order-btn-content">
                    <button type="button" class="default-btn select-productOrder-btn" onclick="selectOrderCart()">선택 상품 주문</button>
                    <button type="button" class="default-btn all-productOrder-btn" onclick="allOrderCart()">전체 상품 주문</button>
                    <button type="button" class="default-btn select-delete-btn" onclick="selectDeleteCart()">선택 상품 삭제</button>
                    <button type="button" class="default-btn all-delete-btn" onclick="allDeleteCart()">전체 상품 삭제</button>
                </div>
                <div class="content-data">
                    <c:choose>
                        <c:when test="${cart.content.size() == 0 or cart == null}">
                            <h3>장바구니에 담긴 상품이 없습니다.</h3>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${cart.content}" var="detail">
                                <div class="cart-data">
                                    <div class="cart-data-header">
                                        <input type="checkbox" class="cart-checkbox" value="${detail.cartDetailId}" onchange="checkBoxOnChange(this)">
                                        <span class="product-name">${detail.productName}</span>
                                        <img src="/display?image=del.jpg" id="${detail.cartDetailId}" onclick="removeCartProduct(this)">
                                    </div>
                                    <div class="cart-data-content">
                                        <img src="/display?image=${detail.thumbnail}" class="cart-thumbnail" alt="">
                                        <div class="cart-info">
                                            <c:set var="result" value=""/>
                                            <c:if test="${detail.PSize ne null}">
                                                <c:set var="result" value="사이즈 : ${detail.PSize}"/>
                                            </c:if>
                                            <c:if test="${detail.PColor ne null}">
                                                <c:if test="${not empty result}">
                                                    <c:set var="result" value="${result}, "/>
                                                </c:if>
                                                <c:set var="result" value="${result}컬러 : ${detail.PColor}"/>
                                            </c:if>

                                            <span class="productOption">${result}</span>

                                            <div class="cart-input-content">
                                                <input type="text" class="cart-input" value="${detail.count}" readonly>
                                                <div class="cart-count">
                                                    <img src="/display?image=up.jpg" id="${detail.cartDetailId}" onclick="cartCountUp(this)">
                                                    <img src="/display?image=down.jpg" id="${detail.cartDetailId}" onclick="cartCountDown(this)">
                                                </div>
                                            </div>
                                            <div class="cart-price">
                                                <p class="cart-option-price">
                                                    <c:if test="${detail.discount > 0}">
                                                        <p class="cart-option-price-discount">
                                                            -${detail.discount}%
                                                        </p>
                                                    </c:if>
                                                    <fmt:formatNumber value="${detail.totalPrice}" pattern="#,###"/> 원
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="cart-total-price">
                    <c:if test="${cart.content.size() != 0 and cart != null}">
                        <span class="total-price">
                        총 <fmt:formatNumber value="${cart.totalPrice}" pattern="#,###"/> 원
                    </span>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
