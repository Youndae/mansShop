<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/member.js"></script>
<link rel="stylesheet" href="/css/member.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content login-content">
        <div class="login-header">
            <h1>로그인</h1>
        </div>
        <div class="login-form">
            <form class="login-form" action="/login" id="loginForm" method="post">
                    <div class="form-group">
                        <input class="form-control" placeholder="아이디" name="username" type="text" autofocus>
                    </div>
                    <div class="form-group">
                        <input class="form-control" placeholder="비밀번호" name="password" type="password">
                    </div>
                <div class="login-form-btn-area">
                    <div class="login-btn">
                        <button class="login-btn default-btn">Login</button>
                    </div>
                    <div class="join-search-area">
                        <button class="join-btn default-btn">회원가입</button>
                        <button class="search-id-btn default-btn">아이디 찾기</button>
                        <button class="search-pw-btn default-btn">비밀번호 찾기</button>
                    </div>
                </div>
                <sec:csrfInput/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
