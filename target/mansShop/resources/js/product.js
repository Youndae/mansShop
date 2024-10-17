const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let optionIdArr = [];
let noArr = [];
let nameArr = [];
let sizeArr = [];
let colorArr = [];
let countArr = [];
let numArr = [];
let rPageNum = 1;
let qPageNum = 1;

$(document).ready(function () {
    function showProductQnAList(page) {
        const path = window.location.pathname;
        const productId = path.split('/')[2];
        let qnaElements = $(".product-detail-qna-content");

        getProductQnAList({productId: productId, page: page || 1}, function (pageMaker, content) {
            if (content == null || content.length === 0)
                return;

            let str = "<ul>";
            for (let i = 0, len = content.length || 0; i < len; i++) {
                str += "<li class=\"qna-content-default\">" +
                            "<div class=\"qna-content-header\">" +
                                "<strong class=\"qna-writer\">" +
                                    content[i].writer +
                                "</strong>" +
                                "<small class=\"pull-right text-muted\">" +
                                    parseDate(content[i].createdAt) +
                                "</small>";

                if(content[i].status)
                    str += "<small class=\"pull-right answer\">답변완료</small>";

                str += "</div>" +
                        "<div class=\"qna-content-content\">" +
                            content[i].qnaContent +
                        "</div>" +
                    "</li>";

                if(content[i].status) {
                    const replyList = content[i].replyList;
                    for(let j = 0; j < replyList.length; j++) {
                        str += "<div class=\"qna-content-reply\">" +
                                    "<li class=\"qna-content\">" +
                                        "<div>" +
                                            "<div class=\"qna-content-header\">" +
                                                "<strong class=\"qna-writer\">" +
                                                    replyList[j].writer +
                                                "</strong>" +
                                                "<small class=\"pull-right text-muted\">" +
                                                    parseDate(replyList[j].createdAt) +
                                                "</small>" +
                                            "</div>" +
                                            "<div class=\"qna-content-content\">" +
                                                "<p>" +
                                                    replyList[j].content +
                                                "</p>" +
                                            "</div>" +
                                        "</div>" +
                                    "</li>" +
                                "</div>"
                    }
                }
            }
            str += "</ul>";

            qnaElements.html(str);
            showPaginate(pageMaker, "q");
        })
    }

    $(".product-detail-qna-paging .paging ").on('click', "li a", function (e) {
        e.preventDefault();
        const targetPageNum = $(this).attr("href");
        qPageNum = targetPageNum;

        showProductQnAList(qPageNum);
    })


    function showReviewList(page) {
        let reviewElements = $(".product-detail-review-content");
        const path = window.location.pathname;
        const productId = path.split('/')[2];


        getReviewList({productId: productId, page: page || 1}, function (pageMaker, content) {
            if (content == null || content.length === 0)
                return;
            let str = "<ul>";

            for(let i = 0; i < content.length; i++) {
                str += "<li class=\"review-content-default\">" +
                            "<div class=\"review-content-header\">" +
                                "<strong class=\"reviewer\">" +
                                    content[i].reviewer +
                                "</strong>" +
                                "<small class=\"pull-right text-muted\">" +
                                    parseDate(content[i].createdAt) +
                                "</small>" +
                            "</div>" +
                            "<div class=\"review-content-content\">" +
                                "<p>" +
                                    content[i].reviewContent +
                                "</p>" +
                            "</div>" +
                        "</li>";

                if(content[i].answerContent != null) {
                    str += "<div class=\"review-content-reply\">" +
                                "<li class=\"review-content\">" +
                                    "<div class=\"review-content-header\">" +
                                        "<strong class=\"reviewer\">관리자</strong>" +
                                        "<small class=\"pull-right text-muted\">" +
                                            parseDate(content[i].answerCreatedAt) +
                                        "</small>" +
                                    "</div>" +
                                    "<div class=\"review-content-content\">" +
                                        "<p>" +
                                            content[i].answerContent +
                                        "</p>" +
                                    "</div>" +
                                "</li>" +
                            "</div>";
                }
            }

            str += "</ul>";

            reviewElements.html(str);
            showPaginate(pageMaker, "r");
        })
    }


    $(".product-detail-review-paging .paging").on('click', "li a", function (e) {
        e.preventDefault();
        console.log('review paging');
        const targetPageNum = $(this).attr("href");
        console.log('review targetNumber : ', targetPageNum);

        rPageNum = targetPageNum;
        showReviewList(rPageNum);
    })
});

function qnaBtnOnClick() {
    const path = window.location.pathname;
    const productId = path.split('/')[2];
    const content = $("#qna-text-input").val();

    const postData = {
        productId: productId,
        content: content,
    }

    $.ajax({
        url: "/product/qna",
        type: "post",
        contentType: 'application/json',
        data: JSON.stringify(postData),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log('result data : ', data);
            if(data === 'SUCCESS')
                location.reload();
            else
                alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    });
}

