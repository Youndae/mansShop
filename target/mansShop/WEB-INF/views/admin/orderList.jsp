<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<style>
    /* The Modal (background) */
    #orderModal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 10; /* Sit on top */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }
    /* Modal Content/Box */
    .modal-content {
        background-color: #fefefe;
        margin: 15% auto; /* 15% from the top and centered */
        padding: 20px;
        border: 1px solid #888;
        width: 70%; /* Could be more or less, depending on screen size */
    }
</style>
<body>
<div>
    <h1>주문목록</h1>
</div>
<div>
    <table>
        <thead>
            <tr>
                <th>주문번호</th>
                <th>아이디</th>
                <th>처리</th>
                <th>주문일</th>
            </tr>
        </thead>
        <c:forEach items="${order}" var="list">
            <tr>
                <td>
                    <a id="modalShow"><c:out value="${list.orderNo}"/></a>
                        <%--<c:out value="${list.orderNo}"/>--%>
                </td>
                <td><c:out value="${list.userId}"/></td>
                <c:choose>
                    <c:when test="${list.orderStat == 0}">
                        <td>새 주문</td>
                    </c:when>
                    <c:when test="${list.orderStat == 1}">
                        <td>확인완료</td>
                    </c:when>
                </c:choose>
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
                    <span class="form-control" name="orderNo"></span>
                </div>
                <div class="form-group">
                    <label>아 이 디</label>
                    <span class="form-control" name="userId"></span>
                </div>
                <div class="form-group">
                    <label>수 령 인</label>
                    <span class="form-control" name="recipient"></span>
                </div>
                <div class="form-group">
                    <label>연 락 처</label>
                    <span class="form-control" name="orderPhone"></span>
                </div>
                <div class="form-group">
                    <label>주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</label>
                    <span class="form-control" name="addr"></span>
                </div>
                <div class="form-group">
                    <label>배송메모</label>
                    <span class="form-control" name="orderMemo"></span>
                </div>
                <div class="form-group">
                    <label>주문목록</label>
                    <table class="order_detail_list" border="1">

                    </table>
                </div>
            </div>
            <div class="shipping_btn">
                <button type="button" id="shipping">배송처리</button>
            </div>
        </div>
    </div>
</div>
</body>

<!--<table>
    <thead>
        <tr>
            <th>옵션번호</th>
            <th>분류</th>
            <th>상품명</th>
            <th>사이즈</th>
            <th>컬러</th>
            <th>수량</th>
        </tr>
    </thead>
</table>
모달 구성
-->
</html>
