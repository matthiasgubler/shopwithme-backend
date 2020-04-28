package ch.zhaw.swm.wall.model.topic;

import ch.zhaw.swm.wall.model.AbstractDocument;
import ch.zhaw.swm.wall.model.Status;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

public class Topic extends AbstractDocument {
    public static final String ENTITY_NAME = "topic";

    @NotNull
    private String title;

    private String description;

    @NotNull
    @Indexed
    private String personId;

    private Status status = Status.ACTIVE;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
