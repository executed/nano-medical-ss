package dto;

import validation.Email;
import validation.Password;
import validation.Username;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

public class SignupDTO {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email
    private String email;
    @Username
    private String username;
    @Password
    private String password;

    public SignupDTO(HttpServletRequest request){
        decompose(request);
    }

    private void decompose(HttpServletRequest request){
        firstName = request.getParameter("first_name");
        lastName = request.getParameter("last_name");
        username = request.getParameter("username");
        email = request.getParameter("email");
        password = request.getParameter("password");
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
}
