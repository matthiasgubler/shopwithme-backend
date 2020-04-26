package ch.zhaw.swm.wall.controller.exception;


import java.text.MessageFormat;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException(String entityName, Object object) {
        super(MessageFormat.format("{0} entity already exists: {1}", entityName, object));
    }

}
