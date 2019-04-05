/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.controllers;

import com.joa.classes.CanchaTO;
import com.joa.classes.ReservaTO;
import com.joa.dao.CanchaDAO;
import com.joa.dao.ReservaDAO;
import com.joa.utils.PoiUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author developer
 */
@WebServlet(name = "ReservaExcel", urlPatterns = {"/ReservaExcel"})
public class ReservaExcel extends HttpServlet {

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

            String fecha = request.getParameter("fechaExcel");
            String estados = request.getParameter("estadosExcel");

            //RESERVAS POR CANCHA
            List<CanchaTO> canchas = canchaDAO.list();
            request.setAttribute("canchas", canchas);

            for (CanchaTO cancha : canchas) {
                cancha.setReservas(reservaDAO.listExcel(cancha.getId(), fecha, estados));
            }

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=ReservasReporte.xls");

            Workbook wb = new HSSFWorkbook();

            List<Sheet> sheets = new ArrayList();

            for (CanchaTO cancha : canchas) {
                sheets.add(wb.createSheet(cancha.getNombre()));
            }

            DataFormat format = wb.createDataFormat();

            //<editor-fold defaultstate="collapsed" desc=" E S T I L O S ">
            //<editor-fold defaultstate="collapsed" desc="COLORES PERSONALIZADOS">
            HSSFColor ROYAL_BLUE_MOD = PoiUtils.crearFuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.ROYAL_BLUE, 48, 84, 150);
            HSSFColor LAVENDER_MOD = PoiUtils.crearFuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.LAVENDER, 242, 242, 242);
            HSSFColor GOLD_MOD = PoiUtils.crearFuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.GOLD, 214, 220, 228);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="FONTS">
            Boolean negrita = true;
            Font headerFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.WHITE, 17, negrita);
            Font subHeaderFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.WHITE, 9, negrita);
            Font dataSimpleFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.AUTOMATIC, 9, negrita);
            Font greenFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.GREEN, 9, negrita);
            Font redFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.RED, 9, negrita);
            Font greyFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT, 9, negrita);
            Font yellowFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.ORANGE, 9, negrita);
            Font lightBlueFont = PoiUtils.fuente((HSSFWorkbook) wb, HSSFColor.HSSFColorPredefined.LIGHT_BLUE, 9, negrita);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="CELL STYLES">
            CellStyle headerStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, HSSFColor.HSSFColorPredefined.AUTOMATIC, headerFont);
            PoiUtils.addBorders(headerStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle subHeaderStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, HSSFColor.HSSFColorPredefined.AUTOMATIC, subHeaderFont);
            PoiUtils.addBorders(subHeaderStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataSimpleStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.LEFT, HSSFColor.HSSFColorPredefined.WHITE, dataSimpleFont);
            PoiUtils.addBorders(dataSimpleStyle, BorderStyle.THIN, IndexedColors.WHITE);
            dataSimpleStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());

            CellStyle basicStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, dataSimpleFont);
            PoiUtils.addBorders(basicStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle leftStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.LEFT, GOLD_MOD, dataSimpleFont);
            PoiUtils.addBorders(leftStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataStyle2 = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, dataSimpleFont);
            PoiUtils.addBorders(dataStyle2, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle greenStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, greenFont);
            PoiUtils.addBorders(greenStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle redStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, redFont);
            PoiUtils.addBorders(redStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle greyStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, greyFont);
            PoiUtils.addBorders(greyStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle yellowStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, yellowFont);
            PoiUtils.addBorders(yellowStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle lightBlueStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, GOLD_MOD, lightBlueFont);
            PoiUtils.addBorders(lightBlueStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataGreenStyleNeg = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT, greenFont);
            PoiUtils.addBorders(dataGreenStyleNeg, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataRedStyleNeg = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, HSSFColor.HSSFColorPredefined.GREY_25_PERCENT, redFont);
            PoiUtils.addBorders(dataRedStyleNeg, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataSimpleRedStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.CENTER, HSSFColor.HSSFColorPredefined.WHITE, redFont);
            PoiUtils.addBorders(dataSimpleRedStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataValRightStyle = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.RIGHT, LAVENDER_MOD, dataSimpleFont);
            dataValRightStyle.setDataFormat(format.getFormat("#,##0.00"));
            PoiUtils.addBorders(dataValRightStyle, BorderStyle.THIN, IndexedColors.WHITE);

            CellStyle dataValRightStyle2 = PoiUtils.createCellStyle((HSSFWorkbook) wb, HorizontalAlignment.RIGHT, GOLD_MOD, dataSimpleFont);
            dataValRightStyle2.setDataFormat(format.getFormat("#,##0.00"));
            PoiUtils.addBorders(dataValRightStyle2, BorderStyle.THIN, IndexedColors.WHITE);

            //</editor-fold>
            //</editor-fold>
            
            for (Sheet sheet : sheets) {
                int rowNum = 0;
                int colNum = 0;

                //<editor-fold defaultstate="collapsed" desc=" C A B E C E R A ">
                Row header = sheet.createRow(rowNum);
                header.setHeightInPoints((float) 45.00);

                Cell header0 = header.createCell(0);
                header0.setCellStyle(headerStyle);
                header0.setCellValue("R E P O R T E   R E S E R V A S");
                CellRangeAddress region = CellRangeAddress.valueOf("A1:H1");
                sheet.addMergedRegion(region);
                rowNum++;

                Row subheader1 = sheet.createRow(rowNum);

                Cell subHeaderCell3 = subheader1.createCell(colNum);
                subHeaderCell3.setCellStyle(subHeaderStyle);
                subHeaderCell3.setCellValue("ESTADO");
                colNum++;

                Cell subHeaderCell4 = subheader1.createCell(colNum);
                subHeaderCell4.setCellStyle(subHeaderStyle);
                subHeaderCell4.setCellValue("FECHA");
                colNum++;

                Cell subHeaderCell5 = subheader1.createCell(colNum);
                subHeaderCell5.setCellStyle(subHeaderStyle);
                subHeaderCell5.setCellValue("HORA I.");
                colNum++;

                Cell subHeaderCell6 = subheader1.createCell(colNum);
                subHeaderCell6.setCellStyle(subHeaderStyle);
                subHeaderCell6.setCellValue("HORA F.");
                colNum++;

                Cell subHeaderCell7 = subheader1.createCell(colNum);
                subHeaderCell7.setCellStyle(subHeaderStyle);
                subHeaderCell7.setCellValue("COSTO");
                colNum++;

                //VENTAS GRAV.
                Cell subHeaderCell8 = subheader1.createCell(colNum);
                subHeaderCell8.setCellStyle(subHeaderStyle);
                subHeaderCell8.setCellValue("CLIENTE");
                colNum++;

                Cell subHeaderCell9 = subheader1.createCell(colNum);
                subHeaderCell9.setCellStyle(subHeaderStyle);
                subHeaderCell9.setCellValue("DNI");
                colNum++;

                Cell subHeaderCell10 = subheader1.createCell(colNum);
                subHeaderCell10.setCellStyle(subHeaderStyle);
                subHeaderCell10.setCellValue("TELÉFONO");
                colNum++;

                //</editor-fold>
                rowNum++;

                for (CanchaTO cancha : canchas) {
                    if (cancha.getNombre().equals(sheet.getSheetName())) {

                        //<editor-fold defaultstate="collapsed" desc=" L L E N A D O   D E   L A   D A T A ">
                        int i = 1;
                        for (ReservaTO obj : cancha.getReservas()) {
                            Row data = sheet.createRow(rowNum);
                            colNum = 0;

                            Cell dataEstado = data.createCell(colNum);
                            dataEstado.setCellValue(obj.getEstado());
                            switch (obj.getIdEstado()) {
                                case 0:
                                    dataEstado.setCellStyle(greyStyle);
                                    break;
                                case 1:
                                    dataEstado.setCellStyle(lightBlueStyle);
                                    break;
                                case 2:
                                    dataEstado.setCellStyle(greenStyle);
                                    break;
                                
                            }
                            colNum++;

                            Cell dataFecha = data.createCell(colNum);
                            dataFecha.setCellValue(obj.getFecha());
                            dataFecha.setCellStyle(dataSimpleStyle);
                            colNum++;
                            
                            Cell dataHoraI= data.createCell(colNum);
                            dataHoraI.setCellValue(obj.getHoraInicio());
                            dataHoraI.setCellStyle(dataSimpleStyle);
                            colNum++;
                            
                            Cell dataHoraF = data.createCell(colNum);
                            dataHoraF.setCellValue(obj.getHoraFin());
                            dataHoraF.setCellStyle(dataSimpleStyle);
                            colNum++;
                            
                            Cell dataCosto = data.createCell(colNum);
                            dataCosto.setCellValue(obj.getTarifa());
                            dataCosto.setCellStyle(dataValRightStyle);
                            colNum++;
                            
                            Cell dataCliente = data.createCell(colNum);
                            dataCliente.setCellValue(obj.getCliente());
                            dataCliente.setCellStyle(dataSimpleStyle);
                            colNum++;
                            
                            Cell dataDni = data.createCell(colNum);
                            dataDni.setCellValue(obj.getDni());
                            dataDni.setCellStyle(dataSimpleStyle);
                            colNum++;
                            
                            Cell dataTelefono = data.createCell(colNum);
                            dataTelefono.setCellValue(obj.getTelefono());
                            dataTelefono.setCellStyle(dataSimpleStyle);
                            colNum++;
                            
                            rowNum++;
                            i++;
                        }
                        //</editor-fold>

                    }
                }

//                System.out.println("name: "+sheet.getSheetName());
                sheet.createFreezePane(0, 3);

                //<editor-fold defaultstate="collapsed" desc=" A N C H O   D E   C O L U M N A S ">
                for (int contCol = 0; contCol < colNum; contCol++) {
                    sheet.autoSizeColumn(contCol);
                }
                //</editor-fold>

            }

            wb.write(response.getOutputStream());

        } catch (Exception e) {
            System.out.println("ERROR @ReservaExcel: " + e);
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
