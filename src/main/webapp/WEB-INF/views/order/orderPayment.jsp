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
<style>
    .container .header{
        text-align: center;
    }

    .orderPayment_header{
        margin-top: 40px;
        text-align: center;
    }

    .orderPayment_content{
        margin: 40px 0 0 35%;
    }

    .form_content{
        margin-bottom: 20px;
    }

    .form_content_label{
        font-size: x-large;
    }

    .form_content_input input{
        width: 400px;
        height: 30px;
        margin-top: 10px;
    }

    .form_content_input_postCode input{
        width: 100px;
        height: 30px;
    }

    .order_table{
        width: 700px;
        text-align: center;
        margin-top: 40px;
    }

    .order_price{
        margin-top: 30px;
    }

    .order_price span{
        font-size: x-large;
        margin-left: 10px;
    }

    .orderPayment_payment label{
        font-size: x-large;
    }

    .orderPayment_payment{
        margin-top: 60px;
    }

    .orderPayment_payment div{
        margin-top: 10px;
    }

    .orderPayment_btn_area button{
        margin-top: 35px;
        margin-left: 17%;
        width: 300px;
        height: 40px;
        font-size: large;
        font-weight: 500;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/js/orderPayment.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="orderPayment_header">
        <h1>?????? ??????</h1>
    </div>
    <div class="orderPayment_content">
        <form id="order_form" method="post">
            <div class="form_content">
                <div class="form_content_label">
                    <label>?????????</label>
                </div>
                <div class="form_content_input">
                    <input type="text" name="recipient">
                </div>
            </div>
            <div class="form_content">
                <div class="form_content_label">
                    <label>?????????</label>
                </div>
                <div class="form_content_input">
                    <input type="text" name="orderPhone">
                </div>
            </div>
            <div class="form_content">
                <div class="form_content_label">
                    <label>???????????????</label>
                </div>
                <div class="form_content_input">
                    <div class="form_content_input_postCode">
                        <input type="text" id="postCode" placeholder="????????????" readonly>
                        <button type="button" id="searchAddr">???????????? ??????</button>
                    </div>
                    <div>
                        <input style="width: 700px" type="text" id="address" placeholder="??????" readonly>
                    </div>
                    <div>
                        <input type="text" id="addrDetail" placeholder="????????????">
                    </div>
                </div>
            </div>
            <div class="form_content">
                <div class="form_content_label">
                    <label>????????????</label>
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
                        <th>?????????</th>
                        <th>??????</th>
                        <th>??????</th>
                        <th>??????</th>
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
                                                ?????? : <c:out value="${list.PColor}"/>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${list.PColor == ''}">
                                                ????????? : <c:out value="${list.PSize}"/>
                                            </c:when>
                                            <c:otherwise>
                                                ????????? : <c:out value="${list.PSize}"/>  ?????? : <c:out value="${list.PColor}"/>
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
                        <span class="delivery_price">????????? : 2,500???</span>
                        <span class="total_price">??? ???????????? : <fmt:formatNumber value="${total + 2500}" pattern="#,###"/> ???</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="order_price">
                        <span class="delivery_price">????????? : 0???</span>
                        <span class="total_price">??? ???????????? : <fmt:formatNumber value="${total}" pattern="#,###"/> ???</span>
                    </div>
                </c:otherwise>
            </c:choose>
        <div class="orderPayment_payment">
            <div>
                <label>????????????</label>
            </div>
            <div>
                <input type="radio" name="select_payment" value="card">????????????
                <input type="radio" name="select_payment" value="cash">???????????????
            </div>
        </div>
        <div class="orderPayment_btn_area">
            <button type="button" id="orderPay">????????????</button>
        </div>
    </div>

</div>
</div>
</body>
</html>
