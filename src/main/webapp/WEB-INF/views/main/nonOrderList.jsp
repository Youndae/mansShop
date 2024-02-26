<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/nonOrder.js"></script>
<script type="text/javascript" src="/js/orderListStr.js"></script>
<link rel="stylesheet" href="/css/nonOrder.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <div class="nonOrderList_header">
            <h1>주문내역</h1>
        </div>
        <div class="memberOrderList">

        </div>
        <div>
            <input type="hidden" id="recipient" value="${oList.recipient}">
            <input type="hidden" id="orderPhone" value="${oList.orderPhone}">
        </div>
    </div>
</div>
</body>
</html>
