package entity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.Comparator;
import java.util.Objects;

import static service.TimeSlotService.parseTimeBounds;
import static handler.ParameterHandler.checkArgs;

public class TimeSlot implements Comparable<TimeSlot>{

    private DateTime startTime;
    private DateTime endTime;

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

    public DateTime getStartTime() { return startTime; }

    public DateTime getEndTime() { return endTime; }

    public Interval getInterval(){
        return new Interval(this.startTime, this.endTime);
    }

    public int getDuration(){
        return (int) new Duration(this.startTime, this.endTime).getStandardMinutes();
    }

    public boolean overlaps(TimeSlot slot){
        return this.getInterval().overlaps(slot.getInterval());
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
}
