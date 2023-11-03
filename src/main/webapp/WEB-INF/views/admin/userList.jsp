<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminUserList.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/orderList.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="userList_header content_header">
                <h1>회원목록</h1>
            </div>
            <div class="userList_content content_data">
                <table class="tbl_default" id="tbl_userList" border="1">
                    <thead>
                    <tr class="tbl_tr_m_10">
                        <th>아이디</th>
                        <th>이름</th>
                        <th>가입일</th>
                    </tr>
                    </thead>
                    <c:forEach items="${uList}" var="userList">
                        <tr class="tbl_tr_m_10">
                            <td>
                                <a id="user_info_modal"><c:out value="${userList.userId}"/></a>
                            </td>
                            <td><c:out value="${userList.userName}"/></td>
                            <td><c:out value="${userList.joinRegDate}"/></td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="userList_content_bottom list-bottom-search">
                    <div class="search">
                        <div>
                            <form id="searchActionForm" action="/admin/userList" method="get">
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
                <form id="pageActionForm" action="/admin/userList" method="get">
                    <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                    <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
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
                        <label>아 이 디</label>
                        <span class="form-control" name="userId"></span>
                    </div>
                    <div class="form-group">
                        <label>이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름</label>
                        <span class="form-control" name="userName"></span>
                    </div>
                    <div class="form-group">
                        <label>연 락 처</label>
                        <span class="form-control" name="userPhone"></span>
                    </div>
                    <div class="form-group">
                        <label>생년월일</label>
                        <span class="form-control" name="userBirth"></span>
                    </div>
                    <div class="form-group">
                        <label>이 메 일</label>
                        <span class="form-control" name="userEmail"></span>
                    </div>
                    <div class="form-group">
                        <label>가 입 일</label>
                        <span class="form-control" name="joinRegDate"></span>
                    </div>
                </div>
                <div class="shipping_btn">
                    <button type="button" id="shipping">주문내역</button>
                </div>

                <form id="userOrderSearch" action="/admin/orderList" method="get">
                    <input type="hidden" name="keyword" value=""/>
                    <input type="hidden" name="pageNum" value=""/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
