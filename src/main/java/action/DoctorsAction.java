package action;

import dao.DoctorDao;
import entity.Doctor;
import entity.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashSet;

import static dao.DaoCache.getCache;

public class DoctorsAction implements Action{

    private static DoctorDao doctorDB =
            (DoctorDao) getCache().getDao(DoctorDao.class.getName());

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HashSet<Doctor> doctors = doctorDB.getAll();
        request.setAttribute("doctors", doctors);

        View view = new View(true);
        view.setPath("/doctors.jsp");
        return view;
    }
}
