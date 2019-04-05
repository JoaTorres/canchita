<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Alfa City Sport</title>
        <link rel="icon" href="img/icon2.png">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!--IMPORT STYLES-->
        <jsp:include page="css/mainStyles.jsp" />
        <link rel="stylesheet" href="css/loginStyles.css"/>



    </head>
    <body class="text-center">

        <!-- MENSAJES -->
        <c:if test="${msg == 'Error de logeo.'}">
            <script>
                alert("Atención: Usuario/clave incorrecto, contactar con <strong>Sistemas</strong>.", "danger", 450);
            </script>
        </c:if>

        <form class="form-signin card" action="UserLogin" method="POST">

            <h1 class="h3 my-5 font-weight-normal"><span class="fas fa-futbol"></span> CANCHITA</h1>
            <label class="sr-only">Usuario</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Usuario" required autofocus>
            <label class="sr-only">Clave</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Clave" required>
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="enable-show"> Mostrar clave
                </label>
            </div>
            <button class="btn btn-lg btn-success btn-block" type="submit">
                <span class="fas fa-sign-in-alt"></span> Ingresar
            </button>
            <a class="btn btn-lg btn-info btn-block" href="CanchaPick">
                <span class="fas fa-arrow-left"></span> Regresar
            </a>
            <p class="mt-5 mb-3 text-muted">&copy; 2018-2019</p>
        </form>

        <jsp:include page="js/mainScripts.jsp" />
        <script type="text/javascript" language="javascript" src="js/loginScripts.js"></script>
    </body>
</html>
