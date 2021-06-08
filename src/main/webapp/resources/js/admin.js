var firstThumbFile = {};
var thumbFiles = {};
var infoFiles = {};

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
    var file = input.files[0];
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
    reader.onload = function(img){

        var appendStr = "<div class=\"preview-box\" value=\"" + imgNum +"\">" +
                        "<img class=\"thumbnail\" src=\"" + img.target.result + "\"\/>" +
                        "<p>" + file.name + "</p>" +
                        "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
                        "삭제" + "</a>"
                        + "</div>";

        if(type == "first"){
            $("#firstThumbPreview").append(appendStr);
            var indexNo = imgNum.substr(0, imgNum.length+1);
            firstThumbFile[indexNo] = file;
        }else if(type == "thumb"){
            var indexNo = imgNum.substr(0, imgNum.length+1);
            $("#thumbPreview").append(appendStr);
            thumbFiles[indexNo] = file;
        }else if(type == "info"){
            var indexNo = imgNum.substr(0, imgNum.length+1);
            $("#imgPreview").append(appendStr);
            infoFiles[indexNo] = file;
        }
    }
}

function deletePreview(obj){
    var imgNum = obj.attributes['value'].value;
    var imgType = imgNum.substr(0, 1);
    var indexNo = imgNum.substr(0, imgNum.length+1);

    if(imgType == "f"){
        delete firstThumbFile[indexNo];
    }else if(imgType == "t"){
        delete thumbFiles[indexNo];
    }else if(imgType == "i"){
        delete infoFiles[indexNo];
    }

    $("#preview .preview-box[value=" + imgNum + "]").remove();
    resizeHeight();
}


$(document).ready(function() {

})

$(function(){
    $("#addSubmit").click(function(){
        var cObj = document.getElementById('classification');
        var classification = cObj.options[cObj.selectedIndex].value;

        var sObj = document.getElementById('size');
        var size = sObj.options[sObj.selectedIndex].value;

        var test = $("#classification").val();

        $("#addProduct").submit();
    })
})