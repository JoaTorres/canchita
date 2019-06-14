var reservadas = 0, pagadas = 0, libres = 0, total = 0;
var tables;

$(document).ready(function () {

    //FECHA
    var fecha = $('#fecha').val();


    var idsCanchas = $('#idsCanchas').val();
    idsCanchas = toArray(idsCanchas, ',');

    tables = [idsCanchas.length];


    for (var i = 0; i < idsCanchas.length; i++) {
        tables[i] = datatableInit("tabla" + idsCanchas[i], 3);
    }

    for (var i = 0; i < tables.length; i++) {
        tables[i].on('search.dt', function () {
            resetVars();
        });
    }

    //EDITAR RESERVA
    $('.btnClicked').click(function () {
        $("#reservarBtnEdit").removeAttr('disabled');
        $("#horaInicioEdit").attr('readonly', true);
        $("#canchaLabelEdit").attr('readonly', true);
        $("#canchaEdit").attr('readonly', true);
        $("#fechaEdit").attr('readonly', true);

        var idCancha = $(this).attr('idCancha');
        var horaInicio = $(this).attr('horaInicio');

        $.ajax({
            url: "ReservaGetOne",
            type: 'POST',
            data: {fecha: fecha, idCancha: idCancha, horaInicio: horaInicio},
            dataType: 'json',
            success: function (data) {
                $("#canchaLabelEdit").val(data.cancha);
                $("#canchaEdit").val(data.idCancha);
//                $("#idCancha").val(data.idCancha);
                $("#fechaEdit").val(data.fecha);
                $("#horaInicioEdit").val(data.horaInicio);
                $("#horaFinEdit").val(data.horaFin);
                $("#costoEdit").val(data.costo);
                $("#descuentoEdit").val(data.descuento);
                $("#totalEdit").val(data.total);
                $("#estadoEdit").val(data.idEstado).trigger('change');
                $("#pagadoEdit").val(data.pagado);
                $("#saldoEdit").val(data.saldo);
                $("#clienteEdit").val(data.cliente);
                $("#dniEdit").val(data.dni);
                $("#telefonoEdit").val(data.telefono);

                calcMontos("Edit");
            }
        });

    });

    //NUEVA RESERVA
    $('#nuevaReservaBtn').click(() => {
        $("#horaInicioNew").removeAttr('readonly');
        $("#canchaNew").removeAttr('disabled');
        $("#fechaNew").removeAttr('readonly');

        $("#canchaNew").val(1).trigger('change');
//        $("#idCanchaNew").val(1);
        $("#fechaNew").val(fecha);

        $("#horaInicioNew").val('');
        $("#horaFinNew").val('');
        $("#costoNew").val(0);
        $("#descuentoNew").val(0);
        $("#totalNew").val(0);
        $("#estadoNew").val(1).trigger('change');
        $("#pagadoNew").val(0);
        $("#saldoNew").val(0);
        $("#clienteNew").val('');
        $("#dniNew").val('');
        $("#telefonoNew").val('');
    });

    //ELIMINAR RESERVA
    $('.btnEliminarClicked').click(function () {
        var idCancha = $(this).attr('idCancha');
        var idHora = $(this).attr('idHora');
        var horaInicio = $(this).attr('horaInicio');

        $("#idCanchaDelete").val(idCancha);
        $("#idHoraDelete").val(idHora);

        $("#fechaDeleteLbl").val(fecha);
        $("#horaInicioDeleteLbl").val(horaInicio);


    });


    //CAMBIO DE HORA INICIAL
    $("#horaInicioNew").keyup(() => {
        verificarHorario("New");
    });

    $("#horaInicioNew").on('change', () => {
        verificarHorario("New");
    });

    //CAMBIO DE HORA FINAL
    $("#horaFinNew").keyup(() => {
        verificarHorario("New");
    });

    $("#horaFinNew").on('change', () => {
        verificarHorario("New");
    });



    //CAMBIO DE HORA INICIAL
    $("#horaInicioEdit").keyup(() => {
        verificarHorario("Edit");
    });

    $("#horaInicioEdit").on('change', () => {
        verificarHorario("Edit");
    });

    //CAMBIO DE HORA FINAL
    $("#horaFinEdit").keyup(() => {
        verificarHorario("Edit");
    });

    $("#horaFinEdit").on('change', () => {
        verificarHorario("Edit");
    });





    //CHANGE ESTADOS
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

    //CHANGE CANCHA
//    $('#canchaModal').change(function () {
//        var idCancha = $('#canchaModal').val();
//        $("#idCancha").val(idCancha);
//    });

    //KEYUP MONTOS    
    $('#descuentoNew').keyup(() => {
        calcMontos("New");
    });
    $('#pagadoNew').keyup(() => {
        calcMontos("New");
    });

    $('#descuentoEdit').keyup(() => {
        calcMontos("Edit");
    });
    $('#pagadoEdit').keyup(() => {
        calcMontos("Edit");
    });

});

function verificarHorario(sufix) {
    var idCancha = $("#cancha" + sufix).val();
    var fecha = $("#fecha" + sufix).val();
    var horaInicio = $("#horaInicio" + sufix).val();
    var horaFin = $("#horaFin" + sufix).val();


    //if sufix 
    if (sufix == 'New') {

        if (horaInicio.length == 5 && horaFin.length == 5) {

            $.ajax({
                url: "ReservasHoraPermitida",
                type: 'POST',
                data: {idCancha: idCancha, fecha: fecha, horaInicio: horaInicio, horaFin: horaFin},
                dataType: 'json',
                success: function (horaPermitida) {
                    if (horaPermitida == true) {
                        $("#reservarBtn" + sufix).removeAttr('disabled');
                        $("#horaFin" + sufix).tooltip('disable');
                        $("#horaFin" + sufix).tooltip('hide');

                        $.ajax({
                            url: "ReservasCalculoCosto",
                            type: 'POST',
                            data: {horaInicio: horaInicio, horaFin: horaFin},
                            dataType: 'json',
                            success: function (costo) {
                                $("#costo" + sufix).val(costo);

                                calcMontos(sufix);
                            }
                        });

                    } else {
                        $("#reservarBtn" + sufix).attr('disabled', true);
                        $("#horaFin" + sufix).tooltip('enable');
                        $("#horaFin" + sufix).tooltip('show');

                        $("#costo" + sufix).val(0);
                        calcMontos(sufix);
                    }
                }
            });

        }
    } else {
        console.log('xd');
        $("#reservarBtn" + sufix).removeAttr('disabled');
        $("#horaFin" + sufix).tooltip('disable');
        $("#horaFin" + sufix).tooltip('hide');

        $.ajax({
            url: "ReservasCalculoCosto",
            type: 'POST',
            data: {horaInicio: horaInicio, horaFin: horaFin},
            dataType: 'json',
            success: function (costo) {
                $("#costo" + sufix).val(costo);

                calcMontos(sufix);
            }
        });
    }


}

function calcMontos(sufix) {
    var costo = $("#costo" + sufix).val() === '' || isNaN($("#costo" + sufix).val()) ? 0 : parseFloat($("#costo" + sufix).val());
    var descuento = $("#descuento" + sufix).val() === '' || isNaN($("#descuento" + sufix).val()) ? 0 : parseFloat($("#descuento" + sufix).val());

    var total = costo - descuento;
    $("#total" + sufix).val(total);

    var pagado = $("#pagado" + sufix).val() === '' || isNaN($("#pagado" + sufix).val()) ? 0 : parseFloat($("#pagado" + sufix).val());

    var saldo = total - pagado;
    $("#saldo" + sufix).val(saldo);
}

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