package action;

import dto.UserProfileInputDTO;
import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ProfileActionService;
import utility.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static utility.ClassNameUtil.getClassName;

public class ProfileAction implements Action{

    private static final Logger LOG = LogManager.getLogger(getClassName());

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response){

        UserProfileInputDTO inputDTO = new ProfileActionFactory().resolveInputDTO(request);
        View view = new ProfileActionService().resolveView(inputDTO);

        view.getRequestAttributes().forEach(request::setAttribute);
        view.getErrorAttributes().forEach(request::setAttribute);
        view.getSessionAttributes().forEach((x, y) -> SessionUtil.attachObj(request, x, y));

        return view;
    }
}
