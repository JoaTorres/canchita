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
                    <!--                    <button style="margin-bottom: 5px;" class="btn btn-info btn-block" id="nuevaCanchaBtn" data-toggle="modal" data-target="#myNewModal">
                                            <span class="fas fa-plus"></span> Nueva cancha
                                        </button>-->
                    <button style="margin-bottom: 5px;" class="btn btn-warning btn-block" id="editarBtn" data-toggle="modal" data-target="#myNewModal" disabled="">
                        <span class="fas fa-edit"></span> Editar horario
                    </button>

                    <hr>

<!--                    <form action="TableExcel" method="post" class="form-horizontal" role="form" id="formulario" name="formulario">
                        <input  style="display: none;" type="text" class="form-control" name="estadosExcel" id="estadosExcel" value="${estadosExcel}">
                        <input  style="display: none;" type="text" class="form-control" name="gruposExcel" id="gruposExcel" value="${gruposExcel}">

                        <button type="submit" class="btn btn-success form-control" name="action" value="reporteTax">
                            <i class="fas fa-file-excel"></i> Excel</button>
                    </form>-->


                </div>
            </div>
            <div id="main">
                <div class="col-md-12">
                    <br>
                    <p class="d-block d-sm-none">
                        <button type="button" class="btn btn-primary " data-toggle="offcanvas"><i class="fas fa-bars"></i></button>
                    </p>
                    <h2><span class="fas fa-clock"></span> Horarios</h2>
                    <hr>
                    <div class="col-md-12"> 


                        <div class="row">
                            <div class="col-md-12">
                                <table id="myTable" class="table table-bordered table-hover table-sm nowrap display">
                                    <thead>
                                        <tr class="thead-dark">

                                            <th class="text-center">Id</th>
                                            <th class="text-center">H.Inicio</th>
                                            <th class="text-center">H. Fin</th>
                                            <th class="text-center">Tarifa</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="obj" items="${list}">
                                            <tr>
                                                <td class="text-center">${obj.id}</td>
                                                <td class="text-center">${obj.horaInicio}</td>
                                                <td class="text-center">${obj.horaFin}</td>
                                                <td class="text-right">${obj.tarifa}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>      




        <!-- Modal New -->
        <div class="modal fade" id="myNewModal" role="dialog" aria-labelledby="myNewModal" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form action="HorarioNew" method="post" class="form-horizontal" role="form" id="formulario" name="formulario">
                        <div class="modal-header">
                            <h3 class="modal-title" id="myModalLabel"><span class="fas fa-thumbtack"></span> Horario</h3>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">

                                    <div class="col-md-12">

                                        <input type="text" class="form-control" name="id" id="id">

                                        <div class="form-row mb-2">
                                            <div class="col-md-12">
                                                <span><i class="fas fa-clock"></i> Hora inicio</span>
                                                <input type="text"  class="form-control form-control-sm" name="horaInicio" id="horaInicio" autofocus="">
                                            </div>
                                        </div>
                                        <div class="form-row mb-2">
                                            <div class="col-md-12">
                                                <span><i class="fas fa-clock"></i> Hora fin</span>
                                                <input type="text"  class="form-control form-control-sm" name="horaFin" id="horaFin">
                                            </div>
                                        </div>
                                        <div class="form-row mb-2">
                                            <div class="col-md-12">
                                                <span><i class="fas fa-hand-holding-usd"></i> Tarifa</span>
                                                <input type="text"  class="form-control form-control-sm" name="tarifa" id="tarifa" >
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

                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save"></i> Guardar
                            </button>

                        </div>
                    </form>    
                </div> <!--/.modal-content -->
            </div> <!--/.modal-dialog--> 
        </div> <!--/.New Test -->




    </div><!--/row-offcanvas -->

    <!-- Styles -->
    <jsp:include page="js/mainScripts.jsp" />
    <script src="js/horarioScripts.js"></script>


    <!-- MENSAJES DE REGISTRO -->
    <c:if test="${respuesta eq 1}">
        <script>
            alertGrowl("Atención: Cancha <strong>registrada</strong>.", "success");
        </script>

    </c:if>
    <c:if test="${respuesta eq 2}">
        <script>
            alertGrowl("Atención: Cancha <strong>actualizada</strong>.", "warning");
        </script>

    </c:if>
    <c:if test="${respuesta eq 0}">
        <script>
            alertGrowl("Atención: Cancha <strong>NO</strong> registrada.", "danger");
        </script>
    </c:if>
</body>
</html>
<%    }
%>


