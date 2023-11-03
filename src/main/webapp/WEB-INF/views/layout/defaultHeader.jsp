<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<link rel="stylesheet" href="/css/header.css">
<script type="text/javascript" src="/js/header.js"></script>
<body>
<div class="container">
    <div class="header">
        <div class="header_nav">
            <div class="header_nav logo">
                <a href="/"><span>Man's Shop</span></a>
            </div>
            <div class="header_nav menu">
                <ul class="menu_nav">
                    <sec:authentication property="principal" var="userStat"/>
                    <c:choose>
                        <c:when test="${userStat != 'anonymousUser'}">
                            <form id="logoutForm" action="/logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <li><button>로그아웃</button></li>
                            </form>
                            <li><a href="/myPage/modifyCheck">마이페이지</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/member/login">로그인</a></li>
                        </c:otherwise>
                    </c:choose>

                    <li><a href="/myPage/cart">장바구니</a></li>
                    <li><a href="/myPage/memberOrderList">주문조회</a></li>
                    <li>
                        <form id="mainSearchForm" action="/searchProduct" method="get">
                            <input type="text" id="search_keyword">
                            <div class="form_button">
                                <button class="btn" type="button" >
                                    <img src="https://as1.ftcdn.net/v2/jpg/03/25/73/68/1000_F_325736897_lyouuiCkWI59SZAPGPLZ5OWQjw2Gw4qY.jpg">
                                </button>
                            </div>
                            <input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                        </form>
                    </li>
                </ul>
            </div>
            <div class="header_nav top">
                <a href="#">BEST</a>
                <a href="#">NEW</a>
                <a href="#">OUTER</a>
                <a href="#">TOP</a>
                <a href="#">PANTS</a>
                <a href="#">SHOES</a>
                <a href="#">BAGS</a>
            </div>
        </div>
    </div>
</body>
</html>
