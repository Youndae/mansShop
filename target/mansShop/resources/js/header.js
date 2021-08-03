$(document).ready(function(){
    $(".top a").on('click', function(){
        console.log("nav top a : " + $(this).text());

        var classification = $(this).text();

        if(classification == "BEST"){
            location.href="/";
        }else if(classification == "NEW"){
            location.href="/new";
        }else{
            location.href="/"+classification;
        }
    })

    var searchForm = $("#mainSearchForm");

    $("#mainSearchForm button").on('click', function() {
        console.log("hi");

        console.log("amount : " + $("input[name='amount']").val());
        if (!searchForm.find("input[name='keyword']").val()) {
            alert("키워드 입력");
        }

        searchForm.find("input[name='pageNum']").val("1");
        searchForm.find("input[name='amount']").val("10");
        e.preventDefault();

        searchForm.submit();
    });
})