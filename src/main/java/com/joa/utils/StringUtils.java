package com.joa.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static String deleteLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    public static char getLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.charAt(s.length() - 1));
    }

    public static String deleteNLastChar(String s, int n) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - n));
    }

    public static String deleteNBegiChar(String s, int n) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(n, s.length()));
    }

    public static ArrayList<String> splitOnChar(String s, String character) {
        ArrayList<String> list = new ArrayList<>();
        if (s == null) {
            return list;
        } else {
            String[] parts = s.split(character);

            for (int i = 0; i < parts.length; i++) {
                list.add(parts[i]);
            }
            return list;
        }
    }
    
    public static List<String> stringToList(String s, String character) {
        List<String> list = new ArrayList<>();
        if (s == null) {
            return list;
        } else {
            String[] parts = s.split(character);

            for (int i = 0; i < parts.length; i++) {
                list.add(parts[i]);
            }
            return list;
        }
    }

    public static String[] splitOnCharArray(String s, String character) {
        return s.split(character);
    }

    public static String mergeArrayOnString(ArrayList<String> array) {
        String str = "";

        for (String string : array) {
            str += string + ";";
        }

        return deleteLastChar(str);

    }

    //MERGE LIKE DATE
    public static String mergeArrayList(ArrayList<String> array, String s) {
        String str = "";
        for (String string : array) {
            str += string + s;
        }
        return deleteLastChar(str);
    }

    public static String mergeArrayOnString(String[] list) {
        String str = "";

        for (String string : list) {
            str += string + ",";
        }

        return deleteLastChar(str);
    }

    public static String ArrayToString(ArrayList<String> array, String s) {
        String str = "";
        for (String string : array) {
            str += "'" + string + "'" + s;
        }
        return deleteLastChar(str);
    }

    public static String removeChars(String s, String character) {
        return s.replaceAll(character, "");
    }

    public static String replaceChar(String s, String oldChar, String newChar) {
        return s.replace(oldChar, newChar);
    }

    public static String removeLeadingZeroes(String s) {
        return s.replaceFirst("^0+(?!$)", "");
    }

    public static String emptyToString(String s) {
        return s.equals("") ? "0" : s;
    }

    public static String spaceString(String s) {
        return s.replaceAll(".(?!$)", "$0 ");
    }

    public static String completeStringWith(String s, char c, int l) {

        while (s.length() < l) {
            s = c + s;
        }
        return s;
    }

    public static String getNameStMonth(String s) {
        String month = "";

        switch (s) {
            case "01":
                month = "Ene";
                break;
            case "02":
                month = "Feb";
                break;
            case "03":
                month = "Mar";
                break;
            case "04":
                month = "Abr";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "Jun";
                break;
            case "07":
                month = "Jul";
                break;
            case "08":
                month = "Ago";
                break;
            case "09":
                month = "Set";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
            case "12":
                month = "Dic";
                break;
        }

        return month;
    }

    public static String getMonthName(String s) {
        String month = "";

        switch (s) {
            case "01":
                month = "enero";
                break;
            case "02":
                month = "febrero";
                break;
            case "03":
                month = "marzo";
                break;
            case "04":
                month = "abril";
                break;
            case "05":
                month = "mayo";
                break;
            case "06":
                month = "junio";
                break;
            case "07":
                month = "julio";
                break;
            case "08":
                month = "agosto";
                break;
            case "09":
                month = "setiembre";
                break;
            case "10":
                month = "octubre";
                break;
            case "11":
                month = "noviembre";
                break;
            case "12":
                month = "diciembre";
                break;
        }

        return month;
    }

}
