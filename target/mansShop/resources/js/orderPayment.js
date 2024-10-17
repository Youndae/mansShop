const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

$(function() {
    $("#searchAddr").on('click', function(){
        daum_address();
    })

    /**
     * 수령인
     * 연락처
     * 배송지
     * 배송메모
     * 결제 타입
     */

    $("#payment").on('click', function() {
        const orderData = getOrderObject();
        const phonePattern = /^01(?:0|1|6|9)-([0-9]{3,4})-([0-9]{4})$/;
        const addressDetail = $("#addrDetail").val();

        orderOverlapAllClear();

        if(orderData.recipient.length < 2) {
            $(".recipient-overlap").text('수령인은 2글자 이상 작성하셔야 합니다.');
            $("#recipient").focus();
        }else if(orderData.phone === '') {
            $(".phone-overlap").text('연락처는 필수 입력 사항입니다.');
            $("#phone").focus();
        }else if(!phonePattern.test(orderData.phone)) {
            $(".phone-overlap").text('올바른 연락처가 아닙니다. 휴대폰 번호만 가능합니다.');
            $("#phone").focus();
        }else if($('#postCode').val() === '') {
            $(".addr-overlap").text('배송지 입력은 필수 사항입니다.');
            $("#addrDetail").focus();
        }else if(addressDetail === '' || addressDetail === ' ') {
            $(".addr-overlap").text('상세 주소를 입력해주세요');
            $("#addrDetail").focus();
        }else {
            const path = window.location.pathname;
            const pathSplit = path.split('/');
            const type = pathSplit[pathSplit.length - 1];
            const requestUrl = `/order/payment/${type}`;

            if(orderData.paymentType === 'card')
                order(orderData, requestUrl);
            else {
                $.ajax({
                    url: requestUrl,
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(orderData),
                    beforeSend: function(xhr){
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        if(data === 'SUCCESS'){
                            location.href='/order/cash';
                        }else{
                            alert("오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요.");
                        }
                    },
                    error: function(request, status, error){
                        alert("code : " + request.status + "\n"
                            + "message : " + request.responseText
                            + "\n" + "error : " + error);
                    }
                })
            }
        }
    })
})

function orderOverlapAllClear() {
    $(".recipient-overlap").empty();
    $(".phone-overlap").empty();
    $(".addr-overlap").empty();
}

function getOrderObject() {
    const paymentType = $("input[name=select-payment]:checked").val();
    const recipient = $("#recipient").val();
    const phone = $("#phone").val().replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
    const addr = $("#postCode").val() + " " + $("#address").val() + " " + $("#addrDetail").val();
    const memo = $("#orderMemo").val();

    return {
        recipient: recipient,
        phone: phone,
        address: addr,
        memo: memo,
        paymentType: paymentType,
    };
}

function daum_address(){
    new daum.Postcode({
        oncomplete: function(data) {
            let addr = '';
            let extraAddr = '';

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

function order(orderData, requestUrl) {
    let IMP = window.IMP;
    IMP.init('imp78285136');
    const totalPrice = parseInt($(".order-price .total-price").text().replace(/[^\d]/g, ''));
    const name = $("input[name=recipient]").val();
    const payAddr = $("#address").val() + " " + $("#addrDetail").val();
    const table_tbody = $("#order_payment_list");
    const table_tr = table_tbody.children();
    const pName = table_tr.eq(0).attr("data_pName");
    const phone = orderData.phone.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
    let orderName = '';

    if(table_tr.length === 1)
        orderName = pName;
    else
        orderName = pName + " 외 " + (table_tr.length - 1) + " 건";

    IMP.request_pay({
        pg: 'danal',
        pay_method: 'card',
        merchant_uid: 'merchant_' + new Date().getTime(),
        name: orderName,
        amount: totalPrice,
        buyer_email: '',
        buyer_name: name,
        buyer_tel: phone,
        buyer_addr: payAddr,
        buyer_postcode: $("#postCode").val(),
    }, function (rsp) {
        if (rsp.success) {
            $.ajax({
                url: requestUrl,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(orderData),
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    if(data === 'SUCCESS'){
                        location.href='/order/card';
                    }else{
                        alert("오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요.");
                    }
                },
                error: function(request, status, error){
                    alert("code : " + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            })
        } else {
            let msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
            alert(msg);
        }
    });
}