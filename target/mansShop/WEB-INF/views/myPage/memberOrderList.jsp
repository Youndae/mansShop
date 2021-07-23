<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>
<style>
    img .orderList_thumb{
        width: 50px;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myPageOrder.js"></script>
<body>
<div>
    <div class="header">
        <h1>주문내역</h1>
        <select id="select_orderList_term">
            <option value="3">3개월</option>
            <option value="6">6개월</option>
            <option value="12">1년</option>
            <option value="all">전체</option>
        </select>
    </div>
    <div class="memberOrderList">

    </div>
</div>
</body>
<%--<script>
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).ready(function(){


        var date = new Date();
        date.setMonth(date.getMonth() - 3);

        var month = 0;

        if((date.getMonth() + 1) >= 10){
            month = date.getMonth() + 1;
        }else{
            month = '0' + (date.getMonth() + 1);
        }

        var regDate = date.getFullYear() + "/" + month + "/01";

        getList(regDate);



        /*(function(){
            $.getJSON("/myPage/selectOrderList", {regDate: regdate}, function(arr){
                console.log("getMemberOrderList");
                var str = "";
                var date = "";
                var totalPrice = 0;

                $(arr).each(function(i, attach){

                    if(i == 0){
                        date = attach.orderDate;

                        var od = new Date(attach.orderDate);
                        attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();
                        str += "<div>" +
                               "<label>" + attach.orderDate + "</label>" +
                                "<table border=\"1\">" +
                                "<tr>" +
                                "<th>상품명</th>" +
                                "<th>옵션</th>" +
                                "<th>수량</th>" +
                                "<th>가격</th>" +
                                "<th>배송현황</th>" +
                                "</tr>" +
                                "</thead>" +
                                "<tbody>" +
                                "<tr>" +
                                "<td>" +
                                "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                                attach.pname + "</td>";
                        if(attach.psize != null){
                            if(attach.pcolor != null){
                                str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                            }else if(attach.pcolor == null){
                                str += "<td>사이즈 : " + attach.psize + "</td>";
                            }
                        }else if(attach.pcolor != null){
                            str += "<td>컬러 : " + attach.pcolor + "</td>";
                        }
                        str += "<td>" + attach.orderCount + "</td>" +
                                "<td>" + attach.odPrice + "</td>";

                        totalPrice = attach.orderPrice;

                        if(attach.orderStat == 0){
                            str += "<td>배송준비중</td>";
                        }else if(attach.orderStat == 1){
                            str += "<td>배송중</td>";
                        }else if(attach.orderStat == 2){
                            str += "<td>배송완료";
                            if(attach.reviewStat == 0){
                                str += "<button type=\"button\" id=\"pReview_insert\">리뷰작성하기</button></td>";
                            }else{
                                str += "</td>";
                            }
                        }
                        str += "</tr>";
                    }else{
                        if(date == attach.orderDate){
                            str += "<tr>" +
                                    "<td>" +
                                    "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                                    attach.pname + "</td>";
                            if(attach.psize != null){
                                if(attach.pcolor != null){
                                    str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                                }else if(attach.pcolor == null){
                                    str += "<td>사이즈 : " + attach.psize + "</td>";
                                }
                            }else if(attach.pcolor != null){
                                str += "<td>컬러 : " + attach.pcolor + "</td>";
                            }
                            str += "<td>" + attach.orderCount + "</td>" +
                                "<td>" + attach.odPrice + "</td>";

                            if(attach.orderStat == 0){
                                str += "<td>배송준비중</td>";
                            }else if(attach.orderStat == 1){
                                str += "<td>배송중</td>";
                            }else if(attach.orderStat == 2){
                                str += "<td>배송완료";
                                if(attach.reviewStat == 0){
                                    str += "<button type=\"button\" id=\"pReview_insert\">리뷰작성하기</button></td>";
                                }else{
                                    str += "</td>";
                                }
                            }
                            str += "</tr>";
                        }else{
                            date = attach.orderDate;

                            var od = new Date(attach.orderDate);
                            attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();

                            str +=  "</tbody></table><span>총 금액 : " + totalPrice + " 원</span></div>"+
                                "<div>" +
                                "<label>" + attach.orderDate + "</label>" +
                                "<table border=\"1\">" +
                                "<tr>" +
                                "<th>상품명</th>" +
                                "<th>옵션</th>" +
                                "<th>수량</th>" +
                                "<th>가격</th>" +
                                "<th>배송현황</th>" +
                                "</tr>" +
                                "</thead>" +
                                "<tbody>" +
                                "<tr>" +
                                "<td>" +
                                "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                                attach.pname + "</td>";
                            if(attach.psize != null){
                                if(attach.pcolor != null){
                                    str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                                }else if(attach.pcolor == null){
                                    str += "<td>사이즈 : " + attach.psize + "</td>";
                                }
                            }else if(attach.pcolor != null){
                                str += "<td>컬러 : " + attach.pcolor + "</td>";
                            }
                            str += "<td>" + attach.orderCount + "</td>" +
                                "<td>" + attach.odPrice + "</td>";

                            totalPrice = attach.orderPrice;

                            if(attach.orderStat == 0){
                                str += "<td>배송준비중</td>";
                            }else if(attach.orderStat == 1){
                                str += "<td>배송중</td>";
                            }else if(attach.orderStat == 2){
                                str += "<td>배송완료";
                                if(attach.reviewStat == 0){
                                    str += "<button type=\"button\" id=\"pReview_insert\">리뷰작성하기</button></td>";
                                }else{
                                    str += "</td>";
                                }
                            }
                            str += "</tr>";
                        }
                    }

                    console.log("i : " + i);

                    if(i == arr.length - 1){
                        str +=  "</tbody></table><span>총 금액 : " + totalPrice + " 원</span></div>";
                    }

                })

                $(".memberOrderList").append(str);
            })
        })();*/
    })

    $(function(){
        $("#select_orderList_term").change(function(){
            console.log("orderList term change");
            var val = $("#select_orderList_term option:selected").val();

            if(val == "all"){
                var regDate = new Date(1990,1,1);

                var date = regDate.getFullYear() + "/" + (regDate.getMonth() + 1) + "/" + regDate.getDate();
            }else{
                var regDate = new Date();
                regDate.setMonth(regDate.getMonth() - val);

                var date = regDate.getFullYear() + "/" + (regDate.getMonth() + 1) + "/01";
            }




            getList(date);
        })

    })



    function getList(regDate){
        $(".memberOrderList div").remove();
        $.getJSON("/myPage/selectOrderList", {regDate: regDate}, function(arr){
            console.log("getMemberOrderList");
            var str = "";
            var date = "";
            var totalPrice = 0;

            $(arr).each(function(i, attach){

                if(i == 0){
                    date = attach.orderDate;

                    var od = new Date(attach.orderDate);
                    attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();
                    str += "<div>" +
                        "<label>" + attach.orderDate + "</label>" +
                        "<table border=\"1\">" +
                        "<tr>" +
                        "<th>상품명</th>" +
                        "<th>옵션</th>" +
                        "<th>수량</th>" +
                        "<th>가격</th>" +
                        "<th>배송현황</th>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>" +
                        "<tr>" +
                        "<td>" +
                        "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                        attach.pname + "</td>";
                    if(attach.psize != null){
                        if(attach.pcolor != null){
                            str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                        }else if(attach.pcolor == null){
                            str += "<td>사이즈 : " + attach.psize + "</td>";
                        }
                    }else if(attach.pcolor != null){
                        str += "<td>컬러 : " + attach.pcolor + "</td>";
                    }
                    str += "<td>" + attach.orderCount + "</td>" +
                        "<td>" + attach.odPrice + "</td>";

                    totalPrice = attach.orderPrice;

                    if(attach.orderStat == 0){
                        str += "<td>배송준비중</td>";
                    }else if(attach.orderStat == 1){
                        str += "<td>배송중</td>";
                    }else if(attach.orderStat == 2){
                        str += "<td>배송완료";
                        if(attach.reviewStat == 0){
                            str += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.popNo + "/" + attach.pno + "/" + attach.orderNo + "\" onclick=\"insertReview(this)\">리뷰작성하기</button></td>";
                        }else{
                            str += "</td>";
                        }
                    }
                    str += "</tr>";
                }else{
                    if(date == attach.orderDate){
                        str += "<tr>" +
                            "<td>" +
                            "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                            attach.pname + "</td>";
                        if(attach.psize != null){
                            if(attach.pcolor != null){
                                str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                            }else if(attach.pcolor == null){
                                str += "<td>사이즈 : " + attach.psize + "</td>";
                            }
                        }else if(attach.pcolor != null){
                            str += "<td>컬러 : " + attach.pcolor + "</td>";
                        }
                        str += "<td>" + attach.orderCount + "</td>" +
                            "<td>" + attach.odPrice + "</td>";

                        if(attach.orderStat == 0){
                            str += "<td>배송준비중</td>";
                        }else if(attach.orderStat == 1){
                            str += "<td>배송중</td>";
                        }else if(attach.orderStat == 2){
                            str += "<td>배송완료";
                            if(attach.reviewStat == 0){
                                str += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.popNo + "/" + attach.pno + "/" + attach.orderNo + "\" onclick=\"insertReview(this)\">리뷰작성하기</button></td>";
                            }else{
                                str += "</td>";
                            }
                        }
                        str += "</tr>";
                    }else{
                        date = attach.orderDate;

                        var od = new Date(attach.orderDate);
                        attach.orderDate = od.getFullYear() + "/" + (od.getMonth() + 1) + "/" + od.getDate();

                        str +=  "</tbody></table><span>총 금액 : " + totalPrice + " 원</span></div>"+
                            "<div>" +
                            "<label>" + attach.orderDate + "</label>" +
                            "<table border=\"1\">" +
                            "<tr>" +
                            "<th>상품명</th>" +
                            "<th>옵션</th>" +
                            "<th>수량</th>" +
                            "<th>가격</th>" +
                            "<th>배송현황</th>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>" +
                            "<tr>" +
                            "<td>" +
                            "<img class=\"orderList_thumb\" src=\"/display?image=" +attach.firstThumbnail + "\">" +
                            attach.pname + "</td>";
                        if(attach.psize != null){
                            if(attach.pcolor != null){
                                str += "<td>사이즈 : " + attach.psize + "  컬러 : " + attach.pcolor + "</td>";
                            }else if(attach.pcolor == null){
                                str += "<td>사이즈 : " + attach.psize + "</td>";
                            }
                        }else if(attach.pcolor != null){
                            str += "<td>컬러 : " + attach.pcolor + "</td>";
                        }
                        str += "<td>" + attach.orderCount + "</td>" +
                            "<td>" + attach.odPrice + "</td>";

                        totalPrice = attach.orderPrice;

                        if(attach.orderStat == 0){
                            str += "<td>배송준비중</td>";
                        }else if(attach.orderStat == 1){
                            str += "<td>배송중</td>";
                        }else if(attach.orderStat == 2){
                            str += "<td>배송완료";
                            if(attach.reviewStat == 0){
                                str += "<button type=\"button\" id=\"pReview_insert\" value=\"" + attach.popNo + "/" + attach.pno + "/" + attach.orderNo + "\" onclick=\"insertReview(this)\">리뷰작성하기</button></td>";
                            }else{
                                str += "</td>";
                            }
                        }
                        str += "</tr>";
                    }
                }

                console.log("i : " + i);

                if(i == arr.length - 1){
                    str +=  "</tbody></table><span>총 금액 : " + totalPrice + " 원</span></div>";
                }

            })

            $(".memberOrderList").append(str);
        })
    }

    function insertReview(obj){
            var val = obj.attributes['value'].value;

            var no = val.split("/");

        location.href="/myPage/orderReview/"+no[0] + "/" + no[1] + "/" + no[2];

    }
</script>--%>
</html>
