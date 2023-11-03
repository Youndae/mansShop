<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<link rel="stylesheet" href="/css/userOrder.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="orderComplete_header">
        <h1>주문이 완료되었습니다.</h1>
    </div>
    <div class="orderComplete_content">
        <div class="orderComplete_content_content">
            <c:choose>
                <c:when test="${type == 'H'}">

                    <h3>입금이 확인되는대로 상품이 발송됩니다.</h3>
                    <span>입금계좌</span>
                    <ul>
                        <li>농협 : 000-0000000-000</li>
                        <li>기업은행 : 0000-00000-000</li>

                        <li>
                            입금계좌는 상품 상세페이지 - 배송정보에서 확인하실 수 있습니다.
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <span>빠르게 준비해서 발송하겠습니다. 감사합니다.</span>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="orderComplete_btn_area">
            <button type="button" id="orderList">주문내역 보기</button>
        </div>
    </div>
</div>
</div>
</body>
</html>
