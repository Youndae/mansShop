var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var noArr = new Array();
var nameArr = new Array();
var sizeArr = new Array();
var colorArr = new Array();
var countArr = new Array();
var priceArr = new Array();
var numArr = new Array();

$(document).ready(function(){

    var pno = $("#pno").val();
    (function(){
        $.getJSON("/getProductThumb", {pno:pno}, function(arr){
            var str = "";
            var num = 2;
            console.log("get ProductThumb");
            $(arr).each(function(i, attach){
                var thumbId = "thumb"+num;
                str += "<img id=\""+ thumbId +"\" src=\"/display?image=" + attach.pthumbnail + "\" onclick=\"firstThumb(this)\">";
            })
            $(".thumb").append(str);
        });

        $.getJSON("/getProductOp", {pno:pno}, function(arr){
            var str = "<option value=\"default\">------</option>";

            // 수정사항. 가방같은 경우는 사이즈가 없기 때문에 옵션에서 사이즈를 출력하지 않도록 수정해야함.

            $(arr).each(function(i, op){
                str += "<option value=\"" + op.popNo + "/" + op.pcolor + "/" + op.psize + "\">" +
                    "컬러 : " + op.pcolor + "     사이즈 : " + op.psize + "</option>";
            })

            $("#option_select_box").append(str);
        });

        $.getJSON("/getProductInfo", {pno:pno}, function(arr){
            var str = "<div class=\"productInfo\">";
            $(arr).each(function(i, info){
                str += "<img class=\"infoImg\" src=\"/display?image=" + info.pimg + "\">";
            })

            str += "</div>";

            $(".product_detail_info").append(str);
        })

        showReviewList(1);

        showProductQnAList(1);




    })();


    $("#productDetail").on('click', function(e){
        e.preventDefault();
        document.querySelector('.product_detail_info').scrollIntoView(true);
    });

    $("#productReview").on('click', function(e){
        e.preventDefault();
        document.querySelector('.product_Review_List').scrollIntoView(true);
    });

    $("#productQnA").on('click', function(e){
        e.preventDefault();
        document.querySelector('.product_QnA_List').scrollIntoView(true);
    });

    $("#productOrderInfo").on('click', function(e){
        e.preventDefault();
        document.querySelector('.product_Order_Info').scrollIntoView(true);
    });

    function showProductQnAList(page){
        var QnAUL = $(".product_QnA");

        getProductQnAList({pno:pno, page:page||1}, function(QnACount, QnAList){
            console.log("QnACount : " + QnACount);
            console.log("QnAList : " + QnAList);

            if(QnAList == null || QnAList.length == 0){
                return;
            }

            var str = "";

            for(var i = 0, len = QnAList.length || 0; i < len; i++){
                var QnARegDate = new Date(QnAList[i].pqnARegDate);

                QnAList[i].pqnARegDate = QnARegDate.getFullYear() + "/" +
                    (QnARegDate.getMonth() + 1) + "/" +
                    QnARegDate.getDate();

                if(QnAList[i].pqnAAnswer == "0"){
                    QnAList[i].pqnAAnswer = "미답변";
                }else if(QnAList[i].pqnAAnswer == "1"){
                    QnAList[i].pqnAAnswer = "답변완료";
                }



                if(QnAList[i].pqnAIndent == "0"){
                    str += "<li class=\"QnA_content\">";
                    str += "<div><div class=\"QnA_content_header\"><strong class=\"QnA_writer\">"+QnAList[i].userId+"</strong>";
                    str += "<small class=\"pull-right text-muted\">"+QnAList[i].pqnARegDate+"</small>";
                    str += "<small class=\"pull-right answer\">"+QnAList[i].pqnAAnswer+"</small></div>";
                    str += "<p>"+QnAList[i].pqnAContent+"</p></div></li>";
                }else if(QnAList[i].pqnAIndent == "1"){
                    str += "<div class=\"QnA_Content_Reply\">";
                    str += "<li class=\"QnA_content\">";
                    str += "<div><div class=\"QnA_content_header\"><strong class=\"QnA_writer\">"+QnAList[i].userId+"</strong>";
                    str += "<small class=\"pull-right text-muted\">"+QnAList[i].pqnARegDate+"</small>";
                    str += "<p>"+QnAList[i].pqnAContent+"</p></div></li>";
                    str += "</div>";
                }
            }

            QnAUL.html(str);

            showPaginate(QnACount, "q");
        })
    }

    QnAPaginate.on('click', "li a", function(e){
        e.preventDefault();

        console.log("QnA other page");

        var targetPageNum = $(this).attr("href");
        console.log("targetpageNum : " + targetPageNum);

        qPageNum = targetPageNum;

        showProductQnAList(qPageNum);
    })


    function showReviewList(page){

        var reviewUL = $(".review");

        console.log("show Review List : " + page);

        getReviewList({pno:pno, page:page||1}, function(reviewCount, reviewList){
            console.log("reviewCount : " + reviewCount);
            console.log("reviewList : " + reviewList);



            if(reviewList == null || reviewList.length == 0){
                return;
            }

            var str = "";

            for(var i = 0, len = reviewList.length || 0; i < len; i++){


                var reviewRegDate = new Date(reviewList[i].reviewDate);

                reviewList[i].reviewDate = reviewRegDate.getFullYear() + "/" +
                    (reviewRegDate.getMonth() + 1) + "/" +
                    reviewRegDate.getDate();


                str += "<li class=\"review_content\">";
                str += "<div><div class='review_content_header'><strong class='reviewer'>"+reviewList[i].userId+"</strong>";
                str += "<small class='pull-right text-muted'>"+reviewList[i].reviewDate+"</small></div>";
                str += "<p>"+reviewList[i].reviewContent+"</p></div></li>";
            }

            reviewUL.html(str);

            showPaginate(reviewCount, "r");
        })
    }



    reviewPaginate.on('click', "li a", function(e){
        e.preventDefault();

        console.log("other page");

        var targetPageNum = $(this).attr("href");

        console.log("targetPageNum : " + targetPageNum);

        rPageNum = targetPageNum;

        showReviewList(rPageNum);
    })


    $("#QnAInsert").on('click', function(){


        var content = $("#QnAReplyForm").serialize();

        console.log("pQnAContent : " + $("input[name=pQnAContent]").val());

        if($("input[name=pQnAContent]").val() == "" ||$("input[name=pQnAContent]").val() == null){
            console.log("content val is null");
            $("#QnAContentOverlap").text("문의 내용을 입력하세요");
        }else{
            console.log("content val is not null");
            $.ajax({
                url: "/QnAInsert",
                type: "post",
                data: content,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success : function(data){
                    location.reload();
                },
                error: function(request, status, error){
                    alert("code : " + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            });

        }
    })


    $("#like").on('click', function(){
        var pno = $("input[name=pno]").val();

        $.ajax({
            url: "/likeProduct",
            type: 'post',
            data : {pno: pno},
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                location.reload();
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        });
    })

    $("#deLike").on('click', function(){
        var pno = $("input[name=pno]").val();

        $.ajax({
            url: "/deLikeProduct",
            type: 'post',
            data: {pno: pno},
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                location.reload();
            },
            error: function(request, status, error){
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        });
    })

    $("#anonymous").on('click', function(){
        var result = confirm("관심상품으로 등록하기 위해서는 로그인이 필요합니다.\n 로그인 하시겠습니까?");

        if(result){
            location.href='/member/login';
        }
    })

});//document.ready End

function getProductQnAList(param, callback, error){
    var pno = param.pno;
    var page = param.page || 1;

    console.log("getProductQnAList pno : " + param.pno + " page : " + param.page);

    $.getJSON("/productQnAPages/" + pno + "/" + page + ".json", function(data){
        if(callback)
            callback(data.productQnACount, data.productQnAList);
    }).fail(function(xhr, status, err){
        if(error)
            error(err);
    })
}

function getReviewList(param, callback, error){
    var pno = param.pno;
    var page = param.page || 1;

    console.log("getReviewList pno : " + param.pno + " page : " + param.page);

    $.getJSON("/reviewPages/" + pno + "/" + page + ".json", function(data){
        if(callback)
            callback(data.reviewCount, data.reviewList);
    }).fail(function(xhr, status, err){
        if(error)
            error(err);
    });
}

var rPageNum = 1;
var qPageNum = 1;
var reviewPaginate = $(".reviewPaging");
var QnAPaginate = $(".QnAPaging");

function showPaginate(count, type){

    if(type == "r") {
        console.log("type r");
        var endNum = Math.ceil(rPageNum / 10.0) * 10;
    }else if(type == "q"){
        console.log("type q");
        var endNum = Math.ceil(qPageNum / 10.0) * 10;
    }


    var startNum = endNum - 9;
    var prev = startNum != 1;
    var next = false;

    if(endNum * 10 >= count)
        endNum = Math.ceil(count / 10.0);

    if(endNum * 10 < count)
        next = true;

    var str = "<ul class=\"pagination\">";

    if(prev)
        str += "<li class=\"paginate_button previous\"><a href=\""+(startNum - 1) + "\">Prev</a></li>";

    if(type == "r")
        for(var i = startNum; i <= endNum; i++){
            var active = rPageNum == i ? "active" : "";

            str += "<li class=\"paginate_button "+active+"\"><a href=\""+i+"\">"+i+"</a></li>";
        }
    else if(type == "q")
        for(var i = startNum; i <= endNum; i++){
            var active = qPageNum == i ? "active" : "";

            str += "<li class=\"paginate_button "+active+"\"><a href=\""+i+"\">"+i+"</a></li>";
        }

    if(next)
        str += "<li class=\"paginate_button next\"><a href=\"" + (endNum + 1) + "\">Next</a></li>";

    str += "</ul>";

    if(type == "r")
        reviewPaginate.html(str);
    else if(type == "q")
        QnAPaginate.html(str);
}



function countUp(obj){
    console.log("count Up!");
    var id = obj.attributes['value'].value;
    console.log("id : " + id);
    var price = parseInt($("#pPrice").val());

    console.log("price : "+price);

    var count = parseInt($("input[name="+id+"]").val());
    count = count + 1;

    $("input[name="+id+"]").val(count);

    var opPrice = parseInt($("tr[id="+id+"] td[name=productPrice] span").text().replace(/\D/g,''));

    opPrice = opPrice + price;

    $("tr[id="+id+"] td[name=productPrice] span").text(opPrice.toLocaleString()+" 원");

    if($(".totalPrice span p").text().replace(/\D/g,'') == "0"){
        var total = parseInt($(".totalPrice span p").text());
    }else{
        var total = parseInt($(".totalPrice span p").text().replace(/\D/g,''));
    }

    console.log("total : " + total);
    total = total + price;
    console.log("total + p : " + total);
    $(".totalPrice span p").text(total.toLocaleString() + " 원");


    console.log("append");

}

function countDown(obj){
    console.log("count Down!");
    var id = obj.attributes['value'].value;
    var price = parseInt($("#pPrice").val());

    var count = parseInt($("input[name="+id+"]").val());
    count = count - 1;

    $("input[name="+id+"]").val(count);

    var opPrice = parseInt($("tr[id="+id+"] td[name=productPrice] span").text().replace(/\D/g,''));

    opPrice = opPrice - price;

    $("tr[id="+id+"] td[name=productPrice] span").text(opPrice.toLocaleString()+" 원");


    var total = parseInt($(".totalPrice span p").text().replace(/\D/g,''));


    console.log("total : " + total);
    total = total - price;
    console.log("total - p : " + total);
    $(".totalPrice span p").text(total.toLocaleString() + " 원");




}

function opRemove(obj){
    console.log("option remove");
    var id = obj.attributes['value'].value;
    var opPrice = parseInt($(".tempOrderTableData #" + id + " td[name=productPrice] span").text().replace(/\D/g,''));
    var totalPrice = parseInt($(".totalPrice span p").text().replace(/\D/g,''));

    console.log("remove id : " + id + ", opPrice : " + opPrice);

    totalPrice = totalPrice - opPrice;

    console.log("remove totalPrice : " + totalPrice);
    $(".totalPrice span p").text(totalPrice.toLocaleString() + " 원");



    $(".tempOrderTableData #"+id).remove();

    var pcNum = id.substring(12);

    noArr.splice(pcNum, 1);
    nameArr.splice(pcNum, 1);
    sizeArr.splice(pcNum, 1);
    colorArr.splice(pcNum, 1);
    numArr.splice(pcNum, 1);
}

function firstThumb(obj){
    var imgName = obj.attributes['src'].value.substring(15);

    var str = "<img id=\"firstThumb\" src=\"/display?image=" + imgName + "\">";

    $(".firstThumbnail #firstThumb").remove();

    $(".firstThumbnail").append(str);
}

var num = 0;


$(function(){
    $(".productInfo_Info #option_select_box").change(function(){
        console.log("property change");
        var option_txt = $("#option_select_box option:selected").text();

        var option_val = $("#option_select_box option:selected").val();

        var option = option_val.split('/');

        var p = parseInt($("#pPrice").val());

        console.log(option_val);

        console.log("price : " + p);



        var dataStr = "";

        dataStr += "<tr class=\"product_temp_cart\" id=\"productCount" + num +"\" value=\"" + option[0] +"\">" +
            "<td>" + option_txt + "</td>" +
            "<td>" +
            "<input type=\"text\" name=\"productCount" + num + "\" value=\"1\">" +
            "<button class=\"productCount up\" name=\"up\" value=\"productCount" + num + "\" onclick=\"countUp(this)\"'>up</button>" +
            "<button class=\"productCount down\" name=\"down\" value=\"productCount" + num + "\" onclick=\"countDown(this)\"'>down</button>" +
            "<button class=\"remove_btn\" name=\"opRemove\" value=\"productCount" + num + "\" onclick=\"opRemove(this)\"'>x</button>" +
            "</td>" +
            "<td name=\"productPrice\">" + "<span>" + p.toLocaleString() + " 원<span>" + "</td>" +
            "<input type=\"hidden\" name=\"size\" value=\""+option[1] + "\">" +
            "<input type=\"hidden\" name=\"color\" value=\""+option[2] + "\">" +
            "</tr>";

        noArr.push(option[0]);
        nameArr.push($(".name").text());
        sizeArr.push(option[1]);
        colorArr.push(option[2]);
        numArr.push(num);

        num++;






        if($(".totalPrice span p").text().replace(/\D/g,"") == "0"){
            var total = parseInt($(".totalPrice span p").text());
        }else{
            var total = parseInt($(".totalPrice span p").text().replace(/\D/g,''));
        }
        console.log("append total");
        total = total + p;
        $(".totalPrice span p").text(total.toLocaleString() + " 원");

        $(".tempOrderTableData").append(dataStr);

        console.log("append");

        /*
            옵션이 변경 되었을때는 테이블에 td만 추가해주면 됨.

            고민해야할점은 count가 변동이 생겼을때.

            count가 변동되었을때는
        */
    })

    /*
            장바구니 혹은 바로구매를 눌렀을 때
            num값을 이용해 반복문을 돌린다.
            for(int i = 1; i <= num; i++){
                var a = "productCount"+i;
            }
            이런 형태로 tr의 id값을 구하고
            해당 id값의 value인 pOpNo와
            해당 tr아래에 있는 input의 value를 가져와서
            pOpNo와 count값을 배열에 넣어주는데
            pOpNoArr = {};
            pCount = {};
            이렇게 각각 넣어주고 컨트롤러에서 List로 받아 처리하는 방법으로 구현할것.
        */

    $(function(){
        $("#cart").on('click', function(){
            /*var formdata = new FormData();*/
            /*var pOpNo = new Array();*/
            /*var pCount = new Array();
            var pPrice = new Array();

            console.log("Num : " + num);*/

            /*for(var i = 1; i < num; i++){
                var tVal = "productCount" + i;

                var count = $("input[name="+tVal+"]").val();
                var opNo = $("tr[id="+tVal+"]").attr("value");
                var price = $("tr[id="+tVal+"] td[name=productPrice] span").text().replace(/\D/g, '');

                console.log("opNo : " + opNo);
                console.log("count : " + count);
                console.log("price : " + price);


                /!*pOpNo[pOpNoNum] = opNo;
                pCount[pCountNum] = count;

                pOpNoNum++;
                pCountNum++;*!/

                /!*pOpNo.push(opNo);*!/
                pCount.push(count);
                pPrice.push(price);
            }*/

            for(var i = 0; i < numArr.length; i++){
                var n = numArr[i];
                var tVal = "productCount"+n;
                countArr.push($("input[name="+tVal+"]").val());
                priceArr.push($("tr[id="+tVal+"] td[name=productPrice] span").text().replace(/\D/g, ''));
            }

            /*for(var idx = 0; idx < Object.keys(pOpNo).length; idx++){
                formData.append('pOpNo', pOpNo[idx]);
            }

            for(var idx = 0; idx < Object.keys(pCount).length; idx++){
                formData.append('pOpNo', pOpNo[idx]);
            }*/

            /*var a = pOpNo.length;
            console.log("pOpNo length : " + a);

            console.log("pOpNo : " + pOpNo);*/

            $.ajaxSettings.traditional = true;
            $.ajax({
                url: '/addCart',
                type: 'post',
                data: {pOpNo: noArr, pCount: countArr, pPrice: priceArr},
                beforeSend:function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    alert("장바구니에 상품이 담겼습니다.");
                },
                error: function(request, status, error){
                    alert("code : " + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            });
        })
    })

    $(function(){
        $("#buy").on('click', function(){
          /*  var noArr = new Array();
            var nameArr = new Array();
            var sizeArr = new Array();
            var colorArr = new Array();
            var countArr = new Array();
            var priceArr = new Array();

            for(var i = 1; i < num; i++){
                var tVal = "productCount" + i;
                console.log("tVal : " + tVal);
                noArr.push($("tr[id="+tVal+"]").attr("value"));
                console.log("noArr : " + $("tr[id="+tVal+"]").attr("value"));
                nameArr.push($(".name").text());
                sizeArr.push($("tr[id="+tVal+"] input[name=size]").val());
                colorArr.push($("tr[id="+tVal+"] input[name=color]").val());
                countArr.push($("input[name="+tVal+"]").val());
                priceArr.push($("tr[id="+tVal+"] td[name=productPrice] span").text().replace(/\D/g, ''));
            }*/

            for(var i = 0; i < numArr.length; i++){
                var n = numArr[i];
                var tVal = "productCount"+n;
                countArr.push($("input[name="+tVal+"]").val());
                priceArr.push($("tr[id="+tVal+"] td[name=productPrice] span").text().replace(/\D/g, ''));
            }

            $("input[name=pOpNo]").val(noArr);
            $("input[name=pName]").val(nameArr);
            $("input[name=pSize]").val(sizeArr);
            $("input[name=pColor]").val(colorArr);
            $("input[name=cCount]").val(countArr);
            $("input[name=cPrice]").val(priceArr);


            $("#order_form").attr("action", "/order/orderPayment");
            $("#order_form").submit();
        })
    })



})