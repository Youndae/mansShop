<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<link rel="stylesheet" href="/css/sidenav.css">
<script>
    $(document).ready(function() {
        $("#managerChat").on('click', function() {
            $.ajax({
                url: '/manager/chat-room',
                type: 'get',
                success: function(data) {
                    console.log("manager return data : ", data);
                    if(data == '')
                        alert("상담 요청이 없습니다.");
                    else
                        window.open('/manager/chat-room/' + data, '채팅 상담', 'width=600px,height=700px,scrollbars=yes');
                }
            })
        })
    })
</script>
<body>
    <div class="side_nav">
        <ul class="side_nav_ul">
            <li><a href="/manager/order">주문목록</a></li>
            <li><a id="managerChat">채팅 상담</a></li>
            <li><a href="/admin/product/qna">상담 내역</a></li>
        </ul>
    </div>
</body>
</html>
