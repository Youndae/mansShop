$(document).ready(function(){

    const side_li = "<ul class=\"sales_category\">" +
                        "<li><a href=\"/admin/sales/product\">상품별매출</a></li>" +
                        "<li><a href=\"/admin/sales/term\">기간별매출</a></li>" +
                    "</ul>"
    const actionForm = $("#pageActionForm");

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

    $.getJSON("/admin/sales/term/year", function(arr){

        let str = "";
        let optionYear = "";
        $(arr).each(function(i, termYear){
            const year = termYear.salesTerm.substring(0, 4);

            if(optionYear != year){
                optionYear = year;
                str += "<option value=\"" + optionYear +"\">" +
                    optionYear + "</option>";
            }
        });

        $("#select_Term_Year").append(str);
    })
});
