<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div>
    <div class="header">
        <h1>문의하기</h1>
    </div>
    <div class="myQnA_content">
        <form id="myQnA_InsertForm" method="post">
            <div>
                <label>제목</label>
                <input type="text" name="qTitle">
            </div>
            <div>
                <label>내용</label>
                <textarea name="qContent"></textarea>
            </div>
            <sec:csrfInput/>
        </form>
        <button type="button" id="insertMyQnA">작성</button>
    </div>
</div>
<script>
    $(function(){
        $("#insertMyQnA").on('click', function(){
            var form = $("#myQnA_InsertForm");

            form.attr("action", "/myPage/insertMemberQnA");
            form.submit();
        })
    });
</script>
</body>
</html>
