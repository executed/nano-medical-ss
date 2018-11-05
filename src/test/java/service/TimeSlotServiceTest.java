package service;

import constant.STR_CONSTANT;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static constant.STR_CONSTANT.FORMATTER_TIME_PATTERN_DEF;
import static org.joda.time.format.DateTimeFormat.forPattern;
import static org.testng.Assert.*;
import static service.TimeSlotService.parseTimeBounds;

public class TimeSlotServiceTest {

    @DataProvider
    public static Object[][] parseTimeBoundsProvider() {
        String[] currentDayStrings = {"12:50", "13:10"};
        DateTime[] currentDayTimeArray = { new DateTime().withHourOfDay(12).withMinuteOfHour(50)
                                                   .withSecondOfMinute(0).withMillisOfSecond(0),
                                           new DateTime().withHourOfDay(13).withMinuteOfHour(10)
                                                   .withSecondOfMinute(0).withMillisOfSecond(0)};
        String[] someDateStrings = {"12/05/2017 12:10", "13/10/2018 13:00"};
        DateTime[] someDateTimeArray = { forPattern(FORMATTER_TIME_PATTERN_DEF).parseDateTime(someDateStrings[0]),
                                         forPattern(FORMATTER_TIME_PATTERN_DEF).parseDateTime(someDateStrings[1])};
        return new Object[][] { {currentDayStrings, currentDayTimeArray},
                                {someDateStrings, someDateTimeArray}};
    }

    @Test(dataProvider = "parseTimeBoundsProvider")
    public void parseTimeBoundsTest(String[] timeStrings, DateTime[] expectedValue){
        assertEquals(parseTimeBounds(timeStrings[0], timeStrings[1]), expectedValue);
    }

    @Test
    public void parseTimeBoundsNegativeTest(){
        assertThrows(IllegalArgumentException.class, () -> parseTimeBounds("12:30", "1200"));
    }
}
