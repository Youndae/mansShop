$(document).ready(function(){
    var recipient = $("#recipient").val();
    var orderPhone = $("#orderPhone").val();

    $.getJSON("/getNonOrderList", {recipient: recipient, orderPhone: orderPhone}, function(arr) {
        let str = jQuery.orderListStr(arr, 'Anonymous');
        $(".memberOrderList").append(str);
    })
});

