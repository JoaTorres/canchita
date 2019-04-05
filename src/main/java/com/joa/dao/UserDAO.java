/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.dao;

import com.joa.classes.UserTO;
import com.joa.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author developer
 */
public class UserDAO {

    Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public UserTO login(String username, String password) throws Exception {
        
        String query = "SELECT usuario, clave, nombre "
                     + "FROM users WHERE usuario =\'" + username + "\' "
                     + "AND clave=\'" + password + "\'; ";
        Connection con = DatabaseConnection.connect();
        PreparedStatement ps = con.prepareStatement(query);
        
        UserTO userTO = null;

        try {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userTO = new UserTO();
                userTO.setUsuario(rs.getString("usuario"));
                userTO.setClave(rs.getString("clave"));
                userTO.setNombre(rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println(" ERROR @UserDAO-login: " + e);
        }

        return userTO;
    }
    
    
     public void closeConnections() throws Exception {
        DbUtils.closeQuietly(rs);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(cn);
    }
}
