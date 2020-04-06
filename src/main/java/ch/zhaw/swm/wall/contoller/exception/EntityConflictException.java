package ch.zhaw.swm.wall.contoller.exception;

import java.text.MessageFormat;

public class EntityConflictException extends RuntimeException {

    public EntityConflictException(String entityName, String operation, Object object) {
        super(MessageFormat.format("{0} entity conflict: {1} {2}", entityName, operation, object));
    }

}
