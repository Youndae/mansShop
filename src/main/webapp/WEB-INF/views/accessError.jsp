<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
<div class="content">
    <h1>Access Denied page</h1>

    <h2><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}"/></h2>
    <h2><c:out value="${msg}"/></h2>
</div>
</div>
</body>
</html>
