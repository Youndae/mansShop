<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

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
    <div class="header">
        <h3>회원목록</h3>
    </div>
    <div class="userListTable">
        <table id="tbl_userList">
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

        <form id="userActionForm" action="/admin/userList" method="get">
            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        </form>

        <div class="search">
            <div>
                <form id="userSearchForm" action="/admin/userList" method="get">
                    <input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                    <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                    <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                    <button class="btn">Search</button>
                </form>
            </div>
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

            <form id="userOrderSearch" action="/admin/orderList" method="get"><!--넘겨주는 mapping 잡아서 modelandattribute로 orderList페이지로 이동 -->
                <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
            </form>
        </div>
    </div>
</div>


<script>
    $(document).ready(function(){
        var actionForm = $("#userActionForm");
        var userSearchForm = $("#userSearchForm");

        $("#shipping").on('click', function(){
            var orderSearchForm = $("#userOrderSearch");
            var userId = $(".modal-body span[name=userId]").text();


            console.log("userId : " + userId);
            orderSearchForm.find("input[name='keyword']").val(userId);
            orderSearchForm.find("input[name='pageNum']").val("1");



            orderSearchForm.submit();
        });

        $(".paginate_button a").on('click', function(e){
            e.preventDefault();

            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        })


        $("#userSearchForm button").on('click', function(e){
            if(!userSearchForm.find("input[name='keyword']").val()){
                alert('키워드 입력');
            }

            userSearchForm.find("input[name='pageNum']").val("1");
            e.preventDefault();

            userSearchForm.submit();
        });

        $("#tbl_userList a").on('click', function(e){
            e.preventDefault();

            var userId = $(this).text();
            var formatPhone = "";
            getUserInfo(userId, function(info){
                $(".modal-body span[name=userId]").text(info.userId);
                $(".modal-body span[name=userName]").text(info.userName);
                $(".modal-body span[name=userBirth]").text(info.userBirth);
                $(".modal-body span[name=userEmail]").text(info.userEmail);

                var date = new Date(info.joinRegDate);


                info.joinRegDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();

                $(".modal-body span[name=joinRegDate]").text(info.joinRegDate);




                if(info.userPhone.length == 11){
                    formatPhone = info.userPhone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
                }else if(info.userPhone.length == 10){
                    formatPhone = info.userPhone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
                }

                $(".modal-body span[name=userPhone]").text(formatPhone);
            });

            $(".modal").show();

        });

        $(".modalClose").on('click', function(){
            $(".modal").hide();
        });
    });

    function getUserInfo(userId, callback, error){
        console.log("user Info get");

        $.get("/admin/userInfo/" + userId + ".json", function(result){
            if(callback)
                callback(result);
        }).fail(function(xhr, status, er){
            if(error)
                error(er);
        });
    };
</script>
</body>
</html>
