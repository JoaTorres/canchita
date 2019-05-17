$(document).ready(function () {
    //load soccer field initial data to the modal
    var idCancha = $("#canchas").val();
    var cancha = $("#canchas option:selected").text();

    $('#idCanchaModal').val(idCancha);
    $('#canchaModal').val(cancha);


    //Method to call all reservations from an specific soccer field and time
    $("#consultarDisponibilidadBtn").on('click', () => {
        var idCancha = $("#canchas").val();
        var fecha = $("#fecha").val();

        //Reset table
        resetTabe('reservasTable');

        //Fill table
        $.ajax({
            url: "ReservasGetLista",
            type: 'POST',
            data: {idCancha: idCancha, fecha: fecha},
            dataType: 'json',
            success: function (list) {
                if (list.length > 0) {
                    for (var e in list) {
                        addRow('reservasTable', list[e]);
                    }
                }
            }
        });
    });

    $("#horaInicioModal").keyup(() => {
      verificarHorario();
    });
  
    $("#horaFinModal").keyup(() => {
        verificarHorario();
            });

    $("#horaInicioModal").on('change', () => {
       verificarHorario();
    });

    $("#horaFinModal").on('change', () => {
       verificarHorario();
    });

    $("#seleccionarBtn").on('click', () => {
        var horaInicio = $("#horaInicioModal").val();
        var horaFin = $("#horaFinModal").val();

        $("#horaInicio").val(horaInicio);
        $("#horaFin").val(horaFin);

        $.ajax({
            url: "ReservasCalculoCosto",
            type: 'POST',
            data: {horaInicio: horaInicio, horaFin: horaFin},
            dataType: 'json',
            success: function (costo) {
                $("#costo").val(costo);
            }
        });


    });



    //On change soccer field
    $("#canchas").on('change', () => {
        var idCancha = $("#canchas").val();
        var cancha = $("#canchas option:selected").text();

        $('#idCanchaModal').val(idCancha);
        $('#canchaModal').val(cancha);
    });

    //On change fecha
    $("#fecha").on('change', () => {
        var fecha = $("#fecha").val();
        $('#fechaModal').val(fecha);
    });



});

//ADD ROW
function addRow(tableID, e) {
    var table = document.getElementById(tableID);

    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var nroCol = 0;

    //Hora Inicio
    var cellHoraInicio = row.insertCell(nroCol);
    cellHoraInicio.style.padding = 0;
    var elementHoraInicio = document.createElement("input");
    elementHoraInicio.type = "text";
    elementHoraInicio.className = "form-control form-control-sm text-center";
    elementHoraInicio.name = "horaInicio" + row;
    elementHoraInicio.id = "horaInicio" + row;
    elementHoraInicio.setAttribute('disabled', true);
    elementHoraInicio.value = e.horaInicio;
    cellHoraInicio.appendChild(elementHoraInicio);
    nroCol++;


    //Hora Fin
    var cellHoraFin = row.insertCell(nroCol);
    cellHoraFin.style.padding = 0;
    var elementHoraFin = document.createElement("input");
    elementHoraFin.type = "text";
    elementHoraFin.className = "form-control form-control-sm text-center";
    elementHoraFin.name = "horaFin" + row;
    elementHoraFin.id = "horaFin" + row;
    elementHoraFin.setAttribute('disabled', true);
    elementHoraFin.value = e.horaFin;
    cellHoraFin.appendChild(elementHoraFin);
    nroCol++;

}

//RESET TABLE
function resetTabe(tableID) {
    $("#" + tableID).find("tr:gt(1)").remove();
}

function verificarHorario(){
    var idCancha = $("#canchas").val();
        var fecha = $("#fecha").val();
        var horaInicio = $("#horaInicioModal").val();
        var horaFin = $("#horaFinModal").val();

        let bandera=0;
        let html=`<button type="button" class="btn-block btn btn-info" data-dismiss="modal" id="seleccionarBtn" disabled="">
                                                        <i class="fas fa-check"></i> Seleccionar
                                                    </button>`;
        
        if (horaInicio.length >= 5 && horaFin.length >= 5) {
            if(bandera<1){
                bandera++;
             $.ajax({
                url: "ReservasHoraPermitida",
                type: 'POST',
                data: {idCancha: idCancha, fecha: fecha, horaInicio: horaInicio, horaFin: horaFin},
                dataType: 'json',
                 beforeSend: function () {
                  
                    document.querySelector('#loading').innerHTML="<img src='img/loading.gif' height='38' width='42'> cargando...";
                },
                success: function (horaPermitida) {
                    if (horaPermitida == true) {
                        $("#seleccionarBtn").removeAttr('disabled');
                    } else {
                        $("#seleccionarBtn").attr('disabled', true);
                    }
                      console.log("TERMINADO-KEYUP-HORAFINAL");
                      bandera=0;
                   document.querySelector('#loading').innerHTML=html;
                }
            });   
            }
            
        } else {
            $("#seleccionarBtn").attr('disabled', true);
        }

}
