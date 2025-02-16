package exception;

import jakarta.ws.rs.core.Response;

public class DatabaseException extends BaseException {
    public DatabaseException(String message) {
        super(Response.Status.INTERNAL_SERVER_ERROR, message);
    }
}