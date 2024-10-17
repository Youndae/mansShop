<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/main.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content product-main">
        <div class="product-header">
            <h1>${productClassification}</h1>
        </div>
        <div class="product-content">
            <c:forEach items="${pList}" var="list">
                <div class="product-img">
                        <div class="thumb-image">
                            <a href="/product/${list.productId}" class="productThumbnail"><img class="image-data" id="ImageData" src="/display?image=${list.thumbnail}"/></a>
                        </div>
                        <div class="product-info">
                            <c:choose>
                                <c:when test="${list.stock == 0}">
                                    <p class="product-name sold-out">${list.productName} (품절)</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="product-name">${list.productName}</p>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${list.discount != 0}">
                                    <span class="discount-original-price"><fmt:formatNumber value="${list.price}" pattern="#,###"/>원</span>
                                    <span class="discount-percent">${list.discount}%</span>
                                    <span class="discount-price"><fmt:formatNumber value="${list.disCountPrice}" pattern="#,###"/>원 </span>
                                </c:when>
                                <c:otherwise>
                                    <p class="product-price"><fmt:formatNumber value="${list.price}" pattern="#,###"/>원</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                </div>
            </c:forEach>
        </div>
        <div class="paging">
            <ul class="pagination">
                <c:if test="${pageMaker.prev}">
                    <li class="paginate_button previous">
                        <a href="${pageMaker.startPage - 1}">Prev</a>
                    </li>
                </c:if>

                <c:if test="${pageMaker.endPage != 0}">
                    <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                        <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                            <a href="${num}">${num}</a>
                        </li>
                    </c:forEach>
                </c:if>

                <c:if test="${pageMaker.next}">
                    <li class="paginate_button next">
                        <a href="${pageMaker.endPage + 1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <form id="pageActionForm" method="get">
        <input type="hidden" id="page" name="page" value="<c:out value="${pageMaker.cri.pageNum}"/>">
        <input type="hidden" id="keyword" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
    </form>
</div>
</body>
</html>
