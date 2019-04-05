$(document).ready(function () {

    $("#horas").on('change', () => {
        var idHoraInicio = $('#idHoraInicio').val();
        var horas = $('#horas').val();

        $.ajax({
            url: "HorarioGetCosto",
            type: 'POST',
            data: {idHoraInicio: idHoraInicio, horas: horas},
            dataType: 'json',
            success: function (data) {

                if (data !== null) {
                    $("#costo").val(data.tarifa);
                } else {
                    $("#costo").val('');
                }
            }
        });
    });
});

