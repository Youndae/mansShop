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
                url: '/managerPage/getChatRoom',
                type: 'get',
                success: function(data) {
                    console.log("manager return data : ", data);
                    if(data == null)
                        alert("상담 요청이 없습니다.");
                    else
                        window.open('/managerPage/chatRoom/' + data, '채팅 상담', 'width=600px,height=700px,scrollbars=yes');
                }
            })
        })
    })
</script>
<body>
    <div class="side_nav">
        <ul class="side_nav_ul">
            <li><a href="/managerPage/orderList">주문목록</a></li>
            <li><a id="managerChat">채팅 상담</a></li>
            <li><a href="/admin/productQnAList">상담 내역</a></li>
        </ul>
    </div>
</body>
</html>
