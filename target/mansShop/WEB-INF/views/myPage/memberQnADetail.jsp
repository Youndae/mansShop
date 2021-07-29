<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 486px;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .memQnADetail_header{
        margin: 40px 0 50px 100px;
    }

    .memQnADetail_content{
        margin: 50px 0 50px 100px;
        width: 100%;
        height: 100%;
    }

    .memQnADetail_content .memQnADetail_QnATitle{
        text-align: center;
        display: flex;
        font-size: xx-large;
        border: solid black;
        border-width: 0 0 4px 0;
        width: 770px;
        padding-left: 38%;
    }

    .memQnADetail_QnAContent{
        width: 1300px;
        height: 100%;
        border: 1px solid lightgray;
        margin-top: 52px;
    }

    .memQnADetail_Reply{
        border: solid;
        margin-top: 40px;
        margin-right: 100px;
        border-width: 4px 0 0 0;
    }

    .memQnADetail_Reply li{
        margin-top: 30px;
        border: solid lightgray;
        border-width: 0 0 1px 0;
        width: 100%;
        height: 80px;
        display: inline-grid;
    }

    .memQnADetail_reply_header{
        display: inline-flex;
    }

    .memQnADetail_reply_header strong{
        font-weight: bold;
        font-size: x-large;
        width: 7%;
    }

    .memQnADetail_reply_header small{
        font-size: small;
        padding: 12px 0 0 0;
    }

    .memQnADetail_reply_content_content{
        display: flex;
        margin-left: 20%;
    }

</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>

    <div class="content_area">
        <div class="memQnADetail_header">
            <h1>문의사항</h1>
        </div>
        <div class="memQnADetail_content">
            <div class="memQnADetail_QnATitle">
                <c:out value="${qDetail.QTitle}"/>
            </div>
            <div class="memQnADetail_QnAContent">
                <c:out value="${qDetail.QContent}"/>
            </div>
            <div class="memQnADetail_Reply">
                <ul>
                <c:forEach items="${qReply}" var="reply">
                    <li class="memQnADetail_reply_content">
                       <div class="memQnADetail_reply_header">
                           <strong class="replyer"><c:out value="${reply.userId}"/></strong>
                           <small class="pull-right text-muted"><c:out value="${reply.qrRegDate}"/></small>
                       </div>
                        <div class="memQnADetail_reply_content_content">
                            <p><c:out value="${reply.qrContent}"/></p>
                        </div>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>

    </div>

</div>
</div>
</body>
</html>
