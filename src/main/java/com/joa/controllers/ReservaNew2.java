package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.classes.ReservaTO;
import com.joa.dao.CanchaDAO;
import com.joa.dao.ReservaDAO;
import com.joa.utils.DateUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaNew2", urlPatterns = {"/ReservaNew2"})
public class ReservaNew2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            ReservaDAO reservaDAO = new ReservaDAO();

            int canchaId = Integer.parseInt(request.getParameter("canchas"));
            String fecha = request.getParameter("fecha");
            String horaInicio = request.getParameter("horaInicio");
            String horaFin = request.getParameter("horaFin");
            double costo = Double.parseDouble(request.getParameter("costo"));
            String cliente = request.getParameter("cliente");
            String dni = request.getParameter("dni");
            String telefono = request.getParameter("telefono");
            int idEstado = 1;

            ReservaTO obj = new ReservaTO();

            obj.setIdCancha(canchaId);
            obj.setFecha(fecha);
            obj.setHoraInicio(horaInicio);
            obj.setHoraFin(horaFin);
            obj.setCosto(costo);
            obj.setCliente(cliente);
            obj.setDni(dni);
            obj.setTelefono(telefono);
            obj.setIdEstado(idEstado);
            
            boolean inserted = reservaDAO.insert2(obj);
            request.setAttribute("inserted", inserted);  
            request.setAttribute("login", 0);  
            
            
            
            
            
            //RETORNAR
            
            String today = DateUtils.localDateToFullString(DateUtils.getToday());
            request.setAttribute("today", today);   
            
            CanchaDAO objDAO = new CanchaDAO();
            List<CanchaTO> canchas = objDAO.list();
            request.setAttribute("canchas", canchas);
            
            
            
            getServletConfig().getServletContext().getRequestDispatcher("/reservaForm2.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("ERROR @ReservaNew2: " + e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
