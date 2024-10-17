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
<script type="text/javascript" src="/js/product.js"></script>
<link rel="stylesheet" href="/css/productDetail.css">
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content product-detail-content">
        <div class="product-detail-header">
            <div class="product-thumbnail product-detail-first-thumbnail">
                <div class="first-thumbnail">
                    <img id="firstThumb" src="/display?image=${product.productImageName}">
                </div>
                <div class="thumbnail product-detail-thumbnail-list">
                    <img src="/display?image=${product.productImageName}" onmouseover="thumbnailMouseOver(this)">
                    <c:forEach items="${product.thumbnails}" var="thumb">
                        <img src="/display?image=${thumb}" onmouseover="thumbnailMouseOver(this)">
                    </c:forEach>
                </div>
            </div>

            <div class="product-detail-option">
                <div class="product-detail-option-detail">
                    <div class="product-default-info">
                        <div class="product-name mgt-4">
                            <label>상품명</label>
                            <span class="name">${product.productName}</span>
                        </div>
                        <div class="product-price mgt-4">
                            <label>가격</label>
                            <c:choose>
                                <c:when test="${product.discount == 0}">
                                    <span class="price"><fmt:formatNumber value="${product.productPrice}" pattern="#,###"/> 원</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="original-price"><fmt:formatNumber value="${product.productPrice}"
                                                                                   pattern="#,###"/></span>
                                    <span class="discount-value">${product.discount}%</span>
                                    <span class="price"><fmt:formatNumber value="${product.discountPrice}"
                                                                                   pattern="#,###"/> 원</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <c:if test="${product.options.size() != 0}">
                            <div class="product-detail-select mgt-4">
                                <label>옵션</label>
                                <select id="product-detail-option-select-box">
                                    <option value="default" selected hidden>옵션을 선택해주세요</option>
                                    <c:forEach items="${product.options}" var="option">
                                        <c:set var="result" value=""/>
                                        <c:set var="optionValue" value="${option.optionId}"/>
                                        <c:if test="${option.PSize ne null}">
                                            <c:set var="result" value="사이즈 : ${option.PSize}"/>
                                            <c:set var="optionValue" value="${optionValue}/${option.PSize}"/>
                                        </c:if>
                                        <c:if test="${option.color ne null}">
                                            <c:if test="${not empty result}">
                                                <c:set var="result" value="${result}, "/>
                                            </c:if>
                                            <c:set var="optionValue" value="${optionValue}/${option.color}"/>
                                            <c:set var="result" value="${result}컬러 : ${option.color}"/>
                                        </c:if>

                                        <c:choose>
                                            <c:when test="${option.stock == 0}">
                                                <option value="${optionValue}"  disabled>${result}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${optionValue}">${result}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:if>
                    </div>

                    <div class="product-info temp-order mgt-4">
                        <table class="temp-order-table">
                            <tbody class="temp-order-table-body">

                            </tbody>
                        </table>
                    </div>

                    <button class="default-btn" type="button" onclick="directBuy()">바로구매</button>
                    <button class="default-btn" type="button" onclick="addCart()">장바구니</button>
                    <c:choose>
                        <c:when test="${product.likeStat}">
                            <button class="default-btn" type="button" onclick="deLike()">관심상품 해제</button>
                        </c:when>
                        <c:otherwise>
                            <button class="default-btn" type="button" onclick="like()">관심상품 등록</button>
                        </c:otherwise>
                    </c:choose>
                    <div class="total-price">
                        <p>
                            총 금액 : <span>0 원</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <div class="product-detail-content">
            <div class="product-detail-content-btn">
                <label for="detail-btn">상품정보</label>
                <button id="detail-btn" value="detail" onclick="detailBtnOnClick(this)"></button>
                <label for="review-btn">리뷰(${product.reviews.pageMaker.total})</label>
                <button id="review-btn" value="review" onclick="detailBtnOnClick(this)"></button>
                <label for="qna-btn">QnA(${product.qnAs.pageMaker.total})</label>
                <button id="qna-btn" value="qna" onclick="detailBtnOnClick(this)"></button>
                <label for="order-info-btn">주문정보</label>
                <button id="order-info-btn" value="order-info" onclick="detailBtnOnClick(this)"></button>
            </div>
            <div class="product-detail-info">
                <h2>상품 정보</h2>
                <c:forEach items="${product.infoImages}" var="infoImage">
                    <div class="info-image-div">
                        <img src="/display?image=${infoImage}" class="info-image">
                    </div>
                </c:forEach>
            </div>
            <div class="product-detail-review">
                <div class="product-detail-review-header">
                    <h2>상품 리뷰</h2>
                </div>
                <div class="product-detail-review-content">
                    <ul>
                        <c:forEach items="${product.reviews.content}" var="review">
                            <li class="review-content-default">
                                <div class="review-content-header">
                                    <strong class="reviewer">${review.reviewer}</strong>
                                    <small class="pull-right text-muted">${review.createdAt}</small>
                                </div>
                                <div class="review-content-content">
                                    <p>${review.reviewContent}</p>
                                </div>
                            </li>
                            <c:if test="${review.answerContent != null}">
                                <div class="review-content-reply">
                                    <li class="review-content">
                                        <div class="review-content-header">
                                            <strong class="reviewer">관리자</strong>
                                            <small class="pull-right text-muted">${review.answerCreatedAt}</small>
                                        </div>
                                        <div class="review-content-content">
                                            <p>${review.answerContent}</p>
                                        </div>
                                    </li>
                                </div>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
                <div class="product-detail-review-paging">
                    <div class="paging">
                        <ul class="pagination">
                            <c:if test="${product.reviews.pageMaker.prev}">
                                <li class="paginate_button previous">
                                    <a href="${product.reviews.pageMaker.startPage - 1}">Prev</a>
                                </li>
                            </c:if>

                            <c:if test="${product.reviews.pageMaker.endPage != 0}">
                                <c:forEach var="num" begin="${product.reviews.pageMaker.startPage}"
                                           end="${product.reviews.pageMaker.endPage}">
                                    <li class="paginate_button ${product.reviews.pageMaker.cri.pageNum == num?"active":""}">
                                        <a href="${num}">${num}</a>
                                    </li>
                                </c:forEach>
                            </c:if>

                            <c:if test="${product.reviews.pageMaker.next}">
                                <li class="paginate_button next">
                                    <a href="${product.reviews.pageMaker.endPage + 1}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="product-detail-qna">
                <div class="product-detail-qna-header">
                    <h2>상품 문의</h2>
                    <div class="qna-input">
                        <textarea id="qna-text-input" name="qna-text"></textarea>
                    </div>
                    <button type="button" onclick="qnaBtnOnClick()">문의하기</button>
                </div>
                <div class="product-detail-qna-content">
                    <ul>
                        <c:forEach items="${product.qnAs.content}" var="qna">
                            <li class="qna-content-default">
                                <div class="qna-content-header">
                                    <strong class="qna-writer">${qna.writer}</strong>
                                    <small class="pull-right text-muted">${qna.createdAt}</small>
                                    <c:if test="${qna.status}">
                                        <small class="pull-right answer">답변완료</small>
                                    </c:if>
                                </div>
                                <div class="qna-content-content">
                                    <p>${qna.qnaContent}</p>
                                </div>
                            </li>
                            <c:if test="${qna.replyList.size() != 0}">
                                <c:forEach items="${qna.replyList}" var="reply">
                                    <div class="qna-content-reply">
                                        <li class="qna-content">
                                            <div>
                                                <div class="qna-content-header">
                                                    <strong class="qna-writer">${reply.writer}</strong>
                                                    <small class="pull-right text-muted">${reply.createdAt}</small>
                                                </div>
                                                <div class="qna-content-content">
                                                    <p>${reply.content}</p>
                                                </div>
                                            </div>
                                        </li>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
                <div class="product-detail-qna-paging">
                    <div class="paging">
                        <ul class="pagination">
                            <c:if test="${product.reviews.pageMaker.prev}">
                                <li class="paginate_button previous">
                                    <a href="${product.reviews.pageMaker.startPage - 1}">Prev</a>
                                </li>
                            </c:if>

                            <c:if test="${product.reviews.pageMaker.endPage != 0}">
                                <c:forEach var="num" begin="${product.reviews.pageMaker.startPage}"
                                           end="${product.reviews.pageMaker.endPage}">
                                    <li class="paginate_button ${product.reviews.pageMaker.cri.pageNum == num?"active":""}">
                                        <a href="${num}">${num}</a>
                                    </li>
                                </c:forEach>
                            </c:if>

                            <c:if test="${product.reviews.pageMaker.next}">
                                <li class="paginate_button next">
                                    <a href="${product.reviews.pageMaker.endPage + 1}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="product-detail-order-info">
                <div class="product-detail-order-info-header">
                    <h2>배송 정보</h2>
                </div>
                <div class="product-detail-order-info-content">
                    <table class="delivery-info" border="1">
                        <tbody>
                        <tr>
                            <th>배송 방법</th>
                            <td>순차 배송</td>
                        </tr>
                        <tr>
                            <th>묶음배송 여부</th>
                            <td>가능</td>
                        </tr>
                        <tr>
                            <th>배송비</th>
                            <td>
                                <ul>
                                    <li>3,500원 / 10만원 이상 구매시 무료배송</li>
                                    <li>제주, 도서산간 지역 배송은 5,000원 / 10만원 이상 구매시 무료배송</li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <th>배송기간</th>
                            <td>결제일 기준 평균 2 ~ 4일 소요</td>
                        </tr>
                        <tr>
                            <th>무통장입금 계좌번호</th>
                            <td>
                                <ul>
                                    <li>농협 000-000000-000</li>
                                    <li>기업은행 0000-00000-000</li>
                                </ul>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="product-detail-order-info-header">
                    <h2>교환 / 반품 안내</h2>
                </div>
                <div class="product-detail-order-info-content">
                    <table class="delivery-info" border="1">
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

                <div class="product-detail-order-info-header">
                    <h2>교환 / 반품 제한사항</h2>
                </div>
                <div class="product-detail-order-info-content">
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
</div>
</body>
</html>
