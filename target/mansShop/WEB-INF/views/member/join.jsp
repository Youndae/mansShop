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
<link rel="stylesheet" href="/css/member.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <div class="join_header">
            <h1>회원가입</h1>
        </div>
        <div class="join_content">
            <form class="formJoin" action="/member/join" method="post" id="joinForm" role="form">
                <div>
                    <div>
                        <label>아이디</label>
                    </div>
                    <div>
                         <span>
                            <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요">
                            <button type="button" id="IdCheck">중복체크</button>
                            <div class="overlap" id="idOverlap"></div>
                            <input type="hidden" id="idStat" value="">
                         </span>
                    </div>
                </div>
                <div>
                    <div>
                        <label>비밀번호</label>
                    </div>
                    <div>
                        <input type="password" id="userPw" name="userPw" placeholder="비밀번호를 입력하세요">
                        <div class="overlap" id="pwOverlap"></div>
                    </div>
                </div>
                <div>
                    <div>
                        <label>비밀번호 확인</label>
                    </div>
                    <div>
                        <input type="password" id="checkUserPw" placeholder="비밀번호를 한번 더 입력하세요">
                        <div class="overlap" id="pwCheckOverlap"></div>
                        <input type="hidden" id="pwStat" value="">
                    </div>
                </div>
                <div>
                    <div>
                        <label>이름</label>
                    </div>
                    <div>
                        <input type="text" id="userName" name="userName">
                        <div class="overlap" id="nameOverlap"></div>
                    </div>
                </div>
                <div>
                    <div>
                        <label>이메일</label>
                    </div>
                    <div>
                        <input type="text" id="userEmail" name="userEmail">
                        <div class="overlap" id="emailOverlap"></div>
                        <input type="hidden" id="mailStat" value="">
                    </div>
                </div>
                <div>
                    <div>
                        <label>생년월일</label>
                    </div>
                    <div>
                        <select id="select_year"></select>년&nbsp;
                        <select id="select_month"></select>월&nbsp;
                        <select id="select_day"></select>일
                    </div>

                    <%--<input type="hidden" name="userBirth">--%>

                </div>
                <div>
                    <div>
                        <label>연락처</label>
                    </div>
                    <div>
                        <input type="text" id="userPhone" name="userPhone" placeholder="-를 제외한 숫자만 입력하세요">
                        <div class="overlap" id="phoneOverlap"></div>
                        <input type="hidden" id="phoneStat" value="">
                    </div>
                </div>
                <sec:csrfInput/>
            </form>
            <button type="button" id="join">가입</button>
        </div>
    </div>
</div>
</body>
</html>
