<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminQnA.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<script>
    $(function () {
        const path = window.location.pathname;

        if(path.startsWith('/admin/qna/member/new'))
            $('#searchActionForm').attr('action', '/admin/qna/member/new');
        else if(path.startsWith('/admin/qna/member/all'))
            $('#searchActionForm').attr('action', '/admin/qna/member/all');
    })
</script>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="admin-content">
            <div class="admin-content-header">
                <h1>회원 문의</h1>
                <select class="admin-qna-select-box">
                    <option value="new">미처리 문의</option>
                    <option value="all">전체 문의</option>
                </select>
            </div>
            <div class="admin-content-content">
                <table class="admin-content-table">
                    <thead>
                    <tr>
                        <th>문의 분류</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>답변 상태</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list.content}" var="qna">
                        <c:set value="미답변" var="status"/>
                        <c:if test="${qna.status}">
                            <c:set value="답변 완료" var="status"/>
                        </c:if>

                        <tr class="admin-order-body-tr" onclick="memberQnAListOnClick(this)" data-qna-id="${qna.qnaId}">
                            <td>${qna.classification}</td>
                            <td>${qna.title}</td>
                            <td>${qna.writer}</td>
                            <td>${qna.createdAt}</td>
                            <td>${status}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
                    <form id="searchActionForm" method="get">
                        <input type="hidden" id="page" name="page" value="${list.pageMaker.cri.pageNum}">
                        <input type="hidden" id="keyword" name="keyword" value="${list.pageMaker.cri.keyword}">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
