package entity;

import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;
public class Client implements IUser{

    private UUID id;
    private String firstName;
    private String lastName;
    private TreeSet<TimeSlot> timeSlots;

    public UUID getId() { return id; }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public TreeSet<TimeSlot> getTimeSlots() { return this.timeSlots; }

    public void addTimeSlot(TimeSlot slot){ this.timeSlots.add(slot); }

    public void removeTimeSlot(TimeSlot slot){ this.timeSlots.remove(slot); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName) &&
                Objects.equals(timeSlots, client.timeSlots);
    }

    @Override
    public int hashCode() { return Objects.hash(id, firstName, lastName); }

    @Override
    public String getClassName() { return this.getClass().getName(); }

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

        public ClientBuilder setFirstName(String name){
            this.client.firstName = name;
            return this;
        }

        public ClientBuilder setLastName(String name){
            this.client.lastName = name;
            return this;
        }

        public Client build(){ return this.client; }
    }
}
