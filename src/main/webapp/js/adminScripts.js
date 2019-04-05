var reservadas = 0, pagadas = 0, libres = 0, total = 0;
var tables;

$(document).ready(function () {

    $("#nuevaReservaBtn").show();
    $('#editarReservaBtn').hide();

    //FECHA
    var fecha = $('#fecha').val();


    var idsCanchas = $('#idsCanchas').val();
    idsCanchas = toArray(idsCanchas, ',');

    tables = [idsCanchas.length];


    for (var i = 0; i < idsCanchas.length; i++) {
        console.log('meow');
        tables[i] = datatableInit("tabla" + idsCanchas[i], 4);
    }

    for (var i = 0; i < tables.length; i++) {
        console.log('esdsfdasdfasdfasdf');
        tables[i].on('search.dt', function () {
            resetVars();
        });
    }


    $('.btnClicked').click(function () {
        var idCancha = $(this).attr('idCancha');
        var idHora = $(this).attr('idHora');
        var horaInicio = $(this).attr('horaInicio');

        $.ajax({
            url: "ReservaGetOne",
            type: 'POST',
            data: {fecha: fecha, idCancha: idCancha, idHora: idHora},
            dataType: 'json',
            success: function (data) {

                if (data !== null) {
                    $("#idHora").val(data.id);
                    $("#idCancha").val(data.idCancha);
                    $("#fecha").val(data.fecha);
                    $("#horaInicio").val(data.horaInicio);
                    $("#horaFin").val(data.horaFin);
                    $("#tarifa").val(data.tarifa);
                    $("#cliente").val(data.cliente);
                    $("#dni").val(data.dni);
                    $("#telefono").val(data.telefono);
                } else {

                    $.ajax({
                        url: "HorarioGetTarifa",
                        type: 'POST',
                        data: {idHoraInicio: idHora},
                        dataType: 'text',
                        success: function (data) {
                            $("#tarifa").val(data);
                        }
                    });

                    $("#idHora").val(idHora);
                    $("#idCancha").val(idCancha);
                    $("#fecha").val(fecha);
                    $("#horaInicio").val(horaInicio);
                    $("#tarifa").val('');
                    $("#horaFin").val('');
                    $("#cliente").val('');
                    $("#dni").val('');
                    $("#telefono").val('');
                }

            }
        });

    });


    $('.btnEliminarClicked').click(function () {
        var idCancha = $(this).attr('idCancha');
        var idHora = $(this).attr('idHora');
        var horaInicio = $(this).attr('horaInicio');

        $("#idCanchaDelete").val(idCancha);
        $("#idHoraDelete").val(idHora);

        $("#fechaDeleteLbl").text(fecha);
        $("#horaDeleteLbl").text(horaInicio);


    });

    //CHANGE
    $('#estados').change(function () {
        var estados = $('#estados').val();
        $("#estadosHidden").val(estados);
        $("#estadosExcel").val(estados);
        $("#estadosEdit").val(estados);
        $("#estadosNew").val(estados);
        $("#estadosDelete").val(estados);
        resetVars();

        for (var i = 0; i < tables.length; i++) {
            tables[i].draw();
        }
    });


});

function resetVars() {
    reservadas = 0;
    pagadas = 0;
    libres = 0;
    total = 0;
}




$.fn.dataTable.ext.search.push(
        function (settings, data, dataIndex) {
            var estadosSelect = $('#estados').val();
            var estadosColumna = data[0];
           
            if (estadosSelect.indexOf(estadosColumna) !== -1) {
                switch (estadosColumna) {
                    case '0':
                        libres++;
                        total++;
                        break;
                    case '1':
                        reservadas++;
                        total++;
                        break;
                    case '2':
                        pagadas++;
                        total++;
                        break;
                }

                $('#libres').text(libres);
                $('#reservadas').text(reservadas);
                $('#pagadas').text(pagadas);

                $('#total').text(total);
                return true;
            }
            return false;
        }
);