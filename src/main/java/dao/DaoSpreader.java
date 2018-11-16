package dao;

import manager.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoSpreader {

    private static Connection connection;

    public DaoSpreader(DataSourceManager manager){
        connection = (Connection) manager.getConnection();
    }

    public Connection getConnection(){
        return connection;
    }

    public void closePreparedStatements(PreparedStatement ... statements) throws SQLException {
        for (PreparedStatement statement: statements){
            if (statement != null) statement.close();
        }
    }
}
