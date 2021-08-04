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

    .memberReviewList_content img{
        width: 150px;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .memberReviewList_header{
        margin: 40px 0 50px 200px;
    }

    .memberReviewList_header button{
        float: right;
    }

    .memberReviewList_content{
        margin: 50px 0 50px 200px;
        width: 100%;
        height: 100%;
    }

    .memberReviewList_content table{
        font-size: large;
        margin: 25px 0 25px 0;
        width: 1000px;
    }

    .paging li{
        list-style: none;
        display: inline;
        margin: 10px;
        font-size: large;
    }

    .tbl_review_a{
        display: inline-flex;
        width: 100%;
    }

    .tbl_productName{
        width: 100%;
        text-align: center;
        margin: 11% 10% 0 0;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
   <div class="memberReviewList">
       <div class="memberReviewList_header">
           <h1>리뷰 목록</h1>
       </div>
       <div class="memberReviewList_content">
           <table border="1">
               <thead>
               <tr>
                   <th>상품명</th>
                   <th>작성일</th>
               </tr>
               </thead>
               <tbody>
               <c:forEach items="${rList}" var="list">
                   <tr>
                       <td>
                           <a class="tbl_review_a" href="<c:url value="/myPage/memberReviewDetail/${list.RNum}"/>">
                               <div class="tbl_firstThumbnail">
                                   <img src="/display?image=${list.firstThumbnail}">
                               </div>
                                <div class="tbl_productName">
                                   <span>${list.PName}</span>
                                </div>
                           </a>
                       </td>
                       <td class="tbl_reviewDate">${list.reviewDate}</td>
                   </tr>
               </c:forEach>
               </tbody>
           </table>
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

        <form id="pageActionForm" action="/myPage/memberReviewList" method="get">
            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>">
            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>">
        </form>
    </div>
</div>
</div>
</body>
</html>
