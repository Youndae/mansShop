var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function(){
    //like List, memberQnAList pagination
    /*var actionForm = $("#actionForm");

    $(".paginate_button a").on("click", function (e) {
        e.preventDefault();

        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
        actionForm.submit();
    });*/

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