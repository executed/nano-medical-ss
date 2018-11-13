package configuration;

import java.util.UUID;

public class ClientConfiguration {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private boolean admin;

    public ClientConfiguration(UUID id, String username, String email, String password, boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }
}
