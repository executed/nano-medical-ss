package action;

import constant.URL_CONSTANT;
import entity.View;
import utility.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignoutAction implements Action{
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response){
        SessionUtil.stopSession(request);

        View view = new View();
        view.setPath(URL_CONSTANT.BASE);

        return view;
    }
}
