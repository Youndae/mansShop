var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
var phonePattern = /^01(?:0|1|6|9)([0-9]{3,4})([0-9]{4})$/;

$(document).ready(function(){
    //like List, memberQnAList pagination
    /*var actionForm = $("#actionForm");

    $(".paginate_button a").on("click", function (e) {
        e.preventDefault();

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    });*/

    //modifyInfo
    $("#modify_info").on('click', function(){

        if($("#userEmail").val() == "") {
            console.log("Email null || overlap not null");
            $("#emailOverlap").text("이메일을 입력하세요");
            $("#userEmail").focus();
        }else if($("#mailStat").val() != "1"){
            $("#userEmail").focus();
        }else if($("#userPhone").val() == "") {
            console.log("Phone null || overlap not null");
            $("#phoneOverlap").text("연락처를 입력하세요");
            $("#userPhone").focus();
        }else if($("#phoneStat").val() != "1"){
            $("#userPhone").focus();
        }else{
            $("#userInfo").attr('action', '/myPage/modifyInfo');
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

    //memberQnAList
    $("#insertMemberQnA").on('click', function(){
        location.href='/myPage/insertMemberQnA';
    })


})


$(function(){
    //insertMemberQnA
    $("#insertMyQnA").on('click', function(){
        var form = $("#myQnA_InsertForm");

        form.attr("action", "/myPage/insertMemberQnA");
        form.submit();
    });

    //memberReviewDetail
    $("#modifyReview").on('click',function(){
        var rNum = $(this).val();

        location.href='/myPage/memberReviewModify/' + rNum;
    });

    $("#deleteReview").on('click',function(){
        var rNum = $(this).val();
        var result = confirm("리뷰를 삭제하게 되면 해당 상품의 리뷰를 재작성할 수 없습니다.\n 정말 삭제하시겠습니까?");

        if(result){
            location.href='/myPage/memberReviewDelete/' + rNum;
        }
    });

    //memberReviewModify
    $("#reviewModifyProc").on('click', function(){
        var form = $("#reviewModifyForm");

        form.attr("action", "/myPage/memberReviewModify");
        form.submit();
    });

    //modifyCheck
    $("#userCheck").on('click', function(){
        var userPw = {
            userPw: $("input[name=userPw]").val()
        };

        $.ajax({
            url: '/myPage/modifyCheck',
            type: 'post',
            data: userPw,
            async: false,
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function(data){
                if(data == "1"){
                    location.href='/myPage/modifyInfo';
                }else{
                    $(".pwOverlap").text("비밀번호가 틀렸습니다.");
                }
            }
        })
    });



});