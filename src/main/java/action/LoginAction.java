package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action{

    private boolean redirect;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //some executes
        return null;
    }

    @Override
    public boolean isRedirect() { return this.redirect; }

    @Override
    public void setRedirect(boolean status) { this.redirect = status; }
}
