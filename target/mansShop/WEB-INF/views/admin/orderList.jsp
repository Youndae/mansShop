<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<script>
    $(function() {
        const path = window.location.pathname;
        if(path.startsWith('/admin/order/new'))
            $(".admin-content-header").append(`<h1>미처리 주문 목록 (${list.pageMaker.total} 건)</h1>`);
        else
            $(".admin-content-header").append(`<h1>전체 주문 목록</h1>`);
    })
</script>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="admin-content">
            <div class="admin-content-header"></div>
            <div class="admin-content-content">
                <table class="admin-content-table">
                    <thead>
                        <tr>
                            <th>받는사람</th>
                            <th>사용자 아이디</th>
                            <th>연락처</th>
                            <th>주문일</th>
                            <th>처리 상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list.content}" var="order">
                            <tr class="admin-order-body-tr" onclick="handleOrderElementsOnClick(this)" id="${order.id}">
                                <td>${order.recipient}</td>
                                <td>${order.userId}</td>
                                <td>${order.phone}</td>
                                <td>${order.createdAt}</td>
                                <td>${order.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="modal-background modal">
                    <div class="admin-modal-content">

                    </div>
                </div>
            </div>

            <div class="admin-search">
                <select class="admin-order-search search-type-select">
                    <option value="recipient">받는 사람</option>
                    <option value="userId">사용자 아이디</option>
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
                <form id="searchActionForm" method="get" action="/admin/order/all">
                    <input type="hidden" id="page" name="page" value="${list.pageMaker.cri.pageNum}">
                    <input type="hidden" id="keyword" name="keyword" value="${list.pageMaker.cri.keyword}">
                    <input type="hidden" id="searchType" name="searchType" value="${list.pageMaker.cri.searchType}">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
