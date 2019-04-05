/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.dao.HorarioDAO;
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
@WebServlet(name = "ReservaForm", urlPatterns = {"/ReservaForm"})
public class ReservaForm extends HttpServlet {

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
            HorarioDAO objDAO = new HorarioDAO();
            
            
            int canchaId = Integer.parseInt(request.getParameter("canchaId"));
            request.setAttribute("canchaId", canchaId);
            
            String canchaNombre = request.getParameter("canchaNombre");
            request.setAttribute("canchaNombre", canchaNombre);
            
            String fecha = request.getParameter("fecha");
            request.setAttribute("fecha", fecha);
            
            int idHoraInicio = Integer.parseInt(request.getParameter("idHoraInicio"));
            request.setAttribute("idHoraInicio", idHoraInicio);
            
            double tarifa = objDAO.getTarifa(idHoraInicio);
            request.setAttribute("tarifa", tarifa);
            
            String horaInicio = objDAO.getHoraInicio(idHoraInicio);
            request.setAttribute("horaInicio", horaInicio);
            
            
            //OBTENER LAS HORAS DISPONIBLES PARA LA RESERVA
            List<Integer> horasDisponibles = new ArrayList();
            
            int cantidadDeHorasDisponibles = objDAO.getHorasDisponibles(canchaId, fecha, idHoraInicio);
            for (int i = 1; i <= cantidadDeHorasDisponibles; i++) {
                horasDisponibles.add(i);
            }
            request.setAttribute("horasDisponibles", horasDisponibles);
            
            //ALTERNATIVA PARA MOSTAR NO SOLO LA CANTIDAD SINO TAMBIÉN LA HORA
//            List<HorarioTO> horasDisponibles = new ArrayList();
//            
//            int cantidadDeHorasDisponibles = objDAO.getHorasDisponibles(canchaId, fecha, idHoraInicio);
//            for (int i = 1; i <= cantidadDeHorasDisponibles; i++) {
//                horasDisponibles.add(i);
//            }
//            request.setAttribute("horasDisponibles", horasDisponibles);
            
            
                               

            getServletConfig().getServletContext().getRequestDispatcher("/reservaForm.jsp").forward(request, response);

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
