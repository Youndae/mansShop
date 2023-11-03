<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/member.js"></script>
<link rel="stylesheet" href="/css/member.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content login_content">
        <div class="login_header">
            <h1>비밀번호 변경</h1>
        </div>
        <div class="reset_pw_form">
            <form action="/member/resetPw" id="resetPwForm" method="post">
                <div class="form-group">
                    <input class="form-control" placeholder="비밀번호" name="password" type="password" autofocus>
                </div>
                <div class="form-group">
                    <input class="form-control" placeholder="비밀번호 재입력" name="checkPassword" type="password">
                </div>
                <input type="hidden" name="userId" value="${info.userId}">
                <input type="hidden" name="cno" value="${info.cno}">
                <sec:csrfInput/>
                <div class="reset-pw-btn-area">
                    <button class="resetPwBtn">비밀번호 변경</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
