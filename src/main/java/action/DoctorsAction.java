package action;

import dao.DoctorDao;
import entity.Doctor;
import entity.View;
import service.DoctorActionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashSet;

import static dao.DaoCache.getCache;

public class DoctorsAction implements Action{

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response){

        View view = new DoctorActionService().resolveView();
        view.getRequestAttributes().forEach(request::setAttribute);

        return view;
    }
}
