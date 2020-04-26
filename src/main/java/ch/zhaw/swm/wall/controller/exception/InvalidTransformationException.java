package ch.zhaw.swm.wall.controller.exception;

import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;

import java.text.MessageFormat;

public class InvalidTransformationException extends RuntimeException {

    public InvalidTransformationException(String entityName, Relationship relationship, RelationshipStatus status) {
        super(MessageFormat.format("invalid transformation on {0} id {1} to {2}", entityName, relationship.getId(), status));
    }

}
