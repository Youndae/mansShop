<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageReview.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-qna-header">
                <h1>리뷰 작성하기</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-write-title">
                    <c:set var="option" value=""/>
                    <c:if test="${detail.productSize ne null}">
                        <c:set var="option" value="사이즈 : ${detail.productSize}"/>
                    </c:if>
                    <c:if test="${detail.productColor ne null}">
                        <c:if test="${not empty option}">
                            <c:set var="option" value="${option}, "/>
                        </c:if>
                        <c:set var="option" value="${option}컬러 : ${detail.productColor}"/>
                    </c:if>
                    <h3>${detail.productName}(${option})</h3>
                </div>
                <div class="mypage-qna-content-textarea">
                    <textarea id="content" class="qna-content"></textarea>
                </div>
                <div class="mypage-qna-content-btn">
                    <button type="button" class="default-btn" onclick="handleReviewSubmit(this)" value="${detail.orderDetailId}">작성</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
