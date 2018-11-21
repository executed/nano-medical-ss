package action;

import constant.URL_CONSTANT;
import entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static constant.URL_CONSTANT.BASE;
import static utility.ClassNameUtil.getClassName;

public class RedirectAction implements Action{

    private String path;

    public RedirectAction(){ }

    public RedirectAction(String path){ this.path = path; }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response){

        View view = new View();
        //if path was/wasn't set by constructor
        view.setPath((path == null) ? (BASE + request.getPathInfo().substring(1)) : path);
        return view;
    }
}
