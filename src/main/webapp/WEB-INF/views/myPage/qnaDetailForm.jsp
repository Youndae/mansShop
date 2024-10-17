<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<body>
<div class="mypage-qna-content-title">
  <c:set var="status" value=""/>
  <c:choose>
    <c:when test="${detail.status}">
      <c:set var="status" value="답변 완료"/>
    </c:when>
    <c:otherwise>
      <c:set var="status" value="미답변"/>
    </c:otherwise>
  </c:choose>
  <h2>${detail.title}(${status})</h2>
  <small class="qna-reply-date">${detail.date}</small>
</div>
<div class="mypage-qna-content-content">
  <p class="qna-detail-content">
    ${detail.content}
  </p>
</div>
</body>
</html>
