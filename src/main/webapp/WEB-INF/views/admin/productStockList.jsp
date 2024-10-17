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
                <h1>상품 재고</h1>
            </div>
            <div class="admin-content-content">
                <table class="admin-content-table product-stock-table">
                    <thead>
                        <tr>
                            <th>분류</th>
                            <th>상품명</th>
                            <th>총 재고</th>
                            <th>옵션 수</th>
                            <th>공개 여부</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list.content}" var="info">
                            <tr>
                                <td>${info.classification}</td>
                                <td>
                                    <a href="/admin/product/${info.productId}">
                                        ${info.productName}
                                    </a>
                                </td>
                                <td>${info.totalStock}</td>
                                <td>${info.options.size()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${info.open}">
                                            공개
                                        </c:when>
                                        <c:otherwise>
                                            비공개
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach items="${info.options}" var="options">
                                <c:set var="optionValue" value=""/>
                                <c:set var="isOpenValue" value="비공개"/>
                                <c:if test="${options.productSize ne null}">
                                    <c:set var="optionValue" value="사이즈 : ${options.productSize}"/>
                                </c:if>
                                <c:if test="${options.productColor ne null}">
                                    <c:if test="${not empty optionValue}">
                                        <c:set var="optionValue" value="${optionValue}, "/>
                                    </c:if>
                                    <c:set var="optionValue" value="${optionValue}컬러 : ${options.productColor}"/>
                                </c:if>
                                <c:if test="${options.open}">
                                    <c:set var="isOpenValue" value="공개"/>
                                </c:if>

                                <tr class="admin-stock-option">
                                    <td colspan="2">${optionValue}</td>
                                    <td><fmt:formatNumber value="${options.stock}" pattern="#,###"/> </td>
                                    <td></td>
                                    <td>${isOpenValue}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="admin-search">
                <input type="text" id="search-input" value="${list.pageMaker.cri.keyword}">
                <img src="https://as1.ftcdn.net/v2/jpg/03/25/73/68/1000_F_325736897_lyouuiCkWI59SZAPGPLZ5OWQjw2Gw4qY.jpg" alt="" onclick="paginationSearchBtnOnClick()">
                <div class="paging like-paging">
                    <ul class="search-pagination">
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
                <form id="searchActionForm" method="get" action="/admin/product/stock">
                    <input type="hidden" id="page" name="page" value="${list.pageMaker.cri.pageNum}">
                    <input type="hidden" id="keyword" name="keyword" value="${list.pageMaker.cri.keyword}">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
