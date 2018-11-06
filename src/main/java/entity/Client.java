package entity;

import java.util.Objects;
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

    public void addTimeSlot(TimeSlot slot){
        this.timeSlots.add(slot);
    }

    public void removeTimeSlot(TimeSlot slot){
        this.timeSlots.remove(slot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(fullName, client.fullName) &&
                Objects.equals(timeSlots, client.timeSlots);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, fullName);
    }

    /**
     *  Static inner class created as util to work with Client entity.
     */
    public static class ClientBuilder {

        private Client client;

        public ClientBuilder(){
            this.client = new Client();
        }

        public ClientBuilder(Client client) { this.client = client; }

        public ClientBuilder setId(UUID uuid){
            this.client.id = uuid;
            return this;
        }

        public ClientBuilder setFullName(String fullName){
            this.client.fullName = fullName;
            return this;
        }

        public Client build(){ return this.client; }
    }
}
