package service;

import configuration.ClientConfiguration;
import configuration.DoctorConfiguration;
import dto.LoginDTO;
import entity.Client;
import entity.Client.ClientBuilder;
import entity.IUser;
import entity.Role;
import entity.View;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static constant.URL_CONSTANT.BASE;
import static constant.URL_CONSTANT.SIGNIN;
import static constant.VALIDATION_MSG_CONSTANT.USERNAME_OR_PASS_WRONG;
import static validation.ValidatorCache.getDefValidator;

public class LoginActionService {

    public View resolveView(LoginDTO dto){

        String clientError, doctorError;
        ClientDBService clientDbService = new ClientDBService();
        DoctorDBService doctorDBService = new DoctorDBService();
        ClientConfiguration clientConfigByUsername =
                            clientDbService.getClientConfigByUsername(dto.getUsername());
        DoctorConfiguration doctorConfigByUsername =
                            doctorDBService.getDoctorConfigByUsername(dto.getUsername());
        clientError = getClientError(dto, clientConfigByUsername);
        doctorError = getDoctorError(dto, doctorConfigByUsername);

        IUser user = null;
        View view = new View(true);
        boolean errorStatus = false;//REMOVE
        if (clientError == null){
            user = clientDbService.getClientById(clientConfigByUsername.getId());
            new ClientBuilder((Client) user).setConfig(clientConfigByUsername);

            view.putSessionAttribute("clientConfig", clientConfigByUsername);
        }
        else if (doctorError == null){
            user = doctorDBService.getById(doctorConfigByUsername.getId());
        }
        else{
            view.putErrorAttribute("errorMessage", clientError);
            errorStatus = true;
        }

        view.setRedirected(clientError == null);
        view.putSessionAttribute("user", user);
        view.putSessionAttribute("role",
                                 checkRole(clientConfigByUsername, doctorConfigByUsername));

        view.setPath((!errorStatus) ? BASE : SIGNIN);
        return view;
    }

    private String getClientError(LoginDTO dto, ClientConfiguration configByUsername){

        if (configByUsername == null) return USERNAME_OR_PASS_WRONG;
        boolean error1 = !validateDTO(dto);
        boolean error2 = !passwordsEquals(dto.getPassword(), configByUsername.getPassword());
        if (error1 || error2) return USERNAME_OR_PASS_WRONG;
        else return null;
    }

    private boolean validateDTO(LoginDTO dto){
        Set<ConstraintViolation<LoginDTO>> violations = getDefValidator().validate(dto);
        return violations.size() == 0;
    }

    private boolean passwordsEquals(String password1, String password2){
        return password1.equals(password2);
    }

    private String getDoctorError(LoginDTO dto, DoctorConfiguration configByUsername){
        if (configByUsername == null) return USERNAME_OR_PASS_WRONG;
        boolean error1 = !validateDTO(dto);
        boolean error2 = !passwordsEquals(dto.getPassword(), configByUsername.getPassword());
        if (error1 || error2) return USERNAME_OR_PASS_WRONG;
        else return null;
    }

    private Role checkRole(ClientConfiguration clientConfig, DoctorConfiguration doctorConfig){
        if (clientConfig != null) return Role.CLIENT;
        if (doctorConfig != null) return Role.DOCTOR;
        //must be admin

        throw new IllegalArgumentException("Role wasn't recognized");
    }
}
