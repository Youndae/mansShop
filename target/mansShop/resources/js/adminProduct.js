let firstThumbFile = null;
let thumbFiles = {};
let infoFiles = {};
let deleteFirstThumbFile = null;
let deleteThumbFiles = {};
let deleteThumbNum = 0;
let deleteInfoFiles = {};
let deleteInfoNum = 0;
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
const numRegex = /\d/;

function validation(fileName) {
    fileName = fileName + "";
    const fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
    const fileNameExtension = fileName.toLowerCase().substring(fileNameExtensionIndex, fileName.length);

    if (!((fileNameExtension === 'jpg') || (fileNameExtension === 'gif') || (fileNameExtension === 'png') || (fileNameExtension === 'jpeg'))) {
        alert("jpg, gif, png, jpeg 확장자만 업로드 가능합니다.");
        return true;
    } else {
        return false;
    }
}

function addFirstPreview(input) {
    const file = input[0].files[0];
    const imgNum = 'f0';
    const previewElement = $("#firstThumbPreview");

    if (validation(file.name))
        return;
    else {
        setPreviewForm(file, imgNum, previewElement);
        firstThumbFile = file;
    }
}

function addThumbPreview(input) {
    const previewElement = $("#thumbPreview");
    if (input[0].files.length <= 4) {
        for (let fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            const file = input[0].files[fileIndex];
            if (validation(file.name))
                continue;
            const imgNum = "t" + fileIndex;
            setPreviewForm(file, imgNum, previewElement);
            thumbFiles[fileIndex] = file;
        }
    } else {
        alert("썸네일은 4장까지만 업로드 가능합니다.");
    }
}

function addInfoPreview(input) {
    const previewElement = $("#infoPreview");
    if (input[0].files.length <= 10) {
        for (let fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            const file = input[0].files[fileIndex];
            if (validation(file.name))
                continue;
            const imgNum = "i" + fileIndex;
            setPreviewForm(file, imgNum, previewElement);
            infoFiles[fileIndex] = file;
        }
    } else {
        alert("정보이미지 10장까지만 업로드 가능합니다.");
    }
}

function setPreviewForm(file, imgNum, previewElement) {

    let reader = new FileReader();
    reader.onload = function (img) {
        const appendStr = "<div class=\"preview-box\" value=\"" + imgNum + "\">" +
            "<img class=\"thumbnail img_default\" src=\"" + img.target.result + "\"\/>" +
            "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
            "삭제" +
            "</a>" +
            "</div>";

        previewElement.append(appendStr);
    };

    reader.readAsDataURL(file);
}

function deletePreview(obj) {
    const imgNum = obj.attributes['value'].value;
    const imgType = imgNum.substr(0, 1);
    const indexNo = imgNum.substr(1, imgNum.length + 1);

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
    const imgNum = obj.attributes['value'].value;
    const imgType = imgNum.substr(0, 2);
    const imgName = $('img[id=' + imgNum + ']').attr("src");
    const idx = imgName.lastIndexOf('=');
    const deleteImg = imgName.substring(idx + 1);

    if (imgType == "of") {
        deleteFirstThumbFile = deleteImg;
        $("#firstThumbPreview .preview-box[value=" + imgNum + "]").remove();
    } else if (imgType == "ot") {
        deleteThumbFiles[deleteThumbNum++] = deleteImg;
        $("#thumbPreview .preview-box[value=" + imgNum + "]").remove();
    } else if (imgType == "oi") {
        deleteInfoFiles[deleteInfoNum++] = deleteImg;
        $("#infoPreview .preview-box[value=" + imgNum + "]").remove();
    }
}


