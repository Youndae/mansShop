<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    /*.content{
    padding: 20px 0 0 0;
    margin: 0 0 0 100px;
    }

    .content .product_header{
        width: 200px;
        display: contents;
    }

    .content .product_content{
        margin-top: 100px;
        display: flex;
        flex-wrap: wrap;
    }

    .product_content  div{
        width: 100%;
        height: 250px;
        flex : 0 0 33.33333%;
    }*/

</style>
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

                            <li><a href="/myPage/cart">장바구니</a></li>
                            <li><a href="/myPage/modifyInfo">마이페이지</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/member/login">로그인</a></li>
                        </c:otherwise>
                    </c:choose>


                    <li><a href="/myPage/memberOrderList">주문조회</a></li>
                    <li>
                        <form id="mainSearchForm" action="/main/searchProduct" method="get">
                            <input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
                            <input type="hidden" name="pageNum" value="<c:out value="${pageMaker.cri.pageNum}"/>"/>
                            <input type="hidden" name="amount" value="<c:out value="${pageMaker.cri.amount}"/>"/>
                            <button class="btn">검색</button>
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
