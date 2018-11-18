package action;

import configuration.ClientConfiguration;
import dao.ClientDao;
import dto.LoginDTO;
import entity.Client;
import entity.Client.ClientBuilder;
import entity.View;
import service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.VALIDATION_MSG_CONSTANT.USERNAME_OR_PASS_WRONG;
import static dao.DaoCache.getCache;
import static validation.ValidatorValueObj.getDefValidator;

public class LoginAction implements Action{

    private static ClientDao clientDB =
            (ClientDao) getCache().getDao(ClientDao.class.getName());

    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception{

        boolean error = true;
        LoginDTO dto = new LoginDTO(request);

        ClientConfiguration clientConfig =
                clientDB.getClientConfigByUsername(dto.getUsername());

        if (!hasErrors(dto, clientConfig, request)){
            //initializing client with data from database
            Client client = clientDB.getById(clientConfig.getId());
            new ClientBuilder(client).setConfig(clientConfig);
            //setting request attributes for jsp
            request.setAttribute("client", client);
            request.setAttribute("clientConfig", clientConfig);
            error = false;
            SessionService.attachUser(request, client);
        }
        View view = new View(true);
        view.setRedirected(!error);
        view.setPathClosed(error);
        view.setPath((error) ? "/signin.jsp" : "/");

        return view;
    }

    private boolean hasErrors(LoginDTO dto, ClientConfiguration config, HttpServletRequest req){

        boolean validationStatus = getDefValidator().validate(dto).size() == 0;
        if (config == null || !config.getPassword().equals(dto.getPassword()) || !validationStatus){
            req.setAttribute("errorMessage", USERNAME_OR_PASS_WRONG);
            return true;
        } else return false;
    }
}
