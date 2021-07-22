<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div>
    <div class="header">
        <h1>리뷰</h1>
    </div>
    <div>
        <div class="title">
            <img src="/display?image=${rDetail.firstThumbnail}">
            <label>${rDetail.PName}</label>
            <span>
                ${rDetail.reviewContent}
                <p>${rDetail.reviewDate}</p>
            </span>
        </div>
    </div>
    <button type="button" id="modifyReview" value="${rDetail.RNum}">리뷰수정</button>
    <button type="button" id="deleteReview" value="${rDetail.RNum}">리뷰삭제</button>
</div>
<script>
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
</script>
</body>
</html>
