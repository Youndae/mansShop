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
        if (!$("#search_keyword").val()) {
            alert("키워드 입력");
        }else{

            searchForm.find("input[name='keyword']").val($("#search_keyword").val());
            searchForm.find("input[name='pageNum']").val("1");
            searchForm.find("input[name='amount']").val("12");

            searchForm.submit();
        }


    });
})