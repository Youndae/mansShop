<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminProduct.js"></script>
<body>
<div class="productList">
    <h1>ProductList</h1>
</div>
<div class="productList-classification">
    <a href="OUTER">OUTER</a>
    <a href="TOP">TOP</a>
    <a href="PANTS">PANTS</a>
    <a href="SHOES">SHOES</a>
    <a href="BAGS">BAGS</a>
</div>

<div class="content">
    <table>
        <thead>
            <tr>
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
            <tr id="productInfo">
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

    <div class="search">
        <div>
            <form id="searchForm" action="/admin/productList" method="get">
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

            <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                <li class="paginate_button ${pageMaker.cri.pageNum == num?"active":""}">
                    <a href="${num}">${num}</a>
                </li>
            </c:forEach>

            <c:if test="${pageMaker.next}">
                <li class="paginate_button next">
                    <a href="${pageMaker.endPage + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </div>

    <form id="actionForm" action="/admin/productList" method="get">
        <input type="hidden" id="classification" name="classification" value="<c:out value="${pageMaker.cri.classification}"/>">
        <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
        <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
    </form>
</div>
<%--<script>
    $(document).ready(function(){

        var actionForm = $("#actionForm");

        var n = null;

        $(".paginate_button a").on("click", function(e){
            e.preventDefault();

            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });

        $(".productList-classification a").on('click', function(e){
            e.preventDefault();

            actionForm.find("input[name='classification']").val($(this).attr("href"));
            actionForm.find("input[name='pageNum']").val("1");
            actionForm.submit();
        });

        var searchForm = $("#searchForm");
        $("#searchForm button").on('click', function(e){
            if(!searchForm.find("input[name='keyword']").val()){
                alert("키워드 입력");
            }

            searchForm.find("input[name='pageNum']").val("1");
            e.preventDefault();

            searchForm.submit();
        })
    })
</script>--%>
<%--<script>
    $(document).ready(function(){
        (function(){


            $.getJSON("/admin/getAttachList", function(arr){
                console.log(arr);

                var str = "";

                $(arr).each(function(i, attach){
                    //image type

                        /*var fileCallPath = encodeURIComponent(attach.firstThumbnail);*/

                        str += "<li data-filename='" + attach.firstThumbnail + "'><div>";
                        str += "<img src='/admin/display?firstThumbnail=" + attach.firstThumbnail + "'>";
                        str += "</div>";
                        str += "</li>";
                });

                $(".testImg ul").html(str);
            });
        })();
    });
</script>--%>
</body>
</html>
