package service;

import configuration.ClientConfiguration;
import dto.SignupDTO;
import entity.View;
import exception.ValidationException;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static constant.URL_CONSTANT.BASE;
import static constant.URL_CONSTANT.SIGNUP;
import static constant.VALIDATION_MSG_CONSTANT.*;
import static validation.ValidatorCache.getDefValidator;

public class SignupActionService {

    public View resolveView(SignupDTO dto){

        HashMap<String, String> errors = validateDTO(dto);

        ClientDBService dbService = new ClientDBService();
        ClientConfiguration clientConfigByEmail =
                            dbService.getClientConfigByEmail(dto.getEmail());
        ClientConfiguration clientConfigByUsername =
                            dbService.getClientConfigByUsername(dto.getUsername());
        //adding new errors to old validation errors
        getErrors(clientConfigByEmail, clientConfigByUsername).forEach(errors::put);

        UUID savedClientId = UUID.randomUUID();//random just to compile
        if (errors.size() == 0){
            savedClientId = saveDataFromDTO(dbService, dto);
            checkSavedClientId(savedClientId).forEach(errors::put);
        }

        View view = new View(true);
        view.setRedirected(errors.size() == 0);
        errors.forEach(view::putErrorAttribute);
        if (errors.size() == 0){
            view.putSessionAttribute("user", dbService.getClientById(savedClientId));
        }
        view.setPath((errors.size() != 0) ? SIGNUP : BASE);

        return view;
    }

    private HashMap<String, String> checkSavedClientId(UUID id){
        HashMap<String, String> errors = new HashMap<>();
        if (id == null) errors.put("unknown", UNKNOWN);

        return errors;
    }

    private UUID saveDataFromDTO(ClientDBService dbService, SignupDTO dto){
        return dbService.saveSignupActionData(dto);
    }

    private HashMap<String, String> validateDTO(SignupDTO dto){
        HashMap<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<SignupDTO>> violations = getDefValidator().validate(dto);
        if (violations.size() == 0) return errors;
        if (violations.stream().anyMatch(x -> x.getMessage().equals(EMAIL_WRONG))){
            errors.put("email", EMAIL_WRONG);
        }
        if (violations.stream().anyMatch(x -> x.getMessage().equals(PASSWORD_WRONG))){
            errors.put("password", PASSWORD_WRONG);
        }
        //unknown validation fail
        if (errors.size() == 0) throw new ValidationException(SignupDTO.class.getName());
        return errors;
    }

    private HashMap<String, String> getErrors(ClientConfiguration configByEmail,
                                              ClientConfiguration configByUsername){
        HashMap<String, String> errors = new HashMap<>();
        if (configByUsername != null) errors.put("username", USERNAME_EXIST);
        if (configByEmail != null) errors.put("email", EMAIL_EXIST);

        return errors;
    }
}
