package dao;

import manager.DataSourceManager;

import java.sql.Connection;

public class Dao {

    private static Connection connection;

    public Dao(DataSourceManager manager){
        connection = (Connection) manager.getConnection();
    }

    public Connection getConnection(){
        return connection;
    }
}
