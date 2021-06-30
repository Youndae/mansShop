<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    #firstThumb{
        width: 300px;
    }
    .thumb>img{
        width: 60px;
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
            <span class="price">${pDetail.PPrice}</span>
            <label>옵션</label>
            <select id="option_select_box">

            </select>
        </div>
        <div class="productInfo_tempOrderInfo">
            <table class="tempOrderTable">
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
        <button type="button" id="like">관심상품</button>
    </div>

    <div class="productInfo_detail">
        <div class="detail_button">
            <a href="#" id="productDetail">상품상세정보</a>
            <a href="#" id="productReview">리뷰</a>
            <a href="#" id="productQnA">QnA</a>
            <a href="#" id="productOrderInfo">주문정보</a>
        </div>
        <div class="detail_area">

        </div>
    </div>
</div>
<input type="hidden" id="pno" value="${pDetail.pno}"/>

<script>
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
                var str = "";
                $(arr).each(function(i, op){
                    str += "<option value=\"" + op.popno + "/" + op.pcolor + op.psize + "\">" +
                            "컬러 : " + op.pcolor + "     사이즈 : " + op.psize + "</option>";
                })

                $("#option_select_box").append(str);
            });
        })();

        var num = 1;

        $("#option_select_box").on("propertychange change keyup paste input", function(){

            var option_val = $("#option_select_box option:selected").text();

            console.log(option_val);

            var str = "";

            str += "<tr class=\"product_temp_cart\">" +
                    "<td>" + option_val + "</td>" +
                    "<td><input type=\"text\" id=\"productCount" + num + "\" value=\"1\"></td>" +
                    "<td name=>";


        })
    })

    function firstThumb(obj){
        var imgName = obj.attributes['src'].value.substring(15);

        var str = "<img id=\"firstThumb\" src=\"/display?image=" + imgName + "\">";

        $(".firstThumbnail #firstThumb").remove();

        $(".firstThumbnail").append(str);
    }
</script>
</body>
</html>
