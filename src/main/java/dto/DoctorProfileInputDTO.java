package dto;

import entity.Doctor;

import javax.servlet.http.HttpServletRequest;

public class DoctorProfileInputDTO implements UserProfileInputDTO{

    private HttpServletRequest request;
    private Doctor doctor;

    public DoctorProfileInputDTO(HttpServletRequest request){
        assignIfAbsent(request);
        decompose(request);
    }

    @Override
    public void decompose(HttpServletRequest request) {
        this.doctor = (Doctor) request.getSession(false).getAttribute("user");
    }

    private void assignIfAbsent(HttpServletRequest request){
        if (this.request == null) this.request = request;
        else throw new IllegalArgumentException("Request was already decomposed : " + request);
    }

    public Doctor getDoctor() { return this.doctor; }

    @Override
    public String getClassName() { return this.getClass().getName(); }
}
