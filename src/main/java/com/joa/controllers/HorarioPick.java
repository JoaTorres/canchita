/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.ReservaTO;
import com.joa.dao.HorarioDAO;
import com.joa.utils.DateUtils;
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
@WebServlet(name = "HorarioPick", urlPatterns = {"/HorarioPick"})
public class HorarioPick extends HttpServlet {

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
           
            
            int canchaId = Integer.parseInt(request.getParameter("canchaId"));
            request.setAttribute("canchaId", canchaId);
            
            String canchaNombre = request.getParameter("canchaNombre");
            request.setAttribute("canchaNombre", canchaNombre);
            
            String fecha = request.getParameter("fecha");
            request.setAttribute("fecha", fecha);
            
            List<String> dias = DateUtils.addXdays(fecha, 2);
            request.setAttribute("dias", dias);
            
            String dia1 = dias.get(0);
            request.setAttribute("dia1", dia1);
            String dia2 = dias.get(1);
            request.setAttribute("dia2", dia2);
            String dia3 = dias.get(2);
            request.setAttribute("dia3", dia3);
            
            List<String> nombresDias = new ArrayList();
            for (String dia : dias) {
                nombresDias.add(DateUtils.getDayStringOfTheWeek(DateUtils.stringToLocalDate(dia)));
            }
            request.setAttribute("nombresDias", nombresDias);
            
            String diaNombre1 = nombresDias.get(0);
            request.setAttribute("diaNombre1", diaNombre1);
            String diaNombre2 = nombresDias.get(1);
            request.setAttribute("diaNombre2", diaNombre2);
            String diaNombre3 = nombresDias.get(2);
            request.setAttribute("diaNombre3", diaNombre3);
                    
                    
            
            
            HorarioDAO objDAO = new HorarioDAO();
            List<ReservaTO> reservas1 = objDAO.list(canchaId, dias.get(0));
            request.setAttribute("reservas1", reservas1);
            List<ReservaTO> reservas2 = objDAO.list(canchaId, dias.get(1));
            request.setAttribute("reservas2", reservas2);
            List<ReservaTO> reservas3 = objDAO.list(canchaId, dias.get(2));
            request.setAttribute("reservas3", reservas3);
            
            
            
            
            
            
            getServletConfig().getServletContext().getRequestDispatcher("/horarioPick.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("ERROR @HorarioPick: " + e);
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
