<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<body>
<div>
    <div>
        <h3>고객문의</h3>
    </div>
    <div class="QnA">
        <div class="QnATop">
            <span class="QnATitle">
            <c:out value="${aqDetail.QTitle}"/>
        </span>
            <label>작성자</label>
            <span class="QnAWriter">
            <c:out value="${aqDetail.userId}"/>
        </span>
        </div>
        <div class="QnAContent">
            <p><c:out value="${aqDetail.QContent}"/></p>
        </div>
        <div class="QnAReply_btn">
            <span>댓글</span>
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
        <div class="QnAReply">

        </div>

    </div>
</div>
</body>
</html>
