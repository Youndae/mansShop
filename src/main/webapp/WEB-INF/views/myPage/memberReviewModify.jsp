<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/memberReview.css">
<link rel="stylesheet" href="/css/default.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <jsp:include page="/WEB-INF/views/layout/sidenav.jsp"/>
    <div class="content_area">
        <div class="review_content content_data">
            <div class="reviewForm_area">
                <form id="reviewModifyForm" action="/my-page/review/${rModify.RNum}" method="post">
                    <div class="review_content_productName">
                        <div>
                            <label><c:out value="${rModify.PName}"/></label>
                        </div>
                    </div>
                    <div class="review_content_content">
                        <div>
                            <textarea name="reviewContent"><c:out value="${rModify.reviewContent}"/></textarea>
                        </div>
                    </div>
                    <input type="hidden" name="_method" value="PATCH">
                    <sec:csrfInput/>
                </form>
            </div>
            <button type="button" id="reviewModifyProc">리뷰 수정</button>
        </div>
    </div>
</div>
</div>
</body>
</html>
