<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <div class="header">
        <h1>리뷰 수정</h1>
    </div>
    <div>
        <form id="reviewModifyForm" method="post">
            <div>
                <label>상품명</label>
                <span><c:out value="${rModify.PName}"/></span>
            </div>
            <div>
                <label>리뷰 내용</label>
                <textarea name="reviewContent"><c:out value="${rModify.reviewContent}"/></textarea>
            </div>
            <input type="hidden" name="rNum" value="<c:out value="${rModify.RNum}"/>">
            <sec:csrfInput/>
        </form>
    </div>
    <button type="button" id="reviewModifyProc">리뷰 수정</button>
</div>
</div>
<%--<script>
    $(function(){
        $("#reviewModifyProc").on('click', function(){
            var form = $("#reviewModifyForm");

            form.attr("action", "/myPage/memberReviewModify");
            form.submit();
        })
    })
</script>--%>
</body>
</html>
