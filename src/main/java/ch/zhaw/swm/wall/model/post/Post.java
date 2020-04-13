package ch.zhaw.swm.wall.model.post;

import ch.zhaw.swm.wall.model.AbstractDocument;
import ch.zhaw.swm.wall.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.text.MessageFormat;

public class Post extends AbstractDocument {

    public static final String ENTITY_NAME = "post";

    @Indexed
    private String topicId;

    @Indexed
    private String personId;

    private String title;
    private Status status = Status.ACTIVE;
    private final PostType postType;

    protected Post(PostType postType) {
        super();
        this.postType = postType;
    }

    protected Post(String postId, PostType postType) {
        super(postId);
        this.postType = postType;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public PostType getPostType() {
        return postType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
