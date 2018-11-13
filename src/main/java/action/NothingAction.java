package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NothingAction implements Action{

    /** Is located in WEB-INF directory */
    private boolean hidden = false;
    private boolean redirect = true;

    public NothingAction() { }

    public NothingAction(boolean hidden){
        this.hidden = hidden;
    }

    @Override
    public boolean isRedirect() { return this.redirect; }

    @Override
    public void setRedirect(boolean status) { this.redirect = status; }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //if page is hidden in WEB-INF, than ServletDispatcher.forward(..) in controller
        //must be used
        if (this.hidden){
            this.redirect = false;
            return "WEB-INF/views/" + request.getPathInfo().substring(1);
        }
        //if page isn't hidden - than page must be redirected in controller
        else return request.getPathInfo().substring(1);
    }
}
