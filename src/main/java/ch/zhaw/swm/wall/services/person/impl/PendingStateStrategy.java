package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.contoller.exception.InvalidTransformationException;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.services.person.RelationshipStateChangeStrategy;

public class PendingStateStrategy implements RelationshipStateChangeStrategy {

    @Override
    public void performStateChange(Relationship relationship) {
        throw new InvalidTransformationException(Relationship.ENTITY_NAME, relationship, RelationshipStatus.PENDING);
    }

}
