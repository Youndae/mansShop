var firstThumbFile = {};
var thumbFiles = {};
var infoFiles = {};
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var numRegex = /\d/;

function validation(fileName){
    fileName = fileName + "";

    var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
    var fileNameExtension = fileName.toLowerCase().substring(fileNameExtensionIndex, fileName.length);

    if(!((fileNameExtension === 'jpg') || (fileNameExtension === 'gif') || (fileNameExtension === 'png') || (fileNameExtension === 'jpeg'))){
        alert("jpg, gif, png, jpeg 확장자만 업로드 가능합니다.");
        return true;
    }else{
        return false;
    }
}



function addFirstPreview(input){
    var file = input[0].files[0];
    var imgNum = 'f0';

    if(validation(file.name)){
        return;
    }else{
        setPreviewForm(file, "first", imgNum);
    }
}

function addThumbPreview(input){
    if(input[0].files.length <= 4){
        for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            var file = input[0].files[fileIndex];
            if(validation(file.name)) continue;
            var imgNum = "t" + fileIndex;
            setPreviewForm(file, "thumb", imgNum);
        }
    }else{
        alert("썸네일은 4장까지만 업로드 가능합니다.");
    }
}

function addInfoPreview(input){
    if(input[0].files.length <= 10){
        for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            var file = input[0].files[fileIndex];
            if(validation(file.name)) continue;
            var imgNum = "i" + fileIndex;
            setPreviewForm(file, "info", imgNum);
        }
    }else{
        alert("정보이미지 10장까지만 업로드 가능합니다.");
    }
}

function setPreviewForm(file, type, imgNum){


    var reader = new FileReader();
    reader.onload = function(img) {
        var appendStr = "<div class=\"preview-box\" value=\"" + imgNum +"\">" +
                        "<img class=\"thumbnail\" src=\"" + img.target.result + "\"\/>" +
                        "<p>" + file.name + "</p>" +
                        "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
                        "삭제" + "</a>"
                        + "</div>";

        if(type == "first"){
            $("#firstThumbPreview").append(appendStr);
            var indexNo = imgNum.substr(1, imgNum.length+1);
            firstThumbFile[indexNo] = file;
        }else if(type == "thumb"){
            var indexNo = imgNum.substr(1, imgNum.length+1);
            $("#thumbPreview").append(appendStr);
            thumbFiles[indexNo] = file;
        }else if(type == "info"){
            var indexNo = imgNum.substr(1, imgNum.length+1);
            $("#infoPreview").append(appendStr);
            infoFiles[indexNo] = file;
        }
    };
    reader.readAsDataURL(file);
}

function deletePreview(obj){
    var imgNum = obj.attributes['value'].value;
    var imgType = imgNum.substr(0, 1);
    var indexNo = imgNum.substr(1, imgNum.length+1);

    if(imgType == "f"){
        delete firstThumbFile[indexNo];
        $("#firstThumbPreview .preview-box[value=" + imgNum + "]").remove();
    }else if(imgType == "t"){
        delete thumbFiles[indexNo];
        $("#thumbPreview .preview-box[value=" + imgNum + "]").remove();
    }else if(imgType == "i"){
        delete infoFiles[indexNo];
        $("#imgPreview .preview-box[value=" + imgNum + "]").remove();
    }

}


