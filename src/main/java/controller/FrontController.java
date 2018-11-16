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
import java.io.File;
import java.io.IOException;

import static utility.ClassNameUtil.getClassName;

@WebServlet(name = "mainServlet", urlPatterns = "/main/*")
public class MainController extends HttpServlet{

    private static final Logger LOG = LogManager.getLogger(getClassName());

    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            Action action = ActionFactory.getAction(request);
            if (action == null){
                LOG.trace("No action specified for request: {}",
                        request.getMethod() + request.getPathInfo().substring(1));
                response.sendRedirect(request.getPathInfo().substring(1));
            }
            View view = action.execute(request, response);
            System.out.println("OUT: "+view.getContext());
            if (view.isRedirect()) response.sendRedirect(view.getContext());
            else request.getRequestDispatcher(view.getContext()).forward(request, response);
        } catch (Exception e){
            LOG.trace("Something went wrong while obtaining request.", e);
            //TODO: must redirect to error page
        }
    }
}
