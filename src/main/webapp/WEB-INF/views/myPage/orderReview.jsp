<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="header">
        <h1>상품 리뷰 작성</h1>
    </div>
    <div>
        <form id="reviewInsertForm" method="post">
            <div class="review_product_Info">
                <%--
                    썸네일 사이즈 줄이고 그 옆으로 상품 정보 출력하도록 디자인.
                --%>
                <img src="/display?image=${pInfo.firstThumbnail}">
                <span>
                    <p>${pInfo.PName}</p>
                    <p>사이즈 : ${pInfo.PSize}</p>
                    <p>컬러 : ${pInfo.PColor}</p>
                </span>
            </div>
            <div>
                <label>리뷰작성</label><br>
                <textarea name="reviewContent"></textarea>
            </div>
            <input type="hidden" name="pno" value="${pno}">
            <input type="hidden" name="orderNo" value="${orderNo}">
            <sec:csrfInput/>
        </form>
    </div>
    <div>
        <button type="button" id="reviewInsert">작성</button>
    </div>
</div>
</div>
<%--<script>
    $(function(){
        $("#reviewInsert").on('click',function(){
            $("#reviewInsertForm").attr("action", "/myPage/orderReview");
            $("#reviewInsertForm").submit();
        })
    })
</script>--%>
</body>
</html>
