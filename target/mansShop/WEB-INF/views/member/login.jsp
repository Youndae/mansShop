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
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content login_content">
        <div class="login_header">
            <h1>로그인</h1>
        </div>
        <div class="login_form">
            <form action="/login" id="loginForm" method="post">
                    <div class="form-group">
                        <input class="form-control" placeholder="아이디" name="username" type="text" autofocus>
                    </div>
                    <div class="form-group">
                        <input class="form-control" placeholder="비밀번호" name="password" type="password">
                    </div>
                <div class="login-form-btn-area">
                    <div class="loginBtn-area">
                        <button class="loginBtn">Login</button>
                    </div>
                    <div class="else-btn-area">
                        <button class="joinBtn">회원가입</button>
                        <button class="searchId">아이디 찾기</button>
                        <button class="searchPw">비밀번호 찾기</button>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
