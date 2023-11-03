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
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/cart.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">
        <div class="cart_header content_header">
            <h1>장바구니</h1>
        </div>
        <div class="cart_content content_data">
            <c:choose>
                <c:when test="${cartList != [] and cartList != null}">
                    <div class="cart_List">
                        <table class="cart_Table" border="1">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>상품명</th>
                                    <th>옵션</th>
                                    <th class="count_head">수량</th>
                                    <th class="price_head">가격</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cartList}" var="cList">
                                    <c:set var="total" value="${total + cList.CPrice}"/>
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="check"
                                                   value="${cList.POpNo}"
                                                   data_pName="${cList.PName}"
                                                   data_pSize="${cList.PSize}"
                                                   data_pColor="${cList.PColor}"
                                                   data_cCount="${cList.CCount}"
                                                   data_cPrice="${cList.CPrice}"
                                                   data_pno="${cList.pno}"
                                                   data_cdNo="${cList.cdNo}"
                                                   checked>
                                        </td>
                                        <td class="pName_body"><c:out value="${cList.PName}"/></td>
                                        <td class="pOption_body">
                                            <c:choose>
                                                <c:when test="${cList.PSize == null}">
                                                    <c:choose>
                                                        <c:when test="${cList.PColor != null}">
                                                            색상 : <c:out value="${cList.PColor}"/>
                                                        </c:when>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${cList.PColor == null}">
                                                            사이즈 : <c:out value="${cList.PSize}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            사이즈 : <c:out value="${cList.PSize}"/>  색상 : <c:out value="${cList.PColor}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="count_body">
                                            <span><c:out value="${cList.CCount}"/></span>
                                            <input type="hidden" value="<c:out value="${cList.cdNo}"/>">
                                            <div class="count_btn">
                                                <button class="productCount up" name="up"><img src="/display?image=up.jpg"></button>
                                                <button class="productCount down" name="down"><img src="/display?image=down.jpg"></button>
                                            </div>
                                        </td>
                                        <td class="price_body"><fmt:formatNumber value="${cList.CPrice}" pattern="#,### 원"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="cart_content_footer">
                        <span class="total_price">총 주문 금액 : <fmt:formatNumber value="${total}" pattern="#,###"/> 원</span>
                        <div class="cart_content_btn">
                            <button type="button" id="select_delete">선택상품 삭제</button>
                            <button type="button" id="select_order">선택상품 주문</button>
                        </div>
                    </div>
                </c:when>
                <c:when test="${cartList == [] or cartList == null}">
                    <span>장바구니에 담은 상품이 없습니다.</span>
                </c:when>
            </c:choose>
        </div>
        <form id="order_form" method="post">
            <input type="hidden" name="cdNo">
            <input type="hidden" name="pOpNo">
            <input type="hidden" name="pName">
            <input type="hidden" name="pSize">
            <input type="hidden" name="pColor">
            <input type="hidden" name="cCount">
            <input type="hidden" name="cPrice">
            <input type="hidden" name="pno">
            <input type="hidden" name="oType" value="c">
            <sec:csrfInput/>
        </form>
    </div>
</div>
</div>
</body>
</html>
