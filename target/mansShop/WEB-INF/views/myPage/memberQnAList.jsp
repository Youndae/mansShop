<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-qna-header">
                <h1>문의 사항</h1>
                <div class="mypage-qna-header-btn">
                    <button class="default-btn" onclick="insertMemberQnA()">문의하기</button>
                </div>
            </div>
            <div class="mypage-qna-content">
                <table class="qna-table">
                    <thead>
                        <tr>
                            <th>분류</th>
                            <th>제목</th>
                            <th>답변 상태</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${qna.content}" var="list">
                            <tr>
                                <td>${list.classification}</td>
                                <td>
                                    <a href="/my-page/qna/member/${list.id}">
                                        ${list.title}
                                    </a>
                                </td>
                                <td>
                                    <c:set var="status" value=""/>
                                    <c:choose>
                                        <c:when test="${list.status}">
                                            <c:set var="status" value="답변 완료"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="status" value="미답변"/>
                                        </c:otherwise>
                                    </c:choose>
                                        ${status}
                                </td>
                                <td>${list.updatedAt}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="paging like-paging">
                <ul class="pagination">
                    <c:if test="${qna.pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${qna.pageMaker.startPage - 1}">Prev</a>
                        </li>
                    </c:if>

                    <c:if test="${qna.pageMaker.endPage gt 1}">
                        <c:forEach var="num" begin="${qna.pageMaker.startPage}" end="${qna.pageMaker.endPage}">
                            <li class="paginate_button ${qna.pageMaker.cri.pageNum == num?"active":""}">
                                <a href="${num}">${num}</a>
                            </li>
                        </c:forEach>
                    </c:if>

                    <c:if test="${qna.pageMaker.next}">
                        <li class="paginate_button next">
                            <a href="${qna.pageMaker.endPage + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <form id="pageActionForm" method="get" action="/my-page/qna/member">
                <input type="hidden" id="page" name="page" value="<c:out value="${qna.pageMaker.cri.pageNum}"/>">
            </form>
        </div>
    </div>
</div>
</body>
</html>
