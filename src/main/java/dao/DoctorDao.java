package dao;

import java.sql.Connection;

public class DoctorDao {

    private Dao dao;
    private Connection connection;

    public DoctorDao(Dao dao){
        this.dao = dao;
        this.connection = dao.getConnection();
    }

}
