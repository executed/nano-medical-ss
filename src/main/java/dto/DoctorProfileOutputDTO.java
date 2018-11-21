package dto;

public class DoctorProfileOutputDTO implements UserProfileOutputDTO{


    @Override
    public String getClassName() { return this.getClass().getName(); }
}
