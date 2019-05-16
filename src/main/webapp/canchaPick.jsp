<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
                    <li class="breadcrumb-item mt-2 active" aria-current="page">Cancha</li>
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
                    <h4 class="mb-3">Cancha
                        <small id="passwordHelpBlock" class="form-text text-muted">
                            Seleccione el tipo de cancha.
                        </small>
                    </h4>

                    <c:forEach var="obj" items="${canchas}">
                        <div class="card mb-3">
                            <div class="row no-gutters">
                                <div class="col-md-4">
                                    <img src="img/ball.png" class="card-img" alt="...">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <form action="HorarioPick" method="POST">
                                            <div class="row">   
                                                <div class="col-md-6"> 
                                                    <h5 class="card-title">${obj.nombre}</h5>
                                                    <p class="card-text">${obj.descripcion} Acompañado de una breve descripción de la cancha.</p>
                                                    <input style="display: none;" type="text" id="canchaNombre" name="canchaNombre" value="${obj.nombre}">
                                                    <input style="display: none;" type="text" id="canchaId" name="canchaId" value="${obj.id}">

                                                </div>
                                                <div class="col-md-6">
                                                    <h5 class="card-title">Fecha</h5>
                                                    <input  type="text" class="datepicker" id="fecha" name="fecha" value="${today}" data-date-start-date = '${today}'>

                                                    <p class="card-text"><small class="text-muted">Seleccione una fecha.</small></p>
                                                </div>

                                            </div>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-search"></i> Ver reservas</button>
                                            <p class="card-text"><small class="text-muted">Para revisar el estado de reservas por cancha y fecha seleccionadas.</small></p>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>                                                      



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
        <script src="js/canchaPickScripts.js"></script>

    </body>
</html>
