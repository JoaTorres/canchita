$(document).ready(function () {

    var table = datatableInit("myTable");



    $("#myTable tbody").on('click', 'tr', function () {
        var id = table.row(this).data()[0];

        if ($(this).hasClass('table-active')) {
            $(this).removeClass('table-active');

            $('#editarBtn').attr('disabled', true);

            $("#id").val('');
            $("#horaInicio").val('');
            $("#horaFin").val('');
            $("#tarifa").val('');



        } else {
            table.$('tr.table-active').removeClass('table-active');
            $(this).addClass('table-active');
            
            $('#editarBtn').removeAttr('disabled');

            $.ajax({
                url: "HorarioGetOne",
                type: 'POST',
                data: {id: id},
                dataType: 'json',
                success: function (data) {

                    if (data !== null) {
                        $("#id").val(data.id);
                        $("#horaInicio").val(data.horaInicio);
                        $("#horaFin").val(data.horaFin);
                        $("#tarifa").val(data.tarifa);
                    } else {
                        $("#id").val('');
                        $("#horaInicio").val('');
                        $("#horaFin").val('');
                        $("#tarifa").val('');
                    }

                }
            });


        }




    });

});

