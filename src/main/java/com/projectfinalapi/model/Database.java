package com.projectfinalapi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class Database {
    @Value("${dbUrl}")
    private String dbUrl;

    public Connection connectDatase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl);        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet getResultSet(Connection conn, String sql) {  //for select
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean executeQuery(Connection conn, String sql) //for insert update del
    {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void closeConnect(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            if (stmt != null) {
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}

