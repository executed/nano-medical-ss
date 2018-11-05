package service;

import entity.Doctor;
import entity.TimeSlot;

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
        return !checkIfOverlaps(slot) && this.doctor.getTimeSlots().add(slot);
    }

    public boolean removeTimeSlot(TimeSlot slot){
        return this.doctor.removeTimeSlot(slot);
    }

    public boolean checkIfOverlaps(TimeSlot slot){
        return this.doctor.getTimeSlots().stream().anyMatch(x -> x.overlaps(slot));
    }
}
