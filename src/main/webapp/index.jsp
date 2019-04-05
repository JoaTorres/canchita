<%-- 
    Document   : index
    Created on : Apr 5, 2019, 3:14:10 PM
    Author     : developer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ page contentType="text/html"%>
        <%@ page import = "javax.servlet.RequestDispatcher" %>
        <%
            RequestDispatcher rd = request.getRequestDispatcher("/CanchaPick");
            request.setAttribute("msg", "HI Welcome");
            rd.forward(request, response);
        %>
    </body>
</html>
