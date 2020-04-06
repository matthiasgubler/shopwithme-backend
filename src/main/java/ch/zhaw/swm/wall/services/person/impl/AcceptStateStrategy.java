package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import ch.zhaw.swm.wall.services.person.RelationshipStateChangeStrategy;

public class AcceptStateStrategy implements RelationshipStateChangeStrategy {

    private RelationshipRepository relationshipRepository;

    public AcceptStateStrategy(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    @Override
    public void performStateChange(Relationship relationship) {
        relationship.setStatus(RelationshipStatus.ACCEPTED);
        relationshipRepository.save(relationship);
        relationshipRepository.save(relationship.createReverseRelationship());
    }

}
