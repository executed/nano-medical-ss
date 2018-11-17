package action;

import dao.ClientDao;
import entity.Client;
import entity.Doctor;
import entity.IUser;
import entity.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static dao.DaoCache.getCache;
import static utility.ClassNameUtil.getClassName;

public class ProfileAction implements Action{

    private static final Logger LOG = LogManager.getLogger(getClassName());
    private static ClientDao clientDB =
            (ClientDao) getCache().getDao(ClientDao.class.getName());
    //servlet instances
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public View execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOG.trace("Executing of ProfileAction started by User {}", request.getSession().getAttribute("user"));
        this.req = request;
        this.resp = response;
        return resolveUser();
    }

    private View resolveUser(){

        IUser user = (IUser) req.getSession().getAttribute("user");
        //getting IUser implementation
        String userType = user.getClassName();
        if (userType.equals(Client.class.getName())) return resolveClient(user);
        if (userType.equals(Doctor.class.getName())) return resolveDoctor(user);
        //default
        return null;
    }

    private View resolveClient(IUser user){
        Client client = (Client) (Client) user;
        //some resolving of client ;))

        View view = new View(true);
        view.setPath("/client-profile.jsp");
        return view;
    }

    private View resolveDoctor(IUser user){
        Doctor doctor = (Doctor) user;
        //some resolving of doctor ;))
        return null;
    }
}
