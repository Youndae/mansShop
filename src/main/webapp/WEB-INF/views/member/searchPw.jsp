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
            <h1>비밀번호 찾기</h1>
        </div>
        <div class="search_pw_form">
            <div class="form-area">
                <div class="form-group userId">
                    <div><label>아이디</label></div>
                    <input class="form-control" id="userId" name="userId" type="text" autofocus>
                    <div class="idOverlap"></div>
                </div>
                <div class="form-group userName">
                    <div><label>이름</label></div>
                    <input class="form-control" id="userName" name="userName" type="text">
                    <div class="nameOverlap"></div>
                </div>
                <div class="form-group userEmail">
                    <div><label>이메일</label></div>
                    <input class="form-control" id="userEmail" name="userEmail" type="text">
                    <div class="overlap"></div>
                </div>
            </div>
            <button class="searchPwBtn">비밀번호 찾기</button>
            <div class="searchOverlap"></div>
        </div>
        <div class="pwForm">
            <form id="pwForm" action="/member/reset-pw" method="get">
                <input type="hidden" id="formId" name="userId" value="">
                <input type="hidden" id="formCno" name="cno" value="">
                <sec:csrfInput/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
