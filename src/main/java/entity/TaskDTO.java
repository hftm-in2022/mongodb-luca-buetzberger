package entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class TaskDTO{
    String id;
    @NotBlank @Size(max= 50)
    private String title;
    @Size(max = 500)
    private String description;
    private LocalDateTime dueDate;
    private boolean completed;
    // private UserDTO user;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // public UserDTO getUser() {
    //     return user;
    // }

    // public void setUser (UserDTO user) {
    //     this.user = user;
    // }
}