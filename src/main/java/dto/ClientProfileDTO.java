package dto;

import entity.Client;
import entity.Doctor;
import entity.TimeSlot;

import java.util.HashMap;
import java.util.TreeSet;

public class ClientProfileDTO {

    private Client client;
    private HashMap<TimeSlot, Doctor> slotDoctorMap = new HashMap<>();

    public void setClient(Client client) { this.client = client; }

    public void addSlotDoctorMapEntry(TimeSlot slot, Doctor doctor){
        this.slotDoctorMap.put(slot, doctor);
    }

    public Client getClient() { return this.client; }

    public HashMap<TimeSlot, Doctor> getSlotDoctorMap() { return this.slotDoctorMap; }
}
