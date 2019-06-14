/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.classes.ReservaTO;
import com.joa.classes.SelectTO;
import com.joa.dao.CanchaDAO;
import com.joa.dao.ReservaDAO;
import com.joa.dao.SelectDAO;
import com.joa.utils.StringUtils;
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
@WebServlet(name = "ReservaEditAdmin", urlPatterns = {"/ReservaEditAdmin"})
public class ReservaEditAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {

            ReservaDAO reservaDAO = new ReservaDAO();
            CanchaDAO canchaDAO = new CanchaDAO();

            String cliente = request.getParameter("clienteEdit");
            String dni = request.getParameter("dniEdit");
            String telefono = request.getParameter("telefonoEdit");

            int idCancha = Integer.parseInt(request.getParameter("canchaEdit"));
            String fecha = request.getParameter("fechaEdit");
            String horaInicio = request.getParameter("horaInicioEdit");
            String horaFin = request.getParameter("horaFinEdit");

            double costo = Double.parseDouble(request.getParameter("costoEdit"));
            double descuento = Double.parseDouble(request.getParameter("descuentoEdit"));
            double total = Double.parseDouble(request.getParameter("totalEdit"));
            double pagado = Double.parseDouble(request.getParameter("pagadoEdit"));
            double saldo = Double.parseDouble(request.getParameter("saldoEdit"));

            int idEstado = Integer.parseInt(request.getParameter("estadoEdit"));

            ReservaTO obj = new ReservaTO();
            obj.setCliente(cliente);
            obj.setDni(dni);
            obj.setTelefono(telefono);

            obj.setIdCancha(idCancha);
            obj.setFecha(fecha);
            obj.setHoraInicio(horaInicio);
            obj.setHoraFin(horaFin);

            obj.setCosto(costo);
            obj.setDescuento(descuento);
            obj.setTotal(total);
            obj.setPagado(pagado);
            obj.setSaldo(saldo);

            obj.setIdEstado(idEstado);

            int respuesta = reservaDAO.updateFormAdmin(obj);
            request.setAttribute("respuesta", respuesta);

            //RETORNAR LA LISTA
            request.setAttribute("fecha", fecha);

            List<CanchaTO> canchas = canchaDAO.list();
            request.setAttribute("canchas", canchas);

            String idsCanchas = "";

            for (CanchaTO cancha : canchas) {
                cancha.setReservas(reservaDAO.list2(cancha.getId(), fecha));
                idsCanchas += cancha.getId() + ",";
            }
            idsCanchas = StringUtils.deleteLastChar(idsCanchas);
            request.setAttribute("idsCanchas", idsCanchas);

            //ESTADOS
            String estadosHidden = request.getParameter("estadosEdit");
            SelectDAO selectDAO = new SelectDAO();
            List<SelectTO> estados = selectDAO.list("reservasEstados");

            if (estadosHidden == null) {
                estadosHidden = "";
                for (SelectTO estado : estados) {
                    estadosHidden += estado.getId() + ",";
                }
                estadosHidden = StringUtils.deleteLastChar(estadosHidden);
            }

            String[] estadosArray = StringUtils.splitOnCharArray(estadosHidden, ",");
            for (SelectTO estado : estados) {
                for (String string : estadosArray) {
                    if (estado.getId() == Integer.parseInt(string)) {
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
            
            //FLAG LOGIN OR NEW/EDIT
            request.setAttribute("login", 0);

            getServletConfig().getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("ERROR @ReservaEditAdmin: " + e);
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
