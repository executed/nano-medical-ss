package entity;

import java.util.TreeSet;
import java.util.UUID;

public class Client {

    private UUID id;
    private String fullName;
    private TreeSet<TimeSlot> timeSlots;

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public TreeSet<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void addTimeSlot(TimeSlot slot){
        this.timeSlots.add(slot);
    }

    public void removeTimeSlot(TimeSlot slot){
        this.timeSlots.remove(slot);
    }
}
