package service;

import configuration.ClientConfiguration;
import dto.LoginDTO;
import entity.Client;
import entity.Client.ClientBuilder;
import entity.View;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static constant.URL_CONSTANT.BASE;
import static constant.URL_CONSTANT.SIGNIN;
import static constant.VALIDATION_MSG_CONSTANT.USERNAME_OR_PASS_WRONG;
import static validation.ValidatorCache.getDefValidator;

public class LoginActionService {

    public View resolveView(LoginDTO dto){

        String error;
        ClientDBService dbService = new ClientDBService();
        ClientConfiguration configByUsername =
                            dbService.getClientConfigByUsername(dto.getUsername());
        error = getError(dto, configByUsername);
        Client client = null;
        if (error == null){
            client = dbService.getClientById(configByUsername.getId());
            new ClientBuilder(client).setConfig(configByUsername);
        }

        View view = new View(true);
        view.setRedirected(error == null);
        view.putErrorAttribute("errorMessage", error);
        view.putSessionAttribute("user", client);
        view.putSessionAttribute("clientConfig", configByUsername);
        view.setPath((error == null) ? BASE : SIGNIN);

        return view;
    }

    private String getError(LoginDTO dto, ClientConfiguration configByUsername){

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
}
