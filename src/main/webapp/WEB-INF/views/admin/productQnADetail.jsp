<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/default.css">
<link rel="stylesheet" href="/css/productQnADetail.css">
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="pQnADetail_content content_data">
                <div class="pQnADetail_productName">
                    <span class="productName">
                        <c:out value="${detail.PName}"/>
                    </span>
                    <div class="productName_writer">
                        <span class="QnAWriter">
                            작성자 : <c:out value="${detail.userId}"/>
                        </span>
                    </div>
                </div>
                <div class="pQnADetail_content_content">
                    <c:out value="${detail.PQnAContent}"/>
                </div>

                <div class="pQnAReply_area">
                    <div class="pQnAReply_input">
                        <form id="pQnAReplyForm" method="post">
                            <textarea name="pQnAContent"></textarea>
                            <input type="hidden" name="pQnANo" value="${detail.PQnANo}">
                            <input type="hidden" name="pno" value="${detail.pno}">
                            <sec:csrfInput/>
                        </form>
                        <button type="button" id="pQnAReplyForm_btn">등록</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
