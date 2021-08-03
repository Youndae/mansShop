<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 30%;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .pQnAList_header{
        margin: 40px 0 50px 200px;
    }

    .pQnAList_content{
        margin: 50px 0 50px 200px;
        width: 100%;
        height: 100%;
    }

    .pQnAList_content table{
        font-size: large;
        margin: 25px 0 25px 0;
        width: 1000px;
        height: 200px;
        text-align: center;
        border: 1px solid lightgray;
    }

    table tr{
        margin: 10px;
    }

    .paging li{
        list-style: none;
        display: inline;
        margin: 10px;
        font-size: large;
    }


</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/page.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="pQnAList_header">
                <h1>상품문의</h1>
            </div>
            <div class="pQnAList_content">
                <table border="1">
                    <thead>
                        <tr>
                            <th>상품명</th>
                            <th>작성자</th>
                            <th>상태</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="QnAList">
                            <c:choose>
                                <c:when test="${QnAList.PQnAIndent == 0}">
                                        <tr>
                                            <td>
                                            <a href="<c:url value="/admin/productQnADetail/${QnAList.PQnANo}"/>">
                                                <c:out value="${QnAList.PName}"/>
                                            </a>
                                            </td>
                                            <td><c:out value="${QnAList.userId}"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${QnAList.PQnAAnswer == 0}">
                                                        미답변
                                                    </c:when>
                                                    <c:otherwise>
                                                        답변완료
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><c:out value="${QnAList.PQnARegDate}"/></td>
                                        </tr>
                                </c:when>
                                <c:when test="${QnAList.PQnAIndent == 1}">
                                        <tr class="pQnA_Reply">
                                            <td>
                                                <a href="<c:url value="/admin/productQnADetail/${QnAList.PQnANo}"/>">
                                                    <c:out value="${QnAList.PName}"/>
                                                </a>
                                            </td>
                                            <td><c:out value="${QnAList.userId}"/></td>
                                            <td>
                                                관리자 답변
                                            </td>
                                            <td><c:out value="${QnAList.PQnARegDate}"/></td>
                                        </tr>
                                </c:when>
                            </c:choose>
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

                        <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                            <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                                <a href="${num}">${num}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${pageMaker.next}">
                            <li class="paginate_button next">
                                <a href="${pageMaker.endPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <form id="pageActionForm" action="/admin/productQnAList" method="get">
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
            </form>
        </div>
    </div>
</div>
</body>
</html>
