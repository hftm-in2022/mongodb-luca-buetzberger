
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Task extends PanacheMongoEntity{
    
    @JsonIgnore
    public ObjectId id;
    public String title;
    public String description;
    public LocalDateTime dueDate;
    public boolean completed;
}