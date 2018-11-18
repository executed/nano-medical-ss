package service;

import entity.IUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionService {

    public static void attachUser(HttpServletRequest request, IUser user){
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60*60*24);
    }

    public static void detachUser(HttpServletRequest request, IUser user){
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
    }

    public static void stopSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.invalidate();
    }
}
