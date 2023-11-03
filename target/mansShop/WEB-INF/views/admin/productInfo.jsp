<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminProduct.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/addProduct.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">

        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="addProduct_header">
                <h1>상품정보</h1>
                <div class="button-area">
                    <button type="button" id="addProductOp">옵션 추가</button>
                    <button type="button" id="modifyProduct">수정</button>
                </div>
            </div>

            <div class="addProduct_content">
                <div class="addProduct_form">
                    <form id="modifyProductForm" method="post" action="/admin/addProductOp">
                        <div class="label_t4">
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
                            <input type="hidden" value="${info.PClassification}" id="classification">
                        </div>
                        <div class="label_t3">
                            <label>상품명</label>
                            <input type="text" name="pName" value="${info.PName}">
                            <div class="overlap" id="checkPName"></div>
                        </div>
                        <div class="label_t3">
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
                            <input type="hidden" value="${info.PSize}" id="size">
                        </div>
                        <div class="label_t2">
                            <label>컬러</label>
                            <input type="text" id="pColor" name="pColor" value="${info.PColor}">
                            <div class="overlap" id="checkPColor"></div>
                        </div>
                        <div class="label_t2">
                            <label>가격</label>
                            <input type="text" name="pPrice" value="${info.PPrice}">
                            <div class="overlap" id="checkPPrice"></div>
                        </div>
                        <div class="label_t2">
                            <label>재고</label>
                            <input type="text" name="pStock" placeholder="입력하지 않으면 0으로 등록됩니다." value="${info.PStock}">
                            <div class="overlap" id="checkPStock"></div>
                        </div>
                        <input type="hidden" name="pno" value="${info.pno}">
                        <input type="hidden" name="pOpNo" value="${info.POpNo}">
                        <sec:csrfInput/>
                    </form>
                    <input type="hidden" id="pClosed" value="${info.PClosed}">
                    <input type="hidden" id="opClosed" value="${info.opClosed}">
                </div>
                <div class="addProduct_img_area">
                    <div id="firstThumb">
                        <label>대표이미지</label>
                        <input type="file" name="firstThumbnail" value="파일 선택">
                        <div id="firstThumbPreview"></div>
                        <div class="overlap" id="checkFirstThumb"></div>
                    </div>
                    <div id="thumb">
                        <label>썸네일</label>
                        <input type="file" name="productthumbnail" value="파일 선택" multiple>
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>