var firstThumbFile = {};
var thumbFiles = {};
var infoFiles = {};
var deleteFirstThumbFile = {};
var deleteFirstThumbNum = 0;
var deleteThumbFiles = {};
var deleteThumbNum = 0;
var deleteInfoFiles = {};
var deleteInfoNum = 0;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var numRegex = /\d/;

function validation(fileName) {
    fileName = fileName + "";

    var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
    var fileNameExtension = fileName.toLowerCase().substring(fileNameExtensionIndex, fileName.length);

    if (!((fileNameExtension === 'jpg') || (fileNameExtension === 'gif') || (fileNameExtension === 'png') || (fileNameExtension === 'jpeg'))) {
        alert("jpg, gif, png, jpeg 확장자만 업로드 가능합니다.");
        return true;
    } else {
        return false;
    }
}


function addFirstPreview(input) {
    var file = input[0].files[0];
    var imgNum = 'f0';

    if (validation(file.name)) {
        return;
    } else {
        setPreviewForm(file, "first", imgNum);
    }
}

function addThumbPreview(input) {
    if (input[0].files.length <= 4) {
        for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            var file = input[0].files[fileIndex];
            if (validation(file.name)) continue;
            var imgNum = "t" + fileIndex;
            setPreviewForm(file, "thumb", imgNum);
        }
    } else {
        alert("썸네일은 4장까지만 업로드 가능합니다.");
    }
}

function addInfoPreview(input) {
    if (input[0].files.length <= 10) {
        for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            var file = input[0].files[fileIndex];
            if (validation(file.name)) continue;
            var imgNum = "i" + fileIndex;
            setPreviewForm(file, "info", imgNum);
        }
    } else {
        alert("정보이미지 10장까지만 업로드 가능합니다.");
    }
}

function setPreviewForm(file, type, imgNum) {


    var reader = new FileReader();
    reader.onload = function (img) {
        var appendStr = "<div class=\"preview-box\" value=\"" + imgNum + "\">" +
            "<img class=\"thumbnail\" src=\"" + img.target.result + "\"\/>" +
            "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
            "삭제" + "</a>"
            + "</div>";

        if (type == "first") {
            $("#firstThumbPreview").append(appendStr);
            var indexNo = imgNum.substr(1, imgNum.length + 1);
            firstThumbFile[indexNo] = file;
        } else if (type == "thumb") {
            var indexNo = imgNum.substr(1, imgNum.length + 1);
            $("#thumbPreview").append(appendStr);
            thumbFiles[indexNo] = file;
        } else if (type == "info") {
            var indexNo = imgNum.substr(1, imgNum.length + 1);
            $("#infoPreview").append(appendStr);
            infoFiles[indexNo] = file;
        }
    };
    reader.readAsDataURL(file);
}

function deletePreview(obj) {
    var imgNum = obj.attributes['value'].value;
    var imgType = imgNum.substr(0, 1);
    var indexNo = imgNum.substr(1, imgNum.length + 1);

    if (imgType == "f") {
        delete firstThumbFile[indexNo];
        $("#firstThumbPreview .preview-box[value=" + imgNum + "]").remove();
    } else if (imgType == "t") {
        delete thumbFiles[indexNo];
        $("#thumbPreview .preview-box[value=" + imgNum + "]").remove();
    } else if (imgType == "i") {
        delete infoFiles[indexNo];
        $("#infoPreview .preview-box[value=" + imgNum + "]").remove();
    }

}

