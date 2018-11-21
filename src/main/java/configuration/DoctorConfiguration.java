package configuration;

import entity.Doctor;
import org.joda.time.DateTime;

import java.util.UUID;

public class DoctorConfiguration {

    private Doctor doctor;

    private UUID id;
    private String username;
    private String password;
    private String speciality;
    private DateTime startOfWork, endOfWork;
    private int maxDurationOfAppointment;
    private boolean maxDurationChangeable = true;

    /** Sets doctor instance from parameter */
    public DoctorConfiguration(Doctor doctor){
        this.doctor = doctor;
    }

    public String getSpeciality(){ return this.speciality; }

    public DateTime getStartOfWork(){ return this.startOfWork; }

    public DateTime getEndOfWork(){ return this.endOfWork; }

    public int getMaxDurationOfAppointment(){ return this.maxDurationOfAppointment; }

    public boolean isMaxDurationChangeable(){ return this.maxDurationChangeable; }

    public UUID getId(){ return this.id; }

    public String getUsername(){ return this.username; }

    public String getPassword(){ return this.password; }

    public void setWorkTimeBounds(DateTime startOfWork, DateTime endOfWork){
        this.startOfWork = startOfWork;
        this.endOfWork = endOfWork;
    }

    public void setMaxDurationOfAppointment(int duration){
        this.maxDurationOfAppointment = duration;
    }

    public void setMaxDurationChangeable(boolean changeable){
        this.maxDurationChangeable = changeable;
    }

    public void setSpeciality(String speciality){  this.speciality = speciality; }

    public void setId(UUID id){ this.id = id; }

    public void setUsername(String username){ this.username = username; }

    public void setPassword(String password){ this.password = password; }
}
