<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Login</h1>
<form action="/login" role="form" method="post">
    <fieldset>
        <div class="form-group">
            <input class="form-control" placeholder="userId" name="username" type="text" autofocus>
        </div>
        <div class="form-group">
            <input class="form-control" placeholder="userPw" name="userPw" type="password">
        </div>
        <a href="/myPage/ModifyInfo" class="loginBtn">Login</a>
    </fieldset>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<script>
    $(".loginBtn").on("click", function(e){
        e.preventDefault();
        $("form").submit();
    });
</script>
</body>
</html>