function directBuy() {

    if(numArr.length === 0){
        alert("상품 옵션을 선택해주세요.");
    }else {
        for (let i = 0; i < numArr.length; i++) {
            const n = numArr[i];
            const tVal = "productCount" + n;
            countArr.push($("input[name=" + tVal + "]").val());
        }
        console.log('direct');
        $.ajax({
            url: '/order/product',
            type: 'POST',
            data: JSON.stringify({optionNoList: noArr, countList: countArr}),
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                console.log('order success : ', result);
                if(result === 'SUCCESS')
                    location.href = '/order/product';
                else
                    alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
            },
            error: function (request, status, error) {
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        });
    }

}

function parseDate(date) {
    return date.year + "-" +
            padToTwoDigits(date.monthValue) + "-" +
            padToTwoDigits(date.dayOfMonth);
}

function padToTwoDigits(number) {
    return number.toString().padStart(2, '0');
}

function detailBtnOnClick(obj) {
    const val = obj.value;

    let offset = '';

    if(val === 'detail')
        offset = $('.product-detail-info').offset();
    else if(val === 'review')
        offset = $('.product-detail-review').offset();
    else if(val === 'qna')
        offset = $('.product-detail-qna').offset();
    else
        offset = $('.product-detail-order-info').offset();

    moveScroll(offset);
}

function moveScroll(offset){
    $('html, body').animate({scrollTop: offset.top - 300}, 300);
}

function deLike() {
    const path = window.location.pathname;
    const productId = path.split('/')[1];

    $.ajax({
        url: "/product/like/" + productId,
        type: 'delete',
        contentType: 'application/json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
            else
                alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    });
}

function like() {
    const path = window.location.pathname;
    const productId = path.split('/')[1];

    $.ajax({
        url: "/product/like/" + productId,
        type: 'post',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data === 'SUCCESS')
                location.reload();
            else if(data === 'ACCESS DENIED')
                if(confirm('관심상품은 회원만 이용가능합니다.\n로그인하시겠습니까?'))
                    location.href='/member/login';
            else
                alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
        },
        error: function (request, status, error) {
            alert("code : " + request.status + "\n"
                + "message : " + request.responseText
                + "\n" + "error : " + error);
        }
    });
}

function getProductQnAList(param, callback, error) {
    const productId = param.productId;
    const page = param.page || 1;

    $.getJSON("/product/qna/" + productId + "/" + page, function (data) {
        if (callback){
            callback(data.pageMaker, data.content);
        }
    }).fail(function (xhr, status, err) {
        if (error)
            error(err);
    })
}

function getReviewList(param, callback, error) {
    const productId = param.productId;
    const page = param.page || 1;

    $.getJSON("/product/review/" + productId + "/" + page, function (data) {
        if (callback)
            callback(data.pageMaker, data.content);
    }).fail(function (xhr, status, err) {
        if (error)
            error(err);
    });
}

function showPaginate(pageMaker, type) {

    let endNum = pageMaker.endPage;
    let startNum = pageMaker.startPage;
    let prev = pageMaker.prev;
    let next = pageMaker.next;
    let str = "<ul class=\"pagination\">";

    if (prev)
        str += "<li class=\"paginate_button previous\"><a href=\"" + (startNum - 1) + "\">Prev</a></li>";

    if (type == "r")
        for (let i = startNum; i <= endNum; i++) {
            let active = rPageNum == i ? "active" : "";
            str += "<li class=\"paginate_button " + active + "\"><a href=\"" + i + "\">" + i + "</a></li>";
        }
    else if (type == "q")
        for (let i = startNum; i <= endNum; i++) {
            let active = qPageNum == i ? "active" : "";
            str += "<li class=\"paginate_button " + active + "\"><a href=\"" + i + "\">" + i + "</a></li>";
        }

    if (next)
        str += "<li class=\"paginate_button next\"><a href=\"" + (endNum + 1) + "\">Next</a></li>";

    str += "</ul>";

    if (type == "r")
        $(".product-detail-review-paging .paging").html(str);
    else if (type == "q")
        $(".product-detail-qna-paging .paging").html(str);
}


function countUp(obj) {
    const id = obj.attributes['value'].value;
    let price = parseInt($(".price").text().replace(/\D/g, ""));
    let total = 0;
    let count = parseInt($("input[name=" + id + "]").val());
    count = count + 1;

    $("input[name=" + id + "]").val(count);

    let opPrice = parseInt($("tr[id=" + id + "] td[name=product-price] span").text().replace(/\D/g, ''));

    opPrice = opPrice + price;

    $("tr[id=" + id + "] td[name=product-price] span").text(opPrice.toLocaleString() + " 원");

    total = parseInt($(".total-price p span").text().replace(/\D/g, ''));


    total = total + price;
    $(".total-price p span").text(total.toLocaleString() + " 원");
}