$(document).ready(function() {

    $('select[name=pClassification]').on("propertychange change keyup paste input", function(){
        $("#checkClassification").text("");
    })

    $('input[name=pName]').on("propertychange change keyup paste input", function(){
        $("#checkPName").text("");
    })

    $('select[name=pSize]').on("propertychange change keyup paste input", function(){
        $("#checkPSize").text("");
    })

    $('input[name=pColor]').on("propertychange change keyup paste input", function(){
        $("#checkPColor").text("");
    })

    $('input[name=pPrice]').on("propertychange change keyup paste input", function(){

        if($('input[name=pPrice]').val() == ""){
            $("#checkPPrice").text("");
        }else if(numRegex.test($('input[name=pPrice]').val()) == false){
            $("#checkPPrice").text("숫자만 입력 가능합니다.");
        }else{
            $("#checkPPrice").text("");
        }
    })

    $('input[name=pStock]').on("propertychange change keyup paste input", function(){
        if($('input[name=pStock]').val() == ""){
            $("#checkPStock").text("");
        }else if(numRegex.test($('input[name=pStock]').val()) == false){
            $("#checkPStock").text("숫자만 입력 가능합니다.");
        }else{
            $("#checkPStock").text("");
        }
    })

    $('input[name=firstThumbnail]').on("propertychange change keyup paste input", function(){
        $("#checkFirstThumb").text("");
    })

    $('input[name=pImg]').on("propertychange change keyup paste input", function(){
        $("#checkInfo").text("");
    })


    $("#firstThumb input[type=file]").change(function(){
        addFirstPreview($(this));
    });

    $("#thumb input[type=file]").change(function(){
        addThumbPreview($(this));
    });

    $("#productInfo input[type=file]").change(function(){
        addInfoPreview($(this));
    });



        $("#addSubmit").on('click', function(){
            console.log("submit start");
            var form = $('#addProductForm')[0];

            console.log("form : " + form);

            /*console.log("form : " + form);*/
            var formData = new FormData(form);

            console.log("classification : " + $('select[name=pClassification]').val());

            console.log("pName : " + $('input[name=pName]').val());

            console.log("pSize : " + $('select[name=pSize]').val());

            console.log("pColor : " + $('input[name=pColor]').val());

            console.log("pPrice : " + $('input[name=pPrice]').val());

            console.log("firstThumb length : " + Object.keys(firstThumbFile).length);

            console.log("info length : " + Object.keys(infoFiles).length);


            if($('select[name=pClassification]').val() == "default"){
                $("#checkClassification").text("상품 분류를 선택해주세요");
            }else if($('input[name=pName]').val() == ""){
                $("#checkPName").text("상품명을 입력해주세요");
                $('input[name=pName]').focus();
            }else if($('select[name=pSize]').val() == "default"){
                $("#checkPSize").text("사이즈를 선택해주세요");
            }else if($('input[name=pColor]').val() == ""){
                $("#checkPColor").text("색상을 입력해주세요");
                $('input[name=pColor]').focus();
            }else if($('input[name=pPrice]').val() == "") {
                $("#checkPPrice").text("금액을 입력해주세요");
                $('input[name=pPrice]').focus();
            }else if(numRegex.test($('input[name=pPrice]').val()) == false) {
                $("#checkPPrice").text("숫자만 입력 가능합니다.");
                $('input[name=pPrice]').focus();
            }else if($('input[name=pStock]').val() != "" && numRegex.test($('input[name=pStock]').val()) == false){
                $("#checkPStock").text("숫자만 입력 가능합니다.");
                $('input[name=pStock]').focus();
            }else if(Object.keys(firstThumbFile).length == 0){
                $("#checkFirstThumb").text("대표사진을 선택해주세요");
            }else if(Object.keys(infoFiles).length == 0){
                $("#checkInfo").text("상품정보사진을 선택해주세요");
            }else{
                for(var index = 0; index < Object.keys(firstThumbFile).length; index++){
                    formData.append('firstThumbFile', firstThumbFile[index]);
                }

                for(var index = 0; index < Object.keys(thumbFiles).length; index++){
                    formData.append('thumbFiles', thumbFiles[index]);
                }

                for(var index = 0; index < Object.keys(infoFiles).length; index++){
                    formData.append('infoFiles', infoFiles[index]);
                }

                // console.log("formdata1 : " + formData);

                /*console.log("test : " + formData.get('pColor'));*/

                /*console.log("formdata : " + formData);*/

                // console.log("type : " + typeof(formData));

                // console.log("append complete");

                $.ajax({
                    url: '/admin/addProduct',
                    enctype: 'multipart/form-data',
                    contentType: false,
                    processData: false,
                    cache: false,
                    type: 'post',
                    dataType: 'JSON',
                    data: formData,
                    /*beforeSend : function(xhr){
                        xhr.setRequestHeader(header, token);
                    },*/
                    success: function(result){
                        if(result === -1){
                            alert("업로드 실패");
                        }else{
                            location.href="admin/productList";
                        }
                    }
                });
            }


            /*$("#addProductForm").submit();*/
        });


})

