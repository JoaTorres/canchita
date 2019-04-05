/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.dao;

import com.joa.classes.CanchaTO;
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
public class CanchaDAO {
    
    
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    
    
    public List<CanchaTO> list() throws Exception{
        String query = "SELECT * FROM canchas";
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);
        
        List<CanchaTO> list = new ArrayList();
        
        try {
            rs = ps.executeQuery();
            while(rs.next()){
                CanchaTO obj = new CanchaTO();
                obj.setId(rs.getInt("id"));
                obj.setNombre(rs.getString("nombre"));
                obj.setDescripcion(rs.getString("descripcion"));
            
                list.add(obj);
            }
            
        } catch (SQLException e) {
            System.out.println("ERROR CanchaDAO-List: " + e);
        } finally{
            closeConnections();
        }
        
        return list;
    }
    
    public CanchaTO getOne(int id) throws Exception{
        String query = "select * from canchas where id = "+id;
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);
        
        CanchaTO obj = null;
        
        try {
            rs = ps.executeQuery();
            if(rs.next()){
                obj = new CanchaTO();
                obj.setId(rs.getInt("id"));
                obj.setNombre(rs.getString("nombre"));
                obj.setDescripcion(rs.getString("descripcion"));
            
            }
            
        } catch (SQLException e) {
            System.out.println("ERROR CanchaDAO-getOne: " + e);
        } finally{
            closeConnections();
        }
        
        return obj;
    }
    
    public int insertUpdate(CanchaTO obj) throws Exception {
        int respuesta = 0;
        String query = "";
        boolean exist = exist(obj);

        if (exist) {
            query = "update canchas set "
                    + "nombre = \'" + obj.getNombre() + "\', "
                    + "descripcion = \'" + obj.getDescripcion()+ "\' "
                    + "where id = " + obj.getId() + " ";
            respuesta = 2;
        } else {
            query = "INSERT INTO canchas (nombre, descripcion) "
                    + "VALUES ("
                    + " \'" + obj.getNombre() + "\', "
                    + " \'" + obj.getDescripcion()+ "\' "
                    + ")";
            respuesta = 1;
        }

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        try {
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR CanchaDAO-insert: " + e);
            respuesta = 0;
        } finally {
            closeConnections();
        }

        return respuesta;
    }
    
    
    public boolean exist(CanchaTO obj) throws Exception {
        String query = "select * from canchas where id = " + obj.getId();
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        boolean exist = false;

        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR CanchaDAO-exist: " + e);
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
