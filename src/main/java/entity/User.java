package entity;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class User extends PanacheMongoEntity{
    private ObjectId id;
    private String username;

    // Getters and Setters
    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
