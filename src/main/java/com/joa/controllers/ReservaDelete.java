/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.classes.SelectTO;
import com.joa.dao.CanchaDAO;
import com.joa.dao.ReservaDAO;
import com.joa.dao.SelectDAO;
import com.joa.utils.StringUtils;
import java.io.IOException;
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
@WebServlet(name = "ReservaDelete", urlPatterns = {"/ReservaDelete"})
public class ReservaDelete extends HttpServlet {

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
            CanchaDAO canchaDAO = new CanchaDAO();

            String fecha = request.getParameter("idFechaDelete");
            int idCancha = Integer.parseInt(request.getParameter("idCanchaDelete"));
            int idHora = Integer.parseInt(request.getParameter("idHoraDelete"));

            boolean elimino = reservaDAO.delete(fecha, idCancha, idHora);
            request.setAttribute("elimino", elimino);

            //RETORNAR LA LISTA
            request.setAttribute("fecha", fecha);
            
            List<CanchaTO> canchas = canchaDAO.list();
            request.setAttribute("canchas", canchas);
            
            String idsCanchas = "";
            
            for (CanchaTO cancha : canchas) {
                cancha.setReservas(reservaDAO.list(cancha.getId(), fecha));                
                idsCanchas += cancha.getId() +",";
            }
            idsCanchas = StringUtils.deleteLastChar(idsCanchas);
            request.setAttribute("idsCanchas", idsCanchas);
            
            //ESTADOS
            String estadosHidden = request.getParameter("estadosDelete");
            SelectDAO selectDAO = new SelectDAO();
            List<SelectTO> estados = selectDAO.list("reservasEstados");
            
            SelectTO selectLibre = new SelectTO();
            selectLibre.setId(0);
            selectLibre.setDescripcion("Libre");
            estados.add(0, selectLibre);
            
            if(estadosHidden == null){
                estadosHidden = "";
                for (SelectTO estado : estados) {
                    estadosHidden += estado.getId()+",";
                }
                estadosHidden = StringUtils.deleteLastChar(estadosHidden);
            }
            
            String[] estadosArray = StringUtils.splitOnCharArray(estadosHidden, ",");
            for (SelectTO estado : estados) {
                for (String string : estadosArray) {
                    if(estado.getId() == Integer.parseInt(string)){
                        estado.setMarcado(1);
                    }
                }
            }
            
             //MODAL
            List<SelectTO> estadosModal = selectDAO.list("reservasEstados");
            
            //SET TO REQUEST
            request.setAttribute("estados", estados);
            request.setAttribute("estadosModal", estadosModal);
            request.setAttribute("estadosHidden", estadosHidden);
            request.setAttribute("estadosNew", estadosHidden);
            request.setAttribute("estadosExcel", estadosHidden);
            
            
            getServletConfig().getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("ERROR @ReservaDelete: " + e);
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
