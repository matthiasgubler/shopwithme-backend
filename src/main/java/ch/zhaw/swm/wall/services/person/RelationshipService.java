package ch.zhaw.swm.wall.services.person;

import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipCreation;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;

import java.util.List;
import java.util.Optional;

public interface RelationshipService {

    Optional<Relationship> findById(String relationshipId);

    Relationship createRelationship(RelationshipCreation relationship);

    void deleteRelationship(String relationshipId);

    List<Relationship> findByRequestingPersonId(String personId);

    List<Relationship> findByRequestedPersonId(String personId);

    List<Relationship> findByRequestingPersonIdAndStatus(String personId, RelationshipStatus status);

    List<Relationship> findByRequestedPersonIdAndStatus(String personId, RelationshipStatus status);

    void acceptFriendship(String relationshipId, RelationshipStatus status);

}
