<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</head>
<body>
<div>
    <div class="header">
        <h1>기간별 매출</h1>
        <div class="select">
            <form id="salesProductActionForm" action="/admin/salesTermList" method="get">
                <label>연도별 : </label>
                <select id="select_Term_Year">

                </select>
                <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
                <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
                <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>">
            </form>
        </div>
    </div>

    <div class="tbl_salesTerm">
        <table>
            <thead>
                <tr>
                    <th>기간</th>
                    <th>주문건수</th>
                    <th>매출액</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${stList}" var="list">
                <tr>
                    <td><c:out value="${list.salesTerm}"/></td>
                    <td><c:out value="${list.salesOrders}"/></td>
                    <td><fmt:formatNumber value="${list.salesSum}" pattern="#,###"/>원</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="salesSum_Year">
            <c:if test="${pageMaker.cri.keyword != null && pageMaker.cri.keyword != ''}">
                <c:set var="total" value="0"/>
                <c:forEach items="${stList}" var="sum">
                    <c:set var="total" value="${total + sum.salesSum}"/>
                </c:forEach>

                <span>연매출 : <fmt:formatNumber value="${total}" pattern="#,###"/> 원</span>
            </c:if>
        </div>

        <div class="paging">
            <ul class="pagination">
                <c:if test="${pageMaker.prev}">
                    <li class="paginate_button previous">
                        <a href="${pageMaker.startPage - 1}">Prev</a>
                    </li>
                </c:if>

                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <c:if test="${pageMaker.endPage >= 2}">
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
</div>

<script>
$(document).ready(function(){

    var actionForm = $("#salesProductActionForm");

    (function(){
        $.getJSON("/admin/salesTermSelect", function(arr){

            var str = "";
            var optionYear = "";
            $(arr).each(function(i, termYear){
                var year = termYear.salesTerm.substring(0, 4);

                if(optionYear != year){
                    optionYear = year;
                    str += "<option value=\"" + optionYear +"\">" +
                        optionYear + "</option>";
                }
            });

            $("#select_Term_Year").append(str);
        })
    })();




    $(".paginate_button a").on('click', function(e){
        e.preventDefault();

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    });

    $("#select_Term_Year").on("propertychange change keyup paste input", function(){

        actionForm.find("input[name='pageNum']").val("1");
        actionForm.find("input[name='keyword']").val($("#select_Term_Year").val());
        actionForm.find("input[name='amount']").val("12");

        actionForm.submit();
    })

})
</script>
</body>
</html>
