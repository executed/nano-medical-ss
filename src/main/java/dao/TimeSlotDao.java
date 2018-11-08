package dao;

import java.sql.Connection;
//TODO: ADD ID TO TimeSlot CLASS
public class TimeSlotDao {

    private Dao dao;
    private Connection connection;

    public TimeSlotDao(Dao dao){
        this.dao = dao;
        this.connection = dao.getConnection();
    }


}
