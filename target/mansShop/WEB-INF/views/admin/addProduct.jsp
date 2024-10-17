<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminProduct.js"></script>
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="/css/mypage.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="mypage">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="admin-content">
            <div class="admin-content-header">
                <h1>상품 수정</h1>
            </div>
            <div class="admin-content-content">
                <button type="button" class="default-btn" onclick="productAddBtn()">등록</button>
                <div class="product-name add-product">
                    <label class="product-label">상품명</label>
                    <input type="text" class="product-input" id="productName">
                    <div class="overlap product-overlap"></div>
                </div>
                <div class="product-classification add-product">
                    <label class="product-label">상품 분류</label>
                    <select class="product-classification-select-box product-input">
                        <c:forEach items="${classification}" var="category">
                            <option value="${category}">${category}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="product-price add-product">
                    <label class="product-label">가격</label>
                    <input type="number" class="product-input" id="price"/>
                    <div class="overlap product-price-overlap"></div>
                </div>
                <div class="product-isOpen add-product">
                    <label class="product-label">공개여부</label>
                    <div class="product-isOpen-radio isOpen-radio">
                        <label class="radio-label">공개</label>
                        <input type="radio" class="radio-input" name="product-isOpen" value="open" checked/>
                        <label class="radio-label">비공개</label>
                        <input type="radio" class="radio-input" name="product-isOpen" value="close"/>
                    </div>
                </div>
                <div class="product-discount add-product">
                    <label class="product-label">할인율(%)</label>
                    <input type="number" class="product-input" id="discount"/>
                </div>
                <div class="option-test">
                    <div class="option-header">
                        <h3>상품 옵션</h3>
                        <button class="default-btn" onclick="handleAddOption()">옵션 추가</button>
                    </div>
                    <div class="overlap product-option-overlap"></div>
                </div>
                <div class="add-product-thumbnail">
                    <div class="add-product-first-thumbnail">
                        <div class="first-thumbnail-header thumbnail-header">
                            <h3>대표 썸네일</h3>
                        </div>
                        <div class="first-thumbnail-input thumbnail-input">
                            <label for="first-thumb">
                                <div class="file-input-btn">
                                    대표 썸네일 업로드
                                </div>
                            </label>
                            <input type="file" id="first-thumb" class="file-input" onclick="this.value=null"/>
                            <div class="overlap first-thumbnail-overlap"></div>
                        </div>
                        <div class="first-thumbnail-content thumbnail-content"></div>
                    </div>
                </div>
                <div class="add-product-thumbnail">
                    <div class="first-thumbnail-header thumbnail-header">
                        <h3>썸네일</h3>
                    </div>
                    <div class="product-thumbnail-input thumbnail-input">
                        <label for="product-thumb">
                            <div class="file-input-btn">
                                썸네일 업로드
                            </div>
                        </label>
                        <input type="file" id="product-thumb" class="file-input" onclick="this.value=null" multiple/>
                    </div>
                    <div class="product-thumbnail-content thumbnail-content"></div>
                    <div class="add-product-info-image">
                        <div class="info-image-header thumbnail-header">
                            <h3>상품정보이미지</h3>
                        </div>
                        <div class="info-image-input thumbnail-input">
                            <label for="info-image">
                                <div class="file-input-btn">
                                    상품 정보 업로드
                                </div>
                            </label>
                            <input type="file" id="info-image" class="file-input" onclick="this.value=null" multiple/>
                            <div class="overlap info-image-overlap"></div>
                        </div>
                        <div class="info-image-content thumbnail-content"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

