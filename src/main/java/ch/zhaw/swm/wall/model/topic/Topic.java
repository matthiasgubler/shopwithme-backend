package ch.zhaw.swm.wall.model.topic;

import ch.zhaw.swm.wall.model.AbstractDocument;
import org.springframework.data.mongodb.core.index.Indexed;


public class Topic extends AbstractDocument {
    private String title;

    @Indexed
    private String personId;

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
}
