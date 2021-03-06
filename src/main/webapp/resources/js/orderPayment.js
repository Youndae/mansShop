var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ready(function(){
    $("#orderPay").on('click', function(){
        var type = $("input[name=select_payment]:checked").val();

        $("input[name=orderPrice]").val($("span.total_price").text().replace(/\D/g, ''));

        if(type == 'card'){
            $("input[name=orderPayment]").val("C");
            order();
        }else if(type == 'cash'){
            console.log("cash pay");
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
                formData.append('pno', table_tr.eq(i).attr("data_pno"));
                formData.append('cdNo', table_tr.eq(i).attr("data_cdNo"));
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

                if(data.bname !== '' && /[???|???|???]$/g.test(data.bname)){
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
        formData.append('pno', table_tr.eq(i).attr("data_pno"));
        formData.append('cdNo', table_tr.eq(i).attr("data_cdNo"));
    }

    var pName = table_tr.eq(0).attr("data_pName");

    if(table_tr.length == 1){
        var orderName = pName;
    }else{
        var orderName = pName + " ??? " + (table_tr.length - 1) + " ???";
    }


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
        /*m_redirect_url : '/order/orderComplete'
        * ????????? ???????????? ?????? ????????? ????????? url
        * ???????????? ????????? ajax??? ???????????? ???????????? ????????? ????????? ?????????
        * ????????? ???????????? ??????.*/
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
                    var oType = "C";
                    location.href='orderComplete/'+oType;
                },
                error: function(request, status, error){
                    alert("code : " + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            });
        } else {
            var msg = '????????? ?????????????????????.';
            msg += '???????????? : ' + rsp.error_msg;
            alert(msg);
        }
    });
}