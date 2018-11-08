package entity;

import java.util.HashSet;
import java.util.Optional;

public class MedicalCenter {

    private HashSet<Doctor> doctors;

    public void addDoctor(Doctor doctor){ this.doctors.add(doctor); }

    public void removeDoctorByFullName(String firstName, String lastName){
        this.doctors.removeIf(doctor -> doctor.getFirstName().equals(firstName) &&
                                        doctor.getLastName().equals(lastName));
    }

    public Doctor getDoctorByFullName(String firstName, String lastName){
        Optional<Doctor> optionalDoctor =
                this.doctors.stream().filter(x -> x.getFirstName().equals(firstName) &&
                                                  x.getLastName().equals(lastName))
                                                  .findFirst();
        return optionalDoctor.orElse(null);
    }
}
