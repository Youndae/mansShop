const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
let firstThumbFile = null;
let thumbFiles = [];
let infoFiles = [];
let deleteFirstThumbFile = null;
let deleteThumbFiles = [];
let deleteInfoFiles = [];
let deleteOptionIds = [];
let thumbNo = 0;
let infoNo = 0;
let newOptionIdx = 0;
let discountElementsNo = 0;

$(function() {
    $(".first-thumbnail-input input[type=file]").change(function() {
        addFirstPreview($(this));
    })

    $(".product-thumbnail-input input[type=file]").change(function() {
        addThumbPreview($(this));
    })

    $(".info-image-input input[type=file]").change(function() {
        addInfoPreview($(this));
    })

    $(".discount-classification-select-box").change(function() {
        const selectValue = $(".discount-classification-select-box option:selected").val();
        const appendElements = $(".discount-product-select");
        appendElements.empty();
        $.getJSON(`/admin/product/discount/category/${selectValue}`, function (data) {
            let str = "<label>상품 : </label>" +
                "<select class=\"discount-product-select-box\">" +
                "<option value=\"default\" selected hidden>상품을 선택해주세요</option>";

            for(let i = 0; i < data.length; i++)
                str += "<option value=\"" + data[i].id + "\">" + data[i].productName + "</option>";

            str += "</select>";

            appendElements.append(str);
        })
    })

    $('body').on('change', '.discount-product-select-box', async function() {
        const selectValue = $(".discount-product-select-box option:selected").val();
        const appendElements = $(".discount-content-content");
        const productElements = await createElementsBySelectDiscountProduct(selectValue);
        if(appendElements.is(':empty')){
            console.log('appendElements is empty');
            const str = "<div class=\"discount-input\">" +
                "<label>할인율 (%) : </label>" +
                "<input type=\"number\" id=\"discount-value-input\">" +
                "</div>" +
                "<div class=\"discount-product\">" +
                productElements +
                "</div>";
            appendElements.append(str);
        }else
            $(".discount-product").append(productElements);
    })

    $('body').on('change', '#discount-value-input', function() {
        const discountValue = Number($(this).val());
        const appendElements = $(".discount-content-content");

        if(!appendElements.is(':empty')){
            $(".discount-product-content").each(function () {
                const price = $(this).find('.product-original-price').text().replace(',', '');
                const discountPrice = price - (price * discountValue / 100);
                $(this).find('.product-discount-price').text(discountPrice.toLocaleString());
            })
        }
    })
})

async function createElementsBySelectDiscountProduct(productId) {
    const data = await $.getJSON(`/admin/product/discount/select/${productId}`);
    const discountValue = Number($("#discount-value-input").val()) || 0;
    const discountPrice = data.price - (data.price * discountValue / 100);

    const str =  "<div class=\"discount-product-content discount-content-" + discountElementsNo + "\" data-product-id=\"" + data.id + "\">" +
                    "<div class=\"discount-product-content-header\">" +
                        "<h3>" + data.productName + "</h3>" +
                        "<button type=\"button\" class=\"default-btn\" value=\"" + discountElementsNo + "\" onclick=\"deleteDiscountElement(this)\">삭제</button>" +
                    "</div>" +
                    "<div class=\"discount-product-content-content\">" +
                        "<div class=\"form-group\">" +
                            "<label class=\"discount-product-label\">가격 : </label>" +
                            "<span class=\"discount-product-price product-original-price\">" + data.price.toLocaleString() + "</span>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label class=\"discount-product-label\">할인 적용가 : </label>" +
                            "<span class=\"discount-product-price product-discount-price\">" + discountPrice.toLocaleString() + "</span>" +
                        "</div>" +
                    "</div>" +
                "</div>";
    discountElementsNo++;

    return str;
}

function deleteDiscountElement(obj) {
    const value = obj.value;
    $(`.discount-content-${value}`).remove();
}

