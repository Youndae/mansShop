<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                <h1>문의 사항</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-content-header-btn">
                    <button class="header-btn-modify" onclick="patchMemberQnA()">수정</button>
                    <button class="header-btn-modify" onclick="deleteMemberQnA()">삭제</button>
                    <input type="hidden" value="${detail.qnaId}" id="qnaId">
                </div>
                <jsp:include page="/WEB-INF/views/myPage/qnaDetailForm.jsp"/>
                <div class="qna-reply-input">
                    <textarea id="reply-input" cols="30" rows="10" class="reply-input-textarea"></textarea>
                    <button class="default-btn" onclick="insertMemberQnAReply()">작성</button>
                </div>
                <div class="mypage-qna-content-reply">
                    <c:forEach items="${detail.replyList}" var="reply">
                        <sec:authentication property="principal.username" var="username"/>
                        <div class="qna-detail-reply">
                            <div class="qna-reply-writer">
                                <strong>${reply.writer}</strong>
                                <small class="qna-reply-date">${reply.updatedAt}</small>
                            </div>
                            <div class="qna-reply-content member-reply-${reply.replyId}">
                                <p>${reply.content}</p>
                                <c:if test="${username eq reply.writer}">
                                    <button class="default-btn" value="${reply.replyId}" onclick="patchMemberQnAReplyBtnOnClick(this)">댓글 수정</button>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
