<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container div{
        text-align: center;
    }

    .content div{
        display: grid;
    }

    .memberOrderList{
        margin: 0 25% 0 25%;
    }

    .memberOrderList table{
        width: 100%;
        border: 1px solid lightgray;
    }

    .memberOrderList label{
        text-align: justify;
        font-size: larger;
        font-weight: bold;
    }

    .memberOrderList span{
        font-size: large;
        text-align: end;
        font-weight: bold;
    }

    .memberOrderList table p {
        font-weight: bold;
        font-size: larger;
        width: 40%;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/nonOrder.js"></script>

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