function countDown(obj) {
    const id = obj.attributes['value'].value;
    let price = parseInt($(".price").text().replace(/\D/g, ""));

    let count = parseInt($("input[name=" + id + "]").val());

    if(count != 1){
        count = count - 1;

        $("input[name=" + id + "]").val(count);

        let opPrice = parseInt($("tr[id=" + id + "] td[name=product-price] span").text().replace(/\D/g, ''));

        opPrice = opPrice - price;

        $("tr[id=" + id + "] td[name=product-price] span").text(opPrice.toLocaleString() + " 원");

        let total = parseInt($(".total-price p span").text().replace(/\D/g, ''));

        total = total - price;
        $(".total-price p span").text(total.toLocaleString() + " 원");
    }


}

function opRemove(obj) {
    const id = obj.attributes['value'].value;
    let opPrice = parseInt($(".temp-order-table-body #" + id + " td[name=product-price] span").text().replace(/\D/g, ''));
    let totalPrice = parseInt($(".total-price p span").text().replace(/\D/g, ''));

    totalPrice = totalPrice - opPrice;

    $(".total-price p span").text(totalPrice.toLocaleString() + " 원");

    $(".temp-order-table-body #" + id).remove();

    const pcNum = id.substring(12);

    noArr.splice(pcNum, 1);
    nameArr.splice(pcNum, 1);
    sizeArr.splice(pcNum, 1);
    colorArr.splice(pcNum, 1);
    numArr.splice(pcNum, 1);
}

function thumbnailMouseOver(obj) {
    const imgName = obj.attributes['src'].value.substring(15);

    const str = "<img id=\"firstThumb\" src=\"/display?image=" + imgName + "\">";

    $(".first-thumbnail #firstThumb").remove();

    $(".first-thumbnail").append(str);
}

function addCart() {
    if(numArr.length === 0){
        alert("상품 옵션을 선택해주세요.");
    }else{
        for (let i = 0; i < numArr.length; i++) {
            const n = numArr[i];
            const tVal = "productCount" + n;
            countArr.push($("input[name=" + tVal + "]").val());
        }

        $.ajaxSettings.traditional = true;
        $.ajax({
            url: '/product/cart',
            type: 'post',
            data: JSON.stringify({optionNoList: noArr, countList: countArr}),
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if(data === 'SUCCESS')
                    alert("장바구니에 상품이 담겼습니다.");
                else
                    alert("오류가 발생했습니다.\n 잠시후 다시 시도해주세요.");
            },
            error: function (request, status, error) {
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        });
    }
}

$(function () {
    let num = 0;
    let total = 0;

    $("#product-detail-option-select-box").change(function () {
        const option_txt = $("#product-detail-option-select-box option:selected").text();
        const option_val = $("#product-detail-option-select-box option:selected").val().split('/');
        const optionId = option_val[0];
        let price = parseInt($(".price").text().replace(/\D/g, ""));

        let contains = true;

        for(let i = 0; i < noArr.length; i++){
            if(noArr[i] === optionId){
                contains = false;
                break;
            }
        }

        if(contains) {
            let dataStr = "<tr class=\"product-temp-cart\" id=\"productCount" + num + "\" value=\"" + option_val[0] + "\">" +
                            "<td>" + option_txt + "</td>" +
                            "<td class=\"product-temp-cart-input\">" +
                                "<input type=\"text\" name=\"productCount" + num + "\" value=\"1\" readonly>" +
                                "<div class=\"product-temp-count\">" +
                                    "<div class=\"count-up-down\">" +
                                        "<button class=\"productCount up\" name=\"up\" value=\"productCount" + num + "\" onclick=\"countUp(this)\"'>" +
                                            "<img src=\"/display?image=up.jpg\">" +
                                        "</button>" +
                                        "<button class=\"productCount down\" name=\"down\" value=\"productCount" + num + "\" onclick=\"countDown(this)\"'>" +
                                            "<img src=\"/display?image=down.jpg\">" +
                                        "</button>" +
                                    "</div>" +
                                    "<div class=\"count-remove\">" +
                                        "<button class=\"remove-btn\" name=\"opRemove\" value=\"productCount" + num + "\" onclick=\"opRemove(this)\"'>" +
                                            "<img src=\"/display?image=del.jpg\">" +
                                        "</button>" +
                                    "</div>" +
                                "</div>" +
                            "</td>" +
                            "<td name=\"product-price\">" + "<span>" + price.toLocaleString() + " 원<span>" + "</td>" +
                            "<input type=\"hidden\" name=\"color\" value=\"" + option_val[1] + "\">" +
                            "<input type=\"hidden\" name=\"size\" value=\"" + option_val[2] + "\">" +
                    "</tr>";

            noArr.push(option_val[0]);
            nameArr.push($(".name").text());
            colorArr.push(option_val[1] == undefined ? 'nonColor' : option_val[1]);
            sizeArr.push(option_val[2] == undefined ? 'nonSize' : option_val[2]);
            numArr.push(num);
            num++;

            total = parseInt($(".total-price p span").text().replace(/\D/g, ''));
            total = total + price;

            $(".total-price p span").text(total.toLocaleString() + " 원");
            $(".temp-order-table-body").append(dataStr);
        }
    })

})