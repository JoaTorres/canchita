/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joa.dao;

import com.joa.classes.Modulo;
import com.joa.classes.Permiso;
import com.joa.classes.UserTO;
import com.joa.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        UserTO userTO = null;

        try {

            rs = ps.executeQuery();
            if (rs.next()) {
                userTO = new UserTO();
                userTO.setUsuario(rs.getString("usuario"));
                userTO.setClave(rs.getString("clave"));
                userTO.setNombre(rs.getString("nombre"));

                //GET PERMISOS
                Connection cnPer = DatabaseConnection.connect();
                String qPer = " select p.username as usuario, "
                        + " p.listar, p.insertar, p.editar, p.eliminar, p.reportar, "
                        + " m.id as idModulo, m.descripcion as modulo "
                        + " from permiso p inner join modulo m "
                        + " on p.idModulo = m.id where username = ? ";
                PreparedStatement psPer = cnPer.prepareStatement(qPer);
                psPer.setString(1, userTO.getUsuario());

                ResultSet rsPer = psPer.executeQuery();
                List<Permiso> permisos = new ArrayList();
                while (rsPer.next()) {
                    Permiso permiso = new Permiso();
                    Modulo modulo = new Modulo();
                    modulo.setId(rsPer.getInt("idModulo"));
                    modulo.setDescripcion(rsPer.getString("modulo"));
                    permiso.setModulo(modulo);
                    permiso.setListar(rsPer.getBoolean("listar"));
                    permiso.setInsertar(rsPer.getBoolean("insertar"));
                    permiso.setEditar(rsPer.getBoolean("editar"));
                    permiso.setEliminar(rsPer.getBoolean("eliminar"));
                    permiso.setReportar(rsPer.getBoolean("reportar"));
                    
                    permisos.add(permiso);
                }
                
                userTO.setPermisos(permisos);
                
                DbUtils.closeQuietly(cnPer, psPer, rsPer);

            }
        } catch (Exception e) {
            System.out.println(" ERROR @UserDAO-login: " + e);
        } finally {
            closeConnections();
        }

        return userTO;
    }

    public void closeConnections() throws Exception {
        DbUtils.closeQuietly(rs);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(cn);
    }
}
