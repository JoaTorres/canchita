package com.joa.dao;

import com.joa.classes.ReservaTO;
import com.joa.db.DatabaseConnection;
import com.joa.utils.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

public class ReservaDAO {

    Connection cn;
    PreparedStatement ps;
    ResultSet rs;

    public boolean insert(ReservaTO obj) throws Exception {
        String query = "INSERT INTO reservas (idCancha, fecha, idHorario, cliente, dni, telefono, idEstado) "
                + "VALUES ("
                + " " + obj.getIdCancha() + ", "
                + " \'" + obj.getFecha() + "\', "
                + " " + obj.getIdHorario() + ", "
                + " \'" + obj.getCliente() + "\', "
                + " \'" + obj.getDni() + "\', "
                + " \'" + obj.getTelefono() + "\', "
                + " " + obj.getIdEstado() + " "
                + ")";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean inserted = false;

        try {
            ps.executeUpdate();
            inserted = true;

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-insert: " + e);
        } finally {
            closeConnections();
        }

        return inserted;
    }

    public int insertUpdate(ReservaTO obj) throws Exception {
        int respuesta = 0;
        String query = "";
        boolean exist = exist(obj);

        if (exist) {
            query = "update reservas set "
                    + "cliente = \'" + obj.getCliente() + "\', "
                    + "dni = \'" + obj.getDni() + "\', "
                    + "telefono = \'" + obj.getTelefono() + "\', "
                    + "idEstado = " + obj.getIdEstado() + " "
                    + "where idCancha = " + obj.getIdCancha() + " "
                    + "and fecha = \'" + obj.getFecha() + "\' "
                    + "and idHorario = " + obj.getIdHorario();
            respuesta = 2;
        } else {
            query = "INSERT INTO reservas (idCancha, fecha, idHorario, cliente, dni, telefono, idEstado) "
                    + "VALUES ("
                    + " " + obj.getIdCancha() + ", "
                    + " \'" + obj.getFecha() + "\', "
                    + " " + obj.getIdHorario() + ", "
                    + " \'" + obj.getCliente() + "\', "
                    + " \'" + obj.getDni() + "\', "
                    + " \'" + obj.getTelefono() + "\', "
                    + " " + obj.getIdEstado() + " "
                    + ")";
            respuesta = 1;
        }

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        try {
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-insert: " + e);
            respuesta = 0;
        } finally {
            closeConnections();
        }

        return respuesta;
    }

