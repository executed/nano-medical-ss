package controller;

import action.Action;
import action.ActionFactory;
import entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static utility.ClassNameUtil.getClassName;

@WebServlet(name = "mainServlet", urlPatterns = "/nano-medical/*")
public class FrontController extends HttpServlet{

    private static final Logger LOG = LogManager.getLogger(getClassName());

    protected void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("pathinfo: "+request.getPathInfo());
        try {
            Action action = ActionFactory.getAction(request);
            if (action == null){
                LOG.trace("No action specified for request: {}",
                        request.getMethod() + request.getPathInfo().substring(1));
                response.sendRedirect("/");
            }
            View view = action.execute(request, response);
            if (view == null) return;
            System.out.println("OUT: "+view.getPath());
            if (view.isRedirected()) response.sendRedirect(view.getPath());
            else request.getRequestDispatcher(view.getPath()).forward(request, response);
        } catch (Exception e){
            LOG.trace("Something went wrong while obtaining request.", e);
            //TODO: must redirect to error page
        }
    }
}
