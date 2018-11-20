package action;

import entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static utility.ClassNameUtil.getClassName;

public class RedirectAction implements Action{

    private static final Logger LOG = LogManager.getLogger(getClassName());
    private String path;

    public RedirectAction(){ }

    public RedirectAction(String path){ this.path = path; }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        View view = new View();
        //if path wasn't set by constructor
        view.setPath((path == null) ? "/" + request.getPathInfo().substring(1) : path);
        return view;
    }
}
