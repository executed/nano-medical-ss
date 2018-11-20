package service;

import entity.View;

import java.util.UUID;

public class TimeSlotActionService {

    public View removeTimeSlot(UUID id){
        new TimeSlotDBService().deleteSlotById(id);

        View view = new View();
        view.setPath("/client-profile.jsp");
        view.setStatus(200);
        return view;
    }
}
