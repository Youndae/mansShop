<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    .salesTermList_header{
        margin: 40px 0 50px 200px;
    }

    .salesTermList_content{
        margin: 50px 0 50px 200px;
        width: 100%;
        height: 100%;
    }

    .salesTermList_content table{
        font-size: large;
        margin: 25px 0 25px 0;
        width: 1000px;
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

    .salesTermList_content .selectTerm{
        width: 1000px;
    }
    .salesTermList_content .selectTerm form{
        float: right;
    }

    .salesSum_Year{
        font-size: x-large;
        font-weight: bold;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSales.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
    <div class="content_area">
        <div class="salesTermList_header">
            <h1>기간별 매출</h1>
        </div>

        <div class="salesTermList_content">
            <div class="selectTerm">
                <form id="pageActionForm" action="/admin/salesTermList" method="get">
                    <label>연도별 : </label>
                    <select id="select_Term_Year">
                        <option value="default">연도 선택</option>
                    </select>
                    <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                    <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
                    <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
                </form>
            </div>
            <table border="1">
                <thead>
                <tr>
                    <th>기간</th>
                    <th>주문건수</th>
                    <th>매출액</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${stList}" var="list">
                    <tr>
                        <td><c:out value="${list.salesTerm}"/></td>
                        <td><fmt:formatNumber value="${list.salesOrders}" pattern="#,###"/> 건</td>
                        <td><fmt:formatNumber value="${list.salesSum}" pattern="#,###"/> 원</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="salesSum_Year">
                <c:if test="${pageMaker.cri.keyword != null && pageMaker.cri.keyword != ''}">
                    <c:set var="total" value="0"/>
                    <c:forEach items="${stList}" var="sum">
                        <c:set var="total" value="${total + sum.salesSum}"/>
                    </c:forEach>

                    <span><c:out value="${pageMaker.cri.keyword}"/>년 연매출 : <fmt:formatNumber value="${total}" pattern="#,###"/> 원</span>
                </c:if>
            </div>

            <div class="paging">
                <ul class="pagination">
                    <c:if test="${pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${pageMaker.startPage - 1}">Prev</a>
                        </li>
                    </c:if>

                    <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                        <c:if test="${pageMaker.endPage >= 2}">
                            <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                                <a href="${num}">${num}</a>
                            </li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${pageMaker.next}">
                        <li class="paginate_button next">
                            <a href="${pageMaker.endPage + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
