$(document).ready(function(){
    var actionForm = $("#pageActionForm");
    var searchForm = $("#searchActionForm");

    $(".paginate_button a").on("click", function (e) {
        e.preventDefault();

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    });

    $("#searchActionForm button").on('click', function (e) {
        if (!searchForm.find("input[name='keyword']").val()) {
            alert("키워드 입력");
        }

        searchForm.find("input[name='pageNum']").val("1");
        e.preventDefault();

        searchForm.submit();
    });

    $(".productList_classification a").on('click', function (e) {
        e.preventDefault();

        actionForm.find("input[name='classification']").val($(this).attr("href"));
        actionForm.find("input[name='pageNum']").val("1");
        actionForm.submit();
    });

    //selectTermList
    $("#select_Term_Year").on("propertychange change keyup paste input", function(){

        actionForm.find("input[name='pageNum']").val("1");
        actionForm.find("input[name='keyword']").val($("#select_Term_Year").val());
        actionForm.find("input[name='amount']").val("12");

        actionForm.submit();
    })

})