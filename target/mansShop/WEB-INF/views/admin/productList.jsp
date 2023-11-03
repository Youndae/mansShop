<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminProduct.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/productList.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
    <div class="content_area">
        <div class="productList_header content_header">
            <h1>상품목록</h1>
            <button type="button" id="addProduct">상품추가</button>
        </div>
        <div class="productList_classification">
            <a href="OUTER">OUTER</a>
            <a href="TOP">TOP</a>
            <a href="PANTS">PANTS</a>
            <a href="SHOES">SHOES</a>
            <a href="BAGS">BAGS</a>
        </div>

        <div class="productList_content content_data">
            <table class="tbl_h_2" border="1">
                <thead>
                <tr class="tbl_tr_m_10">
                    <th>상품번호</th>
                    <th>상품분류</th>
                    <th>상품명</th>
                    <th>가격</th>
                    <th>사이즈</th>
                    <th>컬러</th>
                    <th>등록일</th>
                </tr>
                </thead>
                <c:forEach items="${pList}" var="list">
                    <tr class="tbl_tr_m_10" id="productInfo">
                        <td id="productNo"><c:out value="${list.pno}"/></td>
                        <td><c:out value="${list.PClassification}"/></td>
                        <td>
                            <a href="<c:url value="/admin/productInfo/${list.POpNo}"/>"><c:out value="${list.PName}"/></a>
                        </td>
                        <td><c:out value="${list.PPrice}"/></td>
                        <td><c:out value="${list.PSize}"/></td>
                        <td><c:out value="${list.PColor}"/></td>
                        <td><c:out value="${list.PRegDate}"/></td>
                    </tr>
                </c:forEach>
            </table>

            <div class="productList_content_bottom">
                <div class="search">
                    <div>
                        <form id="searchActionForm" action="/admin/productList" method="get">
                            <input type="text" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"/>'/>
                            <input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum}"/>'/>
                            <input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount}"/>'/>
                            <button class="btn">Search</button>
                        </form>
                    </div>
                </div>

                <div class="paging">
                    <ul class="pagination">
                        <c:if test="${pageMaker.prev}">
                            <li class="paginate_button previous">
                                <a href="${pageMaker.startPage - 1}">Prev</a>
                            </li>
                        </c:if>

                        <c:if test="${pageMaker.endPage != 1}">
                            <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                                <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                                    <a href="${num}">${num}</a>
                                </li>
                            </c:forEach>
                        </c:if>

                        <c:if test="${pageMaker.next}">
                            <li class="paginate_button next">
                                <a href="${pageMaker.endPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
    </div>

        <form id="pageActionForm" action="/admin/productList" method="get">
            <input type="hidden" id="classification" name="classification" value="<c:out value="${pageMaker.cri.classification}"/>">
            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
            <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
        </form>
    </div>
</div>
</div>
</body>
</html>
