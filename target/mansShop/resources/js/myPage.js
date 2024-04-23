const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
const emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;

$(document).ready(function(){
    //modifyInfo
    $("#modify_info").on('click', function(){

        if($("#userEmail").val() == "") {
            $("#emailOverlap").text("이메일을 입력하세요");
            $("#userEmail").focus();
        }else if($("#mailStat").val() != "1"){
            $("#userEmail").focus();
        }else if($("#userPhone").val() == "") {
            $("#phoneOverlap").text("연락처를 입력하세요");
            $("#userPhone").focus();
        }else if($("#phoneStat").val() != "1"){
            $("#userPhone").focus();
        }else{
            $("#userInfo").attr('action', '/my-page/user/info');
            $("#userInfo").submit();
        }
    })

    $("#userEmail").on("propertychange change keyup paste input", function(){
        if(emailPattern.test($("#userEmail").val()) == false){
            $("#emailOverlap").text("유효한 주소가 아닙니다.");
            $("#mailStat").val("");
        }else{
            $("#emailOverlap").text("");
            $("#mailStat").val("1");
        }
    })

    $("#userPhone").on("propertychange change keyup paste input", function(){
        if(phonePattern.test($("#userPhone").val()) == false){
            $("#phoneOverlap").text("유효한 번호가 아닙니다.");
            $("#phoneStat").text("");
        }else{
            $("#phoneOverlap").text("");
            $("#phoneStat").val("1");
        }
    })

    $("#insertMemberQnA").on('click', function(){
        location.href='/my-page/qna/insert';
    })
})


$(function(){
    //insertMemberQnA
    $("#insertMyQnA").on('click', function(){
        const form = $("#myQnA_InsertForm");

        form.attr("action", "/my-page/qna/insert");
        form.submit();
    });

    //memberReviewDetail
    $("#modifyReview").on('click',function(){
        const rNum = $(this).val();

        location.href='/my-page/review/modify/' + rNum;
    });

    $("#deleteReview").on('click',function(){
        const rNum = $(this).val();
        const result = confirm("리뷰를 삭제하게 되면 해당 상품의 리뷰를 재작성할 수 없습니다.\n 정말 삭제하시겠습니까?");

        if(result){
            $.ajax({
                url: '/my-page/review/' + rNum,
                type: 'delete',
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    if(data == 1){
                        location.href = '/my-page/review';
                    }else{
                        alert("오류가 발생했습니다.\n문제가 계속된다면 관리자에게 문의해주세요.");
                    }
                }
            })
        }
    });

    //memberReviewModify
    $("#reviewModifyProc").on('click', function(){
        const form = $("#reviewModifyForm");

        form.submit();
    });

    //modifyCheck
    $("#userCheck").on('click', function(){
        const userPw = {
                            userPw: $("input[name=userPw]").val()
                        };

        $.ajax({
            url: '/my-page/user',
            type: 'post',
            data: userPw,
            async: false,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == 1)
                    location.href='/my-page/user/info';
                else
                    $(".pwOverlap").text("비밀번호가 일치하지 않습니다.");
            }
        })
    });

    //chat
    $("#startChat").on('click', function() {
        $.ajax({
            url: '/my-page/chatRoom',
            type: 'post',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if(data == '')
                    alert("오류가 발생했습니다.");
                else if(data == 'duplication')
                    alert('마무리 되지 않은 채팅이 있습니다');
                else {
                    window.open('/my-page/chat/' + data
                        , '채팅 문의'
                        , 'width=600px,height=700px,scrollbars=yes'
                    );
                }
            }
        });
    });
});