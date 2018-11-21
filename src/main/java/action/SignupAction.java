package action;

import dto.SignupDTO;
import entity.Client;
import entity.View;
import service.SignupActionService;
import utility.SessionUtil;
import utility.ViewUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.URL_CONSTANT.BASE;

public class SignupAction implements Action{

    public View execute(HttpServletRequest request, HttpServletResponse response){

        if (SessionUtil.checkIfLoggedIn(request)) return ViewUtil.getForwardedView(BASE);
        SignupDTO dto = new SignupDTO(request);
        View view = new SignupActionService().resolveView(dto);

        request.setAttribute("errors", view.getErrorAttributes());
        Client client = (Client) view.getSessionAttribute("user");
        if (client != null) SessionUtil.attachUser(request, client);

        return view;
    }
}
