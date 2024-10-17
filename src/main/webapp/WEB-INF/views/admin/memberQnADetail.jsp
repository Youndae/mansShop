<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <div class="mypage-content">
            <div class="mypage-qna-header">
                <h1>회원 문의</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-content-header-btn">
                    <c:if test="${not detail.status}">
                        <button type="button" class="header-btn-modify" onclick="handleMemberQnACompleteBtn(this)" value="${detail.qnaId}">답변 완료 처리</button>
                    </c:if>
                </div>
                <div class="mypage-qna-content-title">
                    <c:set value="미답변" var="status"/>
                    <c:if test="${detail.status}">
                        <c:set value="답변 완료" var="status"/>
                    </c:if>

                    <h2>${detail.title}(${status})</h2>
                    <small class="qna-reply-date">${detail.createdAt}</small>
                </div>
                <div class="mypage-qna-content-content">
                    <p class="qna-detail-content">
                        ${detail.content}
                    </p>
                </div>
                <div class="qna-reply-input">
                    <textarea id="qna-reply" class="reply-input-textarea"></textarea>
                    <button class="default-btn" value="${detail.qnaId}" onclick="handleMemberQnAReplySubmit(this)">작성</button>
                </div>
                <div class="mypage-qna-content-reply">
                    <c:forEach items="${detail.replyList}" var="reply">
                        <div class="qna-detail-reply detail-reply-${reply.replyId}">
                            <div class="qna-reply-writer">
                                <strong>${reply.writer}</strong>
                                <small class="qna-reply-date">${reply.updatedAt}</small>
                            </div>
                            <div class="qna-reply-content">
                                <p>${reply.content}</p>
                                <c:if test="${reply.writer == '관리자'}">
                                    <button type="button" value="${reply.replyId}" class="default-btn" onclick="handleMemberQnAReplyModifyBtn(this)">댓글 수정</button>
                                </c:if>
                            </div>
                            <div class="qna-reply-modify-input"></div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
