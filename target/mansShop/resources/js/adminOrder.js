$(document).ready(function(){
    var actionForm = $("#orderActionForm");
    var ordersearchForm = $("#orderSearchForm");

    $(".paginate_button a").on('click', function(e){
        e.preventDefault();

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    })

    $("#orderSearchForm button").on('click', function(e){
        if(!ordersearchForm.find("input[name='keyword']").val()){
            alert('키워드 입력');
        }

        ordersearchForm.find("input[name='pageNum']").val("1");
        e.preventDefault();

        ordersearchForm.submit();
    })

    $("#modalShow").on('click', function(e){
        e.preventDefault();
        alert("hi!");
    })

})