<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
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
        margin: 0 0 0 160px;
    }

    .insertQnA_header{
        margin: 40px 0 50px 200px;
    }

    .myQnAInsert_content{
        margin: 50px 0 50px 200px;
        width: 87%;
        height: 100%;
        font-size: large;
        font-weight: bold;
    }

    .myQnAInsert_content .myQnAInsertForm_area{
        margin: 25px 0 25px 20px;
        width: 800px;
    }

    .myQnAInsert_content input{
        width: 870px;
        height: 40px;
    }

    .myQnAInsert_content textarea{
        width: 870px;
        height: 600px;
    }

    .myQnAInsert_content button{
        float: right;
        width: 100px;
        height: 30px;

    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">

        <div class="insertQnA_header">
            <h1>문의하기</h1>
        </div>
        <div class="myQnAInsert_content">
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
                            <textarea name="qContent"></textarea>
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
