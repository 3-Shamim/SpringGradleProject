jQuery(document).ready(function(){

    $("#checkAll").change(function(){
        $(".case").prop('checked', $(this).prop("checked"));
    });

    $('.case').change(function(){
        if(false == $(this).prop("checked")){
            $("#checkAll").prop('checked', false);
        }
        if ($('.case:checked').length == $('.case').length ){
            $("#checkAll").prop('checked', true);
        }
    });

});