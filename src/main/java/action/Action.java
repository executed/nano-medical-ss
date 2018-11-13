package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    boolean isRedirect();
    void setRedirect(boolean status);
}
