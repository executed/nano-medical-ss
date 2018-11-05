package service;

import entity.Doctor;
import entity.Doctor.DoctorBuilder;
import entity.TimeSlot;
import exception.DoctorWorkTimeException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.TreeSet;

import static org.testng.Assert.*;

public class DoctorServiceTest {

    private DoctorService doctorService;
    private Doctor doctor;

    @BeforeClass
    public void setup(){
        doctor = new DoctorBuilder().setFullName("Richardson")
                                                    .setWorkTimeBounds("09:00", "19:00")
                                                    .setMaxDurationOfAppointmentChangeable(false)
                                                    .setMaxDurationOfAppointment(20)
                                                    .build();
        doctor.addTimeSlot(new TimeSlot("12:50", "13:10"));
        doctor.addTimeSlot(new TimeSlot("13:10", "13:25"));
        doctor.addTimeSlot(new TimeSlot("14:00", "14:20"));

        doctorService = new DoctorService(doctor);
    }

    @BeforeMethod
    public void reload(){
        setup();
    }

    @DataProvider
    public Object[][] parseTimeBoundsProvider() {
        doctorService = new DoctorService(doctor);
        return new Object[][] { { new TimeSlot("12:50", "13:30"), false },
                                { new TimeSlot("13:25", "13:45"), true },
                                { new TimeSlot("13:25", "13:50"), false} };
    }

    @DataProvider
    public Object[][] getFreeSlotsProvider() {
        TimeSlot neededSlot1 = new TimeSlot("12:50", "13:10");
        TreeSet<TimeSlot> expectedSlots = new TreeSet<TimeSlot>(){{
            add(new TimeSlot("13:25", "14:00"));
            add(new TimeSlot("14:20", "19:00"));
        }};
        return new Object[][] { { neededSlot1, expectedSlots } };
    }

    @DataProvider
    public Object[][] removeTimeSlotProvider() {

        return new Object[][] { { new TimeSlot("12:50", "13:10"), true },
                                { new TimeSlot("07:00", "07:15"), false } };
    }

    @Test(dataProvider = "parseTimeBoundsProvider")
    public void addTimeSlotTest(TimeSlot timeSlot, boolean expectedValue){
        assertEquals(doctorService.addTimeSlot(timeSlot), expectedValue);
    }

    @Test
    public void addTimeSlotNegativeTest(){
        TimeSlot neededSlot = new TimeSlot("06:00", "06:15");
        assertThrows(DoctorWorkTimeException.class, () -> doctorService.addTimeSlot(neededSlot));
    }


    @Test(dataProvider = "getFreeSlotsProvider")
    public void getFreeSlotsTest(TimeSlot neededSlot, TreeSet<TimeSlot> expectedSlots){
        TreeSet<TimeSlot> resultSlots = doctorService.getFreeSlots(neededSlot);
        assertEquals(resultSlots, expectedSlots);
    }

    @Test
    public void getFreeSlotsNegativeTest(){
        TimeSlot neededSlot = new TimeSlot("06:00", "06:15");
        assertThrows(IllegalArgumentException.class, () -> doctorService.getFreeSlots(neededSlot));
    }

    @Test(dataProvider = "removeTimeSlotProvider")
    public void removeTimeSlot(TimeSlot slot, boolean expectedValue){
        assertEquals(doctorService.removeTimeSlot(slot), expectedValue);
    }
}
