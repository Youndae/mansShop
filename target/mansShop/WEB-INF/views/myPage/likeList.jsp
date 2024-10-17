<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="container">
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content test-div">
            <div class="mypage-like-header">
                <h1>관심 상품</h1>
            </div>
            <div class="mypage-like-content">
                <div class="mypage-like-content-list">
                    <c:forEach items="${likeList.content}" var="like">
                        <div class="mypage-like-detail">
                            <div class="mypage-like-data-detail">
                                <div class="mypage-like-detail-content">
                                    <div class="mypage-like-remove">
                                        <img src="/display?image=del.jpg" class="mypage-like-delete-btn" id="${like.likeId}" onclick="deleteLikeBtnOnClick(this)">
                                    </div>
                                    <div class="mypage-like-thumb">
                                        <img src="/display?image=${like.thumbnail}" class="mypage-like-thumbnail">
                                        <div class="mypage-like-info">
                                            <a href="/product/${like.productId}">
                                                <span class="like-data-product-name">${like.productName}</span>
                                            </a>
                                            <span class="like-data-product-price">
                                                <c:if test="${like.productDiscount > 0}">
                                                    <p class="cart-option-price-discount">
                                                        -${like.productDiscount}%
                                                    </p>
                                                </c:if>
                                                <fmt:formatNumber value="${like.productTotalPrice}" pattern="#,###"/> 원
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="paging like-paging">
                <ul class="pagination">
                    <c:if test="${likeList.pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${likeList.pageMaker.startPage - 1}">Prev</a>
                        </li>
                    </c:if>

                    <c:if test="${likeList.pageMaker.endPage gt 1}">
                        <c:forEach var="num" begin="${likeList.pageMaker.startPage}" end="${likeList.pageMaker.endPage}">
                            <li class="paginate_button ${likeList.pageMaker.cri.pageNum == num?"active":""}">
                                <a href="${num}">${num}</a>
                            </li>
                        </c:forEach>
                    </c:if>

                    <c:if test="${likeList.pageMaker.next}">
                        <li class="paginate_button next">
                            <a href="${likeList.pageMaker.endPage + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <form id="pageActionForm" method="get">
                <input type="hidden" id="page" name="page" value="<c:out value="${likeList.pageMaker.cri.pageNum}"/>">
            </form>
        </div>
    </div>
</div>
</body>
</html>
