package ch.zhaw.swm.wall.controller.exception;

import java.text.MessageFormat;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String personId, String topicid) {
        super(MessageFormat.format("Person with id {0} not authorized to post in Topic: {1}", personId, topicid));
    }

}
