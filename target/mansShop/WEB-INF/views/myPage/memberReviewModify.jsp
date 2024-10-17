<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-qna-header">
                <h1>리뷰 수정</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-write-title">
                    <span>상품명 : ${data.productName}</span>
                </div>
            </div>
            <div class="mypage-qna-content-textarea">
                <textarea id="content" class="qna-content">${data.content}</textarea>
            </div>
            <div class="mypage-qna-content-btn">
                <button class="default-btn" onclick="handlePatchReviewBtnOnClick(this)" value="${data.id}">수정</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
