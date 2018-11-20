package action;

import entity.View;
import utility.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignoutAction implements Action{
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SessionUtil.stopSession(request);

        View view = new View();
        view.setPath("/");

        return view;
    }
}
