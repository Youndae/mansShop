$(document).ready(function(){
    $(".thumbnail").on('click', function(e){
        var pno = $(this).href;

        location.href="/"+pno;
    })
})