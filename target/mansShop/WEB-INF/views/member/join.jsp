<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/memberJoin.js"></script>
<link rel="stylesheet" href="/css/member.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="join">
        <div class="join-header">
            <h1>회원가입</h1>
        </div>
        <div class="join-content">
            <div class="join-form">
                <div>
                    <div>
                        <div>
                            <label>아이디</label>
                        </div>
                        <div>
                            <input type="text" id="userId" placeholder="아이디">
                            <button type="button" class="default-btn" onclick="handleIdCheck()">중복체크</button>
                            <div class="overlap join-id-overlap"></div>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>비밀번호</label>
                        </div>
                        <div>
                            <input type="password" id="userPw" placeholder="비밀번호">
                            <div class="overlap join-pw-overlap"></div>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>비밀번호 확인</label>
                        </div>
                        <div>
                            <input type="password" id="checkPw" placeholder="비밀번호 확인">
                            <div class="overlap join-checkPw-overlap"></div>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>이름</label>
                        </div>
                        <div>
                            <input type="text" id="username" placeholder="이름">
                            <div class="overlap join-username-overlap"></div>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>닉네임</label>
                        </div>
                        <div>
                            <input type="text" id="nickname" placeholder="닉네임">
                            <button type="button" onclick="handleNicknameCheck()">중복 체크</button>
                            <p class="nickname-info">닉네임을 입력하지 않을 시 활동 내역에 대해 닉네임 대신 이름으로 처리됩니다.</p>
                            <div class="overlap join-nickname-overlap"></div>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>연락처</label>
                        </div>
                        <div>
                            <input type="text" id="phone" placeholder="-를 제외한 숫자만 입력하세요">
                            <div class="overlap join-phone-overlap"></div>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>생년월일</label>
                        </div>
                        <div class="join-birth">
                            <select id="year">

                            </select>
                            <select id="month">

                            </select>
                            <select id="day">

                            </select>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>이메일</label>
                        </div>
                        <div>
                            <input type="text" id="email" placeholder="이메일">
                            <span> @ </span>
                            <input type="hidden" class="mail-suffix"/>
                            <select class="email-select" id="select-email-suffix">
                                <option value="naver.com">네이버</option>
                                <option value="daum.net">다음</option>
                                <option value="gmail.com">구글</option>
                                <option value="none">직접입력</option>
                            </select>
                            <div class="overlap join-email-overlap"></div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="button" class="default-btn join-btn" onclick="handleJoinBtnOnClick()">가입</button>
        </div>
    </div>
</div>
</body>
</html>
