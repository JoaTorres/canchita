<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%//EVITAR QUE ACCEDAN A LA PÁGINA A TRAVÉS DE LA URL

    if (session == null || session.getAttribute("userTO") == null) {
        response.sendRedirect("login.jsp"); // Logged in, so just continue.
    } else {
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Alfa City Sport</title>
        <link rel="icon" href="img/icon2.png">

        <!-- Styles -->
        <jsp:include page="css/mainStyles.jsp"/>


    </head>

    <body>
        <!-- LOADING -->
        <div class="se-pre-con"></div>

        <!--NAVBAR-->
        <jsp:include page="navbar.jsp" />

        <div class="row-offcanvas row-offcanvas-left">
            <div id="sidebar" class="sidebar-offcanvas">
                <div class="col-md-12">
                    <br>
                    <div class="text-center">
                        <img src="img/user.png" width="140" height="140" class="rounded" alt="...">
                    </div>
                    <br>
                    <h4 class="page-header text-center"><span class="fas fa-user"></span> Hola <c:out value="${userTO.nombre}"/>!</h4>

                    <hr>
                    <form name="filters" action="ReservaList" method="POST">

                        <span><span class="fas fa-calendar"></span> Fecha : </span>
                        <input type="date" class="form-control" name="fecha" id="fecha" onchange="document.filters.submit();" value="${fecha}">

                    </form>



                    <hr>

                    <span class="fas fa-list-ul mt-2"></span> Estados
                    <select class="form-control selectpicker border" data-style="btn-white" multiple data-actions-box="true" id="estados" name="estados">
                        <c:forEach var="objEstado" items="${estados}">
                            <c:choose>
                                <c:when test="${objEstado.getMarcado() eq 1}">
                                    <option value="${objEstado.getId()}" selected="" >${objEstado.getDescripcion()}</option>
                                </c:when>
                                <c:when test="${objEstado.getMarcado() eq 0}">
                                    <option value="${objEstado.getId()}">${objEstado.getDescripcion()}</option>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <input hidden class="form-control" type="text" id="estadosHidden" name="estadosHidden" value="${estadosHidden}"/>


                    <hr>



                    <button class="btn btn-info form-control mb-2" id="nuevaReservaBtn" data-toggle="modal" data-target="#myNewModal">
                        <span class="fas fa-plus"></span> Reserva
                    </button>

                    <form action="ReservaExcel" method="post" class="form-horizontal" role="form" id="formulario" name="formulario">
                        <input style="display: none;" type="text" class="form-control" name="fechaExcel" id="fechaExcel" value="${fecha}">
                        <input style="display: none;" type="text" class="form-control" name="estadosExcel" id="estadosExcel" value="${estadosHidden}">

                        <button type="submit" class="btn btn-success form-control" name="action" value="reporteTax">
                            <i class="fas fa-file-excel"></i> Excel</button>
                    </form>

                    <hr>



                </div>
            </div>

            <div id="main">
                <div class="col-md-12">
                    <br>
                    <p class="d-block d-sm-none">
                        <button type="button" class="btn btn-primary " data-toggle="offcanvas"><i class="fas fa-bars"></i></button>
                    </p>
                    <h2><span class="fas fa-futbol"></span> Reservas</h2>
                    <input style="display: none;" class="form-control" type="text" id="idsCanchas" name="idsCanchas" value="${idsCanchas}"/>

                    <div class="row">
                        <div class="col-md-12"> 
                            <ul class="nav nav-tabs" id="newTabs" role="tablist">
                                <c:forEach var="cancha" items="${canchas}">
                                    <li class="nav-item">
                                        <a class="nav-link <c:if test="${cancha.id == 1}">active</c:if>" data-toggle="tab" href="#panel${cancha.id}">${cancha.nombre}</a>
                                        </li>
                                </c:forEach>
                            </ul>

                            <div class="tab-content">
                                <c:forEach var="cancha" items="${canchas}">
                                    <div id="panel${cancha.id}" class="tab-pane fade <c:if test="${cancha.id == 1}">show active</c:if>">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <table id="tabla${cancha.id}" class="table table-bordered table-hover table-sm nowrap display">
                                                    <thead>
                                                        <tr class="thead-dark">

                                                            <th class="text-center" style="display: none;">idEstado</th>
                                                            <th class="text-center">Estado</th>
                                                            <th class="text-center">Fecha</th>
                                                            <th class="text-center">Hora I.</th>
                                                            <th class="text-center">Hora F.</th>
                                                            <th class="text-center">Costo</th>
                                                            <th class="text-center">Descuento</th>
                                                            <th class="text-center">Total</th>
                                                            <th class="text-center">Pagado</th>
                                                            <th class="text-center">Saldo</th>
                                                            <th class="text-center">Cliente</th>
                                                            <th class="text-center">DNI</th>
                                                            <th class="text-center">Teléfono</th>
                                                            <th class="text-center">Acciones</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="reserva" items="${cancha.reservas}">
                                                            <tr>

                                                                <td class="text-center" style="display: none;">${reserva.idEstado}</td>

                                                                <c:choose>
                                                                    <c:when test="${reserva.idEstado eq '1'}">
                                                                        <td class="table-secondary"><strong>${reserva.estado}</strong></td>
                                                                            </c:when>
                                                                            <c:when test="${reserva.idEstado eq '2'}">
                                                                        <td class="table-success"><strong>${reserva.estado}</strong></td>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                        <td class="">Libre</td>
                                                                    </c:otherwise>
                                                                </c:choose>


                                                                <td class="text-center">${reserva.fecha}</td>
                                                                <td class="text-center">${reserva.horaInicio}</td>
                                                                <td class="text-center">${reserva.horaFin}</td>
                                                                <td class="text-right">${reserva.costo}</td>
                                                                <td class="text-right">${reserva.descuento}</td>
                                                                <td class="text-right">${reserva.total}</td>
                                                                <td class="text-right">${reserva.pagado}</td>
                                                                <td class="text-right">${reserva.saldo}</td>
                                                                <td>${reserva.cliente}</td>
                                                                <td>${reserva.dni}</td>
                                                                <td>${reserva.telefono}</td>
                                                                <td>

                                                                    <button style="margin-bottom: 5px;" class="btn btn-warning btn-sm btnClicked mb-0" id="editar" idCancha ="${reserva.idCancha}" fecha ="${reserva.fecha}" horaInicio ="${reserva.horaInicio}" data-toggle="modal" data-target="#myNewModal">
                                                                        <span class="fas fa-edit"></span> Editar
                                                                    </button>

                                                                    <button style="margin-bottom: 5px;" class="btn btn-danger btn-sm btnEliminarClicked mb-0" id="eliminar" idCancha ="${reserva.idCancha}" fecha ="${reserva.fecha}" horaInicio ="${reserva.horaInicio}" data-toggle="modal" data-target="#modalDelete">
                                                                        <span class="fas fa-trash"></span> Eliminar
                                                                    </button>

                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>
                                    </div>

                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>    


            <!-- Modal New -->
            <div class="modal fade" id="myNewModal" role="dialog" aria-labelledby="myNewModal" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <form action="ReservaNewAdmin" method="post" class="form-horizontal" role="form" id="formulario" name="formulario">
                            <div class="modal-header">
                                <h3 class="modal-title" id="myModalLabel"><span class="fas fa-briefcase"></span> Reserva</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">

                                        <div class="col-md-12">
                                            <input hidden type="text" class="form-control" id="metodo" name="metodo">
                                            <input hidden type="text" class="form-control" name="estadosNew" id="estadosNew" value="${estadosHidden}">

                                            <h5>Datos del contacto</h5>
                                            <div class="row col-md-12">
                                                <div class="col-md-6">
                                                    Cliente
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-user"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control" id="cliente" name="cliente" autofocus="" placeholder="Nombre" required>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    DNI
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-id-card"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control" id="dni" name="dni" placeholder="DNI" required>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    Teléfono
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-phone"></i></div>    
                                                        </div>
                                                        <input type="text" class="form-control" id="telefono" name="telefono" placeholder="Teléfono" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <br>
                                            <h5>Datos de la reserva</h5>

                                            <div class="row col-md-12">
                                                <div class="col-md-6">
                                                    Cancha
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-futbol"></i></div>
                                                        </div>
                                                        <select class="form-control js-example-basic-single" id="canchaModal" name="canchaModal">
                                                            <c:forEach var="obj" items="${canchas}">
                                                                <option value="${obj.id}">${obj.nombre} - <small>${obj.descripcion}</small></option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <input hidden type="text" class="form-control" id="idCancha" name="idCancha">
                                                </div>
                                                <div class="col-md-6">
                                                    Fecha
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-clock"></i></div>
                                                        </div>
                                                        <input type="date" class="form-control" id="fechaModal" name="fechaModal" value="${fecha}">
                                                    </div>
                                                </div>

                                            </div>


                                            <div class="row col-md-12">
                                                <div class="col-md-6">
                                                    Hora inicio
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-clock"></i></div>
                                                        </div>
                                                        <input type="time" class="form-control" id="horaInicio" name="horaInicio">
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    Hora fin
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-clock"></i></div>
                                                        </div>
                                                        <input type="time" class="form-control" id="horaFin" name="horaFin" data-toggle="tooltip" data-placement="top" title="Hora ocupada">
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="row col-md-12">
                                                <div class="col-md-4">
                                                    Costo
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-money-bill"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control text-right" id="costo" name="costo" readonly="">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    Descuento
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-arrow-circle-down"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control text-right" id="descuento" name="descuento">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    Total
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-money-check"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control text-right" id="total" name="total" readonly="">
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="row col-md-12">
                                                <div class="col-md-4">
                                                    Estado
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-list-ul"></i></div>
                                                        </div>
                                                        <select class="form-control js-example-basic-single" id="estado" name="estado">
                                                            <c:forEach var="obj" items="${estadosModal}">
                                                                <option value="${obj.id}">${obj.descripcion}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <input hidden type="text"  class="form-control" name="estadosNew" id="estadosNew" value="${estadosHidden}" readonly="">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    Pago
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-hand-holding-usd"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control text-right" id="pagado" name="pagado">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    Saldo
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-money-check"></i></div>
                                                        </div>
                                                        <input type="text" class="form-control text-right" id="saldo" name="saldo" readonly="">
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">  
                                <button type="button" class="btn btn-danger" data-dismiss="modal">
                                    <i class="fas fa-sign-out-alt"></i> Cerrar
                                </button>

                                <button type="submit" class="btn btn-primary" id="reservarBtn" disabled="">
                                    <i class="fas fa-save"></i> Reservar
                                </button>

                            </div>
                        </form>    
                    </div> <!--/.modal-content -->
                </div> <!--/.modal-dialog--> 
            </div> <!--/.New Test -->


            <!-- Modal Delete -->
            <div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <form action="ReservaDelete" method="post" class="form-horizontal" role="form" id="formulario" name="formulario">

                            <div class="modal-header">
                                <h3 class="modal-title" id="myModalLabel"><span class="fas fa-thumbtack"></span> Eliminar reserva</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>

                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <label class="control-label">¿Seguro que desea eliminar la reserva del día: <b><label id="fechaDeleteLbl"></label></b> a las <b><label id="horaDeleteLbl"></label></b> ?</label>
                                                <input hidden type="text" class="form-control" name="estadosDelete" id="estadosDelete" value="${estadosHidden}">
                                                <input hidden type="text"  class="form-control" name="idCanchaDelete" id="idCanchaDelete">
                                                <input hidden type="text"  class="form-control" name="idFechaDelete" id="idFechaDelete" value="${fecha}">
                                                <input hidden type="text"  class="form-control" name="horaInicioDeleteLbl" id="horaInicioDeleteLbl">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                    <span class="fas fa-sign-out-alt"></span> Cerrar</button>
                                <button type="submit" class="btn btn-danger">
                                    <span class="fas fa-trash"></span> Eliminar</button>

                            </div>
                        </form>    
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.Delete -->


        </div><!--/row-offcanvas -->



        <!-- Scripts -->
        <jsp:include page="js/mainScripts.jsp" />
        <script src="js/adminScripts.js"></script>

        <!-- MENSAJES DE REGISTRO -->
        <c:choose>
            <c:when test="${login eq 0}">
                <c:if test="${inserted}">
                    <script>
                            alertGrowl("Atención: Registro <strong>exitoso</strong>.", "success");
                    </script>
                </c:if>
                <c:if test="${not inserted}">
                    <script>
                        alertGrowl("Atención: Registro <strong>fallido</strong>.", "danger");
                    </script>
                </c:if>
            </c:when>
        </c:choose>

    </body>
</html>
<%    }
%>


