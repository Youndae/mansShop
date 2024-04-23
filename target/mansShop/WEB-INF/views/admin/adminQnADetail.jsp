<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminOrder.js"></script>
<link rel="stylesheet" href="/css/adminMyPage.css">
<link rel="stylesheet" href="/css/adminQnADetail.css">
<link rel="stylesheet" href="/css/default.css">
<body>
    <jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">
        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="QnADetail_content content_data">
                <div class="QnADetail_QnATitle">
                    <span class="QnATitle">
                        <c:out value="${aqDetail.QTitle}"/>
                    </span>
                    <div class="QnATitle_writer wd_full">
                        <span class="QnAWriter">
                        작성자 : <c:out value="${aqDetail.userId}"/>
                    </span>
                    </div>
                </div>
                <div class="QnADetail_content_content">
                    <c:out value="${aqDetail.QContent}"/>
                </div>

                <div class="QnAReply_area">
                    <div class="reply_btn_area">
                        <div class="QnAReply_btn">
                            <c:if test="${aqDetail.QStat == 0}">
                                <button type="button" id="QnAComplete">처리완료</button>
                            </c:if>
                        </div>
                        <div class="QnAReply_input">
                            <form id="replyForm">
                                <input type="text" name="qrContent">
                                <input type="hidden" id="qno" name="qno" value="${aqDetail.qno}">
                            </form>
                            <button type="button" id="replyForm_submit">등록</button>
                        </div>
                    </div>
                    <div class="QnAReply">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
