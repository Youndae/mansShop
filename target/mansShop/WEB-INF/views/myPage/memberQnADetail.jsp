<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/memberQnA.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>

    <div class="content_area">
        <div class="memQnADetail_content content_data">
            <div class="memQnADetail_QnATitle">
                <span class="QnATitle wd_full">
                    <c:out value="${qDetail.QTitle}"/>
                </span>
            </div>
            <div class="memQnADetail_QnAContent">
                <c:out value="${qDetail.QContent}"/>
            </div>
            <div class="memQnADetail_Reply">
                <ul>
                <c:forEach items="${qReply}" var="reply">
                    <li class="memQnADetail_reply_content">
                       <div class="memQnADetail_reply_header">
                           <strong class="replyer st_7"><c:out value="${reply.userId}"/></strong>
                           <small class="pull-right text-muted sm_default"><c:out value="${reply.qrRegDate}"/></small>
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
