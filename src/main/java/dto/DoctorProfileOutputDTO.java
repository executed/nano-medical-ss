package dto;

import entity.Doctor;
import entity.TimeSlot;

import java.util.HashMap;

public class DoctorProfileOutputDTO implements UserProfileOutputDTO{

    private Doctor doctor;
    private HashMap<TimeSlot, Doctor> slotDoctorMap = new HashMap<>();

    public void setDoctor(Doctor doctor){ this.doctor = doctor; }

    public void addSlotDoctorMapEntry(TimeSlot slot, Doctor doctor){
        this.slotDoctorMap.put(slot, doctor);
    }

    public Doctor getDoctor(){ return this.doctor; }

    public HashMap<TimeSlot, Doctor> getSlotDoctorMap() { return this.slotDoctorMap; }

    @Override
    public String getClassName() { return this.getClass().getName(); }
}
