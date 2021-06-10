var firstThumbFile = {};
var thumbFiles = {};
var infoFiles = {};
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

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
   /* preview-box의 value와 a태그의 value는 동일해야 하고
    * 이 value와 각 이미지 배열의 index도 동일해야 한다.
    * 그럼 구현부를 하나의 변수로 만들게 아니라
    * 각 조건마다 다르게..?
    * 아니면 addPreview 부분에서 imgNum을 넘겨주는데
    * f1, t1, i1 형태로 넘겨주고
    * substr로 분리한다음에 인덱스로 넣어주고
    * 삭제할때도 분리해서 index값을 구하는쪽으로?
    * */

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
            $("#imgPreview").append(appendStr);
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
                        /*location.href="myPage/ModifyInfo";*/
                    }
                }
            });

            /*$("#addProductForm").submit();*/
        });


})

