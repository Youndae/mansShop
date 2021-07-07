<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div>
    <div class="header">
        <h1>장바구니</h1>
    </div>
    <div class="cart_List">
        <table class="cart_Table" border="1">
            <thead>
                <tr>
                    <th></th>
                    <th>상품명</th>
                    <th>옵션</th>
                    <th>수량</th>
                    <th>가격</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${cartList}" var="cList">
                    <c:set var="total" value="${total + cList.CPrice}"/>
                    <tr>
                        <td><input type="checkbox" name="check" value="${cList.POpNo}" checked></td>
                        <td><c:out value="${cList.PName}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${cList.PSize == null}">
                                    <c:choose>
                                        <c:when test="${cList.PColor != null}">
                                            색상 : <c:out value="${cList.PColor}"/>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${cList.PColor == null}">
                                            사이즈 : <c:out value="${cList.PSize}"/>
                                        </c:when>
                                        <c:otherwise>
                                            사이즈 : <c:out value="${cList.PSize}"/>  색상 : <c:out value="${cList.PColor}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span><c:out value="${cList.CCount}"/></span>
                            <button class="productCount up" name="up">up</button>
                            <button class="productCount down" name="down">down</button>
                        </td>
                        <td class="cPrice"><fmt:formatNumber value="${cList.CPrice}" pattern="#,###"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div>
        <span class="total_price">총 주문 금액 : <fmt:formatNumber value="${total}" pattern="#,###"/> 원</span>
    </div>
    <div>
        <button type="button" id="select_delete">선택상품 삭제</button>
        <button type="button" id="select_order">선택상품 주문</button>
    </div>
</div>
<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ready(function(){
        $("button[name=up]").on('click', function(){
            console.log("test : " + $(this).siblings("span").text().replace(/\D/g,''));

            var totalPrice = $(".total_price").text().replace(/\D/g, '');
            var count = $(this).siblings("span").text();
            var pPrice = $(this).parent('td').next().text().replace(/\D/g, '');
            pPrice = parseInt(pPrice) / parseInt(count); //개당 가격
            totalPrice = parseInt(totalPrice) + pPrice;

            count = parseInt(count) + 1;
            pPrice = pPrice * count;

            $(this).siblings("span").text(count);
            $(this).parent('td').next().text(pPrice.toLocaleString());
            $(".total_price").text("총 주문 금액 : " + totalPrice.toLocaleString() + " 원");

            console.log("test price : " + $(this).parent("td").next().text());
        });

        $("button[name=down]").on('click', function(){
            console.log("test : " + $(this).siblings("span").text().replace(/\D/g,''));

            var totalPrice = $(".total_price").text().replace(/\D/g, '');
            var count = $(this).siblings("span").text();
            var pPrice = $(this).parent('td').next().text().replace(/\D/g, '');
            pPrice = parseInt(pPrice) / parseInt(count); //개당 가격
            totalPrice = parseInt(totalPrice) - pPrice;

            count = parseInt(count) - 1;
            pPrice = pPrice * count;

            $(this).siblings("span").text(count);
            $(this).parent('td').next().text(pPrice.toLocaleString());
            $(".total_price").text("총 주문 금액 : " + totalPrice.toLocaleString() + " 원");

            console.log("test price : " + $(this).parent("td").next().text());
        });

        $("#select_delete").on('click', function(){
            var pOpNoArr = new Array();
            $("input[name=check]:checked").each(function(){
                pOpNoArr.push($(this).attr("value"));
            });



            $.ajaxSettings.traditional = true;
            $.ajax({
                url : '/myPage/deleteCart',
                type: 'post',
                data: {pOpNo: pOpNoArr},
                beforeSend:function(xhr){
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
            })
        })
    })
</script>
</body>
</html>
