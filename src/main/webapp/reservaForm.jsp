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
                    <li class="breadcrumb-item mt-2" aria-current="page"><a href="CanchaPick">Cancha</a></li>
                    <li class="breadcrumb-item mt-2" aria-current="page"><a href="HorarioPick?canchaId=${canchaId}&canchaNombre=${canchaNombre}&fecha=${fecha}">Horario</a></li>
                    <li class="breadcrumb-item mt-2 active" aria-current="page">Reserva</li>
                    <li class="ml-auto"><a class="btn btn-outline-dark" href="login.jsp" role="button">Administrar</a></li>
                </ol>
            </nav>




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

                </div>
                <div class="col-md-8 order-md-1">
                    <h4 class="mb-3">Reserva para la ${canchaNombre}
                        <small id="passwordHelpBlock" class="form-text text-muted">
                            Llene el siguiente formulario.
                        </small>
                    </h4>
                    <form action="ReservaNew" method="POST">

                        <input style="display: none;" type="text" class="form-control" name="canchaId" value="${canchaId}">
                        <input style="display: none;" type="text" class="form-control" name="canchaNombre" value="${canchaNombre}">
                        <input style="display: none;" type="text" class="form-control" name="fecha" value="${fecha}">

                        <div class="col-md-12">

                            <div class="form-group row mb-2">
                                <label class="col-md-4 col-form-label">
                                    <i class="fas fa-calendar"></i> Fecha
                                </label>
                                <div class="col-md-8">
                                    <input type="date" class="form-control" value="${fecha}" readonly="">
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <label class="col-md-4 col-form-label">
                                    <i class="fas fa-clock"></i> Hora Inicio
                                </label>
                                <div class="col-md-8">
                                    <input style="display: none;" type="text" class="form-control" name="idHoraInicio" id="idHoraInicio" value="${idHoraInicio}" readonly="">
                                    <input type="text" class="form-control" name="horaInicio" value="${horaInicio}" readonly="">
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <label  class="col-md-4 col-form-label">
                                    <i class="fas fa-futbol"></i> Cantidad de horas
                                </label>
                                <div class="col-md-8">
                                    <select class="form-control" name="horas" id="horas">
                                        <c:forEach var="hora" items="${horasDisponibles}">
                                            <option value="${hora}">${hora}</option>    
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <label class="col-md-4 col-form-label">
                                    <i class="fas fa-hand-holding-usd"></i> Costo (S/)
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control text-right" name="costo" id="costo" value="${tarifa}" readonly="">
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <label class="col-md-4 col-form-label">
                                    <i class="fas fa-user"></i> Nombre
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="cliente">
                                    <small id="passwordHelpBlock" class="form-text text-muted">
                                        Nombre referencial para la reserva.
                                    </small>
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <label class="col-md-4 col-form-label">
                                    <i class="fas fa-id-card"></i> DNI
                                </label>
                                <div class="col-md-8">
                                    <input type="number" class="form-control" name="dni">
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <label class="col-md-4 col-form-label">
                                    <i class="fas fa-phone"></i> Teléfono
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="telefono">
                                </div>
                            </div>
                            <div class="form-group row mb-2">
                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-primary form-control">
                                        <i class="fas fa-save"></i> Reservar</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!--            <footer class="my-5 pt-5 text-muted text-center text-small">
                            <p class="mb-1">&copy; 2019</p>
                            <ul class="list-inline">
                                <li class="list-inline-item"><a href="#">Privacy</a></li>
                                <li class="list-inline-item"><a href="#">Terms</a></li>
                                <li class="list-inline-item"><a href="#">Support</a></li>
                            </ul>
                        </footer>-->
        </div>

        <!-- Bootstrap core CSS -->
        <jsp:include page="js/mainScripts.jsp" />
        <script src="js/reservaFormScripts.js"></script><</-->

    </body>
</html>