function productUpdateLinkBtn(obj) {
    const pid = obj.value;
    location.href=`/admin/product/patch/${pid}`;
}

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
    const previewElement = $(".first-thumbnail-content");
    previewElement.empty();

    if (validation(file.name))
        return;
    else {
        let reader = new FileReader();
        reader.onload = function (img) {
            const appendStr = "<button type=\"button\" class=\"first-thumbnail-delete-btn image-btn\" onclick=\"deleteFirstThumb()\">삭제</button>" +
                            "<img src=\"" + img.target.result + "\"/>";

            previewElement.append(appendStr);
        };

        reader.readAsDataURL(file);

        firstThumbFile = file;
    }
}

function addThumbPreview(input) {
    const previewElement = $(".product-thumbnail-content");
    const onclickFunc = 'deleteThumb(this)';

    for(let fileIdx = 0; fileIdx < input[0].files.length; fileIdx++) {
        const file = input[0].files[fileIdx];
        if(validation(file.name))
            break;

        const className = `new-thumb-${thumbNo}`;

        setPreviewForm(file, thumbNo, previewElement, className, onclickFunc);
        thumbFiles.push({
            imageNo: thumbNo++,
            file: file,
        });
    }
}

function addInfoPreview(input) {
    const previewElement = $(".info-image-content");
    const onclickFunc = 'deleteInfoImage(this)';
    for(let fileIdx = 0; fileIdx < input[0].files.length; fileIdx++) {
        const file = input[0].files[fileIdx];
        if(validation(file.name))
            break;
        const className = `info-image-content-image info-image-${infoNo}`
        setPreviewForm(file, infoNo, previewElement, className, onclickFunc);
        infoFiles.push({
            imageNo: infoNo++,
            file: file,
        });
    }
}

function setPreviewForm(file, imgNum, previewElement, className, onclickFunc) {
    let reader = new FileReader();
    reader.onload = function (img) {
        const appendStr = "<div class=\"" + className + "\">" +
                                "<button type=\"button\" class=\"info-image-delete-btn image-btn\" value=\"" + imgNum + "\" onclick=\"" + onclickFunc + "\">삭제</button>" +
                                "<img src=\"" + img.target.result + "\"/>" +
                            "</div>";

        previewElement.append(appendStr);
    };

    reader.readAsDataURL(file);
}

function deleteOldFirstThumb(obj) {
    deleteFirstThumbFile = obj.value;
    $(".first-thumbnail-content").empty();
}

function deleteFirstThumb() {
    firstThumbFile = null;
    $(".first-thumbnail-content").empty();
}

function deleteOldThumbnail(obj) {
    const val = obj.value.split('/');
    const idx = val[0];
    deleteThumbFiles.push(val[1]);
    $(`.old-thumb-${idx}`).remove();
}

function deleteThumb(obj) {
    const idx = Number(obj.value);
    spliceFileArr(thumbFiles, idx);
    $(`.new-thumb-${idx}`).remove();
}

function deleteOldInfoImage(obj) {
    const val = obj.value.split('/');
    const idx = val[0];
    deleteInfoFiles.push(val[1]);
    $(`.old-info-${idx}`).remove();
}

function deleteInfoImage(obj) {
    const idx = Number(obj.value);
    spliceFileArr(infoFiles, idx);
    $(`.info-image-${idx}`).remove();
}

function spliceFileArr(arr, idx) {
    const deleteObject = arr.find(function(item) {
        return item.imageNo === idx;
    });
    const deleteIdx = arr.indexOf(deleteObject);
    arr.splice(deleteIdx, 1);
}

