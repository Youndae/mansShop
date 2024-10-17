$(function() {
    const paginationForm = $("#pageActionForm");
    const searchForm = $("#searchActionForm");

    $(".pagination li a").on('click', function(e) {
        e.preventDefault();
        paginationForm.find("#page").val($(this).attr('href'));
        paginationForm.submit();
    })

    $(".search-pagination li a").on('click', function(e) {
        e.preventDefault();
        searchForm.find("#page").val($(this).attr('href'));
        searchForm.submit();
    })

})

function paginationSearchBtnOnClick() {
    const searchForm = $("#searchActionForm");
    searchForm.find('#keyword').val($('#search-input').val());
    searchForm.find("#page").val('1');
    searchForm.submit();
}

function paginationTypeSearchBtnOnClick() {
    const searchForm = $("#searchActionForm");
    const selectValue = $('.search-type-select option:selected').val();
    searchForm.find('#keyword').val($('#search-input').val());
    searchForm.find('#searchType').val(selectValue);
    searchForm.find("#page").val('1');

    searchForm.submit();
}