/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.dao;

import com.joa.classes.HorarioTO;
import com.joa.classes.ReservaTO;
import com.joa.db.DatabaseConnection;
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
public class HorarioDAO {

    Connection cn;
    PreparedStatement ps;
    ResultSet rs;

    //PARA LA PARTE ADMINISTRATIVA
    public List<HorarioTO> list() throws Exception {
        String query = "SELECT * FROM horarios ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);
        System.out.println("query: " + query);

        List<HorarioTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                HorarioTO obj = new HorarioTO();
                obj.setId(rs.getInt("id"));
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setTarifa(rs.getDouble("tarifa"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-List: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    //PARA MODAL PARTE ADMINISTRATIVA
    public HorarioTO getOne(int id) throws Exception {
        String query = "select * from horarios where id = " + id;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        HorarioTO obj = null;

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = new HorarioTO();
                obj.setId(rs.getInt("id"));
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setTarifa(rs.getDouble("tarifa"));

            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-getOne: " + e);
        } finally {
            closeConnections();
        }

        return obj;
    }

    //INSERT UPDATE PARA LA PARTE DE ADMINISTRACIÓN
    public int update(HorarioTO obj) throws Exception {
        int respuesta = 0;
        String query = "";
        boolean exist = exist(obj);

        if (exist) {
            query = "update horarios set "
                    + "horaInicio = \'" + obj.getHoraInicio() + "\', "
                    + "horaFin = \'" + obj.getHoraFin() + "\', "
                    + "tarifa = " + obj.getTarifa() + " "
                    + "where id = " + obj.getId();
            respuesta = 2;
        } else {
            query = "INSERT INTO horarios (horaInicio, horaFin, tarifa) "
                    + "VALUES ("
                    + " \'" + obj.getHoraInicio() + "\', "
                    + " \'" + obj.getHoraFin() + "\' "
                    + " " + obj.getTarifa() + " "
                    + ")";
            respuesta = 1;
        }

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        try {
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-insert: " + e);
            respuesta = 0;
        } finally {
            closeConnections();
        }

        return respuesta;
    }

    //PARA LA PARTE DEL CLIENTE
    public List<ReservaTO> list(int idCancha, String fecha) throws Exception {

        String query = "select * from horarios h "
                + "left join reservas r "
                + "on h.id = r.idHorario "
                + "and r.idCancha = " + idCancha + " "
                + "and r.fecha = \'" + fecha + "\' ";

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<ReservaTO> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                ReservaTO obj = new ReservaTO();
                obj.setId(rs.getInt("id"));
                obj.setHoraInicio(rs.getString("horaInicio"));
                obj.setHoraFin(rs.getString("horaFin"));
                obj.setCliente(rs.getString("cliente"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-List: " + e);
        } finally {
            closeConnections();
        }

        return list;
    }

    public int getHorasDisponibles(int idCancha, String fecha, int idHora) throws Exception {
        int horasDisponibles = 0;

        String query = "select h.id, h.horaInicio from reservas r "
                + "inner join horarios h on r.idHorario = h.id  "
                + "where idCancha = " + idCancha + " "
                + "and fecha = \'" + fecha + "\' ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<Integer> list = new ArrayList();

        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("id") - idHora);
            }

            boolean allNegative = true;
            for (Integer integer : list) {
                if (integer > 0) {
                    allNegative = false;
                }
            }

            int min = Integer.MAX_VALUE;

            if (allNegative) {
                horasDisponibles = 18 - idHora;
            } else {
                for (Integer integer : list) {
                    if (min > integer && integer > 0) {
                        min = integer;
                    }
                }
                horasDisponibles = min;
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-getHorasDisponibles: " + e);
        } finally {
            closeConnections();
        }

        return horasDisponibles;
    }

    public String getHoraInicio(int idHora) throws Exception {
        String hora = "00:00";

        String query = "SELECT horaInicio FROM horarios WHERE id = " + idHora;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                hora = rs.getString("horaInicio");
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-getHoraInicio: " + e);
        } finally {
            closeConnections();
        }

        return hora;
    }

    public String getHoraFin(int idHora) throws Exception {
        String hora = "00:00";

        String query = "SELECT horaFin FROM horarios WHERE id = " + idHora;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                hora = rs.getString("horaFin");
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-getHoraInicio: " + e);
        } finally {
            closeConnections();
        }

        return hora;
    }

    public double getTarifa(int id) throws Exception {
        double tarifa = 0.0;

        String query = "SELECT tarifa FROM horarios WHERE id = " + id;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                tarifa = rs.getDouble("tarifa");
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-getTarifa: " + e);
        } finally {
            closeConnections();
        }

        return tarifa;
    }

    public double getCosto(String ids) throws Exception {
        double costo = 0.0;

        String query = "SELECT SUM(tarifa) AS costo FROM horarios WHERE id IN ( " + ids + ") ";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);
        System.out.println("query get costo: "+query);

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                costo = rs.getDouble("costo");
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-getCosto: " + e);
        } finally {
            closeConnections();
        }

        return costo;
    }

    //MÉTODOS AUXILIARES
    public boolean exist(HorarioTO obj) throws Exception {
        String query = "select * from horarios where id = " + obj.getId();
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean exist = false;

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR HorarioDAO-exist: " + e);
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
