package ch.zhaw.swm.wall.services.person.impl;

import ch.zhaw.swm.wall.contoller.exception.EntityConflictException;
import ch.zhaw.swm.wall.contoller.exception.InvalidTransformationException;
import ch.zhaw.swm.wall.contoller.exception.NotFoundException;
import ch.zhaw.swm.wall.model.person.PersonBuilder;
import ch.zhaw.swm.wall.model.person.Relationship;
import ch.zhaw.swm.wall.model.person.RelationshipCreation;
import ch.zhaw.swm.wall.model.person.RelationshipStatus;
import ch.zhaw.swm.wall.repository.RelationshipRepository;
import ch.zhaw.swm.wall.services.person.PersonService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class RelationshipServiceImplTest {

    private static final String FIRST_PERSON_ID = "1";
    private static final String SECOND_PERSON_ID = "2";

    private static final RelationshipStatus PENDING = RelationshipStatus.PENDING;
    private static final RelationshipStatus ACCEPTED = RelationshipStatus.ACCEPTED;

    @Mock
    private PersonService personServiceMock;

    @Mock
    private RelationshipRepository relationshipRepositoryMock;

    @InjectMocks
    private RelationshipServiceImpl relationshipServiceImpl;

    @Mock
    private RelationshipStateChangeStrategyProvider relationshipStateChangeStrategyProvider;

    @Test
    void createRelationship() {
        Relationship relationshipToSave = new Relationship(FIRST_PERSON_ID, SECOND_PERSON_ID, PENDING);
        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(SECOND_PERSON_ID).build()));
        when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        when(relationshipRepositoryMock.save(Mockito.any())).thenReturn(relationshipToSave);

        RelationshipCreation relationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
        Relationship result = relationshipServiceImpl.createRelationship(relationship);
        Assertions.assertEquals(relationshipToSave, result);
    }

    @Test
    void createRelationship_first_person_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.empty());

            RelationshipCreation relationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
            relationshipServiceImpl.createRelationship(relationship);
        }, "person id not found : 1");
    }

    @Test
    void createRelationship_second_person_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
            when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.empty());

            RelationshipCreation relationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
            relationshipServiceImpl.createRelationship(relationship);
        }, "person id not found : 2");
    }

    @Test
    void createRelationship_both_persons_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.empty());
            when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.empty());
            RelationshipCreation relationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
            relationshipServiceImpl.createRelationship(relationship);
        }, "person id not found : 1");
    }

    @Test
    void createRelationship_relationship_pending_exists() {
        RelationshipCreation existingRelationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
        Relationship relationship = new Relationship(existingRelationship);

        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(SECOND_PERSON_ID).build()));
        when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(FIRST_PERSON_ID, SECOND_PERSON_ID)).thenReturn(Optional.of(relationship));

        Relationship result = relationshipServiceImpl.createRelationship(existingRelationship);
        Assertions.assertEquals(relationship, result);
    }

    @Test
    void createRelationship_reverse_relationship_pending_exists() {
        RelationshipCreation relationshipCreation = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
        Relationship existingRelationshipReverse = new Relationship(relationshipCreation).createReverseRelationship();

        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(SECOND_PERSON_ID).build()));
        when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(FIRST_PERSON_ID, SECOND_PERSON_ID)).thenReturn(Optional.empty());
        when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(SECOND_PERSON_ID, FIRST_PERSON_ID)).thenReturn(Optional.of(existingRelationshipReverse));

        Relationship result = relationshipServiceImpl.createRelationship(relationshipCreation);
        Assertions.assertEquals(existingRelationshipReverse, result);
    }

    @Test
    void createRelationship_relationship_accepted_exists() {
        Assertions.assertThrows(EntityConflictException.class, () -> {
            RelationshipCreation relationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
            Relationship existingRelationship = new Relationship(FIRST_PERSON_ID, SECOND_PERSON_ID, ACCEPTED);

            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
            when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(SECOND_PERSON_ID).build()));
            when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(FIRST_PERSON_ID, SECOND_PERSON_ID)).thenReturn(Optional.of(existingRelationship));

            relationshipServiceImpl.createRelationship(relationship);
        });
    }

    @Test
    void createRelationship_reverse_relationship_accepted_exists() {
        Assertions.assertThrows(EntityConflictException.class, () -> {
            RelationshipCreation relationship = new RelationshipCreation(FIRST_PERSON_ID, SECOND_PERSON_ID);
            Relationship existingRelationship = new Relationship(SECOND_PERSON_ID, FIRST_PERSON_ID, ACCEPTED);

            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
            when(personServiceMock.findById(SECOND_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(SECOND_PERSON_ID).build()));
            when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(FIRST_PERSON_ID, SECOND_PERSON_ID)).thenReturn(Optional.empty());
            when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(SECOND_PERSON_ID, FIRST_PERSON_ID)).thenReturn(Optional.of(existingRelationship));

            relationshipServiceImpl.createRelationship(relationship);
        });

    }

    @Test
    void deleteRelationship_no_reverse_relationship() {
        Relationship relationshipToDelete = new Relationship(FIRST_PERSON_ID, SECOND_PERSON_ID, PENDING);

        when(relationshipRepositoryMock.findById("1")).thenReturn(Optional.of(relationshipToDelete));
        when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(SECOND_PERSON_ID, FIRST_PERSON_ID)).thenReturn(Optional.empty());

        relationshipServiceImpl.deleteRelationship("1");
        verify(relationshipRepositoryMock).delete(relationshipToDelete);
    }

    @Test
    void deleteRelationship_with_reverse_relationship() {
        //TODO
        Relationship relationshipToDelete = new Relationship(FIRST_PERSON_ID, SECOND_PERSON_ID, ACCEPTED);
        Relationship relationshipReverseToDelete = relationshipToDelete.createReverseRelationship();

        when(relationshipRepositoryMock.findById("1")).thenReturn(Optional.of(relationshipToDelete));
        when(relationshipRepositoryMock.findByRequestingPersonIdAndRequestedPersonId(SECOND_PERSON_ID, FIRST_PERSON_ID)).thenReturn(Optional.of(relationshipReverseToDelete));

        relationshipServiceImpl.deleteRelationship("1");
        verify(relationshipRepositoryMock).delete(relationshipToDelete);
        verify(relationshipRepositoryMock).delete(relationshipReverseToDelete);
    }

    @Test
    void findByRequestingPersonId_person_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.empty());
            relationshipServiceImpl.findByRequestingPersonId(FIRST_PERSON_ID);
        }, "person id not found : 1");
    }

    @Test
    void findByRequestingPersonId() {
        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        relationshipServiceImpl.findByRequestingPersonId(FIRST_PERSON_ID);
        verify(relationshipRepositoryMock).findByRequestingPersonId(FIRST_PERSON_ID);
    }

    @Test
    void findByRequestedPersonId_person_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.empty());
            relationshipServiceImpl.findByRequestedPersonId(FIRST_PERSON_ID);
        }, "person id not found : 1");
    }

    @Test
    void findByRequestedPersonId() {
        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        relationshipServiceImpl.findByRequestedPersonId(FIRST_PERSON_ID);
        verify(relationshipRepositoryMock).findByRequestedPersonId(FIRST_PERSON_ID);
    }

    @Test
    void findByRequestingPersonIdAndStatus_person_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.empty());
            relationshipServiceImpl.findByRequestingPersonIdAndStatus(FIRST_PERSON_ID, PENDING);
        }, "person id not found : 1");
    }

    @Test
    void findByRequestingPersonIdAndStatus() {
        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        relationshipServiceImpl.findByRequestingPersonIdAndStatus(FIRST_PERSON_ID, PENDING);
        verify(relationshipRepositoryMock).findByRequestingPersonIdAndStatus(FIRST_PERSON_ID, PENDING);
    }

    @Test
    void findByRequestedPersonIdAndStatus_person_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.empty());
            relationshipServiceImpl.findByRequestedPersonIdAndStatus(FIRST_PERSON_ID, PENDING);
        }, "person id not found : 1");
    }

    @Test
    void findByRequestedPersonIdAndStatus() {
        when(personServiceMock.findById(FIRST_PERSON_ID)).thenReturn(Optional.of(PersonBuilder.aPerson().withId(FIRST_PERSON_ID).build()));
        relationshipServiceImpl.findByRequestedPersonIdAndStatus(FIRST_PERSON_ID, PENDING);
        verify(relationshipRepositoryMock).findByRequestedPersonIdAndStatus(FIRST_PERSON_ID, PENDING);
    }

    @Test
    void acceptFriendship() {
        Relationship relationship = new Relationship(FIRST_PERSON_ID, SECOND_PERSON_ID, PENDING);
        when(relationshipRepositoryMock.findById("1")).thenReturn(Optional.of(relationship));
        when(relationshipStateChangeStrategyProvider.getStrategy(RelationshipStatus.ACCEPTED)).thenReturn(new AcceptStateStrategy(relationshipRepositoryMock));

        relationshipServiceImpl.acceptFriendship("1", ACCEPTED);
        verify(relationshipRepositoryMock, times(2)).save(Mockito.any());
        Assertions.assertEquals(RelationshipStatus.ACCEPTED, relationship.getStatus());

    }

    @Test
    void acceptFriendship_relationship_not_found() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            when(relationshipRepositoryMock.findById("1")).thenReturn(Optional.empty());
            relationshipServiceImpl.acceptFriendship("1", ACCEPTED);
        });
    }

    @Test
    void acceptFriendship_invalid_transformation() {
        Assertions.assertThrows(InvalidTransformationException.class, () -> {
            Relationship relationship = new Relationship(FIRST_PERSON_ID, SECOND_PERSON_ID, ACCEPTED);
            when(relationshipRepositoryMock.findById("1")).thenReturn(Optional.of(relationship));
            when(relationshipStateChangeStrategyProvider.getStrategy(RelationshipStatus.PENDING)).
                thenReturn(new PendingStateStrategy());
            relationshipServiceImpl.acceptFriendship("1", RelationshipStatus.PENDING);
        });
    }

}
