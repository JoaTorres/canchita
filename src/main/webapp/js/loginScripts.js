$(document).ready(function () {


    if ($('#enable-show').is(':checked')) {
        $('#password').removeAttr('type');
        $('#password').attr('type', 'text');

    } else {
        $('#password').removeAttr('type');
        $('#password').attr('type', 'password');
    }

    $('#enable-show').on('click', function () {
        if ($('#enable-show').is(':checked')) {
            $('#password').removeAttr('type');
            $('#password').attr('type', 'text');

        } else {
            $('#password').removeAttr('type');
            $('#password').attr('type', 'password');
        }
    });

});

