<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                <h1>상품 문의</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-content-header-btn">
                    <button class="header-btn-modify" onclick="deleteProductQnA()">삭제</button>
                    <button class="header-btn-modify" onclick="productPageLink(this)" value="${detail.productId}">상품 보기</button>
                    <input type="hidden" value="${detail.qnaId}" id="qnaId"/>
                </div>
                <jsp:include page="/WEB-INF/views/myPage/qnaDetailForm.jsp"/>
                <div class="mypage-qna-content-reply">
                    <c:forEach items="${detail.replyList}" var="reply">
                        <div class="qna-detail-reply">
                            <div class="qna-reply-writer">
                                <strong>${reply.writer}</strong>
                                <small class="qna-reply-date">${reply.updatedAt}</small>
                            </div>
                            <div class="qna-reply-content">
                                <p>${reply.content}</p>
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
