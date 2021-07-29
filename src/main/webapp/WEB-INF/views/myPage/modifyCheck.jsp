<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<style>
    .container header{
        text-align: center;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .modifyCheck_header{
        margin: 40px 0 50px 200px;
    }

    .modifyCheck_content{
        margin: 50px 0 50px 400px;
        width: 100%;
        height: 100%;
    }

    .modifyCheck_content .check{
        margin: 25px 0 25px 20px;
        width: 800px;
    }

    .modifyCheck_header{
        margin: 40px 0 50px 200px;
    }

    .modifyCheck_header h3{
        padding: 20px;
    }

    .modifyCheck_content input{
        width: 200px;
        height: 30px;
    }

    .modifyCheck_content button{
        margin-left: 65px;
        margin-top: 10px;
        width: 100px;
        height: 25px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">
        <div class="modifyCheck_header">
            <h1>정보수정</h1>
            <h3>보안을 위해 비밀번호를 다시 입력해주세요</h3>
        </div>
        <div class="modifyCheck_content">
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
<%--<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(function(){
        $("#userCheck").on('click', function(){
            var userPw = {
                userPw: $("input[name=userPw]").val()
            };

            $.ajax({
                url: '/myPage/modifyCheck',
                type: 'post',
                data: userPw,
                async: false,
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    if(data == "1"){
                        location.href='/myPage/ModifyInfo';
                    }else{
                        $(".pwOverlap").text("비밀번호가 틀렸습니다.");
                    }
                }
            })
        })
    })
</script>--%>
</body>
</html>
