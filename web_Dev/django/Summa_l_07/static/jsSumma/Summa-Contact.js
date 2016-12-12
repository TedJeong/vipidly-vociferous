$(document).ready(function(){
    $("input[type='radio'][name='opt']").change(function(){
        var scopeval = String($(this).val());
        alert(scopeval);

        <!-- radio_value : input_value -->
        $("input[name='scope']").attr('value',scopeval);
    });
});