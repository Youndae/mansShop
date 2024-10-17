<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminReview.js"></script>
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
                <h1>리뷰 목록</h1>
            </div>
            <div class="admin-content-content">
                <table class="admin-content-table">
                    <thead>
                        <tr>
                            <th>상품명</th>
                            <th>작성자</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list.content}" var="review">
                            <tr class="admin-order-body-tr" onclick="reviewElementsOnClick(this)" data-review-id="${review.reviewId}">
                                <td>${review.productName}</td>
                                <td>${review.userId}</td>
                                <td>${review.updatedAt}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="admin-search">
                    <select class="admin-order-search search-type-select">
                        <option value="userId">아이디</option>
                        <option value="productName">상품명</option>
                    </select>
                    <input type="text" id="search-input" value="${list.pageMaker.cri.keyword}">
                    <img src="https://as1.ftcdn.net/v2/jpg/03/25/73/68/1000_F_325736897_lyouuiCkWI59SZAPGPLZ5OWQjw2Gw4qY.jpg" alt="" onclick="paginationTypeSearchBtnOnClick()">
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
                    <form id="searchActionForm" method="get" action="/admin/review">
                        <input type="hidden" id="page" name="page" value="${list.pageMaker.cri.pageNum}">
                        <input type="hidden" id="keyword" name="keyword" value="${list.pageMaker.cri.keyword}">
                        <input type="hidden" id="searchType" name="searchType" value="${list.pageMaker.cri.searchType}">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
