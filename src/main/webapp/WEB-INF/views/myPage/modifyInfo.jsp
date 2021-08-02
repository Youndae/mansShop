<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 30%;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .modifyInfo_header{
        margin: 40px 0 50px 200px;
    }

    .modifyInfo_content{
        margin: 50px 0 50px 400px;
        width: 100%;
        height: 100%;
        font-size: large;
        font-weight: bold;
    }

    .modifyInfo_content .modifyInfo_userInfo{
        margin: 25px 0 25px 20px;
        width: 800px;
    }

    .modifyInfo_content input{
        width: 270px;
        height: 30px;
    }

    .modifyInfo_content button{
        text-align: center;
        width: 100px;
        height: 30px;
        margin-left: 10%;
    }

    .modifyInfo_userInfo form{
        margin: 10px;
    }

    .modifyInfo_userInfo form div{
        padding-top: 10px;
    }

</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">

        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>

        <div class="content_area">
            <div class="modifyInfo_header">
                <h1>정보수정</h1>
            </div>

            <div class="modifyInfo_content">
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
                                <input type="text" name="userName" value="${info.userName}" readonly>
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