function deleteOldPreview(obj) {
    var imgNum = obj.attributes['value'].value;
    var imgType = imgNum.substr(0, 2);
    var imgName = $('img[id=' + imgNum + ']').attr("src");
    var idx = imgName.lastIndexOf('=');
    var deleteImg = imgName.substring(idx + 1);

    if (imgType == "of") {
        console.log("firstThumb deleteImg : " + deleteImg);
        console.log("deleteFirstThumbNum : " + deleteFirstThumbNum);
        deleteFirstThumbFile[deleteFirstThumbNum++] = deleteImg;
        $("#firstThumbPreview .preview-box[value=" + imgNum + "]").remove();
    } else if (imgType == "ot") {
        console.log("Thumb deleteImg : " + deleteImg);
        console.log("deleteThumbNum : " + deleteThumbNum);
        deleteThumbFiles[deleteThumbNum++] = deleteImg;
        $("#thumbPreview .preview-box[value=" + imgNum + "]").remove();
    } else if (imgType == "oi") {
        console.log("InfoFiles deleteImg : " + deleteImg);
        console.log("deleteInfoNum : " + deleteInfoNum);
        deleteInfoFiles[deleteInfoNum++] = deleteImg;
        $("#infoPreview .preview-box[value=" + imgNum + "]").remove();
    }
}


