package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.classes.SelectTO;
import com.joa.dao.CanchaDAO;
import com.joa.dao.ReservaDAO;
import com.joa.dao.SelectDAO;
import com.joa.utils.DateUtils;
import com.joa.utils.StringUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaList", urlPatterns = {"/ReservaList"})
public class ReservaList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            CanchaDAO canchaDAO = new CanchaDAO();
            ReservaDAO reservaDAO = new ReservaDAO();
            
            String fecha = request.getParameter("fecha");
            if (fecha == null) {
                fecha = DateUtils.localDateToFullString(DateUtils.getToday());
            }
            request.setAttribute("fecha", fecha);
            
            //RESERVAS POR CANCHA
            List<CanchaTO> canchas = canchaDAO.list();
            request.setAttribute("canchas", canchas);
            
            String idsCanchas = "";
            
            for (CanchaTO cancha : canchas) {
                cancha.setReservas(reservaDAO.list2(cancha.getId(), fecha));                
                idsCanchas += cancha.getId() +",";
            }
            idsCanchas = StringUtils.deleteLastChar(idsCanchas);
            request.setAttribute("idsCanchas", idsCanchas);
            
            //SELECTS
            String estadosHidden = request.getParameter("estadosHidden");
            SelectDAO selectDAO = new SelectDAO();
            List<SelectTO> estados = selectDAO.list("reservasEstados");
            
            
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
            request.setAttribute("estados", estados);
            
            List<SelectTO> estadosModal = selectDAO.list("reservasEstados");
            request.setAttribute("estadosModal", estadosModal);
            request.setAttribute("estadosHidden", estadosHidden);
            request.setAttribute("estadosNew", estadosHidden);
            request.setAttribute("estadosExcel", estadosHidden);

            getServletConfig().getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
            
            
            
        } catch (Exception e) {
            System.out.println("ERROR @ReservaList: " + e);
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
