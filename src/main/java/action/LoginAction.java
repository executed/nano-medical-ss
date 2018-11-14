package action;

import configuration.ClientConfiguration;
import dao.ClientDao;
import dao.DaoValueObj;
import entity.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static dao.DaoValueObj.getDaoVO;

public class LoginAction implements Action{

    private boolean redirect = false;
    private static ClientDao clientDB =
            (ClientDao) getDaoVO().getDao(ClientDao.class.getName());

    public boolean isRedirect() { return this.redirect; }

    public void setRedirect(boolean status) { this.redirect = status; }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean error = true;
        ClientConfiguration clientConfig = clientDB.getClientConfigByUsername(username);

        if (clientConfig == null || !clientConfig.getPassword().equals(password)){
            request.setAttribute("errorMessage", "Username or password are not valid");
        } else {
            Client client = clientDB.getById(clientConfig.getId());
            request.setAttribute("client", client);
            request.setAttribute("clientConfig", clientConfig);
            error = false;
        }
        return (error) ? request.getPathInfo().substring(1)
                       : "WEB-INF/views/clientProfile";
    }
}
