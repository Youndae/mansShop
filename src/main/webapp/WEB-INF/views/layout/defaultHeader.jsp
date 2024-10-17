<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
    <%--<meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">--%>
</head>
<script type="text/javascript" src="/js/header.js"></script>
<link rel="stylesheet" href="/css/header.css">
<body>

<div class="header">
    <div class="header-nav">
        <div class="header-nav logo">
            <a class="logo-link" href="/"><span>Man's Shop</span></a>
        </div>
        <div class="header-nav menu">
            <ul class="menu-nav">
                <sec:authentication property="principal" var="userStat"/>
                <c:choose>
                    <c:when test="${userStat != 'anonymousUser'}">
                        <form id="logoutForm" action="/logout" method="post">
                            <li>
                                <button class="header-btn" type="button" onclick="logoutBtnOnClick()">로그아웃</button>
                            </li>
                            <sec:csrfInput/>
                        </form>
                        <sec:authentication property="authorities" var="userAuthorities"/>
                        <c:choose>
                            <c:when test="${fn:contains(userAuthorities, 'ROLE_ADMIN')}">
                                <li>
                                    <button class="header-btn" type="button" onclick="adminBtnOnClick()">관리자 페이지</button>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <button class="header-btn" type="button" onclick="myPageBtnOnClick()">마이페이지</button>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <button class="header-btn" type="button" onclick="loginBtnOnClick()">로그인</button>
                        </li>
                        <li>
                            <button class="header-btn" type="button" onclick="nonUserOrderBtnOnClick()">주문조회</button>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li>
                    <button class="header-btn" type="button" onclick="cartBtnOnClick()">장바구니</button>
                </li>
                <li>
                    <div class="main-search-form">
                        <form action="/search" id="main-search" method="get">
                            <input type="text" id="search-keyword" value="${pageMaker.cri.keyword}">
                            <div class="search-btn">
                                <button class="search-button" type="button">
                                    <img src="https://as1.ftcdn.net/v2/jpg/03/25/73/68/1000_F_325736897_lyouuiCkWI59SZAPGPLZ5OWQjw2Gw4qY.jpg" alt="">
                                </button>
                            </div>
                            <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                            <input type="hidden" name="page" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                        </form>
                    </div>
                </li>
            </ul>
        </div>
        <div class="header-nav top">
            <button type="button" onclick="navBtnOnClick(this)">BEST</button>
            <button type="button" onclick="navBtnOnClick(this)">NEW</button>
            <button type="button" onclick="navBtnOnClick(this)">OUTER</button>
            <button type="button" onclick="navBtnOnClick(this)">TOP</button>
            <button type="button" onclick="navBtnOnClick(this)">PANTS</button>
            <button type="button" onclick="navBtnOnClick(this)">SHOES</button>
            <button type="button" onclick="navBtnOnClick(this)">BAGS</button>
        </div>
    </div>
</div>
</body>
</html>
