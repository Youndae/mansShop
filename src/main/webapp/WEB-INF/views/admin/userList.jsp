<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSales.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<style>
    #orderModal {
        display: none;
        position: fixed;
        z-index: 10;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 35%;
    }

    .modal-body div{
        margin: 10px 0 10px 0;
    }

    .modal-body label{
        font-size: x-large;
        font-weight: bold;
    }

    .modal-body span{
        margin-left: 30px;
    }

    .modal-body table{
        border: 1px solid lightgray;
        margin-top: 15px;
    }

    .shipping_btn button{
        width: 100px;
        height: 30px;
        margin-left: 40%;
    }

    .modal-header{
        float: right;
    }

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

    .userList_header{
        margin: 40px 0 50px 200px;
    }

    .userList_content{
        margin: 50px 0 50px 200px;
        width: 100%;
        height: 100%;
    }

    .userList_content table{
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
    }

    .userList_content_bottom{
        display: flex;
    }

    .userList_content_bottom .paging{
        margin-left: 10px;
    }
</style>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="userList_header">
                <h1>회원목록</h1>
            </div>
            <div class="userList_content">
                <table id="tbl_userList" border="1">
                    <thead>
                    <tr>
                        <th>아이디</th>
                        <th>이름</th>
                        <th>가입일</th>
                    </tr>
                    </thead>
                    <c:forEach items="${uList}" var="userList">
                        <tr>
                            <td>
                                <a id="user_info_modal"><c:out value="${userList.userId}"/></a>
                            </td>
                            <td><c:out value="${userList.userName}"/></td>
                            <td><c:out value="${userList.joinRegDate}"/></td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="userList_content_bottom">
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
                    <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                    <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                    <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
