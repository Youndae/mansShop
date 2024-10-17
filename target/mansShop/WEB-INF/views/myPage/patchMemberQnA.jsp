<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <h1>문의하기</h1>
            </div>
            <div class="mypage-qna-content">
                <div class="mypage-qna-write-title">
                    <label>제목</label>
                    <input type="text" id="title" value="${data.title}"/>
                    <select id="member-qna-select-box">
                        <c:forEach items="${classification}" var="name">
                            <c:choose>
                                <c:when test="${name.id == data.classificationId}">
                                    <option value="${name.id}" selected>${name.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${name.id}">${name.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <div class="mypage-qna-content-textarea">
                    <textarea id="content" class="qna-content">${data.content}</textarea>
                </div>
                <div class="mypage-qna-content-btn">
                    <button type="button" onclick="handlePatchMemberQnABtn()">작성</button>
                    <input type="hidden" id="qid" value="${data.id}"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
