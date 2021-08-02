<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 30%;
    }



    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .salesProductList_header{
        margin: 40px 0 50px 200px;
    }

    .salesProductList_content{
        margin: 50px 0 50px 200px;
        width: 100%;
        height: 100%;
    }

    .salesProductList_content table{
        font-size: large;
        margin: 25px 0 25px 0;
        width: 1000px;
        text-align: center;
        border: 1px solid lightgray;
    }

    table tr{
        margin: 10px;
    }

    .paging li{
        list-style: none;
        display: inline;
        margin: 10px;
        font-size: large;
    }

    .salesProductList_content_bottom{
        display: flex;
    }

    .salesProductList_content_bottom .paging{
        margin-left: 10px;
    }

    .salesProductList_btn{
        width: 1000px;
    }

    .salesProductList_btn button{
        float: right;
        margin-left: 5px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminSales.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
    <div class="content_area">
        <div class="salesProductList_header">
            <h1>상품별 매출</h1>
        </div>
        <div class="salesProductList_content">
            <div class="salesProductList_btn">
                <button type="button" id="sales" value="${pageMaker.cri.sortType}">매출순</button>
                <button type="button" id="salesRate" value="${pageMaker.cri.sortType}">판매량</button>
            </div>
            <table class="sales_productList" border="1">
                <thead>
                <tr>
                    <th>상품분류</th>
                    <th>상품명</th>
                    <th>사이즈</th>
                    <th>컬러</th>
                    <th>가격</th>
                    <th>판매량</th>
                    <th>매출액</th>
                </tr>
                </thead>
                <tbody class="tbl_sales_product_list">
                <c:forEach items="${spList}" var="list">
                    <tr>
                        <td>${list.PClassification}</td>
                        <td>${list.PName}</td>
                        <td>${list.PSize}</td>
                        <td>${list.PColor}</td>
                        <td>${list.PPrice}</td>
                        <td>${list.opSalesRate}</td>
                        <td>${list.opSales}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="salesProductList_content_bottom">
                <div class="search">
                    <div>
                        <form id="searchActionForm" action="/admin/salesProductList" method="get">
                            <select name="type">
                                <option value="classification">상품분류</option>
                                <option value="pName">상품명</option>
                            </select>
                            <input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                            <input type="hidden" name="sortType" value="<c:out value="${pageMaker.cri.sortType}"/>"/>
                            <!-- sortType의 0은 default, 1은 매출 내림차순 2는 매출 오름차순 3은 판매량 내림차순 4는 판매량 오름차순
                                혹은 salesRateUp  salesRateDown 이런 형태로. 일단은 숫자로 하고 고민좀 해야 할듯.-->
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
            </div>
            <form id="pageActionForm" action="/admin/salesProductList" method="get">
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
                <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
                <input type="hidden" name="type" value="<c:out value="${pageMaker.cri.type}"/>">
                <input type="hidden" name="sortType" value="<c:out value="${pageMaker.cri.sortType}"/>"/>
            </form>
        </div>
    </div>
</div>
</div>
<%--<script>
    $(document).ready(function(){

        var actionForm = $("#salesProductActionForm");
        var SearchForm = $("#salesProductSearchForm");

        $(".paginate_button a").on('click', function(e){
            e.preventDefault();

            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        })


        $("#salesProductSearchForm button").on('click', function(e){
            if(!SearchForm.find("input[name='keyword']").val()){
                alert('키워드 입력');
            }

            SearchForm.find("input[name='pageNum']").val("1");
            e.preventDefault();

            SearchForm.submit();
        });

        $("#sales").on('click', function(e){
           if($(this).val() == 1)
               $('input[name=sortType]').val("2");//2는 오름차순. null이거나
           else
               $('input[name=sortType]').val("1");

            actionForm.find("input[name='pageNum']").val("1");
            actionForm.submit();
        });

        $("#salesRate").on('click', function(e){
            if($(this).val() == 3)
                $('input[name=sortType]').val("4");
            else
                $('input[name=sortType]').val("3");

            actionForm.find("input[name='pageNum']").val("1");
            actionForm.submit();
        })
    })
</script>--%>
</body>
</html>
