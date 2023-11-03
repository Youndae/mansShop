<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/memberOrderList.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
        <div class="content_area">
            <div class="memberOrderList_header content_header">
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
            <div class="memberOrderList_content content_data">
                <div class="memberOrderList">

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
