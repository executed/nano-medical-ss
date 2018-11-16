package action;

import configuration.ClientConfiguration;
import dao.ClientDao;
import dto.SignupDTO;
import entity.Client;
import entity.Client.ClientBuilder;
import entity.View;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static constant.VALIDATION_MSG_CONSTANT.*;
import static dao.DaoCache.getCache;
import static org.apache.logging.log4j.LogManager.getLogger;
import static utility.ClassNameUtil.getClassName;
import static validation.ValidatorValueObj.getDefValidator;

public class SignupAction implements Action{

    private static final Logger LOG = getLogger(getClassName());
    private static ClientDao clientDB = (ClientDao) getCache().
                                                    getDao(ClientDao.class.getName());

    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        SignupDTO dto = new SignupDTO(request);
        Set<ConstraintViolation<SignupDTO>> violations = getDefValidator().validate(dto);

        ClientConfiguration clientConfigByEmail = clientDB.getClientConfigByEmail(dto.getEmail());
        ClientConfiguration clientConfigByUsername = clientDB.getClientConfigByEmail(dto.getEmail());

        HashMap<String, String> errors =
                getErrors(violations, clientConfigByEmail, clientConfigByUsername);
        request.setAttribute("errors",
                             getErrors(violations, clientConfigByEmail, clientConfigByUsername));
        //after all validations
        boolean error = true;
        if (errors.size() == 0 && violations.size() == 0) {
            if (saveData(dto)) error = false;
            else errors.put("unknown", UNKNOWN);
        }
        View view = new View(true);
        view.setRedirected(false);
        view.setPathClosed(!error);
        view.setPath((error) ? request.getPathInfo().substring(1) : "clientProfile");
        return view;
    }

    private boolean saveData(SignupDTO dto){
        boolean errorStatus = true;

        Client client = new ClientBuilder().setFirstName(dto.getFirstName())
                                           .setLastName(dto.getLastName())
                                           .build();
        try {
            UUID clientId = clientDB.save(client);
            if (clientId == null) throw new SQLException("Client saving to database returned null id value");
            ClientConfiguration clientConfig = new ClientConfiguration(clientId,
                                                                       dto.getUsername(),
                                                                       dto.getEmail(),
                                                                       dto.getPassword(),
                                                                       false);
            if (clientDB.saveClientConfig(clientConfig) == null){
                clientDB.deleteById(clientId);
                throw new SQLException("ClientConfiguration saving to database returned null id value");
            }
            else errorStatus = false;
            LOG.trace("SignupDTO {} saved successful");
        } catch (SQLException e) {
            LOG.info("Something went wrong while saving DTO {} to database", e);
        }
        return !errorStatus;
    }
    //TODO: Make setting more errors as attributes
    private HashMap<String, String> getErrors(Set<ConstraintViolation<SignupDTO>> violations, ClientConfiguration configByEmail, ClientConfiguration configByUsername){
        HashMap<String, String> errors = new HashMap<>();
        //checking if username exists
        if (configByUsername != null) errors.put("username", USERNAME_EXIST);
        //checking if email is among the validator violations
        if (violations.stream().anyMatch(x -> x.getMessage().equals(EMAIL_WRONG))){
            errors.put("email", EMAIL_WRONG);
        } else if (configByEmail != null) {
            errors.put("email", EMAIL_EXIST);
        }
        return errors;
    }
}
