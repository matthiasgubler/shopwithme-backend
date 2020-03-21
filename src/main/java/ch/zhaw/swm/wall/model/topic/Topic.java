package ch.zhaw.swm.wall.model.topic;

import ch.zhaw.swm.wall.model.AbstractDocument;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigInteger;

public class Topic extends AbstractDocument {
    private String title;

    @Indexed
    private BigInteger personId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getPersonId() {
        return personId;
    }

    public void setPersonId(BigInteger personId) {
        this.personId = personId;
    }
}
