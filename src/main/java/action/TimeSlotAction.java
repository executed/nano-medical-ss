package action;

import entity.View;
import service.TimeSlotActionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class TimeSlotAction implements Action{

    public enum Type{ REMOVE, CREATE, UPDATE }

    private Type type;

    private TimeSlotActionService service = new TimeSlotActionService();

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response){
        loadType(request);
        return resolveAction(type, request, response);
    }

    private View resolveAction(Type TYPE, HttpServletRequest req, HttpServletResponse resp){
        switch (TYPE){
            case REMOVE: return removeTimeSlot(req);
            //other actions in future
            default: return null;
        }
    }

    private View removeTimeSlot(HttpServletRequest req){
        //getting needed request data
        UUID slotId = UUID.fromString(req.getParameter("id"));

        return service.removeTimeSlot(slotId);
    }

    private void loadType(HttpServletRequest req){
        String actionType = req.getParameter("action");
        this.type = service.getType(actionType);
    }
}
