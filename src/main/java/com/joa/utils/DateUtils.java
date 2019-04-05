package com.joa.utils;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class DateUtils {

    private static LocalDate today = LocalDate.now();

    public static LocalDate getToday() {
        return today;
    }
    
    public static List<String> addXdays(String someday, int nroDays){
        List<String> listDays = new ArrayList();
        listDays.add(someday);
        
        for (int i = 1; i <= nroDays; i++) {
            listDays.add(addDays(someday, i));
        }
        return listDays;
    }
    

    public static ArrayList<String> getTodayArray() {
        return localDateToString(today);
    }

    public static long getDifferenceInDays(String someday) {
        LocalDate somelocalday = LocalDate.parse(someday);
        long difference = Days.daysBetween(somelocalday, today).getDays();
        return difference;
    }

    public static long getDifference(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);
        long difference = Days.daysBetween(startLocalDate, endLocalDate).getDays();
        return difference;
    }

    //DIFERENCIA DE DÍAS PARA EL MÓDULO DE CONTROL DE DEUDA
    public static int getDifferenceInDays(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);
        int difference = Days.daysBetween(endLocalDate, startLocalDate).getDays();
        return difference;
    }
    
    public static boolean isSameDate(String someday) {
        LocalDate somelocalday = LocalDate.parse(someday);
        boolean is = false;
        if (somelocalday.isEqual(today)) {
            is = true;
        }
        return is;
    }

    public static int whichDateIsGreater(String someday) {
        LocalDate somelocalday = LocalDate.parse(someday);
        int rpta;
        if (somelocalday.isEqual(today)) {
            rpta = 0;
        } else if (somelocalday.isBefore(today)) {
            rpta = -1;
        } else {
            rpta = 1;
        }

        return rpta;
    }

    public static String todayInRange(String somedayI, String somedayF, String hora) {
        Interval interval = null;

        if (hora.equals("")) {
            interval = new Interval(new DateTime(somedayI).getMillis(), new DateTime(somedayF).plusDays(1).getMillis());
        } else {
            interval = new Interval(new DateTime(somedayI).getMillis(), DateTime.parse(somedayF + " " + hora, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")).getMillis());
        }

        if (interval.contains(new DateTime())) {
            return "1";
        } else {
            return "0";
        }

    }

    public static DateTime stringToDateTime(String someday) {
        return new DateTime("someday");
    }

    public static boolean isSameMonth(String someday) {
        LocalDate somelocalday = LocalDate.parse(someday);
        int year = somelocalday.getYear();
        int month = somelocalday.getMonthOfYear();

        int yearToday = today.getYear();
        int monthToday = today.getMonthOfYear();

        boolean is = false;

        if (yearToday == year) {
            if (monthToday == month) {
                is = true;
                return is;
            } else {
                return is;
            }
        } else {
            return is;
        }
    }

    public static ArrayList<String> reduceOneMonth(String someday) {
        LocalDate someLocalDate = stringToLocalDate(someday);
        return localDateToString(someLocalDate.minusMonths(1));
    }

    public static ArrayList<String> reduceMonths(String someday, int nroMonths) {
        LocalDate someLocalDate = stringToLocalDate(someday);
        return localDateToString(someLocalDate.minusMonths(nroMonths));
    }

    //ADD DAYS
    public static String addOneDay(String someday) {
        LocalDate someLocalDate = stringToLocalDate(someday);
        return localDateToFullString(someLocalDate.plusDays(1));
    }

    public static String addDays(String someday, int nroDays) {
        LocalDate someLocalDate = stringToLocalDate(someday);
        return localDateToFullString(someLocalDate.plusDays(nroDays));
    }

    public static LocalDate stringToLocalDate(String someday) {
        LocalDate someLocalDate = LocalDate.parse(someday);
        return someLocalDate;
    }

    public static ArrayList<String> localDateToString(LocalDate someLocalDate) {
        String someStringDate = someLocalDate.toString("yyyy-MM-dd");
        ArrayList<String> parts = StringUtils.splitOnChar(someStringDate, "-");

        return parts;
    }

    public static String localDateToFullString(LocalDate someLocalDate) {
        String someStringDate = someLocalDate.toString("yyyy-MM-dd");
        return someStringDate;
    }

    public static String getDayStringOfTheWeek(LocalDate someLocalDate) {
        String dayOfWeek = "";
        switch (someLocalDate.getDayOfWeek()) {
            
            case 1:
                dayOfWeek = "Lunes";
                break;
            case 2:
                dayOfWeek = "Martes";
                break;
            case 3:
                dayOfWeek = "Miércoles";
                break;
            case 4:
                dayOfWeek = "Jueves";
                break;
            case 5:
                dayOfWeek = "Viernes";
                break;
            case 6:
                dayOfWeek = "Sábado";
                break;
            case 7:
                dayOfWeek = "Domingo";
                break;

        }
        return dayOfWeek;

    }

    public static String getMonthName(String nroMes) {
        String mes = "";
        switch (nroMes) {
            case "01":
                mes = "ENERO";
                break;
            case "02":
                mes = "FEBRERO";
                break;
            case "03":
                mes = "MARZO";
                break;
            case "04":
                mes = "ABRIL";
                break;
            case "05":
                mes = "MAYO";
                break;
            case "06":
                mes = "JUNIO";
                break;
            case "07":
                mes = "JULIO";
                break;
            case "08":
                mes = "AGOSTO";
                break;
            case "09":
                mes = "SETIEMBRE";
                break;
            case "10":
                mes = "OCTUBRE";
                break;
            case "11":
                mes = "NOVIEMBRE";
                break;
            case "12":
                mes = "DICIEMBRE";
                break;
        }
        return mes;
    }

}
