<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/Member.js"></script>
<body>
<h1>회원가입</h1>
<form action="/member/join" method="post" id="joinForm">
    <div>
        <label>아이디</label>
        <input type="text" id="userId" placeholder="아이디를 입력하세요">
        <button type="button" id="IdCheck">중복체크</button>
        <div class="check" id="overlap"></div>
    </div>
    <div>
        <label>비밀번호</label>
        <input type="password" id="userPw" placeholder="비밀번호를 입력하세요">
    </div>
    <div>
        <label>비밀번호 확인</label>
        <input type="password" id="checkUserPw" placeholder="비밀번호를 다시 입력하세요" onchange="javascript:checkPassword();">
    </div>
    <div>
        <label>이름</label>
        <input type="text" id="userName">
    </div>
    <div>
        <label>이메일</label>
        <input type="text" id="userEmail">
    </div>
    <div>
        <label>생년월일</label>
        <select id="select_year" name="select_year" onchange="javascript:lastday();"></select>년&nbsp;
        <select id="select_month" name="select_month" onchange="javascript:lastday();"></select>월&nbsp;
        <select id="select_day"></select>일

    </div>
    <div>
        <label>연락처</label>
        <input type="text" id="userPhone">
    </div>
</form>
<button type="button" id="join">가입</button>
</body>
</html>
