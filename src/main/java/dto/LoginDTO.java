package dto;

import validation.Password;
import validation.Username;

import javax.servlet.http.HttpServletRequest;

public class LoginDTO {

    @Username
    private String username;
    @Password
    private String password;

    public LoginDTO(HttpServletRequest request){
        decompose(request);
    }

    private void decompose(HttpServletRequest request){
        username = request.getParameter("username");
        password = request.getParameter("password");
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
}
