<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<style>
    #firstThumb{
        width: 400px;
    }
    .thumb>img{
        width: 80px;
    }

    .thumbnail{
        padding: 0 0 0 15%;
    }

    .QnA_content_reply{
        text-align: left;
        margin-left: 24px;
    }

    .productInfo_detail .product_detail_info::after{
        display: inline-block;
        content: "";
        vertical-align: middle;
    }

    .container div{
        text-align: center;
    }

    ul{
        list-style: none;
    }

    .product_Info_Order{
        display: inline-flex;
        margin-top: 100px;
        width: 80%;
    }

    .optionArea{
        margin-left: 400px;
        width: 30%;
    }

    .productOption{
        width: fit-content;
        float: right;
        font-size: xx-large;
    }

    .productOption div{
        margin-top: 40px;
    }

    .productOption label{
        font-weight: bold;
        float: left;
    }

    .productOption span{
        font-weight: 100;
        font-size: xx-large;
        margin-left: 20px;
    }

    .productInfo_tempOrderInfo{
        display: grid;
    }

    .productInfo_tempOrderInfo .tempOrderTable{
        text-align: center;
        width: 100%;
        border: 1px solid;
    }

    .productInfo_tempOrderInfo .tempOrderTable span{
        font-size: large;
    }

    .productInfo_tempOrderInfo .tempOrderTable input{
        width: 20px;
        border-style: hidden;
    }

    select#option_select_box{
        margin-top: 10px;
        height: 25px;
    }

    .detail_button{
        font-size: xx-large;
        margin: 100px;
        border: solid;
        border-width: 1px 0px 1px 0px;
    }

    .detail_button a{
        padding: 100px;
    }

    .product_Review_List{
        border: solid;
        border-width: 1px 0px 0px 0px;
    }

    .product_Review_List_header{
        margin-top: 40px;
    }

    .product_Review_List_content{
        margin-top: 40px;
    }

    .product_Review_List_content li{
        margin-top: 30px;
        border: 1px solid lightgray;
        width: 50%;
        display: inline-grid;
    }

    .review_content_header{
        display: inline-flex;
    }

    .review_content_header strong{
        font-weight: bold;
        font-size: x-large;
        width: 10%;
    }

    .review_content_header small{
        font-size: small;
        padding: 12px 0 0 0;
    }

    .review_content_content{
        display: flex;
        margin-left: 20%;
    }

    .reviewPaging li{
        display: inline;
        margin: 10px;
        font-size: large;
    }

    .product_QnA_List{
        margin-top: 100px;
        border: solid;
        border-width: 1px 0 0 0;
    }

    .product_QnA_List .product_QnA_Header{
        margin-top: 40px;
        display: revert;
    }

    .product_QnA_Content li{
        margin-top: 30px;
        border: 1px solid lightgray;
        width: 50%;
        display: inline-grid;
    }

    .QnA_content_header{
        display: flex;
    }

    .QnA_content_header strong{
        font-weight: bold;
        font-size: x-large;
        width: 10%;
    }

    .QnA_content_header small{
        font-size: small;
        padding: 12px 10px 0 0;
    }

    .QnA_content_content{
        display: flex;
        margin-left: 20%;
    }

    .QnA_content_reply{
        margin-left: 50px;
        padding-right: 15px;
    }

    .QnAPaging li{
        display: inline;
        margin: 10px;
        font-size: large;
    }

    .product_Order_Info{
        margin-top: 100px;
        border: solid;
        border-width: 1px 0 0 0;
    }

    .product_order_Info_header{
        margin-top: 40px;
    }

    .product_Order_Info_content{
        display: inline-block;
        margin-top: 40px;
    }

    .product_QnA_Form_area{
        margin-top: 50px;
    }

    .product_QnA_Form_area textarea{
        width: 1000px;
        height: 100px;
        resize: none;
    }

    .product_QnA_Form_area button{
        width: 100px;
        height: 30px;
        margin: 10px 0 0 35%;
    }

    .product_temp_cart img{
        width: 10px;
    }

    .product_temp_cart_input button{
        background-color: white;
        border: 1px solid lightgray;
    }

    .product_temp_cart_input .remove_btn{
        margin-left: 7px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/product.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="product_Info_Order">
        <div class="thumbnail">
            <div class="firstThumbnail">
                <img id="firstThumb" src="/display?image=${pDetail.firstThumbnail}">
            </div>
            <div class="thumb">
                <img id="thumb1" src="/display?image=${pDetail.firstThumbnail}" onclick="firstThumb(this)">
            </div>
        </div>

        <div class="optionArea">
            <div class="productOption">
                <div class="productInfo_Info">
                    <div class="productInfo_productName">
                        <label>상품명</label>
                        <span class="name">${pDetail.PName}</span>
                    </div>
                    <div class="productInfo_productPrice">
                        <label>가격</label>
                        <span class="price"><fmt:formatNumber value="${pDetail.PPrice}" pattern="#,###"/> 원</span>
                    </div>
                    <div class="productInfo_productOption">
                        <label>옵션</label>
                        <select id="option_select_box">

                        </select>
                    </div>
                </div>
                <div class="productInfo_tempOrderInfo">
                    <table class="tempOrderTable" border="1">
                        <thead>
                            <tr>
                                <th>옵  션</th>
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
                <c:choose>
                    <c:when test="${likeStat == 2}">
                        <button type="button" id="anonymous">관심상품</button>
                    </c:when>
                    <c:when test="${likeStat == 0}">
                        <button type="button" id="like">관심상품</button>
                    </c:when>
                    <c:when test="${likeStat == 1}">
                        <button type="button" id="deLike">관심상품해제</button>
                    </c:when>
                </c:choose>
                <div class="totalPrice">
                    <p>
                        총 금액 : <span>0 원</span>
                    </p>
                </div>
                <form id="order_form" method="post">
                    <input type="hidden" name="pOpNo">
                    <input type="hidden" name="pName">
                    <input type="hidden" name="pSize">
                    <input type="hidden" name="pColor">
                    <input type="hidden" name="cCount">
                    <input type="hidden" name="cPrice">
                    <input type="hidden" name="pno">
                    <input type="hidden" name="oType" value="d">
                    <sec:csrfInput/>
                </form>
            </div>
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
            <div class="product_Review_List_header">
                <h2>상품 리뷰</h2>
            </div>
            <div class="product_Review_List_content">
                <ul class="review">

                </ul>
            </div>

            <div class="reviewPaging">

            </div>
        </div>
        <div class="product_QnA_List">
            <div class="product_QnA_Header">
                <h2>상품 문의</h2>
                <div class="product_QnA_Form_area">
                    <form id="product_QnA_InsertForm" method="post">
                        <textarea id="pQnAContent" name="pQnAContent"></textarea>
                        <input type="hidden" name="pno" value="${pDetail.pno}">
                    </form>
                    <sec:authentication property="principal" var="userId"/>
                    <c:choose>
                        <c:when test="${userId == 'anonymousUser'}">
                            <button type="button" disabled>문의하기</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" id="QnAInsert">문의하기</button>
                        </c:otherwise>
                    </c:choose>
                    <input type="hidden" id="userStat" value="${userId}">
                </div>
            </div>
            <div class="product_QnA_Content">
                <ul class="product_QnA">

                </ul>
            </div>
            <div class="QnAPaging">

            </div>
        </div>
        <div class="product_Order_Info">
            <div class="product_order_Info_header">
                <h2>배송 정보</h2>
            </div>
            <div class="product_order_Info_content">
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
                    <tr>
                        <th>무통장입금 계좌번호</th>
                        <td>
                            <ul>
                                <li>농협 000-0000000-000</li>
                                <li>기업은행 0000-00000-000</li>
                            </ul>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="product_order_Info_header">
                <h2>교환/반품 안내</h2>
            </div>
            <div class="product_order_Info_content">
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
            </div>
            <div class="product_order_Info_header">
                <h2>교환/반품 제한사항</h2>
            </div>
            <div class="product_order_Info_content">
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
</div>
</body>
</html>
