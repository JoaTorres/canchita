<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <!--<a class="navbar-brand" href="admin.jsp" style="padding-top: 0px;">Reservas-->
    <!--        <img height="35px" style="text-decoration:none;" alt="Brand" src="">-->
    <!--</a>-->
    <a class="navbar-brand" href="ReservaList"> Inicio</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNavAltMarkup"  >

        <ul class="navbar-nav mr-auto">


            <li class="nav-item">
                <a class="nav-link" href="ReservaList"><span class="fas fa-calendar-check"></span> Reservas</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="CanchaList"><span class="fas fa-futbol"></span> Canchas</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="HorarioList"><span class="fas fa-clock"></span> Horarios</a>
            </li>

        </ul>


        <form class="form-inline my-2 my-lg-0" action="UserLogout">

            <span class="navbar-text mr-sm-2">
                <i class="fas fa-user"></i> Usuario: <span id="sessionUsername">${userTO.nombre}</span>
            </span>

            <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">
                <i class="glyphicon glyphicon-log-out"></i>
                Cerrar Sessión
            </button>
        </form>

    </div>
</nav>
