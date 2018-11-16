package action;

import entity.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NothingAction implements Action{
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
