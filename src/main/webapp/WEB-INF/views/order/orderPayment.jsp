<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/js/orderPayment.js"></script>
<link rel="stylesheet" href="/css/userOrder.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="orderPayment_header">
        <h1>상품 결제</h1>
    </div>
    <div class="orderPayment_content">
        <form id="order_form" method="post">
            <div class="form_content">
                <div class="form_content_label">
                    <label>수령인</label>
                </div>
                <div class="form_content_input">
                    <input type="text" name="recipient">
                </div>
            </div>
            <div class="form_content">
                <div class="form_content_label">
                    <label>연락처</label>
                </div>
                <div class="form_content_input">
                    <input type="text" name="orderPhone">
                </div>
            </div>
            <div class="form_content">
                <div class="form_content_label">
                    <label>배송지주소</label>
                </div>
                <div class="form_content_input">
                    <div class="form_content_input_postCode">
                        <input type="text" id="postCode" placeholder="우편번호" readonly>
                        <button type="button" id="searchAddr">우편번호 찾기</button>
                    </div>
                    <div>
                        <input style="width: 700px" type="text" id="address" placeholder="주소" readonly>
                    </div>
                    <div>
                        <input type="text" id="addrDetail" placeholder="상세주소">
                    </div>
                </div>
            </div>
            <div class="form_content">
                <div class="form_content_label">
                    <label>배송메모</label>
                </div>
                <div class="form_content_input">
                    <input type="text" name="orderMemo">
                </div>
            </div>
            <input type="hidden" name="orderPrice">
            <input type="hidden" name="orderPayment">
            <input type="hidden" name="addr">
            <input type="hidden" name="oType" value="<c:out value="${orderType}"/>">
            <sec:csrfInput/>
        </form>
            <table class="order_table" border="1">
                <thead>
                    <tr>
                        <th>상품명</th>
                        <th>옵션</th>
                        <th>수량</th>
                        <th>가격</th>
                    </tr>
                </thead>
                <tbody id="order_payment_list">
                    <c:forEach var="list" items="${oList}">
                        <c:set var="total" value="${total + list.CPrice}"/>
                        <tr data_opNo="${list.POpNo}"
                            data_cCount="${list.CCount}"
                            data_cprice="${list.CPrice}"
                            data_pName="${list.PName}"
                            data_pno="${list.pno}"
                            data_cdNo="${list.cdNo}">
                            <td>
                                <c:out value="${list.PName}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${list.PSize == ''}">
                                        <c:choose>
                                            <c:when test="${list.PColor != null}">
                                                색상 : <c:out value="${list.PColor}"/>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${list.PColor == ''}">
                                                사이즈 : <c:out value="${list.PSize}"/>
                                            </c:when>
                                            <c:otherwise>
                                                사이즈 : <c:out value="${list.PSize}"/>  색상 : <c:out value="${list.PColor}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:out value="${list.CCount}"/>
                            </td>
                            <td class="cPrice">
                                <fmt:formatNumber value="${list.CPrice}" pattern="#,###"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:choose>
                <c:when test="${total <= 100000}">
                    <div class="order_price">
                        <span class="delivery_price">배송비 : 2,500원</span>
                        <span class="total_price">총 주문금액 : <fmt:formatNumber value="${total + 2500}" pattern="#,###"/> 원</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="order_price">
                        <span class="delivery_price">배송비 : 0원</span>
                        <span class="total_price">총 주문금액 : <fmt:formatNumber value="${total}" pattern="#,###"/> 원</span>
                    </div>
                </c:otherwise>
            </c:choose>
        <div class="orderPayment_payment">
            <div>
                <label>결제수단</label>
            </div>
            <div>
                <input type="radio" name="select_payment" value="card">신용카드
                <input type="radio" name="select_payment" value="cash">무통장입금
            </div>
        </div>
        <div class="orderPayment_btn_area">
            <button type="button" id="orderPay">결제하기</button>
        </div>
    </div>

</div>
</div>
</body>
</html>