$(document).ready(function () {

    //productList

    $("#addProduct").on('click', function(){
        location.href='/admin/addProduct';
    })

    var classification = $("#classification").val();
    var size = $("#size").val();

    $('select[name=pClassification]').val(classification);
    $('select[name=pSize]').val(size);

    var pno = $('input[name=pno]').val();

    (function () {
        $.getJSON("/admin/getFirstThumb", {pno: pno}, function (arr) {
            console.log(arr);

            var str = img("of", arr);

            $("#firstThumbPreview").append(str);
        })
    })();

    (function () {
        $.getJSON("/admin/getThumbnail", {pno: pno}, function (arr) {
            var str = img("ot", arr);

            $("#thumbPreview").append(str);
        })
    })();

    (function () {
        $.getJSON("/admin/getInfoImage", {pno: pno}, function (arr) {
            var str = img("oi", arr);

            $("#infoPreview").append(str);
        })
    })();

    function img(type, arr) {
        var num = 1;
        var str = "";
        console.log("each start");
        $(arr).each(function (i, attach) {
            var imgNum = type + num;
            console.log("imgNum : " + imgNum);
            str += "<div class=\"preview-box\" value=\"" + imgNum + "\">";
            if (type == "of") {
                str += "<img class=\"firstThumb\" id=\"" + imgNum + "\" src=\"/display?image=" + attach.firstThumbnail + "\"\/>";
            } else if (type == "ot") {
                str += "<img class=\"thumb\" id=\"" + imgNum + "\" src=\"/display?image=" + attach.pthumbnail + "\"\/>";
            } else if (type == "oi") {
                str += "<img class=\"infoImg\" id=\"" + imgNum + "\" src=\"/display?image=" + attach.pimg + "\"\/>";
            }
            str += "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deleteOldPreview(this)\">";
            str += "삭제" + "</a>";
            str += "</div>";
            num++;
        });

        return str;
    }


    $("#modifyProduct").on('click', function () {
        console.log("modify product");

        var form = $("#modifyProductForm")[0];

        var formData = new FormData(form);

        if ($('select[name=pClassification]').val() == "default") {
            $("#checkClassification").text("상품 분류를 선택해주세요");
        } else if ($('input[name=pName]').val() == "") {
            $("#checkPName").text("상품명을 입력해주세요");
            $('input[name=pName]').focus();
        } else if ($('input[name=pPrice]').val() == "") {
            $("#checkPPrice").text("금액을 입력해주세요");
            $('input[name=pPrice]').focus();
        } else if (numRegex.test($('input[name=pPrice]').val()) == false) {
            $("#checkPPrice").text("숫자만 입력 가능합니다.");
            $('input[name=pPrice]').focus();
        } else if ($('input[name=pStock]').val() != "" && numRegex.test($('input[name=pStock]').val()) == false) {
            $("#checkPStock").text("숫자만 입력 가능합니다.");
            $('input[name=pStock]').focus();
        } else if (Object.keys(firstThumbFile).length == 0 && ($("#of1").attr("src") == null)) {
            $("#checkFirstThumb").text("대표사진을 선택해주세요");
        } else if (Object.keys(infoFiles).length == 0 && ($("#oi1").attr("src") == null)) {
            $("#checkInfo").text("상품정보사진을 선택해주세요");
        } else {
            for (var index = 0; index < Object.keys(firstThumbFile).length; index++) {
                console.log("first Thumb index : " + index);
                formData.append('firstThumbFile', firstThumbFile[index]);
            }

            for (var index = 0; index < Object.keys(thumbFiles).length; index++) {
                console.log("Thumb index : " + index);
                formData.append('thumbFiles', thumbFiles[index]);
            }

            for (var index = 0; index < Object.keys(infoFiles).length; index++) {
                console.log("info index : " + index);
                formData.append('infoFiles', infoFiles[index]);
            }

            for (var index = 0; index < Object.keys(deleteFirstThumbFile).length; index++) {
                console.log("delete first Thumb index : " + index);
                formData.append('deleteFirstThumbFile', deleteFirstThumbFile[index]);
            }

            for (var index = 0; index < Object.keys(deleteThumbFiles).length; index++) {
                console.log("delete Thumb index : " + index);
                formData.append('deleteThumbFiles', deleteThumbFiles[index]);
            }

            for (var index = 0; index < Object.keys(deleteInfoFiles).length; index++) {
                console.log("delete info index : " + index);
                formData.append('deleteInfoFiles', deleteInfoFiles[index]);
            }

            console.log("formdata : " + formData.get('deleteFirstThumbFile'));
            for (var v of formData.entries()) {
                console.log("form Data val : " + v);
            }


            $.ajax({
                url: '/admin/modifyProduct',
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
                cache: false,
                type: 'post',
                data: formData,
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function (result) {

                        location.href = "admin/productList";

                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            });
        }
    });


    $('select[name=pClassification]').on("propertychange change keyup paste input", function () {
        $("#checkClassification").text("");
    })

    $('input[name=pName]').on("propertychange change keyup paste input", function () {
        $("#checkPName").text("");
    })

    $('select[name=pSize]').on("propertychange change keyup paste input", function () {
        $("#checkPSize").text("");
    })

    $('input[name=pColor]').on("propertychange change keyup paste input", function () {
        $("#checkPColor").text("");
    })

    $('input[name=pPrice]').on("propertychange change keyup paste input", function () {

        if ($('input[name=pPrice]').val() == "") {
            $("#checkPPrice").text("");
        } else if (numRegex.test($('input[name=pPrice]').val()) == false) {
            $("#checkPPrice").text("숫자만 입력 가능합니다.");
        } else {
            $("#checkPPrice").text("");
        }
    })

    $('input[name=pStock]').on("propertychange change keyup paste input", function () {
        if ($('input[name=pStock]').val() == "") {
            $("#checkPStock").text("");
        } else if (numRegex.test($('input[name=pStock]').val()) == false) {
            $("#checkPStock").text("숫자만 입력 가능합니다.");
        } else {
            $("#checkPStock").text("");
        }
    })

    $('input[name=firstThumbnail]').on("propertychange change keyup paste input", function () {
        $("#checkFirstThumb").text("");
    })

    $('input[name=pImg]').on("propertychange change keyup paste input", function () {
        $("#checkInfo").text("");
    })


    $("#firstThumb input[type=file]").change(function () {
        addFirstPreview($(this));
    });

    $("#thumb input[type=file]").change(function () {
        addThumbPreview($(this));
    });

    $("#productInfo input[type=file]").change(function () {
        addInfoPreview($(this));
    });


    $("#addSubmit").on('click', function () {
        console.log("submit start");

        if ($('select[name=pSize]').val() == "default") {
            $('select[name=pSize]').val(null);
        }

        var form = $('#addProductForm')[0];

        console.log("form : " + form);

        var formData = new FormData(form);

        console.log("classification : " + $('select[name=pClassification]').val());

        console.log("pName : " + $('input[name=pName]').val());

        console.log("pSize : " + $('select[name=pSize]').val());

        console.log("pColor : " + $('input[name=pColor]').val());

        console.log("pPrice : " + $('input[name=pPrice]').val());

        console.log("firstThumb length : " + Object.keys(firstThumbFile).length);

        console.log("info length : " + Object.keys(infoFiles).length);


        if ($('select[name=pClassification]').val() == "default") {
            $("#checkClassification").text("상품 분류를 선택해주세요");
        } else if ($('input[name=pName]').val() == "") {
            $("#checkPName").text("상품명을 입력해주세요");
            $('input[name=pName]').focus();
        } else if ($('input[name=pPrice]').val() == "") {
            $("#checkPPrice").text("금액을 입력해주세요");
            $('input[name=pPrice]').focus();
        } else if (numRegex.test($('input[name=pPrice]').val()) == false) {
            $("#checkPPrice").text("숫자만 입력 가능합니다.");
            $('input[name=pPrice]').focus();
        } else if ($('input[name=pStock]').val() != "" && numRegex.test($('input[name=pStock]').val()) == false) {
            $("#checkPStock").text("숫자만 입력 가능합니다.");
            $('input[name=pStock]').focus();
        } else if (Object.keys(firstThumbFile).length == 0) {
            $("#checkFirstThumb").text("대표사진을 선택해주세요");
        } else if (Object.keys(infoFiles).length == 0) {
            $("#checkInfo").text("상품정보사진을 선택해주세요");
        } else {
            for (var index = 0; index < Object.keys(firstThumbFile).length; index++) {
                formData.append('firstThumbFile', firstThumbFile[index]);
            }

            for (var index = 0; index < Object.keys(thumbFiles).length; index++) {
                formData.append('thumbFiles', thumbFiles[index]);
            }

            for (var index = 0; index < Object.keys(infoFiles).length; index++) {
                formData.append('infoFiles', infoFiles[index]);
            }

            $.ajax({
                url: '/admin/addProduct',
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
                cache: false,
                type: 'post',

                data: formData,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    location.href = "/admin/productList";
                },
                error: function (request, status, error) {
                    alert("code:" + request.status + "\n"
                        + "message : " + request.responseText
                        + "\n" + "error : " + error);
                }
            });
        }

    });


    (function () {
        var pclosed = $("#pClosed").val();
        var opClosed = $("#opClosed").val();

        var opc = "<button type='button' id='closedProductOp'>옵션 비공개</button>";
        var opo = "<button type='button' id='openProductOp'>옵션 공개</button>";
        var pc = "<button type='button' id='closedProduct'>상품 비공개</button>";
        var po = "<button type='button' id='openProduct'>상품 공개</button>";

        if (pclosed == 0) {
            $(".button-area").append(pc);
        } else if (pclosed == 1) {
            $(".button-area").append(po);
        }

        if (opClosed == 0) {
            $(".button-area").append(opc);
        } else if (opClosed == 1) {
            $(".button-area").append(opo);
        }

    })();


    $("#addProductOp").on('click', function () {
        var form = $("#modifyProductForm");

        form.submit();

    })

    $("#closedProductOp").on('click', function () {
        var pOpNo = {
            pOpNo: $("input[name=pOpNo]").val()
        };

        console.log(pOpNo);

        $.ajax({
            url: '/admin/closedProductOp',
            type: 'post',
            data: pOpNo,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });

    });

    $("#openProductOp").on('click', function () {
        var pOpNo = {
            pOpNo: $("input[name=pOpNo]").val()
        };

        console.log(pOpNo);

        $.ajax({
            url: '/admin/openProductOp',
            type: 'post',
            data: pOpNo,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });

    });

    $("#closedProduct").on('click', function () {
        var pno = {
            pno: $("input[name=pno]").val()
        };

        console.log(pno);

        $.ajax({
            url: '/admin/closedProduct',
            type: 'post',
            data: pno,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });

    });

    $("#openProduct").on('click', function () {
        var pno = {
            pno: $("input[name=pno]").val()
        };

        console.log(pno);

        $.ajax({
            url: '/admin/openProduct',
            type: 'post',
            data: pno,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });

    });


})

