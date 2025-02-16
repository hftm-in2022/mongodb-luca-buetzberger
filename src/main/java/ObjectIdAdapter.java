import org.bson.types.ObjectId;

import jakarta.json.bind.adapter.JsonbAdapter;

public class ObjectIdAdapter implements JsonbAdapter<ObjectId, String> {

    @Override
    public String adaptToJson(ObjectId obj) {
        return obj != null ? obj.toHexString() : null; // Convert ObjectId to String
    }

    @Override
    public ObjectId adaptFromJson(String obj) {
        return obj != null ? new ObjectId(obj) : null; // Convert String to ObjectId
    }
}