<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/main.js"></script>
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <div class="product_header">
            <h1>${productClassification}</h1>
        </div>
        <div class="product_content">
            <c:forEach items="${pList}" var="list">
                <div class="product_img">

                        <div class="thumb_img">
                            <a href="/product/${list.pno}" class="productThumbnail"><img id="ImageData" src="/display?image=${list.firstThumbnail}"/></a>
                        </div>
                        <div class="productInfo">
                            <span class="pName">${list.PName}</span><br>
                            <span class="pPrice"><fmt:formatNumber value="${list.PPrice}" pattern="#,###"/> 원</span>
                        </div>
                </div>
            </c:forEach>
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
    <form id="pageActionForm" method="get">
        <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
        <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
    </form>
</div>
</body>
</html>
