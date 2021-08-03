$(document).ready(function(){
    $(".thumbnail").on('click', function(e){
        var pno = $(this).href;

        location.href="/"+pno;
    })

    var actionForm = $("#pageActionForm");

    $(".paginate_button a").on("click", function (e) {
        e.preventDefault();

        var keyword = $("input[name='keyword']").val();


        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.attr('action', '/'+keyword);
        actionForm.submit();
    });
})

