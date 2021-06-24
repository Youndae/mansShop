<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div>
    <div class="header">
        <h3>회원목록</h3>
    </div>
    <div class="userListTable">
        <table>
            <thead>
                <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>연락처</th>
                    <th>이메일</th>
                    <th>가입일</th>
                </tr>
            </thead>
            <c:forEach items="${uList}" var="userList">
                <tr>
                    <td><c:out value="${userList.userId}"/></td>
                    <td><c:out value="${userList.userName}"/></td>
                    <td><c:out value="${userList.userPhone}"/></td>
                    <td><c:out value="${userList.userEmail}"/></td>
                    <td><c:out value="${userList.userEmail}"/></td>
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
<script>
    $(document).ready(function(){
        var actionForm = $("#userActionForm");
        var userSearchForm = $("#userSearchForm");

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
        })
    })
</script>
</body>
</html>
