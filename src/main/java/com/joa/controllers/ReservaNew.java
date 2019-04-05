/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.ReservaTO;
import com.joa.dao.HorarioDAO;
import com.joa.dao.ReservaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author developer
 */
@WebServlet(name = "ReservaNew", urlPatterns = {"/ReservaNew"})
public class ReservaNew extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            
            ReservaDAO reservaDAO = new ReservaDAO();
            HorarioDAO horarioDAO = new HorarioDAO();
            
            List<Boolean> respuestas = new ArrayList();

            int canchaId = Integer.parseInt(request.getParameter("canchaId"));
            String canchaNombre = request.getParameter("canchaNombre");
            String fecha = request.getParameter("fecha");

            String cliente = request.getParameter("cliente");
            String dni = request.getParameter("dni");
            String telefono = request.getParameter("telefono");
            int horas = Integer.parseInt(request.getParameter("horas"));
            int idHoraInicio = Integer.parseInt(request.getParameter("idHoraInicio"));
            int idEstado = 1;
            
            
            for (int i = 0; i < horas; i++) {
                ReservaTO obj = new ReservaTO();

                obj.setIdCancha(canchaId);
                obj.setFecha(fecha);
                obj.setIdHorario(idHoraInicio+i);
                obj.setCliente(cliente);
                obj.setDni(dni);
                obj.setTelefono(telefono);
                obj.setIdEstado(idEstado);
                
                respuestas.add(reservaDAO.insert(obj));
                
            }
            
            for (Boolean respuesta : respuestas) {
                System.out.println("Insertó? "+respuesta);
            }

            String urlHorario = "/HorarioPick?canchaId="+canchaId+"&canchaNombre="+canchaNombre+"&fecha="+fecha;
            
            getServletConfig().getServletContext().getRequestDispatcher(urlHorario).forward(request, response);

        } catch (Exception e) {
            System.out.println("ERROR @ReservaForm: " + e);
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
