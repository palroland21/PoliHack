package polihack.backend.dto.response;

public class LoginResponse {
    private String token;
    private Long id; // NOU: ID-ul utilizatorului/companiei

    // Constructor
    public LoginResponse(String token, Long id) {
        this.token = token;
        this.id = id;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    // Setters (opțional, dar recomandat)
    public void setToken(String token) {
        this.token = token;
    }

    public void setId(Long id) {
        this.id = id;
    }
}