<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<body>
<div>
    <h1>주문목록</h1>
</div>
<div>
    <table>
        <thead>
            <tr>
                <th></th>
                <th>주문번호</th>
                <th>주문자</th>
                <th>처리</th>
                <th>주문일</th>
            </tr>
        </thead>
        <c:forEach items="${order}" var="list">
            <tr>
                <td></td><!-- 체크박스 -->
                <td>
                    <a id="modalShow"><c:out value="${list.orderNo}"/></a>
                        <%--<c:out value="${list.orderNo}"/>--%>
                </td>
                <td><c:out value="${list.userId}"/></td>
                <td><c:out value="${list.orderStat}"/></td>
                <td><c:out value="${list.orderDate}"/></td>
            </tr>
        </c:forEach>
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

    <form id="orderActionForm" action="/admin/orderList" method="get">
        <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
        <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
    </form>

    <div class="search">
        <div>
            <form id="orderSearchForm" action="/admin/orderList" method="get">
                <input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                <button class="btn">Search</button>
            </form>
        </div>
    </div>
</div>

<div class="modal" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="orderModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="modalClose" data-dismiss="modal" aria-hidden="true">x</button>
                <h4 class="modal-title" id="orderModalLabel"></h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>주문번호</label>
                    <input class="form-control" name="orderNo" value="">
                </div>
                <div class="form-group">
                    <label>받는사람</label>
                    <input clas="form-control" name="userId" value="">
                </div>
                <div class="form-group">
                    <label>연락처</label>
                    <input clas="form-control" name="orderPhone" value="">
                </div>
                <div class="form-group">
                    <label>주소</label>
                    <input clas="form-control" name="addr" value="">
                </div>
                <div class="form-group">
                    <label>배송메모</label>
                    <input clas="form-control" name="orderMemo" value="">
                </div>
                <div class="form-group">
                    <label>주문목록</label>
                    <table class="order_detail_list">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<table>
    <thead>
        <tr>
            <th>옵션번호</th>
            <th></th>
        </tr>
    </thead>
</table>
</html>
