<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <h1>주문이 완료되었습니다.</h1>
</div>
<div>
    <c:choose>
        <c:when test="${type == 'H'}">

            <h3>입금이 확인되는대로 상품이 발송됩니다.</h3>
            <h5>입금계좌</h5>
            <ul>
                <li>농협 : 000-0000000-000</li>
                <li>기업은행 : 0000-00000-000</li>

                <li>
                    입금계좌는 상품 상세페이지 내 배송정보에서 확인하실 수 있습니다.
                </li>
            </ul>
        </c:when>
        <c:otherwise>
            <span>빠르게 준비해서 발송하겠습니다.</span>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
