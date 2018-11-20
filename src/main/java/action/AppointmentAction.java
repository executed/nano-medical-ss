package action;

import dao.DoctorDao;
import dao.TimeSlotDao;
import dto.AppointmentDTO;
import entity.Client;
import entity.Doctor;
import entity.TimeSlot;
import entity.TimeSlot.TimeSlotBuilder;
import entity.View;
import exception.DoctorWorkTimeException;
import exception.NoOverlapException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;
import java.util.UUID;

import static dao.DaoCache.getCache;

public class AppointmentAction implements Action{

    private static DoctorDao doctorDB =
            (DoctorDao) getCache().getDao(DoctorDao.class.getName());
    private static TimeSlotDao timeSlotDB =
            (TimeSlotDao) getCache().getDao(TimeSlotDao.class.getName());

    public enum REQUEST_TYPE{ GET, POST}

    private REQUEST_TYPE requestType;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public AppointmentAction(REQUEST_TYPE TYPE){
        this.requestType = TYPE;
    }

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws DoctorWorkTimeException,
            NoOverlapException, IOException {
        this.req = request;
        this.resp = response;

        return resolveRequest();
    }

    private View resolveRequest() throws DoctorWorkTimeException, NoOverlapException, IOException {
        //if request was made already with 100% free TimeSlot
        if (req.getParameter("freeSlot") != null) return resolveAddingFreeSlot();
        switch (requestType){
            case GET: return resolveGetRequest();
            case POST: return resolvePostRequest();
            default:
                throw new IllegalArgumentException("No such type of AppointmentAction request");
        }
    }

    private View resolveGetRequest() throws NoOverlapException {

        if (req.getParameter("status") != null) return resolveFreeSlotsRequest();
        UUID doctorId = UUID.fromString(req.getParameter("id"));
        Doctor doctor = doctorDB.getById(doctorId);
        req.setAttribute("doctor", doctor);

        View view = new View(true);
        view.setPath("/appointment.jsp");
        return view;
    }

    private View resolveFreeSlotsRequest() throws NoOverlapException {
        UUID doctorId = UUID.fromString(req.getParameter("doctor_id"));
        Doctor doctor = doctorDB.getById(doctorId);

        DateTime startTime = DateTime.parse(req.getParameter("time"),
                                            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
        DateTime endTime = startTime.plusMinutes(Integer.parseInt(req.getParameter("dif")));

        TimeSlot timeSlot = new TimeSlotBuilder().setClientId(((Client)req.getSession(false).
                                                                       getAttribute("user")).getId())
                                                 .setDoctorId(doctorId)
                                                 .setBounds(new DateTime(startTime),
                                                            new DateTime(endTime))
                                                            .build();
        DoctorService service = new DoctorService(doctorDB.getById(doctorId));
        /*TreeSet<TimeSlot> freeSlots = service.getFreeSlots(timeSlot);*/

        /////////REMOVE
        TreeSet<TimeSlot> freeSlots = new TreeSet<>();
        freeSlots.add(new TimeSlotBuilder().setClientId(UUID.fromString("03e08df0-b835-4fd8-aa4d-d144570e349d"))
                .setDoctorId(UUID.fromString("c1a565cb-1bf9-4e7e-9068-7a54252ae44f"))
                .setBounds("12:30", "12:50")
                .build());
        freeSlots.add(new TimeSlotBuilder().setClientId(UUID.fromString("03e08df0-b835-4fd8-aa4d-d144570e349d"))
                .setDoctorId(UUID.fromString("c1a565cb-1bf9-4e7e-9068-7a54252ae44f"))
                .setBounds("13:40", "14:00")
                .build());
        freeSlots.add(new TimeSlotBuilder().setClientId(UUID.fromString("03e08df0-b835-4fd8-aa4d-d144570e349d"))
                .setDoctorId(UUID.fromString("c1a565cb-1bf9-4e7e-9068-7a54252ae44f"))
                .setBounds("15:10", "15:20")
                .build());
        ///////////////

        req.setAttribute("freeSlots", freeSlots);
        req.setAttribute("doctor", doctor);
        View view = new View(true);
        view.setPath("/appointment.jsp");
        return view;
    }

    private View resolvePostRequest() throws DoctorWorkTimeException, NoOverlapException, IOException {
        AppointmentDTO dto = new AppointmentDTO(req);

        Doctor doctor = doctorDB.getById(dto.getDoctorId());
        //adding TimeSlot's to doctor
        TreeSet<TimeSlot> doctorSlots = timeSlotDB.getByIUserId(dto.getDoctorId(), Doctor.class);
        doctorSlots.forEach(doctor::addTimeSlot);
        //initializing Client needed TimeSlot
        TimeSlot timeSlot = new TimeSlotBuilder().setDoctorId(dto.getDoctorId())
                                                 .setClientId(dto.getClientId())
                                                 .setBounds(dto.getStartTime(), dto.getEndTime())
                                                 .build();
        DoctorService service = new DoctorService(doctor);
        boolean status = service.addTimeSlot(timeSlot);

        /*if (!status){
            TreeSet<TimeSlot> freeSlots = service.getFreeSlots(timeSlot);
            req.setAttribute("freeSlots", freeSlots);
            resp.setStatus(532);
        } else{
            resp.setStatus(235);
            timeSlotDB.save(timeSlot);
        }*/
        ///////////REMOVE
        resp.setStatus(532);
        /////////////////
        return new View();
    }

    private View resolveAddingFreeSlot(){
        String incomeTimeBoundsString = req.getParameter("freeSlot");
        String[] startAndEndTime = incomeTimeBoundsString.split("//");
        UUID doctorId = UUID.fromString(req.getParameter("doctor_id"));
        UUID clientId = ((Client) req.getSession().getAttribute("user")).getId();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        TimeSlot timeSlot = new TimeSlotBuilder().setDoctorId(doctorId)
                                                 .setClientId(clientId)
                                                 .setBounds(DateTime.parse(startAndEndTime[0], formatter),
                                                            DateTime.parse(startAndEndTime[1], formatter))
                                                 .build();
        timeSlotDB.save(timeSlot);

        View view = new View();
        view.setPath("/nano-medical/client-profile");
        return view;
    }
}