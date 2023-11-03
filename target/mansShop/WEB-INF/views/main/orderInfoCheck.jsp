<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<link rel="stylesheet" href="/css/nonOrder.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content orderInfoCheck">
        <div class="orderInfoCheck_header">
            <h1>비회원 주문조회</h1>
        </div>
        <div class="orderInfoCheck_content">
            <form id="orderInfoCheckForm">
                <div>
                    <label>주문자 이름</label>
                    <input type="text" name="recipient">
                </div>
                <div>
                    <label>주문자 연락처</label>
                    <input type="text" name="orderPhone">
                </div>
            </form>
        </div>
        <button type="button" id="orderInfoCheck">조회하기</button>
    </div>
</div>
</body>
</html>
