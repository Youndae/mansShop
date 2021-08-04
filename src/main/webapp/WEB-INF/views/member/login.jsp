<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container div{
        text-align: center;
    }

    .login_header{
        font-size: x-large;
        margin: 10% 0 2% 0;
    }

    .login_form input{
        height: 40px;
        width: 300px;
    }

    .login_form button{
        margin-top: 40px;
        height: 30px;
        width: 200px;
    }
</style>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/member/js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
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
                    <%--<a href="/myPage/ModifyInfo" class="loginBtn">Login</a>--%>
                    <button class="loginBtn">Login</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
