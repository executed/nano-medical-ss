package entity;

import java.util.UUID;

/** Entity class created to hold info about doctor.
 *  All mutable fields getters/setters should clone
 *  theirs parametrized and returnable data.
 */
public class Doctor {

    //fields
    private final UUID id = UUID.randomUUID();
    private String fullName;

    //constructors
    public Doctor(){ }

    public Doctor(String fullName){ this.fullName = fullName; }

    //getters
    public UUID getId() { return this.id; }

    public String getFullName(){ return this.fullName; }

    /** Static inner class created as util to work with Doctor entity */
    public static class DoctorBuilder {

        private Doctor doctor;

        public DoctorBuilder(){ this.doctor = new Doctor(); }

        public DoctorBuilder(Doctor doctor) { this.doctor = doctor; }

        public DoctorBuilder setFullName(String fullName){
            this.doctor.fullName = fullName;
            return this;
        }

        public Doctor build(){ return this.doctor; }
    }
}
