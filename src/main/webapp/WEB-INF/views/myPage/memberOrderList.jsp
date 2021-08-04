<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    .memberOrderList_header{
        margin: 50px 0 50px 200px;
    }

    .memberOrderList_Term_select{
        float: right;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .memberOrderList_content{
        margin: 50px 0 50px 200px;
        width: 100%;
        height: 100%;
    }

    .memberOrderList_content table{
        font-size: large;
        margin: 25px 0 25px 0;
        width: 1000px;
        height: 200px;
    }

    .paging li{
        list-style: none;
        display: inline;
        margin: 10px;
        font-size: large;
    }

    .tbl_review_a{
        display: inline-flex;
        width: 100%;
    }

    .tbl_productName{
        width: 100%;
        text-align: center;
        margin: 11% 10% 0 0;
    }

    .orderList_tbl {
        margin-top: 50px;
    }

    .orderList_tbl label{
        font-size: x-large;
        font-weight: bold;
    }

    .orderList_tbl span{
        font-size: large;
        font-weight: bold;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="content_area">
            <div class="memberOrderList_header">
                <h1>주문내역</h1>
                <div class="memberOrderList_Term_select">
                    <select id="select_orderList_term">
                        <option value="3">3개월</option>
                        <option value="6">6개월</option>
                        <option value="12">1년</option>
                        <option value="all">전체</option>
                    </select>
                </div>
            </div>
            <div class="memberOrderList_content">
                <div class="memberOrderList">

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
