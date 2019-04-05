package com.joa.dao;


import com.joa.classes.SelectTO;
import com.joa.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;

public class SelectDAO {
    
    Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    public List<SelectTO> list(String tabla) throws Exception {
        String query = "SELECT * FROM "+tabla+"; ";

        cn = DatabaseConnection.connect();
        ps = cn.prepareStatement(query);

        List<SelectTO> list = new ArrayList<>();

        try {
            rs = ps.executeQuery();
            SelectTO obj;
            while (rs.next()) {
                obj = new SelectTO();
                obj.setId(rs.getInt("id"));
                obj.setDescripcion(rs.getString("descripcion"));

                list.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("ERROR @SelectDAO-list: " + e);
        } finally {
            DbUtils.closeQuietly(cn, ps, rs);
        }

        return list;
    }

    
}
