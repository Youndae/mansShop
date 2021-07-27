<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="header">
        <h1>문의사항</h1>
    </div>
    <div class="myQnA_Content">
        <div class="title">
            <c:out value="${qDetail.QTitle}"/>
            <div>
                <c:out value="${qDetail.QRegDate}"/>
            </div>
        </div>
        <div class="content">
            <c:out value="${qDetail.QContent}"/>
        </div>
    </div>
    <div class="myQnA_Reply">
        <c:forEach items="${qReply}" var="reply">
            <div>
                <label>
                    <c:out value="${reply.userId}"/>
                </label>
                <span>
                    <c:out value="${reply.qrContent}"/>
                    <p><c:out value="${reply.qrRegDate}"/></p>
                </span>

            </div>
        </c:forEach>
    </div>

</div>
</div>
</body>
</html>
