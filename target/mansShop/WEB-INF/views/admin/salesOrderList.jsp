<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSales.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="admin-content">
            <div class="admin-content-header">
                <h1>${list.term.replace('-', '/')} 주문 내역</h1>
            </div>
            <div class="admin-content-content">
                <c:forEach items="${list.content}" var="order">
                    <div class="admin-period-order-content">
                        <c:forEach items="${order.detailList}" var="detail">
                            <c:set var="option" value=""/>
                            <c:if test="${detail.productSize ne null}">
                                <c:set var="option" value="사이즈 : ${detail.productSize}"/>
                            </c:if>
                            <c:if test="${detail.productColor ne null}">
                                <c:if test="${not empty option}">
                                    <c:set var="option" value="${option}, "/>
                                </c:if>
                                <c:set var="option" value="${option}컬러 : ${detail.productColor}, "/>
                            </c:if>

                            <div class="form-group">
                                <label>${detail.productName}</label>
                                <p>${option}수량 : ${detail.count}, 금액 : <fmt:formatNumber value="${detail.price}" pattern="#,###"/></p>
                            </div>
                        </c:forEach>
                        <p class="admin-period-order-content-total">결제 금액 : <fmt:formatNumber value="${order.totalPrice}" pattern="#,###"/>, 배송비 : <fmt:formatNumber value="${order.deliveryFee}" pattern="#,###"/>, 결제 방식 : ${order.paymentType}</p>
                    </div>
                </c:forEach>
            </div>
            <div class="paging like-paging">
                <ul class="pagination">
                    <c:if test="${list.pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${likeList.pageMaker.startPage - 1}">Prev</a>
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
            <form id="pageActionForm" method="get" action="/admin/sales/period/detail/date/${list.term}">
                <input type="hidden" id="page" name="page" value="<c:out value="${list.pageMaker.cri.pageNum}"/>">
            </form>
        </div>
    </div>
</div>
</body>
</html>
