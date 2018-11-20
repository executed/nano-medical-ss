package service;

import entity.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewService {

    private View view;

    public ViewService(View view){ this.view = view; }

    public boolean checkIfEmpty(){
        return this.view == null || view.getPath() == null;
    }

    public void resolveStatus(HttpServletResponse response){
        if (view.getStatus() != null) response.setStatus(view.getStatus());
    }

    public void makeResponse(HttpServletRequest request, HttpServletResponse response)
                                                                      throws Exception{
        String path = this.view.getPath();
        if (view.isRedirected()){
            response.sendRedirect(path);
        } else{
            request.getRequestDispatcher(path).forward(request, response);
        }
    }
}
