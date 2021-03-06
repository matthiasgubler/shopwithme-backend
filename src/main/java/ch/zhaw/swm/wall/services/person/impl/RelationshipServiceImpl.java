package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.controller.exception.EntityConflictException;
import ch.zhaw.swm.wall.model.person.Person;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipCreation;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import ch.zhaw.swm.wall.services.EntityIdHandler;
import ch.zhaw.swm.wall.services.person.PersonService;
import ch.zhaw.swm.wall.services.person.RelationshipService;
import ch.zhaw.swm.wall.services.person.RelationshipStateChangeStrategy;

import java.util.List;
import java.util.Optional;

public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final PersonService personService;
    private final RelationshipStateChangeStrategyProvider relationshipStateChangeStrategyProvider;

    private final EntityIdHandler entityIdHandler = new EntityIdHandler();

    public RelationshipServiceImpl(RelationshipRepository relationshipRepository,
                                   PersonService personService,
                                   RelationshipStateChangeStrategyProvider relationshipStateChangeStrategyProvider) {
        this.relationshipRepository = relationshipRepository;
        this.personService = personService;
        this.relationshipStateChangeStrategyProvider = relationshipStateChangeStrategyProvider;
    }

    @Override
    public Optional<Relationship> findById(String relationshipId) {
        return relationshipRepository.findById(relationshipId);
    }

    @Override
    public Relationship createRelationship(RelationshipCreation relationship) {
        String requestingPersonId = relationship.getRequestingPersonId();
        String requestedPersonId = relationship.getRequestedPersonId();

        entityIdHandler.checkExisting(Person.ENTITY_NAME, requestingPersonId, personService::findById);
        entityIdHandler.checkExisting(Person.ENTITY_NAME, requestedPersonId, personService::findById);

        Relationship relationshipToSave = new Relationship(relationship);

        Optional<Relationship> optionalRelationship = relationshipRepository.findByRequestingPersonIdAndRequestedPersonId(requestingPersonId, requestedPersonId);
        Optional<Relationship> optionalRelationshipReverse = relationshipRepository.findByRequestingPersonIdAndRequestedPersonId(requestedPersonId, requestingPersonId);

        if (!optionalRelationship.isPresent() && !optionalRelationshipReverse.isPresent()) {
            return relationshipRepository.save(relationshipToSave);
        }
        return optionalRelationship.filter(el -> el.getStatus() == RelationshipStatus.PENDING).
            orElseGet(() -> optionalRelationshipReverse.filter(elReverse -> elReverse.getStatus() == RelationshipStatus.PENDING).
                orElseThrow(() -> new EntityConflictException(Relationship.ENTITY_NAME, "createRelationship", relationship)));
    }

    @Override
    public void deleteRelationship(String relationshipId) {
        Relationship relationship = getRelationshipById(relationshipId);

        relationshipRepository
            .findByRequestingPersonIdAndRequestedPersonId(relationship.getRequestedPersonId(),
                relationship.getRequestingPersonId()).ifPresent(relationshipRepository::delete);

        relationshipRepository.delete(relationship);
    }

    @Override
    public List<Relationship> findByRequestingPersonId(String requestingPersonId) {
        entityIdHandler.checkExisting(Person.ENTITY_NAME, requestingPersonId, personService::findById);
        return relationshipRepository.findByRequestingPersonId(requestingPersonId);
    }

    @Override
    public List<Relationship> findByRequestedPersonId(String requestedPersonId) {
        entityIdHandler.checkExisting(Person.ENTITY_NAME, requestedPersonId, personService::findById);
        return relationshipRepository.findByRequestedPersonId(requestedPersonId);
    }

    @Override
    public List<Relationship> findByRequestingPersonIdAndStatus(String requestingPersonId, RelationshipStatus status) {
        entityIdHandler.checkExisting(Person.ENTITY_NAME, requestingPersonId, personService::findById);
        return relationshipRepository.findByRequestingPersonIdAndStatus(requestingPersonId, status);
    }

    @Override
    public List<Relationship> findByRequestedPersonIdAndStatus(String requestedPersonId, RelationshipStatus status) {
        entityIdHandler.checkExisting(Person.ENTITY_NAME, requestedPersonId, personService::findById);
        return relationshipRepository.findByRequestedPersonIdAndStatus(requestedPersonId, status);
    }

    @Override
    public void acceptFriendship(String relationshipId, RelationshipStatus status) {
        Relationship relationship = getRelationshipById(relationshipId);
        if (relationship.getStatus() != status) {
            RelationshipStateChangeStrategy stateChangeStrategy = relationshipStateChangeStrategyProvider.getStrategy(status);
            stateChangeStrategy.performStateChange(relationship);
        }
    }

    public Relationship getRelationshipById(String relationshipId) {
        return entityIdHandler.handle(Relationship.ENTITY_NAME, relationshipId, relationshipRepository::findById, relationship -> relationship);
    }

}
