<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminQnA.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="admin-content">
            <div class="admin-content-header">
                <h1>문의 카테고리 설정</h1>
                <button type="button" class="admin-qna-classification-add-btn" onclick="qnaClassificationAddBtnOnclick()">카테고리 추가</button>
            </div>
            <div class="admin-content-content admin-qna-classification-content"></div>
            <c:forEach items="${list}" var="category">
                <div class="admin-qna-classification">
                    <div class="admin-qna-classification-info">
                        <label>카테고리명 : </label>
                        <span class="admin-qna-classification-name">${category.name}</span>
                    </div>
                    <div class="admin-qna-classification-delete-btn">
                        <button type="button" value="${category.id}" onclick="deleteClassificationBtn(this)">카테고리 삭제</button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
