<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
        <h1>상품 결제</h1>
    </div>
    <div>
        <form id="order_form">
            <div>
                <label>수령인</label>
                <input type="text" name="recipient">
            </div>
            <div>
                <label>연락처</label>
                <input type="text" name="orderPhone">
            </div>
            <div>
                <label>배송지주소</label>
                <input type="text" name="addr">
            </div>
            <div>
                <label>배송메모</label>
                <input type="text" name="orderMemo">
            </div>
            <table class="order_table">
                <thead>
                    <tr>
                        <th>상품명</th>
                        <th>옵션</th>
                        <th>수량</th>
                        <th>금액</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            <span>총 주문금액 : </span>
        </form>
    </div>
    <div>
        <label>결제수단</label>
        <input type="radio">신용카드
    </div>
    <div>
        <button type="button" id="orderPay">결제하기</button>
    </div>
</div>
</body>
</html>
