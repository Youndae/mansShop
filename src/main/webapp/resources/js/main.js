$(document).ready(function(){
    $(".thumbnail").on('click', function(e){
        const pno = $(this).href;

        location.href="/"+pno;
    })

    const actionForm = $("#pageActionForm");

    $(".paginate_button a").on("click", function (e) {
        e.preventDefault();

        const keyword = $("input[name='keyword']").val();

        if(keyword == 'OUTER' || keyword == 'TOP' || keyword == 'PANTS' || keyword == 'SHOES' || keyword == 'BAGS'){
            actionForm.attr('action', '/'+keyword);
        }else{
            actionForm.attr('action', '/search');
        }

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    });
})

