package action;

import configuration.ClientConfiguration;
import dao.ClientDao;
import dto.LoginDTO;
import entity.Client;
import entity.Client.ClientBuilder;
import entity.IUser;
import entity.View;
import service.LoginActionService;
import utility.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.VALIDATION_MSG_CONSTANT.USERNAME_OR_PASS_WRONG;
import static dao.DaoCache.getCache;
import static validation.ValidatorCache.getDefValidator;

public class LoginAction implements Action{

    public View execute(HttpServletRequest request, HttpServletResponse response){

        LoginDTO dto = new LoginDTO(request);
        View view = new LoginActionService().resolveView(dto);

        view.getErrorAttributes().forEach(request::setAttribute);
        IUser user = (IUser) view.getSessionAttribute("user");
        if (user != null) SessionUtil.attachUser(request, user);

        return view;
    }
}
