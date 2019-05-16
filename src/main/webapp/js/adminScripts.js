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
        $("#reservarBtn").removeAttr('disabled');
        $("#metodo").val("EDITAR");
        $("#horaInicio").attr('readonly', true);
        $("#canchaModal").attr('disabled', true);
        $("#fechaModal").attr('readonly', true);

        var idCancha = $(this).attr('idCancha');
        var horaInicio = $(this).attr('horaInicio');

        $.ajax({
            url: "ReservaGetOne",
            type: 'POST',
            data: {fecha: fecha, idCancha: idCancha, horaInicio: horaInicio},
            dataType: 'json',
            success: function (data) {
                $("#canchaModal").val(data.idCancha).trigger('change');
                $("#idCancha").val(data.idCancha);
                $("#fecha").val(data.fecha);
                $("#horaInicio").val(data.horaInicio);
                $("#horaFin").val(data.horaFin);
                $("#costo").val(data.costo);
                $("#descuento").val(data.descuento);
                $("#total").val(data.total);
                $("#estado").val(data.idEstado).trigger('change');
                $("#pagado").val(data.pagado);
                $("#saldo").val(data.saldo);
                $("#cliente").val(data.cliente);
                $("#dni").val(data.dni);
                $("#telefono").val(data.telefono);
                
                calcMontos();
            }
        });

    });

    //NUEVA RESERVA
    $('#nuevaReservaBtn').click(() => {
        $("#metodo").val("NUEVO");
        $("#horaInicio").removeAttr('readonly');
        $("#canchaModal").removeAttr('disabled');
        $("#fechaModal").removeAttr('readonly');

        $("#canchaModal").val(1).trigger('change');
        $("#idCancha").val(1);
        $("#fecha").val(fecha);

        $("#horaInicio").val('');
        $("#horaFin").val('');
        $("#costo").val(0);
        $("#descuento").val(0);
        $("#total").val(0);
        $("#estado").val(1).trigger('change');
        $("#pagado").val(0);
        $("#saldo").val(0);
        $("#cliente").val('');
        $("#dni").val('');
        $("#telefono").val('');
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
    $("#horaInicio").keyup(() => {
        var idCancha = $("#idCancha").val();
        var fecha = $("#fecha").val();
        var horaInicio = $("#horaInicio").val();
        var horaFin = $("#horaFin").val();

        var metodo = $("#metodo").val();

        if (metodo == 'NUEVO') {
            if (horaInicio.length == 5 && horaFin.length == 5) {
                $.ajax({
                    url: "ReservasHoraPermitida",
                    type: 'POST',
                    data: {idCancha: idCancha, fecha: fecha, horaInicio: horaInicio, horaFin: horaFin},
                    dataType: 'json',
                    success: function (horaPermitida) {
                        if (horaPermitida == true) {
                            $("#reservarBtn").removeAttr('disabled');
                            $('#horaFin').tooltip('disable');
                            $('#horaFin').tooltip('hide');

                            $.ajax({
                                url: "ReservasCalculoCosto",
                                type: 'POST',
                                data: {horaInicio: horaInicio, horaFin: horaFin},
                                dataType: 'json',
                                success: function (costo) {
                                    $("#costo").val(costo);
                                    calcMontos();
                                }
                            });
                        } else {
                            $("#reservarBtn").attr('disabled', true);
                            $('#horaFin').tooltip('enable');
                            $('#horaFin').tooltip('show');
                            $("#costo").val(0);
                            calcMontos();
                        }
                    }
                });
            } else {
                $("#reservarBtn").attr('disabled', true);
                $("#costo").val(0);
                calcMontos();
            }
        } else {
            var horaInicio = $("#horaInicio").val();
            var horaFin = $("#horaFin").val();

            if (horaInicio.length == 5 && horaFin.length == 5) {
                $.ajax({
                    url: "ReservasCalculoCosto",
                    type: 'POST',
                    data: {horaInicio: horaInicio, horaFin: horaFin},
                    dataType: 'json',
                    success: function (costo) {
                        $("#costo").val(costo);
                        calcMontos();
                    }
                });
            } else {
                $("#costo").val(0);
                calcMontos();
            }
        }

    });

    //CAMBIO DE HORA FINAL
    $("#horaFin").keyup(() => {
        var idCancha = $("#canchaModal").val();
        var fecha = $("#fecha").val();
        var horaInicio = $("#horaInicio").val();
        var horaFin = $("#horaFin").val();

        var metodo = $("#metodo").val();

        if (metodo == 'NUEVO') {
            if (horaInicio.length == 5 && horaFin.length == 5) {
                $.ajax({
                    url: "ReservasHoraPermitida",
                    type: 'POST',
                    data: {idCancha: idCancha, fecha: fecha, horaInicio: horaInicio, horaFin: horaFin},
                    dataType: 'json',
                    success: function (horaPermitida) {
                        if (horaPermitida == true) {
                            $("#reservarBtn").removeAttr('disabled');
                            $('#horaFin').tooltip('disable');
                            $('#horaFin').tooltip('hide');

                            $.ajax({
                                url: "ReservasCalculoCosto",
                                type: 'POST',
                                data: {horaInicio: horaInicio, horaFin: horaFin},
                                dataType: 'json',
                                success: function (costo) {
                                    $("#costo").val(costo);
                                    
                                    calcMontos();
                                }
                            });

                        } else {
                            $("#reservarBtn").attr('disabled', true);
                            $('#horaFin').tooltip('enable');
                            $('#horaFin').tooltip('show');

                            $("#costo").val(0);
                            calcMontos();
                        }
                    }
                });
            } else {
                $("#reservarBtn").attr('disabled', true);
                $("#costo").val(0);
                calcMontos();
            }
        } else {
            var horaInicio = $("#horaInicio").val();
            var horaFin = $("#horaFin").val();

            if (horaInicio.length == 5 && horaFin.length == 5) {
                $.ajax({
                    url: "ReservasCalculoCosto",
                    type: 'POST',
                    data: {horaInicio: horaInicio, horaFin: horaFin},
                    dataType: 'json',
                    success: function (costo) {
                        $("#costo").val(costo);
                        calcMontos();
                    }
                });

            } else {
                $("#costo").val(0);
                calcMontos();
            }
        }

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
    $('#canchaModal').change(function () {
        var idCancha = $('#canchaModal').val();
        $("#idCancha").val(idCancha);
    });
    
    //KEYUP MONTOS    
    $('#descuento').keyup(() => {
        calcMontos();
    });
    $('#pagado').keyup(() => {
        calcMontos();
    });

});

function calcMontos(){
    var costo = $("#costo").val() === '' || isNaN($("#costo").val()) ? 0 : parseFloat($("#costo").val());
    var descuento = $("#descuento").val() === '' || isNaN($("#descuento").val()) ? 0 : parseFloat($("#descuento").val());
    
    var total = costo - descuento;
    $("#total").val(total);
    
    var pagado = $("#pagado").val() === '' || isNaN($("#pagado").val()) ? 0 : parseFloat($("#pagado").val());
    
    var saldo = total - pagado;
    $("#saldo").val(saldo);
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