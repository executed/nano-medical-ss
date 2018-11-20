package service;

import action.TimeSlotAction;
import entity.View;

import java.util.UUID;

import static constant.EXCEPTION_MSG_CONSTANT.NO_ACTION_TYPE;
import static constant.URL_CONSTANT.CLIENT_PROFILE;

public class TimeSlotActionService {

    public View removeTimeSlot(UUID id){
        new TimeSlotDBService().deleteSlotById(id);

        View view = new View();
        view.setPath(CLIENT_PROFILE);
        view.setStatus(200);
        return view;
    }

    public TimeSlotAction.Type getType(String actionType){
        switch (actionType){
            case "REMOVE": return TimeSlotAction.Type.REMOVE;
            case "CREATE": return TimeSlotAction.Type.CREATE;
            case "UPDATE": return TimeSlotAction.Type.UPDATE;

            default: throw new IllegalArgumentException(NO_ACTION_TYPE);
        }
    }
}
