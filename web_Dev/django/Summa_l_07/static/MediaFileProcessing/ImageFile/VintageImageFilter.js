$(document).ready(function(){
    alert('inside VintageImageFilter.js');
    $('#filter_nav button').on('click',function(e){
        var value = $(this).val();
        console.log(value);
        $('#preview').vintage({
            mime: 'image/jpeg'
        }, vintagePresets[value]);
        return false;
    });


    $('#form-post').on('submit', function(e){
        var image = $('#preview').attr('src');
        if( image ){
            $('input[name="filtered_image"]').val(image);
        }else{
            $('input[name="filtered_image"]').val('');
        }

        /*return false; ajax */

    });

    $('#id_image').on('change', function(e){
        var reader = new FileReader();
        reader.onerror = function(e){
            console.log(e);
        }
        reader.onloadend = function(e){
            console.log('loaded!');
            if((/^data\:image\/(png|jpeg);base64/i).test(e.target.result)){
                $('#preview').attr('src', e.target.result);
            }else{
                alert('사진을 가져오지 못했거나 허용된 이미지 형식이 아닙니다.');
            }
        }

        reader.readAsDataURL(this.files[0])
    });



});