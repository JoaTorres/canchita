package com.joa.utils;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class PoiUtils {
    
    public static HSSFColor crearFuente(HSSFWorkbook wb, HSSFColor.HSSFColorPredefined colorPredefined, int A, int B, int C) {
        HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
        HSSFColor newColor = null;
        try {
            palette.setColorAtIndex(colorPredefined.getIndex(), (byte) A, (byte) B, (byte) C);
            newColor = palette.getColor(colorPredefined.getIndex());
        } catch (Exception e) {
            System.out.println("ERROR DEL COLOR: " + e);
        }
        return newColor;
    }

    public static Font fuente(HSSFWorkbook wb, HSSFColor color, int tamano) {
        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) tamano);
        font.setColor(color.getIndex());
        return font;
    }

    public static Font fuente(HSSFWorkbook wb, HSSFColor color, int tamano, Boolean neg) {
        Font font = wb.createFont();
        font.setBold(neg);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) tamano);
        font.setColor(color.getIndex());
        return font;
    }

    public static Font fuente(HSSFWorkbook wb, HSSFColor.HSSFColorPredefined color, int tamano) {
        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) tamano);
        font.setColor(color.getIndex());
        return font;
    }

    public static Font fuente(HSSFWorkbook wb, HSSFColor.HSSFColorPredefined color, int tamano, Boolean neg) {
        Font font = wb.createFont();
        font.setBold(neg);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) tamano);
        font.setColor(color.getIndex());
        return font;
    }
    
    public static Font fuente(Workbook wb, IndexedColors color, int tamano) {
        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) tamano);
        font.setColor(color.getIndex());
        return font;
    }
    
    public static Font fuente(Workbook wb, IndexedColors color, int tamano, Boolean neg) {
        Font font = wb.createFont();
        font.setBold(neg);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) tamano);
        font.setColor(color.getIndex());
        return font;
    }

    public static CellStyle createCellStyle(HSSFWorkbook wb, HorizontalAlignment alineacHorizontal, HSSFColor colorFondo, Font fuente) {
        CellStyle estiloCelda = wb.createCellStyle();
        estiloCelda.setAlignment(alineacHorizontal);
        estiloCelda.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloCelda.setFillForegroundColor(colorFondo.getIndex());
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCelda.setFont(fuente);

        return estiloCelda;
    }

    public static CellStyle createCellStyle(HSSFWorkbook wb, HorizontalAlignment alineacHorizontal, HSSFColor.HSSFColorPredefined colorFondo, Font fuente) {
        CellStyle estiloCelda = wb.createCellStyle();
        estiloCelda.setAlignment(alineacHorizontal);
        estiloCelda.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloCelda.setFillForegroundColor(colorFondo.getIndex());
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCelda.setFont(fuente);

        return estiloCelda;
    }
    
    public static CellStyle createCellStyle(HSSFWorkbook wb, HorizontalAlignment alineacHorizontal, IndexedColors colorFondo, Font fuente) {
        CellStyle estiloCelda = wb.createCellStyle();
        estiloCelda.setAlignment(alineacHorizontal);
        estiloCelda.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloCelda.setFillForegroundColor(colorFondo.getIndex());
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCelda.setFont(fuente);

        return estiloCelda;
    }

    public static CellStyle createCellStyle(HSSFWorkbook wb, HorizontalAlignment alineacHorizontal, Font fuente) {
        CellStyle estiloCelda = wb.createCellStyle();
        estiloCelda.setAlignment(alineacHorizontal);
        estiloCelda.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloCelda.setFont(fuente);

        return estiloCelda;
    }

    public static void addBorders(CellStyle CellStyle, BorderStyle borderStyle, IndexedColors colorBorde) {
        CellStyle.setBorderBottom(borderStyle);
        CellStyle.setBottomBorderColor(colorBorde.getIndex());
        CellStyle.setBorderTop(borderStyle);
        CellStyle.setTopBorderColor(colorBorde.getIndex());
        CellStyle.setBorderRight(borderStyle);
        CellStyle.setRightBorderColor(colorBorde.getIndex());
        CellStyle.setBorderLeft(borderStyle);
        CellStyle.setLeftBorderColor(colorBorde.getIndex());
    }

    public static void addBorders(CellRangeAddress region, BorderStyle borderStyle, IndexedColors color, Sheet sheet) {
        RegionUtil.setBorderTop(borderStyle, region, sheet);
        RegionUtil.setTopBorderColor(color.getIndex(), region, sheet);
        RegionUtil.setBorderBottom(borderStyle, region, sheet);
        RegionUtil.setBottomBorderColor(color.getIndex(), region, sheet);
        RegionUtil.setBorderLeft(borderStyle, region, sheet);
        RegionUtil.setLeftBorderColor(color.getIndex(), region, sheet);
        RegionUtil.setBorderRight(borderStyle, region, sheet);
        RegionUtil.setRightBorderColor(color.getIndex(), region, sheet);
    }

    public static void addBorders(CellRangeAddress region, BorderStyle borderStyle, HSSFColor color, Sheet sheet) {
        RegionUtil.setBorderTop(borderStyle, region, sheet);
        RegionUtil.setTopBorderColor(color.getIndex(), region, sheet);
        RegionUtil.setBorderBottom(borderStyle, region, sheet);
        RegionUtil.setBottomBorderColor(color.getIndex(), region, sheet);
        RegionUtil.setBorderLeft(borderStyle, region, sheet);
        RegionUtil.setLeftBorderColor(color.getIndex(), region, sheet);
        RegionUtil.setBorderRight(borderStyle, region, sheet);
        RegionUtil.setRightBorderColor(color.getIndex(), region, sheet);
    }

    public static void setColWidth(Sheet sheet, int colNum, double width){
        sheet.setColumnWidth(colNum, (int) (width * 256) + 200);
    }
    
    
}
