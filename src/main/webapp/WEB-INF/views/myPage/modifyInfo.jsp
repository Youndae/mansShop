<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
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
            <div class="modifyInfo_header content_header">
                <h1>정보수정</h1>
            </div>

            <div class="modifyInfo_content content_data">
                <div class="modifyInfo_userInfo">
                    <form id="userInfo" method="post">
                        <div>
                            <div>
                                <label>아이디</label>
                            </div>
                            <div>
                                <input type="text" name="userId" value="${info.userId}" readonly>
                            </div>
                        </div>
                        <div>
                            <div>
                                <label>이름</label>
                            </div>
                            <div>
                                <input type="text" name="userName" value="${info.userName}">
                            </div>
                        </div>
                        <div>
                            <div>
                                <label>연락처</label>
                            </div>
                            <div>
                                <input type="text" id="userPhone" name="userPhone" value="${info.userPhone}">
                                <div class="overlap" id="phoneOverlap"></div>
                                <input type="hidden" id="phoneStat" value="">
                            </div>
                        </div>
                        <div>
                            <div>
                                <label>이메일</label>
                            </div>
                            <div>
                                <input type="text" id="userEmail" name="userEmail" value="${info.userEmail}">
                                <div class="overlap" id="emailOverlap"></div>
                                <input type="hidden" id="mailStat" value="">
                            </div>
                        </div>
                        <input type="hidden" name="_method" value="PATCH">
                        <sec:csrfInput/>
                    </form>
                </div>
                <button type="button" id="modify_info">정보수정</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
