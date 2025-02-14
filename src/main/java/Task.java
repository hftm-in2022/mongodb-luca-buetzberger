
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.jackson.ObjectIdSerializer;

public class Task extends PanacheMongoEntity{
    
    @JsonSerialize(using = ObjectIdSerializer.class) // Serialize ObjectId as a string
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Include in GET responses but exclude in POST requests
    public ObjectId id;
    
    public String title;
    public String description;
    public LocalDateTime dueDate;
    public boolean completed;
}