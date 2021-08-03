<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .likeList_content img{
        width: 200px;
    }

    .container .header{
        text-align: center;
    }

    /*.container header{
        text-align: center;
    }*/

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .side_nav{
        margin: 0 0 0 486px;
    }

    .likeList_header{
        margin: 40px 0 50px 100px;
    }

    .likeList_content{
        margin: 50px 0 50px 100px;
        width: 100%;
        height: 100%;
        display: flex;
        flex-wrap: wrap;
    }

    .likeList_content{
        /*margin-top: 100px;*/

    }

    .likeList_content div{
        width: 100%;
        /*height: 250px;*/
        flex: 0 0 25%;
    }

    .content_area{
        width: 50%;
    }

    .paging ul{
        margin-left: 50%;
    }

    .paging li{
        list-style: none;
        display: inline;
        margin: 10px;
        font-size: large;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">

    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">
        <div class="likeList_header">
            <h1>찜한상품</h1>
        </div>

        <div class="likeList_content">
            <c:forEach items="${lList}" var="list">
                <div class="likeList_img">
                    <div class="likeThumb_img">
                        <a href="/product/${list.pno}">
                            <img src="/display?image=${list.firstThumbnail}">
                        </a>
                    </div>
                    <div class="likeProductInfo">
                        <span class="pName">${list.PName}</span>
                        <span class="pPrice"><fmt:formatNumber value="${list.PPrice}" pattern="#,###"/> 원</span>
                    </div>
                </div>
                <%--<a href="/${list.pno}">
                    <img src="/display?image=${list.firstThumbnail}">
                    <label>${list.PName}</label>
                    <span>${list.PPrice}</span>
                </a>--%>
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


        <form id="pageActionForm" action="/myPage/likeList" method="get">
            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        </form>
    </div>

</div>
</div>
<%--<script>
    $(document).ready(function(){
        var actionForm = $("#actionForm");

        $(".paginate_button a").on("click", function (e) {
            e.preventDefault();

            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });
    })
</script>--%>
</body>
</html>
