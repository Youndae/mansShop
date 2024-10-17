<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminReview.js"></script>
<script type="text/javascript" src="/js/pagination.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-qna-header">
                <h1>리뷰</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-content-title">
                    <c:set var="option" value=""/>
                    <c:if test="${detail.review.productSize ne null}">
                        <c:set var="option" value="사이즈 : ${detail.review.productSize}"/>
                    </c:if>
                    <c:if test="${detail.review.productColor ne null}">
                        <c:if test="${not empty option}">
                            <c:set var="option" value="${option}, "/>
                        </c:if>
                        <c:set var="option" value="${option}컬러 : ${detail.review.productColor}"/>
                    </c:if>

                    <h3>${detail.review.productName}(${option})</h3>
                    <small class="qna-reply-date">작성자 : ${detail.review.userId}</small>
                    <c:choose>
                        <c:when test="${detail.review.createdAt != detail.review.updatedAt}">
                            <small class="qna-reply-date">작성일 : ${detail.review.createdAt}, 수정일 : ${detail.review.updatedAt}</small>
                        </c:when>
                        <c:otherwise>
                            <small class="qna-reply-date">작성일 : ${detail.review.createdAt}</small>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="mypage-qna-content-content">
                    <p class="qna-detail-content">
                        ${detail.review.content}
                    </p>
                </div>
                <div class="qna-reply-input">
                    <textarea id="qna-reply" class="reply-input-textarea"></textarea>
                    <button type="button" class="default-btn" value="${detail.review.reviewId}" onclick="handleReviewReplySubmit(this)">작성</button>
                </div>
                <div class="mypage-qna-content-reply">
                    <c:if test="${not empty detail.reply}">
                        <div class="qna-detail-reply">
                            <div class="qna-reply-writer">
                                <strong>관리자</strong>
                                <small class="qna-reply-date">${detail.reply.updatedAt}</small>
                            </div>
                            <div class="qna-reply-content">
                                <p>${detail.reply.content}</p>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
