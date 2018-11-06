package dao;

import java.sql.Connection;

public class ClientDao {

    private Dao dao;
    private Connection connection;

    public ClientDao(Dao dao){
        this.dao = dao;
        this.connection = dao.getConnection();
    }


}
