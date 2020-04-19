package ch.zhaw.swm.wall.model.post;

import ch.zhaw.swm.wall.model.AbstractDocument;
import ch.zhaw.swm.wall.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class Post extends AbstractDocument {

    public static final String ENTITY_NAME = "post";

    @Indexed
    private String topicId;

    @Indexed
    private String personId;

    @NotNull
    private PostType postType;

    private Status status = Status.ACTIVE;

    public Post(String topicId, String personId, PostType postType) {
        this.topicId = topicId;
        this.personId = personId;
        this.postType = postType;
    }

    protected Post(PostType postType) {
        this.postType = postType;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
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
        structure.setType(this.getPostType());
        structure.setLink(MessageFormat.format("/posts/{0}", this.getId()));
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
