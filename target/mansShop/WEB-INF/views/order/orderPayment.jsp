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
<body>
<div>
    <div class="header">
        <h1>상품 결제</h1>
    </div>
    <div>
        <form id="order_form" method="post">
            <div>
                <label>수령인</label>
                <input type="text" name="recipient">
            </div>
            <div>
                <label>연락처</label>
                <input type="text" name="orderPhone">
            </div>
            <div>
                <div>
                    <label>배송지주소</label>
                </div>
                <div>
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
            <div>
                <label>배송메모</label>
                <input type="text" name="orderMemo">
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
                            data_pName="${list.PName}">
                            <td>
                                <c:out value="${list.PName}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${list.PSize == null}">
                                        <c:choose>
                                            <c:when test="${list.PColor != null}">
                                                색상 : <c:out value="${list.PColor}"/>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${list.PColor == null}">
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
                                <span><c:out value="${list.CCount}"/></span>
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
                    <span class="delivery_price">배송비 : 2,500원</span>
                    <span class="total_price">총 주문금액 : <fmt:formatNumber value="${total + 2500}" pattern="#,###"/> 원</span>
                </c:when>
                <c:otherwise>
                    <span class="delivery_price">배송비 : 0원</span>
                    <span class="total_price">총 주문금액 : <fmt:formatNumber value="${total}" pattern="#,###"/> 원</span>
                </c:otherwise>
            </c:choose>

    </div>
    <div>
        <label>결제수단</label>
        <input type="radio" name="select_payment" value="card">신용카드
        <input type="radio" name="select_payment" value="cash">무통장입금
    </div>
    <div>
        <button type="button" id="orderPay">결제하기</button>
    </div>
</div>


<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ready(function(){
        $("#orderPay").on('click', function(){
            var type = $("input[name=select_payment]:checked").val();

            console.log("payment type : " + type);

            $("input[name=orderPrice]").val($("span.total_price").text().replace(/\D/g, ''));

            if(type == 'card'){
                $("input[name=orderPayment]").val("C");
                order();
            }else if(type == 'cash'){
                $("input[name=orderPayment]").val("H");
                var saveAddr = $("#postCode").val() + " " + $("#address").val() + " " + $("#addrDetail").val();
                $("input[name=addr]").val(saveAddr);

                var table_tbody = $("#order_payment_list");
                var table_tr = table_tbody.children();
                var form = $("#order_form")[0];
                var formData = new FormData(form);

                for(var i = 0; i < table_tr.length; i++){
                    formData.append('pOpNo', table_tr.eq(i).attr("data_opNo"));
                    formData.append('orderCount', table_tr.eq(i).attr("data_cCount"));
                    formData.append('odPrice', table_tr.eq(i).attr("data_cPrice"));
                }

                $.ajax({
                    url: '/order/payment',
                    type: 'post',
                    contentType: false,
                    processData: false,
                    cache: false,
                    data: formData,
                    beforeSend: function(xhr){
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        var oType = "H";
                        location.href='orderComplete/'+oType;
                    },
                    error: function(request, status, error){
                        alert("code : " + request.status + "\n"
                                + "message : " + request.responseText
                                + "\n" + "error : " + error);
                    }
                })
            }
        })

        $("#searchAddr").on('click', function(){
            daum_address();
        })
    });

    function daum_address(){
        new daum.Postcode({
            oncomplete: function(data) {

                var addr = '';
                var extraAddr = '';

                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }


                if(data.userSelectedType === 'R'){

                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }

                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }

                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }

                    addr += extraAddr;

                }


                document.getElementById('postCode').value = data.zonecode;
                document.getElementById("address").value = addr;
                document.getElementById("addrDetail").focus();
            }
        }).open();
    }

    function order() {
        var IMP = window.IMP;
        IMP.init('imp78285136');
        var totalPrice = parseInt($("input[name=orderPrice]").val());
        var name = $("input[name=recipient]").val();
        var phone = $("input[name=orderPhone]").val();
        var saveAddr = $("#postCode").val() + " " + $("#address").val() + " " + $("#addrDetail").val();
        var payAddr = $("#address").val() + " " + $("#addrDetail").val();

        console.log(saveAddr);

        $("input[name=addr]").val(saveAddr);


        var table_tbody = $("#order_payment_list");
        var table_tr = table_tbody.children();

        if(phone.length == 11){
            phone = phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }else if(phone.length == 10){
            phone = phone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
        }

        var form = $("#order_form")[0];
        var formData = new FormData(form);

        for(var i = 0; i < table_tr.length; i++){
            formData.append('pOpNo', table_tr.eq(i).attr("data_opNo"));
            formData.append('orderCount', table_tr.eq(i).attr("data_cCount"));
            formData.append('odPrice', table_tr.eq(i).attr("data_cPrice"));
        }

        var pName = table_tr.eq(0).attr("data_pName");

        if(table_tr.length == 1){
            var orderName = pName;
        }else{
            var orderName = pName + " 외 " + (table_tr.length - 1) + " 건";
        }


        IMP.request_pay({
            pg: 'danal', // version 1.1.0부터 지원.
            pay_method: 'card',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: orderName,
            amount: totalPrice,
            buyer_email: '',
            buyer_name: name,
            buyer_tel: phone,
            buyer_addr: payAddr,
            buyer_postcode: $("#postCode").val(),
            /*m_redirect_url : '/order/orderComplete'
            * 결제가 성공햇을 경우 이동할 페이지 url
            * 가이드와 다르게 ajax로 데이터를 넘겨주는 형태로 바꿔서 그런지
            * 제대로 동작하지 않음.*/
        }, function (rsp) {
            if (rsp.success) {
                $.ajax({
                    url: '/order/payment',
                    type: 'post',
                    contentType: false,
                    processData: false,
                    cache: false,
                    data: formData,
                    beforeSend: function(xhr){
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        location.href='orderComplete';
                    },
                    error: function(request, status, error){
                        alert("code : " + request.status + "\n"
                                + "message : " + request.responseText
                                + "\n" + "error : " + error);
                    }
                });


                /*var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;*/
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
                alert(msg);
            }
        });
    }


</script>
</body>
</html>
