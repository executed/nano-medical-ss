package action;

import entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static utility.ClassNameUtil.getClassName;

public class ForwardAction implements Action{

    private String path;

    public ForwardAction(String path){ this.path = path; }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        View view = new View(true);
        view.setPath(path);
        return view;
    }
}