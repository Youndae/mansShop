<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
            <span class="price"><fmt:formatNumber value="${pDetail.PPrice}" pattern="#,###"/> 원</span>
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
                <c:forEach items="${pReviewList}" var="rList">
                    <div class="reviewContent">
                        <label><c:out value="${rList.userId}"/></label>
                        <span class="reviewContent_area">
                                ${rList.reviewContent}
                        </span>
                        <span class="reviewContent_RegDate">
                                ${rList.reviewDate}
                        </span>
                    </div>
                </c:forEach>
            </div>

            <div class="paging">
                <ul class="pagination">
                    <c:if test="${pageMaker.prev}">
                        <li class="paginate_button previous">
                            <a href="${pageMaker.startPage - 1}">Prev</a>
                        </li>
                    </c:if>

                    <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                        <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                            <a href="${num}">${num}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${pageMaker.next}">
                        <li class="paginate_button next">
                            <a href="${pageMaker.endPage + 1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </div>

            <form id="pReviewActionForm" action="/getProductReview" method="get">
                <input type="hidden" name="pno" value="<c:out value="${pDetail.pno}"/>">
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
            </form>

        </div>
        <div class="product_QnA_List">
            <h2>상품 문의</h2>

        </div>
        <div class="product_Order_Info">
            <h2>주문 정보</h2>
        </div>
    </div>

    <div class="QnAInput">
        <input type="text" name="pQnAContent">
        <button type="button" id="QnAinsert">등록</button>
    </div>

</div>
<input type="hidden" id="pno" value="${pDetail.pno}"/><!-- 작성 위치 확인하고 삭제 -->
<input type="hidden" id="pPrice" value="${pDetail.PPrice}"/>




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
        })();

        $("#productReview").on('click', function(e){
            e.preventDefault();
            document.querySelector('.product_Review_List').scrollIntoView(true);
        })

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


        $(".paginate_button a").on('click', function(e){
            e.preventDefault();


        })






    })

    function countUp(obj){
        console.log("count Up!");
        var id = obj.attributes['value'].value;
        console.log("id : " + id);
        var price = parseInt($("#pPrice").val());

        console.log("price : "+price);

        var count = parseInt($("input[id="+id+"]").val());
        count = count + 1;

        $("input[id="+id+"]").val(count);

        var opPrice = parseInt($("tr[value="+id+"] td[name=productPrice] span").text().replace(/\D/g,''));

        opPrice = opPrice + price;

        $("tr[value="+id+"] td[name=productPrice] span").text(opPrice.toLocaleString()+" 원");

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

        var count = parseInt($("input[id="+id+"]").val());
        count = count - 1;

        $("input[id="+id+"]").val(count);

        var opPrice = parseInt($("tr[value="+id+"] td[name=productPrice] span").text().replace(/\D/g,''));

        opPrice = opPrice - price;

        $("tr[value="+id+"] td[name=productPrice] span").text(opPrice.toLocaleString()+" 원");


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
            var option_val = $("#option_select_box option:selected").text();

            var p = parseInt($("#pPrice").val());

            console.log(option_val);

            console.log("price : " + p);

            var dataStr = "";

            dataStr += "<tr class=\"product_temp_cart\" value=\"productCount" + num +"\">" +
                "<td>" + option_val + "</td>" +
                "<td>" +
                "<input type=\"text\" id=\"productCount" + num + "\" value=\"1\">" +
                "<button class=\"productCountUp\" name=\"up\" value=\"productCount" + num + "\" onclick=\"countUp(this)\"'>up</button>" +
                "<button class=\"productCountDown\" name=\"down\" value=\"productCount" + num + "\" onclick=\"countDown(this)\"'>down</button>" +
                "<button class=\"remove_btn\" name=\"opRemove\" value=\"productCount" + num + "\" onclick=\"opRemove(this)\"'>x</button>" +
                "</td>" +
                "<td name=\"productPrice\">" + "<span>" + p.toLocaleString() + " 원<span>" + "</td>" +
                "</tr>";

            num++;

            /*
                <tr class="product_temp_cart">
                    <td>코트<td>
                    <td>
                        <input type="text" id="productCount1" value="1">
                        <a name="up">up</a>
                    </td>
                    <td name="productPrice">10,000원</td>
                </tr>

                toLocaleString으로 찍으면 중간중간 콤마가 들어가게 되는데
                다시 불러왔을 때 그 콤마때문에 int처리가 제대로 되지 않는 문제.

            */

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



    })
</script>
</body>
</html>
