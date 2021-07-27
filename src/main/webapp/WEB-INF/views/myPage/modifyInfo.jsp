<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="">
        <h1>정보수정</h1>
    </div>

    <div class="">
        <form name="userInfo">
            <label>아이디</label><input type="text" name="userId" value="${info.userId}" readonly>
            <label>이름</label><input type="text" name="userName" value="${info.userName}" readonly>
            <label>이메일</label><input type="text" name="userEmail" value="${info.userEmail}">
        </form>
        <a href="/member/login">로그인</a>
    </div>
</div>
</div>
</body>
</html>
