<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/modal.css">
<link rel="stylesheet" href="/css/pagination.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-content-header">
                <h1>리뷰 내역</h1>
            </div>
            <div class="mypage-like-content mypage-review-content">
                <div class="mypage-like-content-list myhpage-review-content-list">
                    <c:forEach items="${list.content}" var="review">
                        <div class="mypage-like-detail mypage-review-detail">
                            <div class="mypage-like-data-detail">
                                <div class="mypage-like-detail-content">
                                    <div class="mypage-like-remove">
                                        <img src="/display?image=del.jpg" class="mypage-like-delete-btn" id="${review.id}" onclick="deleteReviewBtnOnClick(this)">
                                    </div>
                                    <div class="mypage-like-thumb">
                                        <img src="/display?image=${review.thumbnail}" class="mypage-like-thumbnail">
                                        <div class="mypage-like-info">
                                            <div>
                                                <span class="like-data-product-name pointer" onclick="handleReviewOnClick(this)" id="${review.id}">
                                                    ${review.productName}
                                                </span>
                                            </div>
                                            <span class="mypage-review-detail-date">${review.updatedAt}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <%-- modal --%>
                    <div class="review-modal modal">
                        <div class="modal-content">

                        </div>
                    </div>
                </div>
            </div>

            <div class="paging like-paging">
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
            </form>
        </div>
    </div>
</div>
</body>
</html>
