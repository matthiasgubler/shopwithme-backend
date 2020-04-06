package ch.zhaw.swm.wall.repository;

import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends MongoRepository<Relationship, String> {

    Optional<Relationship> findByRequestingPersonIdAndRequestedPersonId(String requestingPersonId, String requestedPersonId);

    List<Relationship> findByRequestingPersonId(String requestingPersonId);

    List<Relationship> findByRequestedPersonId(String requestingPersonId);

    List<Relationship> findByRequestingPersonIdAndStatus(String requestingPersonId, RelationshipStatus status);

    List<Relationship> findByRequestedPersonIdAndStatus(String requestingPersonId, RelationshipStatus status);


}
