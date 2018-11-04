package configuration;

import entity.Doctor;
import org.joda.time.DateTime;

public class DoctorConfiguration {

    private Doctor doctor;

    private DateTime startOfWork, endOfWork;
    private int maxDurationOfAppointment;
    private boolean maxDurationChangeable = true;

    /** Sets doctor instance from parameter */
    public DoctorConfiguration(Doctor doctor){
        this.doctor = doctor;
    }

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
}
