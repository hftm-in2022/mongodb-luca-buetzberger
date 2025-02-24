package entity;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;


public class Task extends PanacheMongoEntity{
    private ObjectId id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean completed;
    private List<ObjectId> assignedUsers;

    // Getters and Setters
    public ObjectId getId() {
        return this.id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<ObjectId> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<ObjectId> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}