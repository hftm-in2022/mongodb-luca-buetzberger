package exception;

import jakarta.ws.rs.core.Response;

public abstract class BaseException extends RuntimeException {
    private final Response.Status status;

    public BaseException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}