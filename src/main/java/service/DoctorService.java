package service;

import constant.STR_CONSTANT;
import entity.Doctor;
import entity.TimeSlot;
import exception.DoctorWorkTimeException;
import exception.NoOverlapException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Interval;
import utility.ClassNameUtil;

import java.util.TreeSet;

import static constant.STR_CONSTANT.DOC_WORKTIME_EXC;
import static constant.STR_CONSTANT.NO_OVERLAP_EXC;
import static utility.ClassNameUtil.getClassName;

public class DoctorService {

    private final Doctor doctor;
    private static final Logger LOGGER = LogManager.getLogger(getClassName());

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
    public boolean addTimeSlot(TimeSlot slot) throws DoctorWorkTimeException {
        boolean status = checkIfNotOverlapsSet(slot) &&
                         slotSatisfiesDoctorConfig(slot) &&
                         this.doctor.getTimeSlots().add(slot);
        LOGGER.trace("Time slot {} adding status: {}", slot, status);
        return status;
    }

    /**
     * Generates free time periods that satisfy needed slot duration
     * @param slot Needed slot that is used to generate further
     * @return free time intervals
     */
    public TreeSet<TimeSlot> getFreeSlots(TimeSlot slot) throws NoOverlapException {
        if (checkIfNotOverlapsSet(slot)) throw new NoOverlapException(NO_OVERLAP_EXC, 101);
        TreeSet<TimeSlot> resultSlots = new TreeSet<>();
        resultSlots.add(new TimeSlot(doctor.getTimeSlots().last().getEndTime(),
                                     doctor.getEndOfWork()));
        for (TimeSlot current: doctor.getTimeSlots().descendingSet()){
            if (current.overlaps(slot) || doctor.getTimeSlots().lower(current) == null) break;
            TimeSlot freeTime = new TimeSlot(doctor.getTimeSlots().lower(current).getEndTime(),
                                                                   current.getStartTime());
            if (freeTime.getInterval().toDurationMillis() != 0) resultSlots.add(freeTime);
        }
        LOGGER.trace("Doctor {}: free slots: {}", doctor, resultSlots.size());
        return resultSlots;
    }

    /**
     * Checks if slot duration isn't 0, if duration is lower than max duration,
     * if slot time matches doctor work time.
     * @param slot checked slot
     * @return true if all described checks are true, else false
     */
    public boolean slotSatisfiesDoctorConfig(TimeSlot slot) throws DoctorWorkTimeException{
        Interval slotInterval = slot.getInterval();
        if (slotInterval.toDurationMillis() == 0) return false;
        if (!withinWorkTime(slot)) throw new DoctorWorkTimeException(DOC_WORKTIME_EXC, 101);
        return doctor.isMaxDurationChangeable() || withinMaxDurationOfAppointment(slot);
    }

    public boolean removeTimeSlot(TimeSlot slot){
        return this.doctor.getTimeSlots().remove(slot);
    }

    private boolean checkIfNotOverlapsSet(TimeSlot slot){
        return this.doctor.getTimeSlots().stream().noneMatch(x -> x.overlaps(slot));
    }

    private boolean withinWorkTime(TimeSlot slot){
        return new TimeSlot(doctor.getStartOfWork(), doctor.getEndOfWork()).contains(slot);
    }

    private boolean withinMaxDurationOfAppointment(TimeSlot slot){
        return doctor.getMaxDurationOfAppointment() >= slot.getDuration();
    }
}
