package ch.zhaw.swm.wall.model.person;

import ch.zhaw.swm.wall.model.AbstractDocument;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

public class Relationship extends AbstractDocument {

    public static final String ENTITY_NAME = "relationship";

    @NotNull
    @Indexed
    private String requestingPersonId;

    @NotNull
    @Indexed
    private String requestedPersonId;

    @NotNull
    private RelationshipStatus status;

    @PersistenceConstructor
    public Relationship(String requestingPersonId, String requestedPersonId, RelationshipStatus status) {
        this.requestingPersonId = requestingPersonId;
        this.requestedPersonId = requestedPersonId;
        this.status = status;
    }

    public Relationship(RelationshipCreation relationshipCreation) {
        this.requestingPersonId = relationshipCreation.getRequestingPersonId();
        this.requestedPersonId = relationshipCreation.getRequestedPersonId();
        this.status = RelationshipStatus.PENDING;
    }

    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    public String getRequestedPersonId() {
        return requestedPersonId;
    }

    public void setRequestedPersonId(String requestedPersonId) {
        this.requestedPersonId = requestedPersonId;
    }

    public RelationshipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }

    public Relationship createReverseRelationship() {
        return new Relationship(this.requestedPersonId, this.requestingPersonId, this.status);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
