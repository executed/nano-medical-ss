package service;

import entity.Doctor;
import entity.TimeSlot;
import org.joda.time.Interval;

import java.util.TreeSet;

import static handler.ParameterHandler.checkArgs;

public class DoctorService {

    private Doctor doctor;

    public DoctorService(Doctor doctor){
        this.doctor = doctor;
    }

    /**
     * Adds new TimeSlot instance to Doctor TreeSet of TimeSlot.
     * @param slot instance that will be added.
     * @return - true if slot doesn't overlap any of doctor's timeSlots
     *           and if there are no same timeSlot
     *         - false if any of the upper conditions is false.
     */
    public boolean addTimeSlot(TimeSlot slot){
        return !checkIfOverlapsSet(slot) &&
                slotSatisfiesDoctorConfig(slot) &&
                this.doctor.getTimeSlots().add(slot);
    }

    /**
     * Generates free time periods that satisfy needed slot duration
     * @param slot Needed slot that is used to generate further
     * @return free time intervals
     */
    public TreeSet<TimeSlot> getFreeSlots(TimeSlot slot){
        TreeSet<TimeSlot> resultSlots = new TreeSet<>();
        resultSlots.add(new TimeSlot(doctor.getTimeSlots().last().getEndTime(),
                                     doctor.getEndOfWork()));
        for (TimeSlot current: doctor.getTimeSlots().descendingSet()){
            if (current.overlaps(slot)) break;
            TimeSlot freeTime = new TimeSlot(doctor.getTimeSlots().lower(current).getEndTime(),
                                                                   current.getStartTime());
            if (slotSatisfiesDoctorConfig(freeTime)) resultSlots.add(freeTime);
        }
        return resultSlots;
    }

    public boolean slotSatisfiesDoctorConfig(TimeSlot slot){
        Interval slotInterval = new Interval(slot.getStartTime(), slot.getEndTime());
        if (slotInterval.toDurationMillis() == 0) return false;
        return doctor.isMaxDurationChangeable() || doctor.getMaxDurationOfAppointment() >= slot.getDuration() &&
                                                                slotInterval.abuts(this.doctor.getWorkInterval());
    }

    public boolean removeTimeSlot(TimeSlot slot){
        return this.doctor.removeTimeSlot(slot);
    }

    private boolean checkIfOverlapsSet(TimeSlot slot){
        return this.doctor.getTimeSlots().stream().anyMatch(x -> x.overlaps(slot));
    }
}
