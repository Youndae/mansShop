<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        margin: 0 0 0 480px;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .QnADetail_header{
        margin: 40px 0 50px 100px;
    }

    .QnADetail_content{
        margin: 50px 0 50px 100px;
        width: 100%;
        height: 100%;
    }

    .QnADetail_content .QnADetail_QnATitle{
        text-align: center;
        display: flex;
        font-size: xx-large;
        border: solid black;
        border-width: 0 0 4px 0;
        width: 770px;
        padding-left: 38%;
    }

    .QnADetail_content .QnADetail_QnATitle .QnATitle_RegDate{
        width: 100%;
    }

    .QnADetail_content .QnADetail_QnATitle .QnAWriter{
        font-size: large;
        margin-top: 20px;
        float: right;
    }

    .QnADetail_content .QnADetail_QnATitle span.QnATitle{
        width: 100%;
        display: flex;
    }

    .QnADetail_content_content{
        width: 1300px;
        height: 100%;
        border: 1px solid lightgray;
        margin-top: 52px;
    }

    .QnAReply_area{
        border: solid;
        margin-top: 40px;
        margin-right: 100px;
        border-width: 4px 0 0 0;
    }

    .QnAReply_btn{
        float: right;
        margin-right: 100px;
    }

    .reply_btn_area{
        margin-top: 15px;
    }

    .QnAReply_input{
        display: flex;
        margin-left: 10px;
    }

    .QnAReply_input input{
        width: 500px;
        height: 20px;
    }

    .QnAReply_input button{
        margin-left: 5px;
    }

    .QnAReply li{
        margin-top: 30px;
        border: solid lightgray;
        border-width: 0 0 1px 0;
        width: 100%;
        height: 80px;
        display: inline-grid;
    }

    .QnADetail_reply_header {
        display: inline-flex;
    }

    .QnADetail_reply_header strong{
        font-weight: bold;
        font-size: x-large;
        width: 7%;
    }

    .QnADetail_reply_header small{
        font-size: small;
        padding: 12px 0 0 0;
    }

    .QnADetail_reply_content_content{
        display: flex;
        margin-left: 20%;
    }

</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<body>
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="QnADetail_header">
                <h1>고객문의</h1>
            </div>
            <div class="QnADetail_content">
                <div class="QnADetail_QnATitle">
                    <span class="QnATitle">
                        <c:out value="${aqDetail.QTitle}"/>
                    </span>
                    <div class="QnATitle_RegDate">
                        <span class="QnAWriter">
                        작성자 : <c:out value="${aqDetail.userId}"/>
                    </span>
                    </div>
                </div>
                <div class="QnADetail_content_content">
                    <c:out value="${aqDetail.QContent}"/>
                </div>

                <div class="QnAReply_area">
                    <div class="reply_btn_area">
                        <div class="QnAReply_btn">
                            <c:if test="${aqDetail.QStat == 0}">
                                <button type="button" id="QnAComplete">처리완료</button>
                            </c:if>
                        </div>
                        <div class="QnAReply_input">
                            <form id="replyForm">
                                <input type="text" name="qrContent">
                                <input type="hidden" id="qno" name="qno" value="${aqDetail.qno}">
                            </form>
                            <button type="button" id="replyForm_submit">등록</button>
                        </div>
                    </div>
                    <div class="QnAReply">

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