$(document).ready(function () {

    $("#addProduct").on('click', function(){
        location.href='/admin/product/new';
    })

    const classification = $("#classification").val();
    const size = $("#size").val();

    $('select[name=pClassification]').val(classification);
    $('select[name=pSize]').val(size);

    const pno = $('input[name=pno]').val();

    (function () {
        $.getJSON("/admin/first-thumb", {pno: pno}, function (arr) {
            const str = img("of", 'firstThumb img_default', arr);

            $("#firstThumbPreview").append(str);
        })
    })();

    (function () {
        $.getJSON("/admin/thumbnail", {pno: pno}, function (arr) {
            const str = img("ot", 'thumb img_default', arr);

            $("#thumbPreview").append(str);
        })
    })();

    (function () {
        $.getJSON("/admin/info-image", {pno: pno}, function (arr) {
            const str = img("oi", 'infoImg', arr);

            $("#infoPreview").append(str);
        })
    })();

    function img(type, className, arr) {
        let num = 1;
        let str = "";
        $(arr).each(function (i, attach) {
            const imgNum = type + num;
            str += "<div class=\"preview-box\" value=\"" + imgNum + "\">";
            str += "<img class=\"" + className + "\" id=\"" + imgNum + "\" src=\"/display?image=" + attach.imageName + "\"\/>";
            str += "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deleteOldPreview(this)\">";
            str += "삭제" + "</a>";
            str += "</div>";
            num++;
        });

        return str;
    }


    $("#modifyProduct").on('click', function () {
        const form = $("#modifyProductForm")[0];
        const formData = new FormData(form);

        if ($('select[name=pClassification]').val() == "default" || $('select[name=pClassification]').val() == null) {
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
        } else if (firstThumbFile == null && $("#of1").attr("src") == null) {
            $("#checkFirstThumb").text("대표사진을 선택해주세요");
        } else if (Object.keys(infoFiles).length == 0 && ($("#oi1").attr("src") == null)) {
            $("#checkInfo").text("상품정보사진을 선택해주세요");
        } else {
            if(firstThumbFile != null)
                formData.append('firstThumbFile', firstThumbFile);

            for (let index = 0; index < Object.keys(thumbFiles).length; index++)
                formData.append('thumbFiles', thumbFiles[index]);

            for (let index = 0; index < Object.keys(infoFiles).length; index++)
                formData.append('infoFiles', infoFiles[index]);

            if(deleteFirstThumbFile != null)
                formData.append('deleteFirstThumbFile', deleteFirstThumbFile);

            for (let index = 0; index < Object.keys(deleteThumbFiles).length; index++)
                formData.append('deleteThumbFiles', deleteThumbFiles[index]);

            for (let index = 0; index < Object.keys(deleteInfoFiles).length; index++)
                formData.append('deleteInfoFiles', deleteInfoFiles[index]);


            $.ajax({
                url: '/admin/product/',
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
                cache: false,
                type: 'patch',
                data: formData,
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success: function (result) {
                    if(result == 1)
                        location.href = "/admin/product";
                    else
                        alert("오류가 발생했습니다.");
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
        if ($('select[name=pSize]').val() == "default") {
            $('select[name=pSize]').val(null);
        }

        const form = $('#addProductForm')[0];
        const formData = new FormData(form);

        if ($('select[name=pClassification]').val() == "default" || $('select[name=pClassification]').val() == null) {
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
        } else if (firstThumbFile == null) {
            $("#checkFirstThumb").text("대표사진을 선택해주세요");
        } else if (Object.keys(infoFiles).length == 0) {
            $("#checkInfo").text("상품정보사진을 선택해주세요");
        } else {
            formData.append('firstThumbFile', firstThumbFile);

            for (let index = 0; index < Object.keys(thumbFiles).length; index++) {
                formData.append('thumbFiles', thumbFiles[index]);
            }

            for (let index = 0; index < Object.keys(infoFiles).length; index++) {
                formData.append('infoFiles', infoFiles[index]);
            }

            $.ajax({
                url: '/admin/product',
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
                    if(data == 1){
                        location.href = "/admin/product";
                    }else{
                        alert('오류 발생');
                    }
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
        const pclosed = $("#pClosed").val();
        const opClosed = $("#opClosed").val();

        const opc = "<button type='button' id='closedProductOp'>옵션 비공개</button>";
        const opo = "<button type='button' id='openProductOp'>옵션 공개</button>";
        const pc = "<button type='button' id='closedProduct'>상품 비공개</button>";
        const po = "<button type='button' id='openProduct'>상품 공개</button>";

        if (pclosed == 0)
            $(".button-area").append(pc);
        else if (pclosed == 1)
            $(".button-area").append(po);

        if (opClosed == 0)
            $(".button-area").append(opc);
        else if (opClosed == 1)
            $(".button-area").append(opo);

    })();

    $("#addProductOp").on('click', function () {
        const form = $("#modifyProductForm");
        form.submit();
    })

    $("#closedProductOp").on('click', function () {
        const pOpNo = $("input[name=pOpNo]").val();

        $.ajax({
            url: '/admin/product-option/close/' + pOpNo,
            type: 'patch',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });
    });

    $("#openProductOp").on('click', function () {
        const pOpNo = $("input[name=pOpNo]").val();

        $.ajax({
            url: '/admin/product-option/open/' + pOpNo,
            type: 'patch',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });
    });

    $("#closedProduct").on('click', function () {
        const pno = $("input[name=pno]").val();


        $.ajax({
            url: '/admin/product/close/' + pno,
            type: 'patch',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });
    });

    $("#openProduct").on('click', function () {
        const pno = $("input[name=pno]").val();

        $.ajax({
            url: '/admin/product/open/' + pno,
            type: 'patch',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.reload();
            }
        });
    });
})