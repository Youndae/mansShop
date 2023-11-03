<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/memberReview.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>

    <div class="content_area">
        <div class="reviewDetail_content content_data">
            <div class="reviewDetail_title">
                <span class="review_title">
                    <c:out value="${rDetail.PName}"/>
                </span>
            </div>
            <div class="reviewDetail_reviewContent">
                <c:out value="${rDetail.reviewContent}"/>
            </div>
            <div class="reviewDetail_btn">
                <button type="button" id="modifyReview" value="${rDetail.RNum}">리뷰수정</button>
                <button type="button" id="deleteReview" value="${rDetail.RNum}">리뷰삭제</button>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