    public List<ReservaTO> list(int idCancha, String fecha) throws Exception {
        String query = "SELECT * FROM horarios h "
                + "left join reservas r on h.id = r.idHorario and r.idCancha = " + idCancha + " and r.fecha = \'" + fecha + "\' "
                + "left join reservasEstados re on r.idEstado = re.id ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        System.out.println("reservas query: " + query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setId(rs.getInt("id"));
                obj.setIdCancha(rs.getInt("idCancha") == 0 ? idCancha : rs.getInt("idCancha"));
                obj.setFecha(rs.getString("fecha") == null ? fecha : rs.getString("fecha"));
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setTarifa(rs.getString("tarifa"));
                obj.setCliente(rs.getString("cliente"));
                obj.setDni(rs.getString("dni"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setIdEstado(rs.getInt("idEstado"));
                obj.setEstado(rs.getString("descripcion"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-List: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    //CAMBIAR EL REPORTE DE EXCEL
    public List<ReservaTO> listExcel(int idCancha, String fecha, String estados) throws Exception {
        List<String> estadosAux = StringUtils.stringToList(estados, ",");

        String query = "SELECT * FROM horarios h "
                + "left join reservas r on h.id = r.idHorario and r.idCancha = " + idCancha + " and r.fecha = \'" + fecha + "\' "
                + "left join reservasEstados re on r.idEstado = re.id ";

        if (estadosAux.get(0).equals("0")) {
            query += "where ((idEstado in(" + estados + ")) or (idEstado is null))";
        } else {
            query += "where ((idEstado in(" + estados + "))";
        }

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        System.out.println("reservas query: " + query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setId(rs.getInt("id"));
                obj.setIdCancha(rs.getInt("idCancha") == 0 ? idCancha : rs.getInt("idCancha"));
                obj.setFecha(rs.getString("fecha") == null ? fecha : rs.getString("fecha"));
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setTarifa(rs.getString("cliente") == null ? "" : rs.getString("tarifa"));
                obj.setCliente(rs.getString("cliente"));
                obj.setDni(rs.getString("dni"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setIdEstado(rs.getString("cliente") == null ? 0 : rs.getInt("idEstado"));
                obj.setEstado(rs.getString("cliente") == null ? "Libre" : rs.getString("descripcion"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-List: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    public ReservaTO getOne(String fecha, int idCancha, int idHora) throws Exception {
        String query = "SELECT * FROM horarios h "
                + "LEFT JOIN reservas r "
                + "ON h.id = r.idHorario "
                + "WHERE r.idCancha = " + idCancha + " "
                + "AND r.fecha = \'" + fecha + "\' "
                + "AND h.id = " + idHora;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);
        System.out.println("query getOne: " + query);

        ReservaTO obj = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new ReservaTO();
                obj.setId(rs.getInt("id"));
                obj.setIdCancha(rs.getInt("idCancha"));
                obj.setFecha(rs.getString("fecha"));
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setTarifa(rs.getString("tarifa"));
                obj.setCliente(rs.getString("cliente"));
                obj.setDni(rs.getString("dni"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setIdEstado(rs.getInt("idEstado"));

            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-getOne: " + e);
        } finally {
            closeConnections();
        }

        return obj;
    }

    public boolean delete(String fecha, int idCancha, int idHora) throws Exception {
        String query = "delete from reservas  "
                + "where idCancha = " + idCancha + " "
                + "and fecha = \'" + fecha + "\' "
                + "and idHorario = " + idHora;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean elimino = false;

        try {
            ps.executeUpdate();
            elimino = true;

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-delete: " + e);
        } finally {
            closeConnections();
        }

        return elimino;
    }

    public boolean exist(ReservaTO obj) throws Exception {
        String query = "select * from reservas "
                + "where idCancha = " + obj.getIdCancha() + " "
                + "and fecha = \'" + obj.getFecha() + "\' "
                + "and idHorario = " + obj.getIdHorario();
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean exist = false;

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-insert: " + e);
        } finally {
            closeConnections();
        }

        return exist;
    }

    //VERSION 2.0
    public List<ReservaTO> listReservas(int idCancha, String fecha) throws Exception {
        String query = "SELECT horaInicio, horaFin FROM reservas2 where idCancha = " + idCancha + " and fecha = \'" + fecha + "\' ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setIdCancha(idCancha);
                obj.setFecha(fecha);
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-List: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    public boolean horaPermitida(int idCancha, String fecha, String horaInicio, String horaFin) throws Exception {
        boolean horaPermitida = true;

        LocalTime hI = LocalTime.parse(horaInicio);
        LocalTime hF = LocalTime.parse(horaFin);

        if (hF.isBefore(hI) || hF.isEqual(hI)) {
            horaPermitida = false;
        } else {
            String query = "SELECT horaInicio, horaFin FROM reservas2 where idCancha = " + idCancha + " and fecha = \'" + fecha + "\' ";
            cn = DatabaseConnection.connect();
            ps = cn.prepareStatement(query);

            List<ReservaTO> list = new ArrayList();

            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    ReservaTO obj = new ReservaTO();
                    obj.setIdCancha(idCancha);
                    obj.setFecha(fecha);
                    obj.setHoraInicio(rs.getString("horaInicio"));
                    obj.setHoraFin(rs.getString("horaFin"));

                    list.add(obj);
                }

            } catch (SQLException e) {
                System.out.println("ERROR ReservaDAO-List: " + e);
            } finally {
                closeConnections();
            }

            for (ReservaTO reservaTO : list) {
                DateTime reservaInicio = DateTime.parse(reservaTO.getFecha()+"T"+reservaTO.getHoraInicio());
                DateTime reservaFin = DateTime.parse(reservaTO.getFecha()+"T"+reservaTO.getHoraFin());
                Interval reservaIntervalo = new Interval(reservaInicio, reservaFin);
                
                
                DateTime porReservarInicio = DateTime.parse(reservaTO.getFecha()+"T"+hI);
                DateTime porReservarFin = DateTime.parse(reservaTO.getFecha()+"T"+hF);
                Interval porReservarIntervalo = new Interval(porReservarInicio, porReservarFin);
                
                boolean overlaps = reservaIntervalo.overlaps(porReservarIntervalo);
                
                if(overlaps){
                    horaPermitida = false;
                    break;
                }                               

            }
        }

        return horaPermitida;
    }

    public double calcularCosto(String horaInicio, String horaFin) throws Exception {
        double costo = 0.0;

        LocalTime hI = LocalTime.parse(horaInicio);
        LocalTime hF = LocalTime.parse(horaFin);

        String query = " SELECT horaInicio, horaFin, tarifa FROM tarifas2 ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setTarifa(rs.getString("tarifa"));
                obj.setPrecioPorMinuto(Double.parseDouble(rs.getString("tarifa")) / 60);

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-calcularCosto: " + e);
        } finally {
            closeConnections();
        }

        //Determinar si está en el intervalo de la mñn
        ReservaTO turnoDia = list.get(0);
        LocalTime inicioDia = LocalTime.parse(turnoDia.getHoraInicio());
        LocalTime finDia = LocalTime.parse(turnoDia.getHoraFin());

        ReservaTO turnoNoche = list.get(1);
        LocalTime inicioNoche = LocalTime.parse(turnoNoche.getHoraInicio());
        LocalTime finNoche = LocalTime.parse(turnoNoche.getHoraFin());

        //DÍA
        if (((hI.equals(inicioDia) || hI.isAfter(inicioDia)) && hI.isBefore(finDia))
                && (hF.isAfter(inicioDia) && (hF.isBefore(finDia) || hF.isEqual(finDia)))) {

            Minutes minutosTotales = Minutes.minutesBetween(hI, hF);
            costo = minutosTotales.getMinutes() * turnoDia.getPrecioPorMinuto();
        }

        //NOCHE
        if (((hI.equals(inicioNoche)) || (hI.isAfter(inicioNoche)) && hI.isBefore(finNoche))
                && (hF.isAfter(inicioNoche) && (hF.isBefore(finNoche) || hF.isEqual(finNoche)))) {
            
            Minutes minutosTotales = Minutes.minutesBetween(hI, hF);
            if(hF.isEqual(finNoche)){
                minutosTotales = minutosTotales.plus(Minutes.ONE);
            }
            costo = minutosTotales.getMinutes() * turnoNoche.getPrecioPorMinuto();
        }

        //MIXTO
        if (((hI.equals(inicioDia) || hI.isAfter(inicioDia)) && hI.isBefore(finDia))
                && (hF.isAfter(inicioNoche) && (hF.isBefore(finNoche) || hF.isEqual(finNoche)))) {
            Minutes minutosInicio = Minutes.minutesBetween(hI, finDia);
            Minutes minutosFin = Minutes.minutesBetween(inicioNoche, hF);

            if (hF.isEqual(finNoche)) {
                minutosFin = minutosFin.plus(Minutes.ONE);
            }

            double costoDia = minutosInicio.getMinutes() * turnoDia.getPrecioPorMinuto();
            double costoNoche = minutosFin.getMinutes() * turnoNoche.getPrecioPorMinuto();

            costo = costoDia + costoNoche;
        }

        return costo;
    }

    public boolean insert2(ReservaTO obj) throws Exception {
        String query = "INSERT INTO reservas2 (idCancha, fecha, horaInicio, horaFin, "
                + " costo, total, saldo, cliente, dni, telefono, idEstado) "
                + "VALUES ("
                + " " + obj.getIdCancha() + ", "
                + " \'" + obj.getFecha() + "\', "
                + " \'" + obj.getHoraInicio() + "\', "
                + " \'" + obj.getHoraFin() + "\', "
                + " " + obj.getCosto() + ", "
                + " " + obj.getCosto() + ", "
                + " " + obj.getCosto() + ", "
                + " \'" + obj.getCliente() + "\', "
                + " \'" + obj.getDni() + "\', "
                + " \'" + obj.getTelefono() + "\', "
                + " " + obj.getIdEstado() + " "
                + ")";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean inserted = false;

        try {
            ps.executeUpdate();
            inserted = true;

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-insert2: " + e);
        } finally {
            closeConnections();
        }

        return inserted;
    }

    public List<ReservaTO> list2(int idCancha, String fecha) throws Exception {
        String query = "select * from reservas2 r "
                + "left join reservasEstados re on r.idEstado = re.id "
                + "where r.idCancha = " + idCancha + " and r.fecha = \'" + fecha + "\'; "
                + "";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setIdCancha(idCancha);
                obj.setFecha(fecha);
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setCosto(rs.getDouble("costo"));
                obj.setDescuento(rs.getDouble("descuento"));
                obj.setTotal(rs.getDouble("total"));
                obj.setPagado(rs.getDouble("pagado"));
                obj.setSaldo(rs.getDouble("saldo"));
                obj.setCliente(rs.getString("cliente"));
                obj.setDni(rs.getString("dni"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setIdEstado(rs.getInt("idEstado"));
                obj.setEstado(rs.getString("descripcion"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-List2: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    //CAMBIAR EL REPORTE DE EXCEL
    public List<ReservaTO> listExcel2(int idCancha, String fecha, String estados) throws Exception {

        String query = " SELECT * FROM reservas2 r "
                + " LEFT JOIN reservasEstados re ON r.idEstado = re.id "
                + " WHERE r.idCancha = " + idCancha + " "
                + " AND r.fecha = \'" + fecha + "\' "
                + " AND r.idEstado IN (" + estados + ")";

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setIdCancha(idCancha);
                obj.setFecha(fecha);
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setCosto(rs.getDouble("costo"));
                obj.setDescuento(rs.getDouble("descuento"));
                obj.setTotal(rs.getDouble("total"));
                obj.setPagado(rs.getDouble("pagado"));
                obj.setSaldo(rs.getDouble("saldo"));
                obj.setCliente(rs.getString("cliente"));
                obj.setDni(rs.getString("dni"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setIdEstado(rs.getInt("idEstado"));
                obj.setEstado(rs.getString("descripcion"));
                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-listExcel2: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    public ReservaTO getOne2(String fecha, int idCancha, String horaInicio) throws Exception {
        String query = "SELECT * FROM reservas2 r "
                + "inner join canchas c on r.idCancha = c.id "
                + "WHERE idCancha = " + idCancha + " "
                + "AND fecha = \'" + fecha + "\' "
                + "AND horaInicio = \'" + horaInicio + "\'; ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        ReservaTO obj = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new ReservaTO();
                obj.setIdCancha(idCancha);
                obj.setCancha(rs.getString("nombre"));
                obj.setFecha(fecha);
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setCosto(rs.getDouble("costo"));
                obj.setDescuento(rs.getDouble("descuento"));
                obj.setTotal(rs.getDouble("total"));
                obj.setPagado(rs.getDouble("pagado"));
                obj.setSaldo(rs.getDouble("saldo"));
                obj.setCliente(rs.getString("cliente"));
                obj.setDni(rs.getString("dni"));
                obj.setTelefono(rs.getString("telefono"));
                obj.setIdEstado(rs.getInt("idEstado"));

            }

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-getOne: " + e);
        } finally {
            closeConnections();
        }

        return obj;
    }

    public int insertFormAdmin(ReservaTO obj) throws Exception {
        String query = "INSERT INTO reservas2 (idCancha, fecha, horaInicio, horaFin, "
                + " costo, descuento, total, pagado, saldo, cliente, dni, telefono, idEstado) "
                + "VALUES ( "
                + " " + obj.getIdCancha() + ", "
                + " \'" + obj.getFecha() + "\', "
                + " \'" + obj.getHoraInicio() + "\', "
                + " \'" + obj.getHoraFin() + "\', "
                + " " + obj.getCosto() + ", "
                + " " + obj.getDescuento() + ", "
                + " " + obj.getTotal() + ", "
                + " " + obj.getPagado() + ", "
                + " " + obj.getSaldo() + ", "
                + " \'" + obj.getCliente() + "\', "
                + " \'" + obj.getDni() + "\', "
                + " \'" + obj.getTelefono() + "\', "
                + " " + obj.getIdEstado() + " "
                + ")";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        int rpta = 0;

        try {
            ps.executeUpdate();
            rpta = 1;

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-insertFormAdmin: " + e);
        } finally {
            closeConnections();
        }

        return rpta;
    }

    public int updateFormAdmin(ReservaTO obj) throws Exception {
        String query = "UPDATE reservas2 set "
                + " horaFin = \'" + obj.getHoraFin() + "\', "
                + " costo = " + obj.getCosto() + ", "
                + " descuento = " + obj.getDescuento() + ", "
                + " total = " + obj.getTotal() + ", "
                + " pagado = " + obj.getPagado() + ", "
                + " saldo = " + obj.getSaldo() + ", "
                + " cliente = \'" + obj.getCliente() + "\', "
                + " dni = \'" + obj.getDni() + "\', "
                + " telefono = \'" + obj.getTelefono() + "\', "
                + " idEstado = " + obj.getIdEstado() + " "
                + " WHERE idCancha = " + obj.getIdCancha() + " "
                + " and fecha = \'" + obj.getFecha() + "\' "
                + " and horaInicio = \'" + obj.getHoraInicio() + "\'; ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        int rpta = 0;

        try {
            ps.executeUpdate();
            rpta = 2;

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-updateFormAdmin: " + e);
        } finally {
            closeConnections();
        }

        return rpta;
    }

    public boolean delete2(String fecha, int idCancha, String horaInicio) throws Exception {
        String query = "delete from reservas2  "
                + "where idCancha = " + idCancha + " "
                + "and fecha = \'" + fecha + "\' "
                + "and horaInicio = \'" + horaInicio + "\' ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean elimino = false;

        try {
            ps.executeUpdate();
            elimino = true;

        } catch (SQLException e) {
            System.out.println("ERROR ReservaDAO-delete2: " + e);
        } finally {
            closeConnections();
        }

        return elimino;
    }

    public void closeConnections() throws Exception {
        DbUtils.closeQuietly(rs);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(cn);
    }
}
