package action;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private static Map<String, Action> actions = new HashMap<>();

    static {
        //nothing action
        actions.put("GET/index", new NothingAction(false));
        actions.put("GET/main", new NothingAction(false));
        actions.put("GET/signin", new NothingAction(false));
        actions.put("GET/about", new NothingAction(false));
        actions.put("GET/signup", new NothingAction(false));
        actions.put("GET/contact", new NothingAction(false));
        actions.put("GET/test", new NothingAction(true));
        //other action
        actions.put("POST/signin", new LoginAction());
        //more
    }

    public static Action getAction(HttpServletRequest request) {
        return actions.getOrDefault(request.getMethod() + request.getPathInfo(), null);
    }
}