function handleAddOption() {
    const elements = $(".option-test");
    const addOptionId = 'new/' + newOptionIdx;
    const str = "<div class=\"option-detail-new option-" + addOptionId + "\">" +
                    "<div class=\"option-detail-header\">" +
                        "<button type=\"button\" class=\"default-btn\" value=\"" + addOptionId + "\" onclick=\"deleteProductOption(this)\">옵션 삭제</button>" +
                    "</div>" +
                    "<div class=\"option-size\">" +
                        "<label class=\"product-label\">사이즈</label>" +
                        "<input type=\"text\" class=\"product-input\" name=\"size\">" +
                    "</div>" +
                    "<div class=\"option-color\">" +
                        "<label class=\"product-label\">컬러</label>" +
                        "<input type=\"text\" class=\"product-input\" name=\"color\">" +
                    "</div>" +
                    "<div class=\"option-stock\">" +
                        "<label class=\"product-label\">재고</label>" +
                        "<input type=\"number\" class=\"product-input\" name=\"optionStock\">" +
                    "</div>" +
                    "<div class=\"option-isOpen\">" +
                        "<label class=\"product-label\">옵션 공개 여부</label>" +
                        "<div class=\"product-isOpen-radio isOpen-radio\">" +
                            "<label class=\"radio-label\">공개</label>" +
                            "<input type=\"radio\" class=\"radio-input option-radio-open\" name=\"option-isOpen-" + addOptionId + "\" value=\"open\" checked />" +
                            "<label class=\"radio-label\">비공개</label>" +
                            "<input type=\"radio\" class=\"radio-input option-radio-close\" name=\"option-isOpen-" + addOptionId + "\" value=\"close\" />" +
                        "</div>" +
                    "</div>" +
                "</div>";

    newOptionIdx++;
    elements.append(str);
}

function productAddBtn() {
    const validateData = createValidateProductDataObject();
    if(validateToProductData(validateData)){
        const formData = createFormData(validateData);

        $.ajax({
            url: `/admin/product`,
            type: 'POST',
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            data: formData,
            beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.href = `/admin/product/${result}`;
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })
    }
}

function productUpdateBtn(obj) {
    const validateData = createValidateProductDataObject();
    if(validateToProductData(validateData)){
        const productId = obj.value;
        const formData = createFormData(validateData);
        appendProductDeleteImageToFormData(formData);
        for(let i = 0; i < deleteOptionIds.length; i++)
            formData.append('deleteOptionIds', deleteOptionIds[i]);

        $.ajax({
            url: `/admin/product/${productId}`,
            type: 'PATCH',
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            data: formData,
            beforeSend : function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                location.href = `/admin/product/${result}`;
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })
    }
}

function createValidateProductDataObject() {
    const productName = $("#productName").val();
    const price = $("#price").val() || '0';
    const newOption = createNewOptionArray();
    const oldOption = createOriginalOptionArray();

    return {
        productName: productName,
        price: price,
        newOption: newOption,
        oldOption: oldOption,
    };
}

function validateToProductData(data) {
    productOverlapAllClear();
    if(data.productName === ''){
        $('.product-overlap').text('상품명은 필수 입력사항입니다.');
        $("#productName").focus();
    }else if(data.price === '0') {
        $('.product-price-overlap').text('가격은 필수 입력사항입니다.');
        $("#price").focus();
    }else if(data.newOption.length === 0 && data.oldOption.length === 0){
        $('.product-option-overlap').text('옵션 1개는 필수로 생성해야 합니다.');
        document.querySelector(".option-header").scrollIntoView({
            behavior: "smooth",
            block: 'center'
        });
    }else if($('.first-thumbnail-content').is(':empty')){
        $(".first-thumbnail-overlap").text('대표 썸네일은 필수 선택 사항입니다.');
        document.querySelector(".first-thumbnail-input").scrollIntoView({
            behavior: "smooth",
            block: 'center'
        });
    }else if($(".info-image-content").is(':empty')) {
        $('.info-image-overlap').text('상품 정보 이미지는 필수 선택 사항입니다.');
        document.querySelector(".info-image-input").scrollIntoView({
            behavior: "smooth",
            block: 'center'
        });
    }else {
        return true;
    }

    return false;
}

function productOverlapAllClear() {
    $('.product-overlap').empty();
    $('.product-price-overlap').empty();
    $('.product-option-overlap').empty();
    $(".first-thumbnail-overlap").empty();
    $('.info-image-overlap').empty();
}

function createFormData(data) {
    const formData = new FormData();
    const classification = $(".product-classification-select-box option:selected").val();
    const productIsOpen = $(".product-isOpen .product-isOpen-radio input:checked").val() === 'open';
    const discount = $("#discount").val() || '0';
    formData.append('productName', data.productName);
    formData.append('classification', classification);
    formData.append('price', data.price);
    formData.append('isOpen', productIsOpen.toString());
    formData.append('discount', discount);
    appendNewOptionArrayToFormData(formData, data.newOption, 'newOptions');
    appendProductImageToFormData(formData);

    if(data.oldOption.length !== 0)
        appendOldOptionArrayToFormData(formData, data.oldOption, 'oldOptions');

    return formData;
}

