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
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/memberQnA.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">

        <div class="insertQnA_header">
            <h1>문의하기</h1>
        </div>
        <div class="myQnAInsert_content content_data">
            <div class="myQnAInsertForm_area">
                <form id="myQnA_InsertForm" method="post">
                    <div>
                        <div>
                            <label>제목</label>
                        </div>
                        <div>
                            <input type="text" name="qTitle">
                        </div>
                    </div>
                    <div>
                        <div>
                            <label>내용</label>
                        </div>
                        <div>
                            <textarea name="qContent" ></textarea>
                        </div>
                    </div>
                    <sec:csrfInput/>
                </form>
            </div>
            <button type="button" id="insertMyQnA">작성</button>
        </div>
    </div>
</div>
</div>

</body>
</html>
