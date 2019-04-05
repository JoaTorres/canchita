/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.HorarioTO;
import com.joa.dao.HorarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "HorarioNew", urlPatterns = {"/HorarioNew"})
public class HorarioNew extends HttpServlet {

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
            
            int id = request.getParameter("id") == null ? 0 :  Integer.parseInt(request.getParameter("id"));
            String horaInicio = request.getParameter("horaInicio");
            String horaFin = request.getParameter("horaFin");
            double tarifa = request.getParameter("tarifa") == null ? 0.0 : Double.parseDouble(request.getParameter("tarifa"));
            
            HorarioTO objTO = new HorarioTO();
            objTO.setId(id);
            objTO.setHoraInicio(horaInicio);
            objTO.setHoraFin(horaFin);
            objTO.setTarifa(tarifa);
            
            int respuesta = objDAO.update(objTO);
            request.setAttribute("respuesta", respuesta);
            
            //RETORNAR LA LISTA
            List<HorarioTO> list = objDAO.list();
            request.setAttribute("list", list);
            
            
            getServletConfig().getServletContext().getRequestDispatcher("/horarioList.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("ERROR @HorarioNew: " + e);
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
