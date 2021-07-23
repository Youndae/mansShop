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
<body>
<div>
    <div class="header">
        <h1>정보수정</h1>
    </div>
    <div>
        <sec:authentication property="principal.username" var="userId"/>

            <div>
                <label>아이디</label>
                <input type="text" name="userId" value="${userId}" readonly>
            </div>
            <div>
                <label>비밀번호</label>
                <input type="password" name="userPw" placeholder="보안을위해 비밀번호를 재입력해주세요">
                <span class="pwOverlap"></span>
            </div>

        <button type="button" id="userCheck">확인</button>
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
