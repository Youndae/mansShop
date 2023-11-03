<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/memberQnA.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">
        <div class="memberQnAList_header content_header">
            <h1>문의사항</h1>
            <button type="button" id="insertMemberQnA">문의하기</button>
        </div>
        <div class="memberQnAList_content content_data">
            <table class="tbl_default" border="1">
                <thead>
                <tr>
                    <th>상태</th>
                    <th>제목</th>
                    <th>작성일</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${qList}" var="list">
                    <tr>
                        <c:choose>
                            <c:when test="${list.QStat == 0}">
                                <td>미답변</td>
                            </c:when>
                            <c:when test="${list.QStat == 1}">
                                <td>답변완료</td>
                            </c:when>
                            <c:when test="${list.QStat == 2}">
                                <td>공지</td>
                            </c:when>
                        </c:choose>
                        <td>
                            <a href="<c:url value="/myPage/memberQnADetail/${list.qno}"/>"><c:out value="${list.QTitle}"/></a>
                        </td>
                        <td><c:out value="${list.QRegDate}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="paging">
                <ul class="pagination">
                    <c:if test="${pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${pageMaker.startPage - 1}">Prev</a>
                        </li>
                    </c:if>

                    <c:if test="${pageMaker.endPage != 1}">
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
    </div>

        <form id="pageActionForm" action="/myPage/memberQnAList" method="get">
            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        </form>
    </div>
</div>
</div>
</body>
</html>
