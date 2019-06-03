<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Joaquín Torres Sáenz">
        <title>Alfa City Sport</title>
        <link rel="icon" href="img/icon2.png">

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/checkout/">

        <!-- Bootstrap core CSS -->
        <jsp:include page="css/mainStyles.jsp" />

        <style>
            .bd-placeholder-img {
                font-size: 1.125rem;
                text-anchor: middle;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
            }

            @media (min-width: 768px) {
                .bd-placeholder-img-lg {
                    font-size: 3.5rem;
                }
            }
        </style>

        <!-- Bootstrap core CSS -->
        <jsp:include page="js/mainScripts.jsp" />
        <script src="js/reservaFormScripts.js"></script>


    </head>
    <body class="bg-light">



        <div class="container">
            <div class="py-5 text-center">
                <!--<img class="d-block mx-auto mb-4" src="" alt="" width="72" height="72">-->
                <h2>Reservas</h2>
                <p class="lead">Seleccione de entre las opciones dadas para realizar su reserva. Gracias. </p>
            </div>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="ml-auto"><a class="btn btn-outline-dark" href="login.jsp" role="button">Administrar</a></li>
                </ol>
            </nav>

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
                        
                        <div class="row"></div>

            <div class="row">
                <div class="col-md-4 order-md-2 mb-4">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Precios</span>
                        <span class="badge badge-secondary badge-pill">S/</span>
                    </h4>
                    <ul class="list-group mb-3">
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <h6 class="my-0">Mañana</h6>
                                <small class="text-muted">07:00 am - 13:00 pm</small>
                            </div>
                            <span class="text-muted">S/ 50</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <h6 class="my-0">Tarde</h6>
                                <small class="text-muted">13:00 pm - 19:00 pm</small>
                            </div>
                            <span class="text-muted">S/ 50</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <h6 class="my-0">Noche</h6>
                                <small class="text-muted">19:00 pm - 00:00 am</small>
                            </div>
                            <span class="text-muted">S/ 90</span>
                        </li>
                    </ul>
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Eventos</span>
                        <span class="badge badge-secondary badge-pill"><span class="fas fa-gift"></span></span>
                    </h4>
                    <ul class="list-group mb-3">

                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <h6 class="my-0">Contactar con: </h6>
                            </div>
                            <span class="text-muted">960186739</span>
                        </li>
                    </ul>

                </div>
                <div class="col-md-8 order-md-1">
                    <form action="ReservaNew2" method="POST">
                        <h4 class="mb-3">Datos de la reserva</h4>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label><i class="fas fa-futbol"></i> Cancha</label>
                                <select class="form-control" name="canchas" id="canchas">
                                    <c:forEach var="cancha" items="${canchas}">
                                        <option value="${cancha.id}">${cancha.nombre} - <small>${cancha.descripcion}</small></option>    
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label><i class="fas fa-calendar"></i> Fecha</label>
                                <input  type="date" class="form-control" id="fecha" name="fecha" value="${today}">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label><i class="fas fa-clock"></i> Hora Inicio</label>
                                <input type="time" class="form-control text-right" name="horaInicio" id="horaInicio" readonly="">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label><i class="fas fa-clock"></i> Hora Fin</label>
                                <input type="time" class="form-control text-right" name="horaFin" id="horaFin" readonly="">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label><i class="fas fa-search"></i> Consultar Disponibilidad</label>
                                <button type="button" class="btn btn-info form-control" id="consultarDisponibilidadBtn" 
                                        data-toggle="modal" data-target="#disponibilidadModal">
                                    <i class="fas fa-search"></i>
                                </button>

                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <label><i class="fas fa-hand-holding-usd"></i> Costo (S/)</label>
                                <input type="text" class="form-control text-right" name="costo" id="costo" readonly="">
                            </div>
                        </div>

                        <h4 class="mb-3">Datos de contacto</h4>
                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <label><i class="fas fa-user"></i> Nombre</label>
                                <input type="text" class="form-control" name="cliente">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label><i class="fas fa-id-card"></i> DNI</label>
                                <input type="number" class="form-control" name="dni">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label><i class="fas fa-phone"></i> Teléfono</label>
                                <input type="text" class="form-control" name="telefono">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <button type="submit" class="btn btn-primary form-control">
                                    <i class="fas fa-save"></i> Reservar</button>
                            </div>
                        </div>
                    </form>     
                </div>
            </div>


            <!-- MODAL DISPONIBILIDAD -->
            <div class="modal fade" id="disponibilidadModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h2 class="modal-title" id="myModalLabel"><i class="fas fa-thumbtack"></i> DISPONIBILIDAD</h2>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-12">                                                        
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <span><i class="fas fa-futbol"></i> Cancha</span>
                                                    <input class="form-control" id="canchaModal" name="canchaModal" readonly="">
                                                    <input class="form-control" id="idCanchaModal" name="idCanchaModal" readonly="" hidden="">
                                                </div>
                                                <div class="col-md-6">
                                                    <span><i class="fas fa-calendar"></i> Fecha</span>
                                                    <input class="form-control" id="fechaModal" name="fechaModal" value="${today}" readonly="">
                                                </div>
                                            </div>
                                            <hr>
                                            <table class="table table-bordered table-sm nowrap" id="reservasTable">
                                                <thead> 
                                                    <tr class="thead-dark text-center">
                                                        <th colspan="2">HORAS RESERVADAS</th>    
                                                    </tr>
                                                    <tr class="thead-dark text-center">
                                                        <th><i class="fas fa-clock"></i> Hora Inicio</th>                                                
                                                        <th><i class="fas fa-clock"></i> Hora Fin</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                </tbody>
                                            </table>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-clock"></i>&nbsp;Hora Inicio</div>
                                                        </div>
                                                        <input type="time" class="form-control text-right" id="horaInicioModal" name="horaInicioModal" min="07:00:00" max="23:58:00">
                                                    </div>

                                                </div>
                                                <div class="col-md-4">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text"><i class="fas fa-clock"></i>&nbsp;Hora Fin</div>
                                                        </div>
                                                        <input type="time" class="form-control text-right" id="horaFinModal" name="horaFinModal">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <button type="button" class="btn-block btn btn-info" data-dismiss="modal" id="seleccionarBtn" disabled="">
                                                        <i class="fas fa-check"></i> Seleccionar
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.MODAL PAGOS -->

        </div>









    </body>
</html>
