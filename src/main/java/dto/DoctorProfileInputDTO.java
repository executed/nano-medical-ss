package dto;

import javax.servlet.http.HttpServletRequest;

public class DoctorProfileInputDTO implements UserProfileInputDTO{

    public DoctorProfileInputDTO(HttpServletRequest request){
        decompose(request);
    }

    @Override
    public void decompose(HttpServletRequest request) {
        //decompose
    }

    @Override
    public String getClassName() { return this.getClass().getName(); }
}
