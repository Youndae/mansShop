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
            <h1>아이디 찾기</h1>
        </div>
        <div class="search-radio-area">
            <input type="radio" id="searchIdPhone" name="searchId" value="phone" checked="checked">휴대폰 번호로 찾기
            <input type="radio" id="searchIdEmail" name="searchId" value="email">이메일로 찾기
        </div>
        <div class="search_id_form">
            <div class="form-area">
                <div class="form-group userName">
                    <div><label>이름</label></div>
                    <input class="form-control" name="userName" type="text" autofocus>
                </div>
                <div class="form-group userPhone">
                    <div><label>휴대폰번호</label></div>
                    <input class="form-control" placeholder="-를 제외한 숫자만 입력해주세요" name="userPhone" type="text">
                    <div class="overlap" style="color: red"></div>
                </div>
            </div>
            <button class="searchIdBtn">아이디 찾기</button>
            <div class="searchOverlap"></div>
        </div>
    </div>
</div>
</body>
</html>
