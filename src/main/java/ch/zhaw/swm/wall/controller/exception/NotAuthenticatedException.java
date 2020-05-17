package ch.zhaw.swm.wall.controller.exception;

public class NotAuthenticatedException extends RuntimeException {

    public NotAuthenticatedException(Exception e) {
        super("Authentication failed", e);
    }
}
