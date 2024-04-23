$(document).ready(function(){
    const userSearchForm = $("#searchActionForm");

    $("#shipping").on('click', function(){
        const orderSearchForm = $("#userOrderSearch");
        const userId = $(".modal-body span[name=userId]").text();

        orderSearchForm.find("input[name='keyword']").val(userId);
        orderSearchForm.find("input[name='pageNum']").val("1");

        orderSearchForm.submit();
    });

    $("#searchActionForm button").on('click', function(e){
        if(!userSearchForm.find("input[name='keyword']").val())
            alert('키워드 입력');

        userSearchForm.find("input[name='pageNum']").val("1");
        e.preventDefault();

        userSearchForm.submit();
    });

    $("#tbl_userList a").on('click', function(e){
        e.preventDefault();

        const userId = $(this).text();
        let formatPhone = "";
        getUserInfo(userId, function(info){
            $(".modal-body span[name=userId]").text(info.userId);
            $(".modal-body span[name=userName]").text(info.userName);

            $(".modal-body span[name=userEmail]").text(info.userEmail);

            const date = new Date(info.joinRegDate);

            info.joinRegDate = date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate();

            $(".modal-body span[name=joinRegDate]").text(info.joinRegDate);

            const birth = new Date(info.userBirth);

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
    $.get("/admin/user/" + userId + ".json", function(result){
        if(callback)
            callback(result);
    }).fail(function(xhr, status, er){
        if(error)
            error(er);
    });
};