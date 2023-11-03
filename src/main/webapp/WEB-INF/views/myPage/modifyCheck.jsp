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
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/userInfo.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="content_area">
            <div class="modifyCheck_header content_header">
                <h1>정보수정</h1>
                <h3>보안을 위해 비밀번호를 다시 입력해주세요</h3>
            </div>
            <div class="modifyCheck_content content_data">
                <sec:authentication property="principal.username" var="userId"/>
                <div class="check">
                    <div>
                        <input type="text" name="userId" value="${userId}" readonly>
                    </div>
                    <div>
                        <input type="password" name="userPw" placeholder="비밀번호">
                        <span class="pwOverlap"></span>
                    </div>
                </div>

                <button type="button" id="userCheck">확인</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
