const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let pOpNoArr = new Array();
let cdNoArr = new Array();

$(document).ready(function(){
    //orderComplete
    $("#orderList").on('click', function(){
       location.href='/myPage/memberOrderList';
    });

    $("#orderInfoCheck").on('click', function(){
        $("#orderInfoCheckForm").attr("action", "/nonOrderList");
        $("#orderInfoCheckForm").submit();
    })

    //cart
    $("#select_order").on('click', function(){
        let noArr = new Array();
        let nameArr = new Array();
        let sizeArr = new Array();
        let colorArr = new Array();
        let countArr = new Array();
        let priceArr = new Array();
        let pnoArr = new Array();
        let cdArr = new Array();

        $("input[name=check]:checked").each(function(){
            noArr.push($(this).attr("value"));
            nameArr.push($(this).attr("data_pName"));
            sizeArr.push($(this).attr("data_pSize"));
            colorArr.push($(this).attr("data_pColor"));
            countArr.push($(this).attr("data_cCount"));
            priceArr.push($(this).attr("data_cPrice"));
            pnoArr.push($(this).attr("data_pno"));
            cdArr.push($(this).attr("data_cdNo"))
        });

        $("input[name=pOpNo]").val(noArr);
        $("input[name=pName]").val(nameArr);
        $("input[name=pSize]").val(sizeArr);
        $("input[name=pColor]").val(colorArr);
        $("input[name=cCount]").val(countArr);
        $("input[name=cPrice]").val(priceArr);
        $("input[name=pno]").val(pnoArr);
        $("input[name=cdNo]").val(cdArr);

        $("#order_form").attr("action", "/order/orderPayment");
        $("#order_form").submit();
    })

    $("button[name=up]").on('click', function(){
        const cdNo = $(this).parent('div').siblings("input").val();
        const count = $(this).parent('div').siblings("span").text();
        let pPrice = $(this).parent('div').parent('td').next().text().replace(/\D/g, '');
        pPrice = parseInt(pPrice) / parseInt(count);
        const countType = "up";

        $.ajaxSettings.traditional = true;
        $.ajax({
            url: '/myPage/cartCount',
            type: 'post',
            data: {cdNo: cdNo, cPrice: pPrice, countType: countType},
            beforeSend:function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1){
                    location.reload();
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

    });

    $("button[name=down]").on('click', function(){

        const cdNo = $(this).parent('div').siblings("input").val();
        const count = $(this).parent('div').siblings("span").text();
        let pPrice = $(this).parent('div').parent('td').next().text().replace(/\D/g, '');
        pPrice = parseInt(pPrice) / parseInt(count);
        const countType = "down";

        if(count == "1"){
            alert("최소 개수입니다.");
        }else{
            $.ajaxSettings.traditional = true;
            $.ajax({
                url: '/myPage/cartCount',
                type: 'post',
                data: {cdNo: cdNo, cPrice: pPrice, countType: countType},
                beforeSend:function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    if(data == 1)
                        location.reload();

                    if(data == 0)
                        alert("오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요.");
                },
                error: function(request, status, error){
                    alert("code : " + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            })
        }
    });

    $("#select_delete").on('click', function(){
        $("input[name=check]:checked").each(function(){
            cdNoArr.push($(this).attr("data_cdNo"));
        });

        $.ajaxSettings.traditional = true;
        $.ajax({
            url : '/myPage/deleteCart',
            type: 'delete',
            data: JSON.stringify(cdNoArr),
            contentType: 'application/json',
            beforeSend:function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1){
                    location.reload();
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
    });

    //memberOrderList
    const date = new Date();
    let month = 0;

    date.setMonth(date.getMonth() - 3);

    if((date.getMonth() + 1) >= 10)
        month = date.getMonth() + 1;
    else
        month = '0' + (date.getMonth() + 1);

    const regDate = date.getFullYear() + "/" + month + "/01";

    getList(regDate);
});


$(function(){
    $("#select_orderList_term").change(function(){
        const val = $("#select_orderList_term option:selected").val();
        let date;

        if(val == "all"){
            const regDate = new Date(1990,1,1);
            date = regDate.getFullYear() + "/" + (regDate.getMonth() + 1) + "/" + regDate.getDate();
        }else{
            let regDate = new Date();
            regDate.setMonth(regDate.getMonth() - val);
            date = regDate.getFullYear() + "/" + (regDate.getMonth() + 1) + "/01";
        }

        getList(date);
    })

})

function getList(regDate){
    $(".memberOrderList div").remove();
    $.getJSON("/myPage/selectOrderList", {regDate: regDate}, function(arr){
        let str = jQuery.orderListStr(arr, 'user');
        $(".memberOrderList").append(str);
    });
};

function insertReview(obj){
    const val = obj.attributes['value'].value;
    const no = val.split("/");

    location.href="/myPage/orderReview/"+no[0] + "/" + no[1];
};


//orderReview
$(function(){
    $("#reviewInsert").on('click',function(){
        $("#reviewInsertForm").attr("action", "/myPage/orderReview");
        $("#reviewInsertForm").submit();
    })
})