package service;

import entity.Doctor;
import entity.TimeSlot;
import exception.DoctorWorkTimeException;
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
        if (!checkIfOverlapsSet(slot)) throw new IllegalArgumentException("TimeSlot doesn't overlap any of the set");
        TreeSet<TimeSlot> resultSlots = new TreeSet<>();
        resultSlots.add(new TimeSlot(doctor.getTimeSlots().last().getEndTime(),
                                     doctor.getEndOfWork()));
        for (TimeSlot current: doctor.getTimeSlots().descendingSet()){
            if (current.overlaps(slot) || doctor.getTimeSlots().lower(current) == null) break;
            TimeSlot freeTime = new TimeSlot(doctor.getTimeSlots().lower(current).getEndTime(),
                                                                   current.getStartTime());
            if (freeTime.getInterval().toDurationMillis() != 0) resultSlots.add(freeTime);
        }
        return resultSlots;
    }

    /**
     * Checks if slot duration isn't 0, if duration is lower than max duration,
     * if slot time matches doctor work time.
     * @param slot checked slot
     * @return true if all described checks are true, else false
     */
    public boolean slotSatisfiesDoctorConfig(TimeSlot slot){
        Interval slotInterval = slot.getInterval();
        if (slotInterval.toDurationMillis() == 0) return false;
        if (!withinWorkTime(slot)) throw new DoctorWorkTimeException("Doctor doesn't work at this time", 101);
        return doctor.isMaxDurationChangeable() || withinMaxDurationOfAppointment(slot);
    }

    public boolean removeTimeSlot(TimeSlot slot){
        return this.doctor.getTimeSlots().remove(slot);
    }

    private boolean checkIfOverlapsSet(TimeSlot slot){
        return this.doctor.getTimeSlots().stream().anyMatch(x -> x.overlaps(slot));
    }

    private boolean withinWorkTime(TimeSlot slot){
        return new TimeSlot(doctor.getStartOfWork(), doctor.getEndOfWork()).contains(slot);
    }

    private boolean withinMaxDurationOfAppointment(TimeSlot slot){
        return doctor.getMaxDurationOfAppointment() >= slot.getDuration();
    }
}
