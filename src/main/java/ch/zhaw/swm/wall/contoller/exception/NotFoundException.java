package ch.zhaw.swm.wall.contoller.exception;


import java.text.MessageFormat;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityName, String id) {
        super(MessageFormat.format("{0} id not found : {1}", entityName, id));
    }

}
