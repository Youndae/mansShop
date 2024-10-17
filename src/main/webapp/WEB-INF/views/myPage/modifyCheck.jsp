<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-content-header">
                <h1>정보 수정</h1>
                <h3>보안을 위해 비밀번호를 다시 입력해주세요</h3>
            </div>
            <div class="mypage-like-content member-check-content">
                <div class="member-info-check-form">
                    <div class="info-check-username">
                        <input type="text" id="username" value="${uid}" readonly>
                    </div>
                    <div class="info-check-password">
                        <input type="password" id="userPw" placeholder="비밀번호">
                        <div class="overlap"></div>
                    </div>
                </div>
                <div class="member-info-check-btn">
                    <button type="button" class="default-btn member-check-btn" onclick="memberCheckBtnOnClick()">확인</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
