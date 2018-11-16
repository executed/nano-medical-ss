package dao;

import entity.TimeSlot;
import entity.TimeSlot.TimeSlotBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.TreeSet;
import java.util.UUID;

import static utility.ClassNameUtil.getClassName;
import static utility.SqlQueryUtil.getQuery;

public class TimeSlotDao implements IDao{

    private DaoSpreader daoSpreader;
    private Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(getClassName());

    public TimeSlotDao(DaoSpreader daoSpreader){
        this.daoSpreader = daoSpreader;
        this.connection = daoSpreader.getConnection();
    }

    private boolean alreadyExists(TimeSlot timeSlot){
        boolean status = (getAll().stream().anyMatch(x -> x.getDoctorId().equals(timeSlot.getDoctorId()) &&
                x.getStartTime().equals(timeSlot.getStartTime()) &&
                x.getEndTime().equals(timeSlot.getEndTime())));
        if (status) LOGGER.debug("TimeSlot {} already exist in database", timeSlot);
        return status;
    }

    public void save(TimeSlot timeSlot){
        if (timeSlot.getId() != null) { update(timeSlot); return; }
        LOGGER.trace("Started saving TimeSlot {} to database", timeSlot);
        try {
            if (alreadyExists(timeSlot)) return;
            PreparedStatement statement = connection.prepareStatement(getQuery("timeslot.insert"));
            statement.setObject(1, timeSlot.getClientId());
            statement.setObject(2, timeSlot.getDoctorId());
            statement.setObject(3, new Timestamp(timeSlot.getStartTime().getMillis()));
            statement.setObject(4, new Timestamp(timeSlot.getEndTime().getMillis()));
            statement.execute();
            LOGGER.trace("TimeSlot {} was saved to database successfully", timeSlot);
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while saving TimeSlot {} to database", timeSlot, e);
        }
    }

    public void update(TimeSlot timeSlot){
        if (timeSlot.getId() == null) { save(timeSlot); }
        LOGGER.trace("Started updating TimeSlot with id {} in database", timeSlot.getId());
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("timeslot.update"));
            statement.setObject(1, timeSlot.getClientId());
            statement.setObject(2, timeSlot.getDoctorId());
            statement.setObject(3, new Timestamp(timeSlot.getStartTime().getMillis()));
            statement.setObject(4, new Timestamp(timeSlot.getEndTime().getMillis()));
            statement.setObject(5, timeSlot.getId());
            statement.execute();
            LOGGER.trace("TimeSlot with id {} was updated in database successfully", timeSlot.getId());
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while updating TimeSlot with id {} to database", timeSlot.getId(), e);
        }
    }

    public TimeSlot getById(UUID id){
        TimeSlot result = null;
        LOGGER.trace("Started getting TimeSlot with id {} from database", id);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("timeslot.select"));
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            boolean status = false;
            if (resultSet.next()){
                result = new TimeSlotBuilder().setId((UUID) resultSet.getObject("id"))
                                              .setClientId((UUID) resultSet.getObject("client_id"))
                                              .setDoctorId((UUID) resultSet.getObject("doctor_id"))
                                              .setBounds(new DateTime(((Timestamp) resultSet.getObject("start_time")).getTime()),
                                                         new DateTime(((Timestamp) resultSet.getObject("end_time")).getTime()))
                                              .build();
                status = true;
            }
            LOGGER.trace((status) ? "TimeSlot with id {} was get successfully" :
                                    "TimeSlot with id {} wasn't found in database", id);
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while getting TimeSlot with id {} from database", id, e);
        }
        return result;
    }

    public TreeSet<TimeSlot> getAll(){
        TreeSet<TimeSlot> resultSlots = new TreeSet<>();
        LOGGER.trace("Started getting all TimeSlot instances from database");
        try {
            ResultSet resultSet = connection.prepareStatement(getQuery("timeslot.selectAll"))
                                            .executeQuery();
            while (resultSet.next()){
                resultSlots.add(new TimeSlotBuilder().setId((UUID) resultSet.getObject("id"))
                                                     .setClientId((UUID) resultSet.getObject("client_id"))
                                                     .setDoctorId((UUID) resultSet.getObject("doctor_id"))
                                                     .setBounds(new DateTime(((Timestamp) resultSet.getObject("start_time")).getTime()),
                                                                new DateTime(((Timestamp) resultSet.getObject("end_time")).getTime()))
                                                     .build());
            }
            LOGGER.trace("{} TimeSlot were get from database", resultSlots.size());
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while getting all TimeSlot instances from database", e);
        }
        return resultSlots;
    }

    public void deleteById(UUID id){
        LOGGER.trace("Started deleting TimeSlot with id {} from database", id);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("timeslot.delete"));
            statement.setObject(1, id);
            int status = statement.executeUpdate();
            if (status == 0) LOGGER.info("No TimeSlot instances were affected while deleting");
            else LOGGER.trace("TimeSlot with id {} was succesfully deleted from database", id);
        } catch (SQLException e) {
            LOGGER.info("Something went wrong while deleting TimeSlot with id {}", id, e);
        }
    }
}
