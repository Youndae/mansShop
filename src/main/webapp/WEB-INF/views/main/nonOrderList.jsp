<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/nonOrder.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <sec:authentication property="principal" var="userStat"/>
    <c:set var="firstClassName" value=""/>
    <c:set var="secondClassName" value=""/>
    <c:choose>
        <c:when test="${userStat != 'anonymousUser'}">
            <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
            <c:set var="firstClassName" value="mypage"/>
            <c:set var="secondClassName" value="mypage-content"/>
        </c:when>
        <c:otherwise>
            <c:set var="firstClassName" value="non-member-order"/>
            <c:set var="secondClassName" value="non-member-order-content"/>
        </c:otherwise>
    </c:choose>
    <div class="${firstClassName}">
        <div class="${secondClassName}">
            <div class="mypage-order-header">
                <h1>주문내역</h1>
                <div class="mypage-order-term-select">
                    <select id="mypage-order-select" onchange="orderSelectOnChange()">
                        <option value="3" ${list.pageMaker.cri.keyword == '3' ? 'selected' : ''}>3개월</option>
                        <option value="6" ${list.pageMaker.cri.keyword == '6' ? 'selected' : ''}>6개월</option>
                        <option value="12" ${list.pageMaker.cri.keyword == '12' ? 'selected' : ''}>12개월</option>
                        <option value="all" ${list.pageMaker.cri.keyword == 'all' ? 'selected' : ''}>전체</option>
                    </select>
                </div>
            </div>
            <c:choose>
                <c:when test="${list.content eq null}">
                    <div class="non-order-data-header">
                        <h2>주문 상품이 없습니다.</h2>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mypage-order-list-content">
                        <div class="mypage-order-list">
                            <c:forEach items="${list.content}" var="order">
                                <div class="mypage-order-content">
                                    <div class="mypage-order-list-content-header">
                                        <span>주문일 : ${order.orderDate}</span>
                                        <button type="button">배송조회</button>
                                    </div>
                                    <c:forEach items="${order.detail}" var="detail">
                                        <c:set var="result" value=""/>
                                        <c:if test="${detail.PSize ne null}">
                                            <c:set var="result" value="사이즈 : ${detail.PSize}"/>
                                        </c:if>
                                        <c:if test="${detail.color ne null}">
                                            <c:if test="${not empty result}">
                                                <c:set var="result" value="${result}, "/>
                                            </c:if>
                                            <c:set var="result" value="${result}컬러 : ${detail.color}"/>
                                        </c:if>

                                        <div class="mypage-order-data-detail">
                                            <div class="mypage-order-data-header">
                                                <a href="/product/${detail.productId}">
                                                    <span>${detail.productName}</span>
                                                </a>
                                            </div>
                                            <div class="mypage-order-data-content">
                                                <img src="/display?image=${detail.thumbnail}"/>
                                                <div class="order-data-info">
                                                    <span class="order-data-product-option">${result}</span>
                                                    <span class="order-data-product-count">
                                                    주문 수량 : <fmt:formatNumber value="${detail.detailCount}" pattern="#,###"/>
                                                </span>
                                                    <span class="order-data-product-price">
                                                    금액 : <fmt:formatNumber value="${detail.detailPrice}" pattern="#,###"/> 원
                                                </span>
                                                </div>
                                                <div class="order-data-info-btn">
                                                    <input type="hidden" name="reviewStatusCheck" value="${detail.reviewStatus}">
                                                    <sec:authentication property="principal" var="userStat"/>
                                                    <c:if test="${userStat != 'anonymousUser' or order.orderStat == '배송완료'}">
                                                        <c:choose>
                                                            <c:when test="${detail.reviewStatus}">
                                                                <button type="button" disabled>리뷰작성완료</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button class="default-btn" type="button" value="${detail.detailId}" onclick="insertReview(this)">리뷰작성</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="order-data-info-content">
                                        <span>배송현황 : ${order.orderStat}</span>
                                        <span class="order-data-totalprice">
                                        총 주문 금액 : <fmt:formatNumber value="${order.orderTotalPrice}" pattern="#,###"/>
                                    </span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <%--페이징--%>
            <div class="paging order-paging">
                <ul class="pagination">
                    <c:if test="${list.pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${list.pageMaker.startPage - 1}">Prev</a>
                        </li>
                    </c:if>

                    <c:if test="${list.pageMaker.endPage gt 1}">
                        <c:forEach var="num" begin="${list.pageMaker.startPage}" end="${list.pageMaker.endPage}">
                            <li class="paginate_button ${list.pageMaker.cri.pageNum == num?"active":""}">
                                <a href="${num}">${num}</a>
                            </li>
                        </c:forEach>
                    </c:if>

                    <c:if test="${list.pageMaker.next}">
                        <li class="paginate_button next">
                            <a href="${list.pageMaker.endPage + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <form id="pageActionForm" method="get">
                <input type="hidden" id="page" name="page" value="<c:out value="${list.pageMaker.cri.pageNum}"/>">
                <input type="hidden" id="term" name="term" value="<c:out value="${list.pageMaker.cri.keyword}"/>">
                <c:if test="${userStat == 'anonymousUser'}">
                    <input type="hidden" id="recipient" name="recipient" value="${list.userData.recipient}">
                    <input type="hidden" id="orderPhone" name="orderPhone" value="${list.userData.orderPhone}">
                </c:if>
            </form>
        </div>
    </div>
</div>
</body>
</html>
