
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import jakarta.json.bind.annotation.JsonbProperty;

public class Task extends PanacheMongoEntity{
    @JsonbProperty("id") // Include the `id` field in GET responses
    // @JsonbTransient // Exclude the `id` field from POST requests
    public ObjectId id;
    public String title;
    public String description;
    public LocalDateTime dueDate;
    public boolean completed;
}