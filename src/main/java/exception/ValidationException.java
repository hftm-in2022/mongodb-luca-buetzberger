package exception;

import jakarta.ws.rs.core.Response;

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }
}