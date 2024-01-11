<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<sec:authentication property="principal.username" var="uid"/>
<style>
    .content{
        display: block;
    }
    #msgArea {
        display: inline-grid;
        height: 550px;
        overflow-y: auto;
        overflow-x: hidden;
        align-content: baseline;
        -ms-overflow-style: none;
    }

    #msgArea::-webkit-scrollbar-thumb {
        background-color: lightgray;
    }

    #msgArea::-webkit-scrollbar {
        width: 5px;
    }

    .input-area {
        margin-top: 20px;
    }

    .alert-info{
        height: 50px;
    }

    .opponent2 {
        float: left;
        max-width: 50%;
    }
    .myself2 {
        float: right;
        max-width: 50%;
        padding-right: 10px;
    }

    .opponent2, .myself2 {
        word-wrap: break-word;
        word-break: break-all;
        margin-top: 10px;
    }
    .col-6 {
        max-width: 100%;
    }
</style>
<script>
    $(document).ready(function() {
        const manager = 'manager';
        const roomId = `${room}`;
        console.log('roomId : ' + roomId);
        const username = `${uid}`;

        console.log(roomId + ', ' + username);

        const sockJs = new SockJS("/stomp/chat");
        const stomp = Stomp.over(sockJs);

        stomp.connect({}, function() {
            console.log('STOMP Connection');

            stomp.subscribe('/sub/chat/room/' + roomId, function (chat) {
                let content = JSON.parse(chat.body);
                let writer = content.writer;
                let str = '';
                let nonId = content.message;
                let managerId = writer + " : " + content.message;

                if(content.message != null){
                    if(username.startsWith(manager)){
                        if(writer === username){
                            str += "<div class='col-6 myself'>" +
                                "<div class='alert alert-secondary myself2'>" +
                                "<b class='chat-text'>" + nonId + "</b>" +
                                "</div></div>";
                        }else {
                            str = "<div class='col-6 opponent'>" +
                                "<div class='alert alert-warning opponent2'>" +
                                "<b class='chat-text'>" + managerId + "</b>" +
                                "</div></div>";
                        }
                    }else{
                        if(writer === username){
                            str += "<div class='col-6 myself'>" +
                                "<div class='alert alert-secondary myself2'>" +
                                "<b class='chat-text'>" + nonId + "</b>" +
                                "</div></div>";
                        }else {
                            str = "<div class='col-6 opponent'>" +
                                "<div class='alert alert-warning opponent2'>" +
                                "<b class='chat-text'>" + nonId + "</b>" +
                                "</div></div>";
                        }
                    }
                    $("#msgArea").append(str);
                    // $("#msgArea").scrollTop($('#msgArea').prop('scrollHeight'));
                    // let maxScroll = $("#msgArea").height() - $("#content-id").height();
                    $('#msgArea').scrollTop($('#msgArea')[0].scrollHeight);
                    $("#msg").focus();
                    console.log("focus");
                }

            });

            stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId : roomId, writer: username}))
        });

        $("#button-send").on('click', function(e) {
            let msg = document.getElementById("msg");

            console.log(username + " : " + msg.value);
            stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, writer: username}));
            msg.value = '';
        });

        $("#msg").on("keyup", function(key) {
            if(key.keyCode == 13)
                $("#button-send").click();
        })
    });


</script>
<body>
<div class="container">
    <div class="col-6 chat-header">
        <h1>채팅방</h1>
    </div>
    <div class="content" id="content-id">
        <div id="msgArea" class="col">
            <sec:authorize access="hasRole('ROLE_MEMBER')">
                <div class="alert alert-info">
                    <b>상담원이 곧 응답합니다. 잠시만 기다려주세요</b>
                </div>
            </sec:authorize>
        </div>
        <div class="col-6 input-area">
            <div class="input-group mb-3">
                <input type="text" id="msg" class="form-control">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" id="button-send" type="button">전송</button>
                </div>
            </div>
        </div>
    </div>
<%--    <div class="col-6"></div>--%>
</div>
</body>
</html>
