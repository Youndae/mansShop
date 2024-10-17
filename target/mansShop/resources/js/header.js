/*const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");*/

$(document).ready(function(){
    $(".top a").on('click', function(){
        const classification = $(this).text();

        if(classification == "BEST"){
            location.href="/";
        }else if(classification == "NEW"){
            location.href="/new";
        }else{
            location.href="/"+classification;
        }
    })

    const searchForm = $("#main-search");

    $("#main-search button").on('click', function() {
        if (!$("#search-keyword").val()) {
            alert("키워드 입력");
        }else{
            searchForm.find("input[name='keyword']").val($("#search-keyword").val());
            searchForm.find("input[name='pageNum']").val("1");
            searchForm.submit();
        }
    });
})

function navBtnOnClick(obj) {
    const menu = obj.innerText;
    if(menu === 'BEST')
        location.href = '/';
    else if(menu === 'NEW')
        location.href = '/new';
    else
        location.href = '/category/' + menu.toLowerCase();
}

function loginBtnOnClick() {
    location.href = '/member/login';
}

function logoutBtnOnClick() {
    const form = $('#logoutForm');
    form.submit();
}

function adminBtnOnClick() {
    location.href='/admin/product';
}

function nonUserOrderBtnOnClick() {
    location.href='/non/check';
}

function cartBtnOnClick() {
    location.href='/cart/';
}

function myPageBtnOnClick() {
    location.href='/my-page/order';
}