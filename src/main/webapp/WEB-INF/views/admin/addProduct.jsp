<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/admin.js"></script>
<style>
    img{
        width: 300px;
        height: 300px;
    }

    .overlap{
        font-size: 12px;
        color: red;
    }
</style>
<body>
<h1>상품추가</h1>
<div>
    <form id="addProductForm" method="post">
        <div>
            <label>상품 분류</label>
            <select name="pClassification">
                <option value="default">------</option>
                <option value="OUTER">OUTER</option>
                <option value="TOP">TOP</option>
                <option value="PANTS">PANTS</option>
                <option value="SHOES">SHOES</option>
                <option value="BAGS">BAGS</option>
            </select>
            <div class="overlap" id="checkClassification"></div>
        </div>
        <div>
            <label>상품명</label>
            <input type="text" name="pName">
            <div class="overlap" id="checkPName"></div>
        </div>
        <div>
            <label>사이즈</label>
            <select name="pSize">
                <option value="default">------</option>
                <option value="S">S</option>
                <option value="M">M</option>
                <option value="L">L</option>
                <option value="XL">XL</option>
                <option value="XXL">XXL</option>
                <option value="FREE">FREE</option>
            </select>
            <div class="overlap" id="checkPSize"></div>
        </div>
        <div>
            <label>컬러</label>
            <input type="text" id="pColor" name="pColor">
            <div class="overlap" id="checkPColor"></div>
        </div>
        <div>
            <label>가격</label>
            <input type="text" name="pPrice">
            <div class="overlap" id="checkPPrice"></div>
        </div>
        <div>
            <label>재고</label>
            <input type="text" name="pStock" placeholder="입력하지 않으면 0으로 등록됩니다.">
            <div class="overlap" id="checkPStock"></div>
        </div>
        <sec:csrfInput/>
    </form>
        <div id="firstThumb">
            <label>대표이미지</label>
            <input type="file" name="firstThumbnail" value="파일 선택">
            <div id="firstThumbPreview"></div>
            <div class="overlap" id="checkFirstThumb"></div>
        </div>
        <div id="thumb">
            <label>썸네일</label>
            <input type="file" name="thumbnail" value="파일 선택" multiple>
            <div id="thumbPreview"></div>
            <div class="overlap" id="checkThumb">
                썸네일을 등록하지 않아도 업로드가 가능합니다.
            </div>
        </div>
        <div id="productInfo">
            <label>상품정보</label>
            <input type="file" name="pImg" value="파일 선택" multiple>
            <div id="infoPreview"></div>
            <div class="overlap" id="checkInfo"></div>
        </div>

    <button type="button" id="addSubmit">등록</button>
</div>
</body>
</html>
