package entity;

import java.util.HashSet;
import java.util.Optional;

public class MedicalCenter {

    private HashSet<Doctor> doctors;

    public void addDoctor(Doctor doctor){ this.doctors.add(doctor); }

    public void removeDoctorByFullName(String fullName){
        this.doctors.removeIf(doctor -> doctor.getFullName().equals(fullName));
    }

    public Doctor getDoctorByFullName(String name){
        Optional<Doctor> optionalDoctor =
                this.doctors.stream().filter(x -> x.getFullName().equals(name)).findFirst();
        return optionalDoctor.orElse(null);
    }
}
