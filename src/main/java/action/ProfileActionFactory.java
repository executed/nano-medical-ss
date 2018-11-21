package action;

import dto.ClientProfileInputDTO;
import dto.DoctorProfileInputDTO;
import dto.UserProfileInputDTO;
import entity.Client;
import entity.Doctor;
import entity.IUser;

import javax.servlet.http.HttpServletRequest;

public class ProfileActionFactory {

    public UserProfileInputDTO resolveInputDTO(HttpServletRequest request){
        IUser user = (IUser) request.getSession().getAttribute("user");
        String userTypeName = user.getClassName();

        if (userTypeName.equals(Client.class.getName())){
            return new ClientProfileInputDTO(request);
        }
        if (userTypeName.equals(Doctor.class.getName())){
            return new DoctorProfileInputDTO(request);
        }
        return null; //if type not found
    }
}
