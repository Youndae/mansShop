<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div class="productList">
    <h1>ProductList</h1>
</div>

<div class="content">
    <table>
        <thead>
            <tr>
                <th>상품번호</th>
                <th>상품분류</th>
                <th>상품명</th>
                <th>가격</th>
                <th>등록일</th>
            </tr>
        </thead>
        <c:forEach items="${pList}" var="list">
            <tr>
                <td><c:out value="${list.pno}"/></td>
                <td><c:out value="${list.PClassification}"/></td>
                <td><c:out value="${list.PName}"/></td>
                <td><c:out value="${list.PPrice}"/></td>
                <td><c:out value="${list.PRegDate}"/></td>
            </tr>
        </c:forEach>
    </table>

    <div class="testImg">
        <ul>

        </ul>
    </div>
</div>

<script>
    $(document).ready(function(){
        (function(){


            $.getJSON("/admin/getAttachList", function(arr){
                console.log(arr);

                var str = "";

                $(arr).each(function(i, attach){
                    //image type

                        /*var fileCallPath = encodeURIComponent(attach.firstThumbnail);*/

                        str += "<li data-filename='" + attach.firstThumbnail + "'><div>";
                        str += "<img src='/admin/display?firstThumbnail=" + attach.firstThumbnail + "'>";
                        str += "</div>";
                        str += "</li>";
                });

                $(".testImg ul").html(str);
            });
        })();
    });
</script>
</body>
</html>
