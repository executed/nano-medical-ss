package dao;

import java.sql.Connection;

public class TimeSlotDao {

    private Dao dao;
    private Connection connection;

    public TimeSlotDao(Dao dao){
        this.dao = dao;
        this.connection = dao.getConnection();
    }


}
