$(document).ready(function(){
    const recipient = $("#recipient").val();
    const orderPhone = $("#orderPhone").val();

    $.getJSON("/non/order/data", {recipient: recipient, orderPhone: orderPhone}, function(arr) {
        let str = jQuery.orderListStr(arr, 'Anonymous');
        $(".memberOrderList").append(str);
    })
});

