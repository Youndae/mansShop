<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="mypage-content">
            <div class="mypage-content-header">
                <h1>정보 수정</h1>
            </div>
            <div class="mypage-user-info-content">
                <div class="form-content">
                    <div>
                        <label>아이디</label>
                    </div>
                    <div>
                        <sec:authentication property="principal.username" var="username"/>
                        <span>${username}</span>
                    </div>
                </div>
                <div class="form-content">
                    <div>
                        <label>닉네임</label>
                    </div>
                    <div>
                        <%--<c:set var="nickname" value=""/>
                        <c:if test="${data.nickname ne null}">
                            <c:set var="nickname" value="${data.nickname}"/>
                        </c:if>--%>
                        <input type="text" id="nickname" value="${data.nickname}"/>
                        <button class="default-btn" onclick="checkNickname()">중복체크</button>
                        <p class="nickname-info">닉네임을 입력하지 않을 시 활동 내역에 대해 닉네임 대신 사용자 이름으로 처리됩니다.</p>
                        <div class="overlap nickname-overlap"></div>
                    </div>
                </div>
                <div class="form-content">
                    <div>
                        <label>연락처</label>
                    </div>
                    <div>
                        <input type="text" id="phone" value="${data.phone}" placeholder="-를 제외한 숫자만 입력하세요">
                        <div class="overlap phone-overlap"></div>
                    </div>
                </div>
                <div class="form-content">
                    <div>
                        <label>이메일</label>
                    </div>
                    <div>
                        <input type="text" id="email" value="${data.mailPrefix}">
                        <span> @ </span>
                        <c:choose>
                            <c:when test="${data.mailType eq 'none'}">
                                <input type="text" value="${data.mailSuffix}" class="mail-suffix"/>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" class="mail-suffix"/>
                            </c:otherwise>
                        </c:choose>
                        <select class="email-select" id="select-email-suffix">
                            <c:forEach items="${mailSuffix}" var="mailSuf">
                                <c:choose>
                                    <c:when test="${data.mailType eq mailSuf.mailSuffixType}">
                                        <option value="${mailSuf.mailSuffixType}" selected>${mailSuf.mailSuffixTypeStr}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${mailSuf.mailSuffixType}">${mailSuf.mailSuffixTypeStr}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <div class="overlap email-overlap"></div>
                    </div>
                </div>
            </div>
            <button class="default-btn" onclick="modifyInfoSubmit()">수정</button>
        </div>
    </div>
</div>
</body>
</html>
