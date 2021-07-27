<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/member.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <h1>회원가입</h1>
    <form class="formJoin" action="/member/join" method="post" id="joinForm" role="form">
        <div>
            <label>아이디</label>
            <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요">
            <button type="button" id="IdCheck">중복체크</button>
            <div class="overlap" id="idOverlap"></div>
            <input type="hidden" id="idStat" value="">
        </div>
        <div>
            <label>비밀번호</label>
            <input type="password" id="userPw" name="userPw" placeholder="비밀번호를 입력하세요">
            <div class="overlap" id="pwOverlap"></div>
        </div>
        <div>
            <label>비밀번호 확인</label>
            <input type="password" id="checkUserPw" placeholder="비밀번호를 한번 더 입력하세요">
            <div class="overlap" id="pwCheckOverlap"></div>
            <input type="hidden" id="pwStat" value="">
        </div>
        <div>
            <label>이름</label>
            <input type="text" id="userName" name="userName">
            <div class="overlap" id="nameOverlap"></div>
        </div>
        <div>
            <label>이메일</label>
            <input type="text" id="userEmail" name="userEmail">
            <div class="overlap" id="emailOverlap"></div>
            <input type="hidden" id="mailStat" value="">
        </div>
        <div>
            <label>생년월일</label>
            <select id="select_year"></select>년&nbsp;
            <select id="select_month"></select>월&nbsp;
            <select id="select_day"></select>일

            <%--<input type="hidden" name="userBirth">--%>

        </div>
        <div>
            <label>연락처</label>
            <input type="text" id="userPhone" name="userPhone" placeholder="-를 제외한 숫자만 입력하세요">
            <div class="overlap" id="phoneOverlap"></div>
            <input type="hidden" id="phoneStat" value="">
        </div>
        <sec:csrfInput/>
    </form>
    <button type="button" id="join">가입</button>
</div>
</div>
</body>
</html>
