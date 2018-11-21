package dto;

import entity.Client;

import javax.servlet.http.HttpServletRequest;

public class ClientProfileInputDTO implements UserProfileInputDTO{

    private HttpServletRequest request;
    private Client client;

    public ClientProfileInputDTO(HttpServletRequest request){
        decompose(request);
    }

    @Override
    public void decompose(HttpServletRequest req){
        assignIfAbsent(request);
        this.client = (Client) req.getSession().getAttribute("user");
    }

    private void assignIfAbsent(HttpServletRequest request){
        if (this.request == null) this.request = request;
        else throw new IllegalArgumentException("Request was already decomposed : " + request);
    }

    public Client getClient(){ return this.client; }

    @Override
    public String getClassName() { return this.getClass().getName(); }
}
