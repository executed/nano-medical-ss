package service;

import entity.Doctor;
import entity.View;

import java.util.HashSet;

import static constant.URL_CONSTANT.DOCTORS;

public class DoctorActionService {

    public View resolveView(){

        HashSet<Doctor> doctors = new DoctorDBService().getAll();

        View view = new View(true);
        view.putRequestAttribute("doctors", doctors);
        view.setPath(DOCTORS);

        return view;
    }
}
