/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author developer
 */
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
    
    public List<ReservaTO> listExcel(int idCancha, String fecha, String estados) throws Exception {
        List<String> estadosAux = StringUtils.stringToList(estados, ",");
        
        
        String query = "SELECT * FROM horarios h "
                + "left join reservas r on h.id = r.idHorario and r.idCancha = " + idCancha + " and r.fecha = \'" + fecha + "\' "
                + "left join reservasEstados re on r.idEstado = re.id ";
        
        if(estadosAux.get(0).equals("0")){
            query += "where ((idEstado in("+estados+")) or (idEstado is null))";
        }else{
            query += "where ((idEstado in("+estados+"))";
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
        System.out.println("query getOne: "+query);

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

    public void closeConnections() throws Exception {
        DbUtils.closeQuietly(rs);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(cn);
    }
}
