<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSales.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/salesProductList.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
    <div class="content_area">
        <div class="salesTermList_header content_header">
            <h1>기간별 매출</h1>
        </div>

        <div class="salesTermList_content content_data">
            <div class="selectTerm">
                <form id="pageActionForm" action="/admin/sales/term" method="get">
                    <label>연도별 : </label>
                    <select id="select_Term_Year">
                        <option value="default">연도 선택</option>
                    </select>
                    <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                    <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
                    <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
                </form>
            </div>
            <table class="tbl_default" border="1">
                <thead>
                <tr class="tbl_tr_m_10">
                    <th>기간</th>
                    <th>주문건수</th>
                    <th>매출액</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${stList}" var="list">
                    <tr class="tbl_tr_m_10">
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
</div>
</div>
</body>
</html>
