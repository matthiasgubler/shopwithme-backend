package ch.zhaw.swm.wall.model.post;

import ch.zhaw.swm.wall.model.AbstractDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigInteger;
import java.text.MessageFormat;

public class Post extends AbstractDocument {
    @Indexed
    private BigInteger topicId;

    @Indexed
    private BigInteger personId;

    private String title;

    private final PostType postType;

    protected Post(PostType postType) {
        this.postType = postType;
    }

    public BigInteger getTopicId() {
        return topicId;
    }

    public void setTopicId(BigInteger topicId) {
        this.topicId = topicId;
    }

    public PostType getPostType() {
        return postType;
    }

    public BigInteger getPersonId() {
        return personId;
    }

    public void setPersonId(BigInteger personId) {
        this.personId = personId;
    }

    public PostSource getPostSource() {
        if (personId != null) {
            return PostSource.PERSON;
        } else if (topicId != null) {
            return PostSource.TOPIC;
        }
        return PostSource.UNKNOWN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    @Transient
    public PostStructure createStructure() {
        PostStructure structure = new PostStructure();
        structure.setId(this.getId());
        structure.setTitle(this.getTitle());
        structure.setType(this.getPostType());
        structure.setLink(MessageFormat.format("/posts/{0}/{1}",
            this.getPostType().name().toLowerCase(), this.getId()));
        return structure;
    }
}