function createOriginalOptionArray() {
    const options = [];
    $(".option-test .option-detail").each(function() {
        const data = createOptionDataObject($(this));
        options.push(data);
    });
    return options;
}

function createNewOptionArray() {
    const options = [];
    $(".option-test .option-detail-new").each(function() {
        const data = createOptionDataObject($(this));
        options.push(data);
    })
    return options;
}

function createOptionDataObject(obj) {
    const optionId = $(obj).find('.option-detail-header button').val();
    const size = $(obj).find('input[name="size"]').val();
    const color = $(obj).find('input[name="color"]').val();
    const stock = $(obj).find('input[name="optionStock"]').val() || '0';
    const optionIsOpen = $(obj).find('.product-isOpen-radio input:checked').val() === 'open';

    return {
        optionId: optionId.split('/')[1],
        size: size,
        color: color,
        stock: stock,
        isOpen: optionIsOpen.toString(),
    };
}

function appendNewOptionArrayToFormData(formData, arr) {
    arr.forEach(function(option, index) {
        formData.append(`newOptions[${index}].size`, option.size);
        formData.append(`newOptions[${index}].color`, option.color);
        formData.append(`newOptions[${index}].stock`, option.stock);
        formData.append(`newOptions[${index}].isOpen`, option.isOpen);
    })
}

function appendOldOptionArrayToFormData(formData, arr) {
    arr.forEach(function(option, index) {
        formData.append(`oldOptions[${index}].optionId`, option.optionId);
        formData.append(`oldOptions[${index}].size`, option.size);
        formData.append(`oldOptions[${index}].color`, option.color);
        formData.append(`oldOptions[${index}].stock`, option.stock);
        formData.append(`oldOptions[${index}].isOpen`, option.isOpen);
    })
}

function appendProductImageToFormData(formData) {
    if(firstThumbFile !== null)
        formData.append('firstThumbnail', firstThumbFile);

    for(let i = 0; i < thumbFiles.length; i++)
        formData.append('thumbnails', thumbFiles[i].file);

    for(let i = 0; i < infoFiles.length; i++)
        formData.append('infoImages', infoFiles[i].file);
}

function appendProductDeleteImageToFormData(formData) {
    if(deleteFirstThumbFile !== null)
        formData.append('deleteFirstThumbnail', deleteFirstThumbFile);

    for(let i = 0; i < deleteThumbFiles.length; i++)
        formData.append('deleteThumbnails', deleteThumbFiles[i]);

    for(let i = 0; i < deleteInfoFiles.length; i++)
        formData.append('deleteInfoImages', deleteInfoFiles[i]);
}

function deleteProductOption(obj) {
    const optionId = obj.value;

    $(`.option-test .option-${optionId}`).remove();
}

function deleteOldProductOption(obj) {
    const valueArr = obj.value.split('/');
    const divIdx = valueArr[0];
    const optionId = valueArr[1];

    $(`.option-test .option-${divIdx}`).remove();
    deleteOptionIds.push(optionId);
}

function addProductLinkBtn() {
    location.href = '/admin/product/add';
}

function addDiscountBtnOnClick() {
    location.href = '/admin/product/discount/add';
}

function handleProductDiscount() {
    const discountValue = $("#discount-value-input").val() || '0';
    if(confirm(`설정된 할인율은 ${discountValue}% 입니다.\n설정하시겠습니까?`)){
        const productIds = [];
        $(".discount-product-content").each(function() {
            productIds.push($(this).attr('data-product-id'));
        })
        const data = {
            discount: discountValue,
            productIds: productIds,
        }
        $.ajax({
            url: '/admin/product/discount',
            type: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                if(data === 'SUCCESS')
                    location.href = '/admin/product/discount';
                else
                    alert('오류가 발생했습니다.');
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n"
                    + "message : " + request.responseText
                    + "\n" + "error : " + error);
            }
        })
    }
}

