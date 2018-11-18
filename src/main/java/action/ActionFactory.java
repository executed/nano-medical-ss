package action;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private static Map<String, Action> actions = new HashMap<>();

    static {
        //nothing action
        actions.put("GET/", new ForwardAction("WEB-INF/views/index.jsp"));
        actions.put("GET/signin.jsp", new RedirectAction());
        actions.put("GET/about.jsp", new RedirectAction());
        actions.put("GET/signup.jsp", new RedirectAction());
        actions.put("GET/contact.jsp", new RedirectAction());
        actions.put("GET/client-profile", new ProfileAction());

        actions.put("GET/signout", new SignoutAction());
        //other action
        actions.put("POST/signin.jsp", new LoginAction());
        actions.put("POST/signup.jsp", new SignupAction());
        //more
    }

    public static Action getAction(HttpServletRequest request) {
        return actions.getOrDefault(request.getMethod() + request.getPathInfo(), null);
    }
}
