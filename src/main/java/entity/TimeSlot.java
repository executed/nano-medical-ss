package entity;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.Objects;
import java.util.UUID;

import static service.TimeSlotService.parseTimeBounds;
import static handler.ParameterHandler.checkArgs;

public class TimeSlot implements Comparable<TimeSlot>{

    private UUID id;
    private UUID clientId;
    private UUID doctorId;
    private DateTime startTime;
    private DateTime endTime;

    private TimeSlot(){ }

    /** Sets time bounds */
    public TimeSlot(DateTime startTime, DateTime endTime){
        checkArgs(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Parses and sets time bounds */
    public TimeSlot(String startTimeStr, String endTimeStr){
        DateTime[] timeBounds = parseTimeBounds(startTimeStr, endTimeStr);
        this.startTime = timeBounds[0];
        this.endTime = timeBounds[1];
    }

    public UUID getId(){ return this.id; }

    public DateTime getStartTime() { return startTime; }

    public DateTime getEndTime() { return endTime; }

    public Interval getInterval(){
        return new Interval(this.startTime, this.endTime);
    }

    public int getDuration(){
        return (int) new Duration(this.startTime, this.endTime).getStandardMinutes();
    }

    public UUID getClientId(){ return this.clientId; }

    public UUID getDoctorId(){ return this.doctorId; }

    public boolean overlaps(TimeSlot slot){
        return this.getInterval().overlaps(slot.getInterval());
    }

    public boolean contains(TimeSlot slot){
        //creating new DateTime instances to prevent bad results by timeslots with different date
        int resultEndTime = new DateTime().withHourOfDay(endTime.getHourOfDay()).withMinuteOfHour(endTime.getMinuteOfHour())
                              .compareTo(new DateTime().withHourOfDay(slot.endTime.getHourOfDay()).withMinuteOfHour(slot.endTime.getMinuteOfHour()));
        int resultStartTime = new DateTime().withHourOfDay(startTime.getHourOfDay()).withMinuteOfHour(startTime.getMinuteOfHour())
                              .compareTo(new DateTime().withHourOfDay(slot.startTime.getHourOfDay()).withMinuteOfHour(slot.startTime.getMinuteOfHour()));
        return resultStartTime <= 0 && resultEndTime >= 0;
    }

    @Override
    public int compareTo(TimeSlot comparable) {
        int result1 = this.startTime.compareTo(comparable.startTime);
        int result2 = this.endTime.compareTo(comparable.endTime);
        return (result1 != 0) ? result1 : result2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(startTime, timeSlot.startTime) &&
                Objects.equals(endTime, timeSlot.endTime);
    }

    public static class TimeSlotBuilder {

        private TimeSlot timeSlot;

        public TimeSlotBuilder(){
            this.timeSlot = new TimeSlot();
        }

        public TimeSlotBuilder(TimeSlot timeSlot) { this.timeSlot = timeSlot; }

        public TimeSlotBuilder setId(UUID uuid){
            this.timeSlot.id = uuid;
            return this;
        }

        public TimeSlotBuilder setClientId(UUID clientId){
            this.timeSlot.clientId = clientId;
            return this;
        }

        public TimeSlotBuilder setDoctorId(UUID doctorId){
            this.timeSlot.doctorId = doctorId;
            return this;
        }

        public TimeSlotBuilder setBounds(DateTime startTime, DateTime endTime){
            this.timeSlot.startTime = startTime;
            this.timeSlot.endTime = endTime;
            return this;
        }

        public TimeSlotBuilder setBounds(String startTimeStr, String endTimeStr){
            DateTime[] timeBounds = parseTimeBounds(startTimeStr, endTimeStr);
            this.timeSlot.startTime = timeBounds[0];
            this.timeSlot.endTime = timeBounds[1];
            return this;
        }

        public TimeSlot build(){ return this.timeSlot; }
    }
}
