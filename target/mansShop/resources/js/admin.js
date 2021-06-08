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