<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<style>
    #firstThumb{
        width: 300px;
    }
    .thumb>img{
        width: 60px;
    }

    .QnA_content_reply{
        text-align: left;
        margin-left: 24px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div>
    <h1>Product Detail Page</h1>

    <div class="thumbnail">
        <div class="firstThumbnail">
            <img id="firstThumb" src="/display?image=${pDetail.firstThumbnail}">
        </div>
        <div class="thumb">
            <img id="thumb1" src="/display?image=${pDetail.firstThumbnail}" onclick="firstThumb(this)">
        </div>
    </div>

    <div class="productInfo">
        <div class="productInfo_Info">
            <label>상품명</label>
            <span class="name">${pDetail.PName}</span>
            <label>가격</label>
            <span class="price"><fmt:formatNumber value="${pDetail.PPrice}" pattern="#,###"/> 원</span>
            <label>옵션</label>
            <select id="option_select_box">

            </select>
        </div>
        <div class="productInfo_tempOrderInfo">
            <table class="tempOrderTable" border="1">
                <thead>
                    <tr>
                        <th>상품명</th>
                        <th>상품수</th>
                        <th>가  격</th>
                    </tr>
                </thead>
                <tbody class="tempOrderTableData">

                </tbody>
            </table>
        </div>
        <button type="button" id="buy">바로구매</button>
        <button type="button" id="cart">장바구니</button>
        <button type="button" id="like">관심상품</button><!-- 관심상품으로 등록 시 버튼 처리 어떻게 할지 고민-->
        <div class="totalPrice">
            <span>
                총 금액 : <p>0 원</p>
            </span>
        </div>
    </div>


    <div class="productInfo_detail">
        <div class="detail_button">
            <a href="#" id="productDetail">상품상세정보</a>
            <a href="#" id="productReview">리뷰(<c:out value="${pReviewCount}"/>)</a>
            <a href="#" id="productQnA">QnA(<c:out value="${pQnACount}"/>)</a>
            <a href="#" id="productOrderInfo">주문정보</a>
        </div>
        <div class="product_detail_info">
            <h2>상품 정보</h2>
        </div>
        <div class="product_Review_List">
            <div class="product_Review_List header">
                <h2>상품 리뷰</h2>
            </div>
            <div class="product_Review_List content">
                <ul class="review">

                </ul>
                <%--<c:forEach items="${pReviewList}" var="rList">
                    <div class="reviewContent">
                        <label><c:out value="${rList.userId}"/></label>
                        <span class="reviewContent_area">
                                ${rList.reviewContent}
                        </span>
                        <span class="reviewContent_RegDate">
                                ${rList.reviewDate}
                        </span>
                    </div>
                </c:forEach>--%>
            </div>

            <div class="reviewPaging">

            </div>



        </div>
        <div class="product_QnA_List">
            <div class="product_QnA_Header">
                <h2>상품 문의</h2>
            </div>
            <div class="QnAInput">
                <form id="QnAReplyForm">
                    <input type="text" name="pQnAContent">
                    <input type="hidden" name="pno" value="${pDetail.pno}">
                </form>
                <button type="button" id="QnAInsert">등록</button>
                <div class="overlap" id="QnAContentOverlap"></div>
            </div>
            <div class="product_QnA_Content">
                <ul class="product_QnA">

                </ul>
            </div>
            <div class="QnAPaging">

            </div>
        </div>
        <div class="product_Order_Info">
            <h2>배송 정보</h2>
            <table class="delivery-Info" border="1">
                <tbody>
                    <tr>
                        <th>배송방법</th>
                        <td>순차배송</td>
                    </tr>
                    <tr>
                        <th>묶음배송 여부</th>
                        <td>가능</td>
                    </tr>
                    <tr>
                        <th>배송비</th>
                        <td>
                            <ul>
                                <li>2,500원 / 10만원 이상 구매시 무료배송</li>
                                <li>제주, 도서산간 지역 배송은 5,000원 / 10만원 이상 구매시 2,500원</li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <th>배송기간</th>
                        <td>결제일 기준 평균 2~4일 소요</td>
                    </tr>
                </tbody>
            </table>
            <h2>교환/반품 안내</h2>
            <table border="1">
                <tbody>
                    <tr>
                        <th>교환/반품 비용</th>
                        <td>
                            5,000원
                            <ul>
                                <li>단, 고객의 변심의 경우에만 발생</li>
                            </ul>
                        </td>
                    </tr>
                <tr>
                    <th>교환 반품 신청 기준일</th>
                    <td>
                        <ul>
                            <li>
                                상품 수령 후 7일 이내 마이페이지>문의사항을 통해 접수.
                            </li>
                            <li>
                                타 택배사 이용 시 2,500원을 동봉해 선불로 보내주셔야 합니다.
                            </li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </table>
            <h2>교환/반품 제한사항</h2>
            <div>
                <ul>
                    <li>향수등의 냄새가 배거나 착용흔적이 보이는 제품, 오랜기간 방치로 인한 제품의 가치가 하락한 제품등의 경우는 교환/환불이 어려울 수 있습니다.</li>
                    <li>착용흔적, 훼손이 있을 경우에는 교환/환불 처리가 어려울 수 있기 때문에 개별적으로 연락을 드립니다.</li>
                    <li>불량품, 오배송의 경우는 mansShop에서 배송비를 부담합니다.</li>
                    <li>반드시 우체국 택배를 통해 보내주셔야 합니다. 그외 택배사 이용시 선불로 택배를 보내주셔야 합니다. 착불 시 추가적 배송비는 고객님이 부담하게 됩니다.</li>
                </ul>
            </div>
        </div>
    </div>



</div>
<input type="hidden" id="pno" value="${pDetail.pno}"/>
<input type="hidden" id="pPrice" value="${pDetail.PPrice}"/>




<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var pOpNoNum = 0;
    var pCountNum = 0;
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
                $(arr).each(function(i, op){
                    str += "<option value=\"" + op.popNo + "/" + op.pcolor + op.psize + "\">" +
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

        /*$("#productDetail").on('click', function(){

            //scrollIntoView

            $(".detail_area div").remove();

            $.getJSON("/getProductInfo", {pno:pno}, function(arr){
                var str = "<div class=\"productInfo\">";
                $(arr).each(function(i, info){
                    str += "<img class=\"infoImg\" src=\"/display?image=" + info.pimg + "\">";
                })
                str += "</div>";
                $(".detail_area").append(str);
            })
        });

        $("#productReview").on('click', function(){

            //scrollIntoView
            $(".detail_area div").remove();

            $.getJSON("/getProductReview", {pno:pno}, function(arr){
                var str = "<div class=\"productReview\">"   +
                            "<table>" +
                                "<thead>" +
                                    "<tr>" +
                                        "<th>아이디</th>" +
                                        "<th>리뷰</th>" +
                                        "<th>작성일</th>" +
                                    "</tr>" +
                                "</thead>" +
                                "<tbody>";
                $(arr).each(function(i, pReview){
                    var reviewRegDate = new Date(pReview.reviewDate);

                    pReview.reviewDate = reviewRegDate.getFullYear() + "/" +
                                            (reviewRegDate.getMonth() + 1) + "/" +
                                            reviewRegDate.getDate();
                    str += "<tr>" +
                            "<td>" + pReview.userId + "</td>" +
                            "<td>" + pReview.reviewContent + "</td>" +
                            "<td>" + pReview.reviewDate + "</td>" +
                            "</tr>";
                })
                str += "</tbody>" +
                        "</table>" +
                        "</div>";

                $(".detail_area").append(str);
            })
        });


        $("#productQnA").on('click', function(){

            //scrollIntoView
            var str = "";
        })*/

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
                    alert("code : " + request.stats + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            });
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
        var opPrice = parseInt($("tr[value="+id+"] td[name=productPrice] span").text().replace(/\D/g,''));
        var totalPrice = parseInt($(".totalPrice span p").text().replace(/\D/g,''));

        totalPrice = totalPrice - opPrice;
        $(".totalPrice span p").text(totalPrice.toLocaleString() + " 원");

        num--;

        $("tr[value="+id+"]").remove();
    }

    function firstThumb(obj){
        var imgName = obj.attributes['src'].value.substring(15);

        var str = "<img id=\"firstThumb\" src=\"/display?image=" + imgName + "\">";

        $(".firstThumbnail #firstThumb").remove();

        $(".firstThumbnail").append(str);
    }

    var num = 1;

    $(function(){
        $(".productInfo_Info #option_select_box").change(function(){
            console.log("property change");
            var option_txt = $("#option_select_box option:selected").text();

            var option_val = $("#option_select_box option:selected").val();

            var idx = option_val.indexOf("/");

            var optionNo = option_val.substring(0, idx);

            var p = parseInt($("#pPrice").val());

            console.log(option_val);

            console.log("price : " + p);

            var dataStr = "";

            dataStr += "<tr class=\"product_temp_cart\" id=\"productCount" + num +"\" value=\"" + optionNo +"\">" +
                "<td>" + option_txt + "</td>" +
                "<td>" +
                "<input type=\"text\" name=\"productCount" + num + "\" value=\"1\">" +
                "<button class=\"productCount up\" name=\"up\" value=\"productCount" + num + "\" onclick=\"countUp(this)\"'>up</button>" +
                "<button class=\"productCount down\" name=\"down\" value=\"productCount" + num + "\" onclick=\"countDown(this)\"'>down</button>" +
                "<button class=\"remove_btn\" name=\"opRemove\" value=\"productCount" + num + "\" onclick=\"opRemove(this)\"'>x</button>" +
                "</td>" +
                "<td name=\"productPrice\">" + "<span>" + p.toLocaleString() + " 원<span>" + "</td>" +
                "</tr>";

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
                var pOpNo = new Array();
                var pCount = new Array();
                var pPrice = new Array();

                console.log("Num : " + num);

                for(var i = 1; i < num; i++){
                    var tVal = "productCount" + i;

                    var count = $("input[name="+tVal+"]").val();
                    var opNo = $("tr[id="+tVal+"]").attr("value");
                    var price = $("tr[id="+tVal+"] td[name=productPrice] span").text().replace(/\D/g, '');

                    console.log("opNo : " + opNo);
                    console.log("count : " + count);
                    console.log("price : " + price);


                    /*pOpNo[pOpNoNum] = opNo;
                    pCount[pCountNum] = count;

                    pOpNoNum++;
                    pCountNum++;*/

                    pOpNo.push(opNo);
                    pCount.push(count);
                    pPrice.push(price);
                }

                /*for(var idx = 0; idx < Object.keys(pOpNo).length; idx++){
                    formData.append('pOpNo', pOpNo[idx]);
                }

                for(var idx = 0; idx < Object.keys(pCount).length; idx++){
                    formData.append('pOpNo', pOpNo[idx]);
                }*/

                var a = pOpNo.length;
                console.log("pOpNo length : " + a);

                console.log("pOpNo : " + pOpNo);

                $.ajaxSettings.traditional = true;
                $.ajax({
                    url: '/addCart',
                    type: 'post',
                    data: {pOpNo: pOpNo, pCount: pCount, pPrice: pPrice},
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



    })
</script>
</body>
</html>
