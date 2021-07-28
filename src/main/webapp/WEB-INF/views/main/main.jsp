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
    .container div{
        text-align: center;
        display: table;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/main.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <div class="product_header">
            <h1>BEST</h1>
        </div>
        <div class="product_content">
            <c:forEach items="${pList}" var="list">
                <div class="product_img">

                        <div class="thumb_img">
                            <a href="${list.pno}" class="thumbnail"><img id="ImageData" src="/display?image=${list.firstThumbnail}"/></a>
                        </div>
                        <div class="productInfo">
                            <span class="pName">${list.PName}</span><br>
                            <span class="pPrice"><fmt:formatNumber value="${list.PPrice}" pattern="#,###"/> 원</span>
                        </div>

                </div>
            </c:forEach>
        </div>
    </div>
</div><%--container div end--%>

<%--<script>
    $(document).ready(function(){
        $(".thumbnail").on('click', function(e){
            var pno = $(this).href;

            location.href="/"+pno;
        })
    })
</script>--%>
</body>
</html>
