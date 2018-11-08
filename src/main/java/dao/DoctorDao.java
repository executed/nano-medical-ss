package dao;

import entity.Doctor;
import entity.Doctor.DoctorBuilder;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.HashSet;
import java.util.UUID;

import static org.apache.logging.log4j.LogManager.getLogger;
import static utility.ClassNameUtil.getClassName;
import static utility.SqlQueryUtil.getQuery;

public class DoctorDao {

    private Dao dao;
    private Connection connection;

    private static final Logger LOGGER = getLogger(getClassName());

    public DoctorDao(Dao dao){
        this.dao = dao;
        this.connection = dao.getConnection();
    }

    public void save(Doctor doctor){
        if (doctor.getId() != null){ update(doctor); return; }
        LOGGER.trace("Started saving doctor {} to database");
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("doctor.insert"));
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.execute();

            UUID savedDoctorId = null;
            //getting id of just saved doctor
            for (Doctor current: getAllDoctorsWithoutConfig()){
                if(current.getFullName().equals(doctor.getFullName())){
                    savedDoctorId = current.getId();
                }
            }
            if (savedDoctorId == null) throw new SQLException();
            statement = connection.prepareStatement(getQuery("doctorConfig.insert"));
            statement.setObject(1, savedDoctorId);
            statement.setObject(2, new Timestamp(doctor.getStartOfWork().getMillis()));
            statement.setObject(3, new Timestamp(doctor.getEndOfWork().getMillis()));
            statement.setInt(4, doctor.getMaxDurationOfAppointment());
            statement.setBoolean(5, doctor.isMaxDurationChangeable());

            if (statement.executeUpdate() == 0){
                deleteById(savedDoctorId);
                throw new SQLException();
            }
            LOGGER.trace("Doctor {} saved to database successfully", doctor);
        } catch (SQLException e) {
            LOGGER.info("Doctor {} wasn't saved to database", doctor, e);
        }
    }

    public void update(Doctor doctor){
        if (doctor.getId() == null){ save(doctor); return; }
        LOGGER.trace("Started updating doctor {} in database.", doctor);
        PreparedStatement statement = null, statement2 = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(getQuery("doctor.update"));
            statement.setString(1, doctor.getFirstName());
            statement.setString(2, doctor.getLastName());
            statement.setObject(3, doctor.getId());
            statement.executeUpdate();

            statement2 = connection.prepareStatement(getQuery("doctorConfig.update"));
            statement2.setObject(1, new Timestamp(doctor.getStartOfWork().getMillis()));
            statement2.setObject(2, new Timestamp(doctor.getEndOfWork().getMillis()));
            statement2.setInt(3, doctor.getMaxDurationOfAppointment());
            statement2.setBoolean(4, doctor.isMaxDurationChangeable());
            statement2.setObject(5, doctor.getId());
            statement2.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.trace("Doctor {} updated successfully.", doctor);
        } catch (SQLException e) {
            LOGGER.warn("Doctor {} wasn't updated in database", doctor, e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.warn("Rollback process failed", e1);
            }
        } finally {
            try {
                dao.closePreparedStatements(statement, statement2);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.warn("Auto-commit wasn't set to default", e);
            }
        }
    }

    public Doctor getById(UUID id){
        Doctor doctor = null;
        LOGGER.trace("Started getting doctor by id {} from database.", id);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("doctor.select"));
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                doctor = new DoctorBuilder().setId(id)
                                            .setFirstName(resultSet.getString("first_name"))
                                            .setLastName(resultSet.getString("last_name"))
                                            .setWorkTimeBounds(new DateTime(((Timestamp)resultSet.getObject("start_work")).getTime()),
                                                               new DateTime(((Timestamp)resultSet.getObject("start_work")).getTime()))
                                            .setMaxDurationOfAppointment(resultSet.getInt("max_app_duration"))
                                            .setMaxDurationOfAppointmentChangeable(resultSet.getBoolean("max_app_not_fixed"))
                                            .build();
                LOGGER.trace("Doctor with id {} was get successfully.", id);
            }
        } catch (SQLException e) {
            LOGGER.debug("Doctor wiht id {} wasn't found in database", id, e);
        }
        return doctor;
    }

    public HashSet<Doctor> getAll(){
        HashSet<Doctor> doctors = new HashSet<>();
        LOGGER.trace("Started getting all doctors from database.");
        try {
            ResultSet resultSet =
                    connection.createStatement().executeQuery(getQuery("doctor.selectAll"));
            while (resultSet.next()){
                doctors.add(new DoctorBuilder().setId((UUID) resultSet.getObject("id"))
                                               .setFirstName(resultSet.getString("first_name"))
                                               .setLastName(resultSet.getString("last_name"))
                                               .setWorkTimeBounds(new DateTime(((Timestamp)resultSet.getObject("start_work")).getTime()),
                                                                  new DateTime(((Timestamp)resultSet.getObject("start_work")).getTime()))
                                               .setMaxDurationOfAppointment(resultSet.getInt("max_app_duration"))
                                               .setMaxDurationOfAppointmentChangeable(resultSet.getBoolean("max_app_not_fixed"))
                                               .build());
            }
            LOGGER.trace("{} doctors were get successfully", doctors.size());
        } catch (SQLException e) {
            LOGGER.warn("Process of getting all doctors crashed.", e);
        }
        return doctors;
    }

    public HashSet<Doctor> getAllDoctorsWithoutConfig(){
        HashSet<Doctor> doctors = new HashSet<>();
        LOGGER.trace("Started getting all doctors without configurations");
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("doctor.selectAllDoctorOnly"));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                doctors.add(new DoctorBuilder().setId((UUID) resultSet.getObject("id"))
                                               .setFirstName(resultSet.getString("first_name"))
                                               .setLastName(resultSet.getString("last_name"))
                                               .build());
            }
            LOGGER.trace("{} doctors were get from database", doctors.size());
        } catch (SQLException e) {
            LOGGER.info("Getting all doctors without configuration failed", e);
        }
        return doctors;
    }

    public void deleteById(UUID id){
        LOGGER.trace("Started deleting doctor with id {} from database.", id);
        try {
            PreparedStatement statement = connection.prepareStatement(getQuery("doctor.delete"));
            statement.setObject(1, id);
            int status = statement.executeUpdate();
            if (status == 0) LOGGER.info("No doctors were affected while deleting");
            else LOGGER.debug("Doctor with id {} deleted successfully", id);
        } catch (SQLException e) {
            LOGGER.info("Process of deleting doctor with id {} crashed", id, e);
        }
    }
}