<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/orderList.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/managerSidenav.jsp"/>
    <div class="content_area">
        <div class="orderList_header content_header">
            <h1>주문목록</h1>
        </div>
        <div class="orderList_content content_data">
            <table class="tbl_default" id="tbl_orderList" border="1">
                <thead>
                <tr class="tbl_tr_m_10">
                    <th>주문번호</th>
                    <th>아이디</th>
                    <th>처리</th>
                    <th>주문일</th>
                </tr>
                </thead>
                <c:forEach items="${order}" var="list">
                    <tr class="tbl_tr_m_10">
                        <td>
                            <a id="modalShow"><c:out value="${list.orderNo}"/></a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${list.userId eq 'Anonymous'}">
                                    비회원
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${list.userId}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <c:choose>
                            <c:when test="${list.orderStat == 0}">
                                <td>새 주문</td>
                            </c:when>
                            <c:when test="${list.orderStat == 1}">
                                <td>확인완료</td>
                            </c:when>
                            <c:when test="${list.orderStat == 2}">
                                <td>배송완료</td>
                            </c:when>
                        </c:choose>
                        <td><c:out value="${list.orderDate}"/></td>
                    </tr>
                </c:forEach>
            </table>

            <div class="orderList_content_bottom list-bottom-search">
                <div class="search">
                    <div>
                        <form id="searchActionForm" action="/admin/orderList" method="get">
                            <input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                            <button class="btn">Search</button>
                        </form>
                    </div>
                </div>

                <div class="paging list-bottom-paging">
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

            <form id="pageActionForm" action="/admin/orderList" method="get">
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
                <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
            </form>
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
                            <thead>
                            <tr class="tbl_tr_m_10">
                                <th>옵션번호</th>
                                <th>분류</th>
                                <th>상품명</th>
                                <th>사이즈</th>
                                <th>컬러</th>
                                <th>수량</th>
                            </tr>
                            </thead>
                            <tbody class="order_detail_info_list">

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="shipping_btn">
                    <button type="button" id="shipping">배송처리</button>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
