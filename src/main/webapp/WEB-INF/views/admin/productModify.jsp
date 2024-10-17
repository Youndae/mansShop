<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <button type="button" class="default-btn" value="${detail.productId}" onclick="productUpdateBtn(this)">수정</button>
                <div class="product-name add-product">
                    <label class="product-label">상품명</label>
                    <input type="text" class="product-input" id="productName" value="${detail.productName}">
                    <div class="overlap product-overlap"></div>
                </div>
                <div class="product-classification add-product">
                    <label class="product-label">상품 분류</label>
                    <select class="product-classification-select-box product-input">
                        <c:forEach items="${detail.classificationList}" var="category">
                            <c:choose>
                                <c:when test="${detail.classification == category}">
                                    <option value="${category}" selected>${category}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${category}">${category}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <div class="product-price add-product">
                    <label class="product-label">가격</label>
                    <input type="number" class="product-input" id="price" value="${detail.price}"/>
                    <div class="overlap product-price-overlap"></div>
                </div>
                <div class="product-isOpen add-product">
                    <label class="product-label">공개여부</label>
                    <div class="product-isOpen-radio isOpen-radio">
                        <label class="radio-label">공개</label>
                        <input type="radio" class="radio-input" name="product-isOpen" value="open"
                               <c:if test="${detail.open}">checked="checked"</c:if>
                        />
                        <label class="radio-label">비공개</label>
                        <input type="radio" class="radio-input" name="product-isOpen" value="close"
                               <c:if test="${!detail.open}">checked="checked"</c:if>
                        />
                    </div>
                </div>
                <div class="product-discount add-product">
                    <label class="product-label">할인율(%)</label>
                    <input type="number" class="product-input" id="discount" value="${detail.discount}"/>
                </div>
                <div class="option-test">
                    <div class="option-header">
                        <h3>상품 옵션</h3>
                        <button class="default-btn" onclick="handleAddOption()">옵션 추가</button>
                    </div>
                    <div class="overlap product-option-overlap"></div>
                    <c:forEach items="${detail.optionList}" var="options" varStatus="idx">
                        <div class="option-detail option-${idx.index}">
                            <div class="option-detail-header">
                                <button type="button" class="default-btn" value="${idx.index}/${options.optionId}" onclick="deleteOldProductOption(this)">옵션 삭제</button>
                            </div>
                            <div class="option-size">
                                <label class="product-label">사이즈</label>
                                <input type="text" class="product-input" name="size" value="${options.PSize}"/>
                            </div>
                            <div class="option-color">
                                <label class="product-label">컬러</label>
                                <input type="text" class="product-input" name="color" value="${options.color}"/>
                            </div>
                            <div class="option-stock">
                                <label class="product-label">재고</label>
                                <input type="number" class="product-input" name="optionStock" value="${options.stock}"/>
                            </div>
                            <div class="option-isOpen">
                                <label class="product-label">옵션 공개여부</label>
                                <div class="product-isOpen-radio isOpen-radio">
                                    <label class="radio-label">공개</label>
                                    <input type="radio" class="radio-input option-radio-open" name="option-isOpen-${options.optionId}" value="open"
                                           <c:if test="${options.open}">checked="checked"</c:if>
                                    />
                                    <label class="radio-label">비공개</label>
                                    <input type="radio" class="radio-input option-radio-close" name="option-isOpen-${options.optionId}" value="close"
                                           <c:if test="${!options.open}">checked="checked"</c:if>
                                    />
                                </div>
                            </div>
                        </div>
                    </c:forEach>
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
                        <div class="first-thumbnail-content thumbnail-content">
                            <button type="button" class="first-thumbnail-delete-btn image-btn" value="${detail.firstThumbnail}" onclick="deleteOldFirstThumb(this)">삭제</button>
                            <img src="/display?image=${detail.firstThumbnail}"/>
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
                        <div class="product-thumbnail-content thumbnail-content">
                            <c:if test="${detail.thumbnailList.size() != 0}">
                                <c:forEach items="${detail.thumbnailList}" var="thumb" varStatus="idx">
                                    <div class="old-thumb-${idx.index}">
                                        <button class="thumbnail-delete-btn image-btn" type="button" value="${idx.index}/${thumb}" onclick="deleteOldThumbnail(this)">삭제</button>
                                        <img src="/display?image=${thumb}"/>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
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
                            <div class="info-image-content thumbnail-content">
                                <c:if test="${detail.infoImageList.size() != 0}">
                                    <c:forEach items="${detail.infoImageList}" var="infoImage" varStatus="idx">
                                        <div class="info-image-content-image old-info-${idx.index}">
                                            <button type="button" class="info-image-delete-btn image-btn" value="${idx.index}/${infoImage}" onclick="deleteOldInfoImage(this)">삭제</button>
                                            <img src="/display?image=${infoImage}"/>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
