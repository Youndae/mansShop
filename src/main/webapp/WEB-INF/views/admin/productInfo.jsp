<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/adminProduct.js"></script>
<style>
    .container .header{
        text-align: center;
    }

    .side_nav{
        margin: 0 0 0 221px;
    }

    .content_area{
        margin-left: 100px;
        height: 100%;
    }

    .content{
        display: inline-flex;
        padding: 100px 0 0 0;
        margin: 0 0 0 359px;
    }

    .addProduct_header{
        margin: 40px 0 50px 0;
        width: 100%;
    }

    .button-area{
        margin-right: 20%;
    }

    .button-area button{
        float: right;
        margin-right: 10px;
    }

    .addProduct_content{
        font-size: large;
        font-weight: bold;
    }

    .addProduct_content div{
        margin: 25px 0 25px 0;
    }

    .addProduct_content input{
        width: 250px;
    }

    .addProduct_content select{
        width: 250px;
    }

    img{
        width: 300px;
        height: 300px;
    }

    .overlap{
        font-size: 12px;
        font-weight: 100;
        color: red;
    }

    .addProduct_img_area{
        width: 1200px;
    }

    .label_t2 label{
        margin-right: 80px;
    }

    .label_t3 label{
        margin-right: 62px;
    }

    .label_t4 label{
        margin-right: 44px;
    }

    #infoPreview{
        float: left;
    }

    #infoPreview img{
        width: 100%;
        height: 10%;
    }
</style>
<body>
<jsp:include page="/WEB-INF/views/layout/defaultHeader.jsp"/>
    <div class="content">

        <jsp:include page="/WEB-INF/views/layout/adminSidenav.jsp"/>
        <div class="content_area">
            <div class="addProduct_header">
                <h1>????????????</h1>
                <div class="button-area">
                    <button type="button" id="addProductOp">?????? ??????</button>
                    <button type="button" id="modifyProduct">??????</button>
                </div>
            </div>

            <div class="addProduct_content">
                <div class="addProduct_form">
                    <form id="modifyProductForm" method="post" action="/admin/addProductOp">
                        <div class="label_t4">
                            <label>?????? ??????</label>
                            <select name="pClassification">
                                <option value="default">------</option>
                                <option value="OUTER">OUTER</option>
                                <option value="TOP">TOP</option>
                                <option value="PANTS">PANTS</option>
                                <option value="SHOES">SHOES</option>
                                <option value="BAGS">BAGS</option>
                            </select>
                            <div class="overlap" id="checkClassification"></div>
                            <input type="hidden" value="${info.PClassification}" id="classification">
                        </div>
                        <div class="label_t3">
                            <label>?????????</label>
                            <input type="text" name="pName" value="${info.PName}">
                            <div class="overlap" id="checkPName"></div>
                        </div>
                        <div class="label_t3">
                            <label>?????????</label>
                            <select name="pSize">
                                <option value="default">------</option>
                                <option value="S">S</option>
                                <option value="M">M</option>
                                <option value="L">L</option>
                                <option value="XL">XL</option>
                                <option value="XXL">XXL</option>
                                <option value="FREE">FREE</option>
                            </select>
                            <div class="overlap" id="checkPSize"></div>
                            <input type="hidden" value="${info.PSize}" id="size">
                        </div>
                        <div class="label_t2">
                            <label>??????</label>
                            <input type="text" id="pColor" name="pColor" value="${info.PColor}">
                            <div class="overlap" id="checkPColor"></div>
                        </div>
                        <div class="label_t2">
                            <label>??????</label>
                            <input type="text" name="pPrice" value="${info.PPrice}">
                            <div class="overlap" id="checkPPrice"></div>
                        </div>
                        <div class="label_t2">
                            <label>??????</label>
                            <input type="text" name="pStock" placeholder="???????????? ????????? 0?????? ???????????????." value="${info.PStock}">
                            <div class="overlap" id="checkPStock"></div>
                        </div>
                        <input type="hidden" name="pno" value="${info.pno}">
                        <input type="hidden" name="pOpNo" value="${info.POpNo}">
                        <sec:csrfInput/>
                    </form>
                    <input type="hidden" id="pClosed" value="${info.PClosed}">
                    <input type="hidden" id="opClosed" value="${info.opClosed}">
                </div>
                <div class="addProduct_img_area">
                    <div id="firstThumb">
                        <label>???????????????</label>
                        <input type="file" name="firstThumbnail" value="?????? ??????">
                        <div id="firstThumbPreview"></div>
                        <div class="overlap" id="checkFirstThumb"></div>
                    </div>
                    <div id="thumb">
                        <label>?????????</label>
                        <input type="file" name="thumbnail" value="?????? ??????" multiple>
                        <div id="thumbPreview"></div>
                        <div class="overlap" id="checkThumb">
                            ???????????? ???????????? ????????? ???????????? ???????????????.
                        </div>
                    </div>
                    <div id="productInfo">
                        <label>????????????</label>
                        <input type="file" name="pImg" value="?????? ??????" multiple>
                        <div id="infoPreview"></div>
                        <div class="overlap" id="checkInfo"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>