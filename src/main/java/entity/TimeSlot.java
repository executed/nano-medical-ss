package entity;

import org.joda.time.DateTime;

import static service.TimeSlotService.parseTimeBounds;
import static handler.ParameterHandler.checkArgs;

public class TimeSlot {

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
}
