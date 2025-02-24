package entity;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private String id;
    @NotBlank
    private String username;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
