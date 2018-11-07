package service;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import static handler.ParameterHandler.checkArgs;
import static org.joda.time.format.DateTimeFormat.forPattern;

public final class DateTimeService {

    private static final DateTimeFormatter FORMATTER_DEF = forPattern("dd/MM/yyyy HH:mm");

    /**
     * Converts string to DateTime instance.
     * @param dateStr string that contains date
     * @return DateTime instance: - if dateStr matched the pattern HH:mm, than current
     *                              date with given hours and minutes will be returned
     *                            - else DateTime with given parameters will be returned
     */
    public static DateTime parseDateTime(String dateStr){
        checkArgs(dateStr);
        if (dateStr.contains("/")) return DateTime.parse(dateStr, FORMATTER_DEF);
        else return getNowTimeWithHoursAndMinutes(dateStr);
    }

    /**
     * Converts string with hours and minutes to current date with these hours and minutes
     * @param dateStr string that contains hours and minutes in pattern HH:mm
     * @return new DateTime instance with given hours and minutes
     */
    private static DateTime getNowTimeWithHoursAndMinutes(String dateStr){
        if (! dateStr.contains(":")){
            throw new IllegalArgumentException("Format HH:mm needed: " + dateStr);
        }
        String[] hoursAndMinutes = dateStr.split(":");
        int hours = Integer.parseInt(hoursAndMinutes[0]),
            minutes = Integer.parseInt(hoursAndMinutes[1]);
        return new DateTime().withHourOfDay(hours)
                             .withMinuteOfHour(minutes)
                             .withSecondOfMinute(0)
                             .withMillisOfSecond(0);
    }
}
