package dto;

import entity.Client;
import entity.TimeSlot;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AppointmentDTO {

    private UUID doctorId;
    private UUID clientId;
    private DateTime startTime;
    private DateTime endTime;

    public AppointmentDTO(HttpServletRequest request){
        decompose(request);
    }

    private void decompose(HttpServletRequest request){
        this.doctorId = UUID.fromString(request.getParameter("doctor_id"));
        this.clientId = ((Client) request.getSession(false).getAttribute("user")).getId();

        String dateString = request.getParameter("date");
        String[] hourAndMinute = request.getParameter("time").split(":");
        int neededDuraion = Integer.parseInt(request.getParameter("duration"));
        this.startTime = new DateTime(dateString).withHourOfDay(Integer.parseInt(hourAndMinute[0]))
                                                 .withMinuteOfHour(Integer.parseInt(hourAndMinute[1]))
                                                 .withSecondOfMinute(0).withMillisOfSecond(0);
        this.endTime = startTime.plusMinutes(neededDuraion);
    }

    public UUID getDoctorId() { return doctorId; }

    public UUID getClientId() { return clientId; }

    public DateTime getStartTime() { return startTime; }

    public DateTime getEndTime() { return endTime; }
}
