<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/admin.js"></script>
<body>
<h1>상품추가</h1>
<div>
    <form action="/admin/addProduct" id="addProduct" method="post">
        <div>
            <label>상품 분류</label>
            <select id="classification" name="pClassification">
                <option value="OUTER">OUTER</option>
                <option value="TOP">TOP</option>
                <option value="PANTS">PANTS</option>
                <option value="SHOES">SHOES</option>
                <option value="BAGS">BAGS</option>
            </select>
        </div>
        <div>
            <label>상품명</label>
            <input type="text" name="pName">
        </div>
        <div>
            <label>사이즈</label>
            <select id="size" name="pSize">
                <option value="S">S</option>
                <option value="M">M</option>
                <option value="L">L</option>
                <option value="XL">XL</option>
                <option value="XXL">XXL</option>
                <option value="FREE">FREE</option>
            </select>
        </div>
        <div>
            <label>컬러</label>
            <input type="text" name="pColor">
        </div>
        <div>
            <label>가격</label>
            <input type="text" name="pPrice">
        </div>
        <div>
            <label>재고</label>
            <input type="text" name="pStock">
        </div>
        <div>
            <label>대표이미지</label>
            <input type="file" name="firstThumbnail" value="파일 선택">
            <div id="firstThumbPreview"></div>
        </div>
        <div>
            <label>썸네일</label>
            <input type="file" name="thumbnail" value="파일 선택" multiple>
        </div>
        <div id="thumbPreview"></div>
        <div>
            <label>상품정보</label>
            <input type="file" name="pImg" value="파일 선택" multiple>
        </div>
        <div id="imgPreview"></div>
        <sec:csrfInput/>
    </form>
    <button type="button" id="addSubmit">등록</button>
</div>
</body>
</html>
