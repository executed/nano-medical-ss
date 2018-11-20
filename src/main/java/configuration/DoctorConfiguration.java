package configuration;

import entity.Doctor;
import org.joda.time.DateTime;

public class DoctorConfiguration {

    private Doctor doctor;

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
}
