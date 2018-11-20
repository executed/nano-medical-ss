package action;

import dao.TimeSlotDao;
import entity.IUser;
import entity.TimeSlot;
import entity.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static dao.DaoCache.getCache;

public class TimeSlotAction implements Action{

    public enum Type{ REMOVE, CREATE, UPDATE }

    private static TimeSlotDao timeSlotDB =
            (TimeSlotDao) getCache().getDao(TimeSlotDao.class.getName());

    private Type type;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.req = request;
        this.resp = response;
        loadType();
        return resolveAction(type);
    }

    private void loadType(){
        String actionType = req.getParameter("action");
        switch (actionType){
            case "REMOVE": this.type = Type.REMOVE; break;
            case "CREATE": this.type = Type.CREATE; break;
            case "UPDATE": this.type = Type.UPDATE; break;
            default: throw new IllegalArgumentException("No such TimeSlotAction Type");
        }
    }

    private View resolveAction(Type TYPE){
        switch (TYPE){
            case REMOVE: return removeTimeSlot();
            default: return null;
        }
    }

    private View removeTimeSlot(){
        UUID slotId = UUID.fromString(req.getParameter("id"));
        timeSlotDB.deleteById(slotId);
        View view = new View();
        view.setPath("/client-profile.jsp");
        resp.setStatus(200);
        return view;
    }
}
