<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .thumbnail img{
        width: 300px;
    }
    .container div{
        text-align: center;

    }

    .content div{
        display: table;
    }

    .content{
        padding: 20px 0 0 0;
        margin: 0 0 0 100px;
    }

    .content .product_header{
        width: 200px;
        display: contents;
    }

    .content .product_content{
        margin-top: 100px;
        display: flex;
        flex-wrap: wrap;
    }

    .product_content  div{
        width: 100%;
        height: 250px;
        flex : 0 0 33.33333%;
    }

    .paging{
        width: 100%;
    }

    .paging li{
        list-style: none;
        display: inline;
        margin: 10px;
        font-size: large;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/main.js"></script>
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
                            <a href="${list.pno}" class="thumbnail"><img id="ImageData" src="/display?image=${list.firstThumbnail}"/></a>
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

                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <c:if test="${num == 0 || num == 1}">
                        <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                            <a href="${num}">${num}</a>
                        </li>
                    </c:if>
                </c:forEach>

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
</div><%--container div end--%>

<%--<script>
    $(document).ready(function(){
        $(".thumbnail").on('click', function(e){
            var pno = $(this).href;

            location.href="/"+pno;
        })
    })
</script>--%>
</body>
</html>
