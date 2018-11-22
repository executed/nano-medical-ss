package action;

import constant.URL_CONSTANT;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static action.AppointmentAction.REQUEST_TYPE.GET;
import static action.AppointmentAction.REQUEST_TYPE.POST;
import static constant.URL_CONSTANT.BASE;

public class ActionFactory {

    private static Map<String, Action> actions = new HashMap<>();

    static {
        //nothing action
        actions.put("GET/", new ForwardAction(BASE + "index.jsp"));
        actions.put("GET/signin.jsp", new ForwardAction("/signin.jsp"));
        actions.put("GET/about.jsp", new ForwardAction("/about.jsp"));
        actions.put("GET/signup.jsp", new ForwardAction("/signup.jsp"));
        actions.put("GET/contact.jsp", new ForwardAction("/contact.jsp"));
        actions.put("GET/client-profile", new ProfileAction());
        actions.put("GET/doctor-profile", new ProfileAction());

        actions.put("GET/signout", new SignoutAction());
        actions.put("GET/doctors", new DoctorsAction());
        actions.put("GET/appointment", new AppointmentAction(GET));
        //other action
        actions.put("POST/signin.jsp", new LoginAction());
        actions.put("POST/signup.jsp", new SignupAction());
        actions.put("POST/slot-action", new TimeSlotAction());
        actions.put("POST/appointment", new AppointmentAction(POST));
        //more
    }

    public static Action routeAction(HttpServletRequest request) {
        return actions.getOrDefault(request.getMethod() + request.getPathInfo(), null);
    }
}
