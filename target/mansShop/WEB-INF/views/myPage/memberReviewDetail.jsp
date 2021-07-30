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
        margin: 0 0 0 486px;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 100px;
    }

    .reviewDetail_header{
        margin: 40px 0 50px 100px;
    }

    .reviewDetail_content{
        margin: 50px 0 50px 100px;
        width: 100%;
        height: 100%;
    }

    .reviewDetail_content .reviewDetail_title{
        text-align: center;
        display: flex;
        font-size: xx-large;
        border: solid black;
        border-width: 0 0 4px 0;
        width: 770px;
        padding-left: 38%;
    }

    .reviewDetail_content .reviewDetail_reviewContent{
        width: 1300px;
        height: 100%;
        border: 1px solid lightgray;
        margin-top: 52px;
    }

    .reviewDetail_btn{
        text-align: center;
    }

    .reviewDetail_btn button{
        width: 100px;
        height: 30px;
        margin: 20px;
    }

</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>

    <div class="content_area">
        <div class="reviewDetail_header">
            <h1>리뷰</h1>
        </div>

        <div class="reviewDetail_content">
            <div class="reviewDetail_title">
                <c:out value="${rDetail.PName}"/>
            </div>
            <div class="reviewDetail_reviewContent">
                <c:out value="${rDetail.reviewContent}"/>
            </div>
            <div class="reviewDetail_btn">
                <button type="button" id="modifyReview" value="${rDetail.RNum}">리뷰수정</button>
                <button type="button" id="deleteReview" value="${rDetail.RNum}">리뷰삭제</button>
            </div>
        </div>
    </div>

</div>
</div>
<%--<script>
    $(function(){
        $("#modifyReview").on('click',function(){
            var rNum = $(this).val();

            location.href='/myPage/memberReviewModify/' + rNum;
        })

        $("#deleteReview").on('click',function(){
            var rNum = $(this).val();
            var result = confirm("리뷰를 삭제하게 되면 해당 상품의 리뷰를 재작성할 수 없습니다.\n 정말 삭제하시겠습니까?");

            if(result){
                location.href='/myPage/memberReviewDelete/' + rNum;
            }

        })
    })
</script>--%>
</body>
</html>
