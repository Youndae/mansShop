<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 480px;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .pQnADetail_header{
        margin: 40px 0 50px 100px;
    }

    .pQnADetail_content{
        margin: 50px 0 50px 100px;
        width: 100%;
        height: 100%;
    }

    .pQnADetail_content .pQnADetail_productName{
        text-align: center;
        display: flex;
        font-size: xx-large;
        border: solid black;
        border-width: 0 0 4px 0;
        width: 770px;
        padding-left: 38%;
    }

    .pQnADetail_content .pQnADetail_productName .productName_writer{
        width: 100%;
    }

    .pQnADetail_content .pQnADetail_productName .QnAWriter{
        font-size: large;
        margin-top: 20px;
        float: right;
    }

    .pQnADetail_content .pQnADetail_productName span.productName{
        width: 100%;
        display: flex;
    }

    .pQnADetail_content_content{
        width: 1300px;
        height: 100%;
        border: 1px solid lightgray;
        margin-top: 52px;
    }

    .pQnADetail_btn_area{
        margin-top: 40px;
        margin-right: 100px;
    }

    .pQnADetail_btn_area button{
        float: right;
        width: 100px;
        height: 30px;
    }

    .pQnAReply_area{
        margin: 50px 95px 0 0;
        border: solid;
        border-width: 4px 0 0 0;
    }

    .pQnAReply_input{
        margin-top: 40px;
    }

    textarea{
        width: 100%;
        height: 85px;
        resize: none;
        font-size: large;
    }

    .pQnAReply_input button{
        margin-top: 10px;
        float: right;
        width: 100px;
        height: 30px;
    }

</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="pQnADetail_header">
                <h1>상품 문의</h1>
            </div>
            <div class="pQnADetail_content">
                <div class="pQnADetail_productName">
                    <span class="productName">
                        <c:out value="${detail.PName}"/>
                    </span>
                    <div class="productName_writer">
                        <span class="QnAWriter">
                            작성자 : <c:out value="${detail.userId}"/>
                        </span>
                    </div>
                </div>
                <div class="pQnADetail_content_content">
                    <c:out value="${detail.PQnAContent}"/>
                </div>

                <div class="pQnAReply_area">
                    <div class="pQnAReply_input">
                        <form id="pQnAReplyForm" method="post">
                            <textarea name="pQnAContent"></textarea>
                            <input type="hidden" name="pQnANo" value="${detail.PQnANo}">
                            <input type="hidden" name="pno" value="${detail.pno}">
                            <sec:csrfInput/>
                        </form>
                        <button type="button" id="pQnAReplyForm_btn">등록</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
