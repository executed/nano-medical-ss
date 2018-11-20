package service;

import org.joda.time.DateTime;

import static handler.ParameterHandler.checkArgs;
import static utility.DateTimeUtil.parseDateTime;

public final class TimeSlotService {

    /**
     * Converts income strings into DateTime instances.
     * @param startTimeStr string that contains time of TimeSlot startTime field
     * @param endTimeStr string that contains time of TimeSlot endTime field
     * @return Array of 2 DateTime instances converted from 'startTimeStr' and 'endTimeStr'
     */
    public static DateTime[] parseTimeBounds(String startTimeStr, String endTimeStr){
        checkArgs(startTimeStr, endTimeStr);
        return new DateTime[]{ parseDateTime(startTimeStr), parseDateTime(endTimeStr) };
    }
}
