<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 30%;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 160px;
    }

    .review_header{
        margin: 40px 0 50px 200px;
    }

    .review_content{
        margin: 50px 0 50px 200px;
        width: 87%;
        height: 100%;
        font-size: large;
        font-weight: bold;
    }

    .review_content .reviewForm_area{
        margin: 25px 0 25px 20px;
        width: 800px;
    }

    .review_content .review_content_productName{
        margin-bottom: 20px;
        font-size: x-large;
        text-align: center;
        width: 870px;
        border: solid black;
        border-width: 0 0 4px 0;
    }

    .review_content_content textarea{
        width: 870px;
        height: 600px;
    }

    .review_content button{
        float: right;
        width: 100px;
        height: 30px;

    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="content_area">
            <div class="review_header">
                <h1>상품 리뷰 작성</h1>
            </div>
            <div class="review_content">
                <div class="reviewForm_area">
                    <form id="reviewInsertForm" method="post">
                        <div class="review_content_productName">
                            <div>
                                <label><c:out value="${pInfo.PName}"/></label>
                            </div>
                        </div>
                        <div class="review_content_content">
                            <div>
                                <textarea name="reviewContent"></textarea>
                            </div>
                        </div>
                        <input type="hidden" name="pno" value="${pno}">
                        <input type="hidden" name="orderNo" value="${orderNo}">
                        <sec:csrfInput/>
                    </form>
                </div>
                <button type="button" id="reviewInsert">작성</button>
            </div>
            <div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
