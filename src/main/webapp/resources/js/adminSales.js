$(document).ready(function(){

    var side_li = "<ul class=\"sales_category\"><li><a href=\"/admin/salesProductList\">상품별매출</a></li><li><a href=\"/admin/salesTermList\">기간별매출</a></li></ul>"

    $(".side_nav ul").append(side_li);

    $("#salesRate").on('click', function(e){
        if($(this).val() == 1)
            $('input[name=sortType]').val("2");
        else
            $('input[name=sortType]').val("1");

        actionForm.find("input[name='pageNum']").val("1");
        actionForm.submit();
    });

    $("#sales").on('click', function(e){
        if($(this).val() == 3)
            $('input[name=sortType]').val("4");
        else
            $('input[name=sortType]').val("3");

        actionForm.find("input[name='pageNum']").val("1");
        actionForm.submit();
    })

    $.getJSON("/admin/salesTermSelect", function(arr){

        var str = "";
        var optionYear = "";
        $(arr).each(function(i, termYear){
            var year = termYear.salesTerm.substring(0, 4);

            if(optionYear != year){
                optionYear = year;
                str += "<option value=\"" + optionYear +"\">" +
                    optionYear + "</option>";
            }
        });

        $("#select_Term_Year").append(str);
    })

    var actionForm = $("#pageActionForm");
    var userSearchForm = $("#searchActionForm");

    $("#shipping").on('click', function(){
        var orderSearchForm = $("#userOrderSearch");
        var userId = $(".modal-body span[name=userId]").text();


        console.log("userId : " + userId);
        orderSearchForm.find("input[name='keyword']").val(userId);
        orderSearchForm.find("input[name='pageNum']").val("1");



        orderSearchForm.submit();
    });

    $("#searchActionForm button").on('click', function(e){
        if(!userSearchForm.find("input[name='keyword']").val()){
            alert('키워드 입력');
        }

        userSearchForm.find("input[name='pageNum']").val("1");
        e.preventDefault();

        userSearchForm.submit();
    });

    $("#tbl_userList a").on('click', function(e){
        e.preventDefault();

        var userId = $(this).text();
        var formatPhone = "";
        getUserInfo(userId, function(info){
            $(".modal-body span[name=userId]").text(info.userId);
            $(".modal-body span[name=userName]").text(info.userName);

            $(".modal-body span[name=userEmail]").text(info.userEmail);

            var date = new Date(info.joinRegDate);

            info.joinRegDate = date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate();

            $(".modal-body span[name=joinRegDate]").text(info.joinRegDate);

            var birth = new Date(info.userBirth);

            info.userBirth = birth.getFullYear() + "/" + (birth.getMonth()+1) + "/" + birth.getDate();

            $(".modal-body span[name=userBirth]").text(info.userBirth);

            if(info.userPhone.length == 11){
                formatPhone = info.userPhone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }else if(info.userPhone.length == 10){
                formatPhone = info.userPhone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
            }

            $(".modal-body span[name=userPhone]").text(formatPhone);
        });

        $(".modal").show();

    });

    $(".modalClose").on('click', function(){
        $(".modal").hide();
    });

});

function getUserInfo(userId, callback, error){
    console.log("user Info get");

    $.get("/admin/userInfo/" + userId + ".json", function(result){
        if(callback)
            callback(result);
    }).fail(function(xhr, status, er){
        if(error)
            error(er);
    });
};