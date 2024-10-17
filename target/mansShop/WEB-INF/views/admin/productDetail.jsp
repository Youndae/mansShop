<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                <h1>상품 정보</h1>
            </div>
            <div class="admin-content-content">
                <div class="admin-detail-header">
                    <h2>${detail.productName}</h2>
                    <button type="button" class="default-btn" value="${detail.productId}" onclick="productUpdateLinkBtn(this)">수정</button>
                </div>
                <div class="admin-detail-content">
                    <div>
                        <p>분류 : ${detail.classification}</p>
                        <p>가격 : <fmt:formatNumber value="${detail.price}" pattern="#,###"/></p>
                        <p>
                            <c:set var="openStatus" value="비공개"/>
                            <c:if test="${detail.open}">
                                <c:set var="openStatus" value="공개"/>
                            </c:if>
                            공개 여부 : ${openStatus}
                        </p>
                        <p>판매량 : <fmt:formatNumber value="${detail.sales}" pattern="#,###"/></p>
                        <p>할인율 : ${detail.discount}% (판매가 : <fmt:formatNumber value="${detail.discountPrice}" pattern="#,###"/>)</p>
                    </div>
                    <div class="option-test">
                        <c:forEach items="${detail.optionList}" var="options">
                            <div class="option-detail">
                                <span>사이즈 : ${options.PSize}</span>
                                <span>컬러 : ${options.color}</span>
                                <span>재고 : ${options.stock}</span>
                                <span>
                                    <c:set var="optionOpenStatus" value="비공개"/>
                                    <c:if test="${options.open}">
                                        <c:set var="optionOpenStatus" value="공개"/>
                                    </c:if>
                                    옵션 공개 여부 : ${optionOpenStatus}
                                </span>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="detail-first-thumbnail">
                        <h3>대표 썸네일</h3>
                        <img src="/display?image=${detail.firstThumbnail}" />
                    </div>
                    <div class="detail-thumbnail">
                        <h3>썸네일</h3>
                        <c:forEach items="${detail.thumbnailList}" var="thumb">
                            <img src="/display?image=${thumb}"/>
                        </c:forEach>
                    </div>
                    <div class="detail-info-image">
                        <h3>상세 정보 이미지</h3>
                        <c:forEach items="${detail.infoImageList}" var="info">
                            <img src="/display?image=${info}"/>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>