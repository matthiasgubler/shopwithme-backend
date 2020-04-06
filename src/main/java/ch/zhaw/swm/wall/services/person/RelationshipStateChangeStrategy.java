package ch.zhaw.swm.wall.services.person;

import ch.zhaw.swm.wall.model.person.Relationship;

public interface RelationshipStateChangeStrategy {

    void performStateChange(Relationship relationship);

}
