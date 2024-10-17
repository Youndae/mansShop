const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let checkArr = [];


function checkBoxOnChange(obj) {
    console.log('checkbox : ', obj.value);
    console.log('status : ', obj.checked);

    if(obj.checked)
        checkArr.push(obj.value);
    else{
        const delObject = checkArr.find(function(item) {
            return item === obj.value;
        });
        const delIndex = checkArr.indexOf(delObject);
        checkArr.splice(delIndex, 1);
    }
}

function removeCartProduct(obj) {
    console.log('remove id : ', obj.id);
    const arr = [];
    arr.push(obj.id);
    $.ajax({
        url: '/cart/',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(arr),
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            if(data === 'SUCCESS')
                location.reload();
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function cartCountUp(obj) {
    console.log('countUp id : ', obj.id);
    $.ajax({
        url: '/cart/up/' + obj.id,
        type: 'PATCH',
        contentType: 'application/json',
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            if(data === 'SUCCESS')
                location.reload();
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function cartCountDown(obj) {
    console.log('countDown id : ', obj.id);
    $.ajax({
        url: '/cart/down/' + obj.id,
        type: 'PATCH',
        contentType: 'application/json',
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            if(data === 'SUCCESS')
                location.reload();
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function selectOrderCart() {
    $.ajax({
        url: '/order/cart',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(checkArr),
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result === 'SUCCESS')
                location.href = '/order/cart';
            else
                alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function allOrderCart() {
    const allArr = [];
    $(".cart-checkbox").each(function() {
        allArr.push($(this).val());
    });

    $.ajax({
        url: '/order/cart',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(allArr),
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(result){
            if(result === 'SUCCESS')
                location.href = '/order/cart';
            else
                alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function selectDeleteCart() {
    $.ajax({
        url: '/cart/',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(checkArr),
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            if(data === 'SUCCESS')
                location.reload();
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}

function allDeleteCart() {
    $.ajax({
        url: '/cart/all',
        type: 'DELETE',
        contentType: 'application/json',
        beforeSend:function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){
            if(data === 'SUCCESS')
                location.reload();
        },
        error: function(request, status, error){
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    })
}
