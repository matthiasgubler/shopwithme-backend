package ch.zhaw.swm.wall.model.person;

import javax.validation.constraints.NotNull;

public class RelationshipCreation {

    @NotNull
    private String requestingPersonId;

    @NotNull
    private String requestedPersonId;

    public RelationshipCreation(String requestingPersonId, String requestedPersonId) {
        this.requestingPersonId = requestingPersonId;
        this.requestedPersonId = requestedPersonId;
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

}
