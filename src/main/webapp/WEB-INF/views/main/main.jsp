<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .thumbnail img{
        width: 300px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div>
    <div class="header">
        <h1>BEST</h1>
    </div>
    <div>
        <c:forEach items="${pList}" var="list">
            <div class="product_img">
                <a href="${list.pno}" class="thumbnail">
                    <img id="ImageData" src="/display?image=${list.firstThumbnail}"/>
                    <span class="pName">${list.PName}</span>
                    <span class="pPrice"><fmt:formatNumber value="${list.PPrice}" pattern="#,###"/> 원</span>
                </a>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    $(document).ready(function(){
        $(".thumbnail").on('click', function(e){
            var pno = $(this).href;

            location.href="/"+pno;
        })
    })
</script>
</body>
</html>
