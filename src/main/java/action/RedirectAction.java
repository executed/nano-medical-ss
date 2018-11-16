package action;

import entity.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NothingAction implements Action{

    private boolean forwarded = false;

    public NothingAction() { }

    public NothingAction(boolean forwarded){
        this.forwarded = forwarded;
    }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //if page is forwarded in WEB-INF, than ServletDispatcher.forward(..) in controller
        //must be used
        View view = new View(forwarded);
        view.setRedirected(!forwarded);
        view.setPathClosed(forwarded);
        view.setPath(request.getPathInfo().substring(1));
        return view;
    }
}
