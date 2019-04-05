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
                    <li class="breadcrumb-item mt-2 active" aria-current="page">Horario</li>
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
                    <h4 class="mb-3">Horario para la ${canchaNombre}
                        <small id="passwordHelpBlock" class="form-text text-muted">
                            Seleccione el horario.
                        </small>
                    </h4>

                    <div class="row">
                        <div class="col">
                            <form action="ReservaForm" method="POST">
                                <h5>${diaNombre1}</h5>
                                <p>Día: ${dia1}</p>
                                <input style="display: none;" type="text" name="canchaId" value="${canchaId}">
                                <input style="display: none;" type="text" name="canchaNombre" value="${canchaNombre}">
                                <input style="display: none;" type="text" name="fecha" value="${dia1}">
                                <table id="myTable" class="table table-bordered table-hover table-sm nowrap" width="100%" >
                                    <thead>
                                        <tr class="thead-dark">
                                            <th class="text-center">Hora</th>
                                            <th class="text-center">Estado</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="reserva" items="${reservas1}">
                                            <tr>
                                                <td>${reserva.horaInicio}-${reserva.horaFin}</td>
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${not empty reserva.cliente}">
                                                            <button type="button" class="btn btn-outline-secondary" disabled="">${reserva.cliente}</button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-success" value="${reserva.id}" name="idHoraInicio" id="reservarBtn">Reservar</button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                        <div class="col">
                            <form action="ReservaForm" method="POST">
                                <h5>${diaNombre2}</h5>
                                <p>Día: ${dia2}</p>
                                <input style="display: none;" type="text" name="canchaId" value="${canchaId}">
                                <input style="display: none;" type="text" name="canchaNombre" value="${canchaNombre}">
                                <input style="display: none;" type="text" name="fecha" value="${dia2}">
                                <table id="myTable" class="table table-bordered table-hover table-sm nowrap" width="100%" >
                                    <thead>
                                        <tr class="thead-dark">
                                            <th class="text-center">Hora</th>
                                            <th class="text-center">Estado</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="reserva" items="${reservas2}">
                                            <tr>
                                                <td>${reserva.horaInicio}-${reserva.horaFin}</td>
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${not empty reserva.cliente}">
                                                            <button type="button" class="btn btn-outline-secondary" disabled="">${reserva.cliente}</button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-success" value="${reserva.id}" name="idHoraInicio" id="reservarBtn">Reservar</button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                        <div class="col">
                            <form action="ReservaForm" method="POST">
                                <h5>${diaNombre3}</h5>
                                <p>Día: ${dia3}</p>
                                <input style="display: none;" type="text" name="canchaId" value="${canchaId}">
                                <input style="display: none;" type="text" name="canchaNombre" value="${canchaNombre}">
                                <input style="display: none;" type="text" name="fecha" value="${dia3}">
                                <table id="myTable" class="table table-bordered table-hover table-sm nowrap" width="100%" >
                                    <thead>
                                        <tr class="thead-dark">
                                            <th class="text-center">Hora</th>
                                            <th class="text-center">Estado</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="reserva" items="${reservas3}">
                                            <tr>
                                                <td>${reserva.horaInicio}-${reserva.horaFin}</td>
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${not empty reserva.cliente}">
                                                            <button type="button" class="btn btn-outline-secondary" disabled="">${reserva.cliente}</button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-success" value="${reserva.id}" name="idHoraInicio" id="reservarBtn">Reservar</button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
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

    </body>
</html>
