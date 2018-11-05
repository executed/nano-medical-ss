package entity;

import configuration.DoctorConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.TreeSet;
import java.util.UUID;

import static service.DateTimeService.parseDateTime;

/** Entity class created to hold info about doctor.
 *  All mutable fields getters/setters should clone
 *  theirs parametrized and returnable data.
 */
public class Doctor {

    //fields
    private final UUID id = UUID.randomUUID();
    private String fullName;
    private DoctorConfiguration configuration;
    private TreeSet<TimeSlot> timeSlots;

    //initialization fields
    {
        this.configuration = new DoctorConfiguration(this);
        timeSlots = new TreeSet<>();
    }

    private Doctor(){ }

    //getters
    public UUID getId() { return this.id; }

    public String getFullName(){ return this.fullName; }

    public DateTime getStartOfWork(){ return this.configuration.getStartOfWork(); }

    public DateTime getEndOfWork(){ return this.configuration.getEndOfWork(); }

    public Interval getWorkInterval(){
        return new Interval(this.getStartOfWork(), this.getEndOfWork());
    }

    public int getMaxDurationOfAppointment(){
        return this.configuration.getMaxDurationOfAppointment();
    }

    public boolean isMaxDurationChangeable(){
        return this.configuration.isMaxDurationChangeable();
    }

    public DoctorConfiguration getConfiguration() { return this.configuration; }

    public TreeSet<TimeSlot> getTimeSlots(){ return this.timeSlots; }

    public boolean addTimeSlot(TimeSlot slot){
        return this.timeSlots.add(slot);
    }

    public boolean removeTimeSlot(TimeSlot slot){
        return this.timeSlots.remove(slot);
    }

    /**
     *  Static inner class created as util to work with Doctor entity.
     */
    public static class DoctorBuilder {

        private Doctor doctor;

        public DoctorBuilder(){
            this.doctor = new Doctor();
        }

        public DoctorBuilder(Doctor doctor) { this.doctor = doctor; }

        public DoctorBuilder setFullName(String fullName){
            this.doctor.fullName = fullName;
            return this;
        }

        public DoctorBuilder setWorkTimeBounds(DateTime startTime, DateTime endTime){
            this.doctor.configuration.setWorkTimeBounds(startTime, endTime);
            return this;
        }

        public DoctorBuilder setWorkTimeBounds(String startTimeStr, String endTimeStr){
            this.doctor.configuration.setWorkTimeBounds(parseDateTime(startTimeStr),
                                                        parseDateTime(endTimeStr));
            return this;
        }

        public DoctorBuilder setMaxDurationOfAppointment(int duration){
            this.doctor.configuration.setMaxDurationOfAppointment(duration);
            return this;
        }

        public DoctorBuilder setMaxDurationOfAppointmentChangeable(boolean status){
            this.doctor.configuration.setMaxDurationChangeable(status);
            return this;
        }

        public Doctor build(){ return this.doctor; }
    }
}
