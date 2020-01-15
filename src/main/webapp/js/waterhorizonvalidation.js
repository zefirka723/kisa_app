$(document).ready(function(){
    $('.addingButton').prop('disabled',true);
    $('#horIdField').keyup(function(){
        $('.addingButton').prop('disabled', this.value == "" ? true : false);
    })
});