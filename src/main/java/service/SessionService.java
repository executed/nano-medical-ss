package service;

import entity.IUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionService {

    public static void attachUser(HttpSession session, IUser user){
        session.setAttribute("user", user);
    }
}
