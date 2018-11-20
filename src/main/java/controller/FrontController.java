package controller;

import action.Action;
import action.ActionFactory;
import constant.URL_CONSTANT;
import entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ViewService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static constant.URL_CONSTANT.BASE;
import static constant.URL_CONSTANT.UNKNOWN;
import static utility.ClassNameUtil.getClassName;

@WebServlet(name = "mainServlet", urlPatterns = "/nano-medical/*")
public class FrontController extends HttpServlet{

    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Action action = ActionFactory.routeAction(request);
            if (action == null) response.sendRedirect(BASE);

            View view = action.execute(request, response);
            ViewService viewService = new ViewService(view);

            if (viewService.checkIfEmpty()) return;
            viewService.resolveStatus(response);

            viewService.makeResponse(request, response);
        } catch (Exception e){
            response.sendRedirect(UNKNOWN);
        }
    }
}
