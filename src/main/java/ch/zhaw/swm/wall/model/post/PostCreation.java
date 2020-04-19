package ch.zhaw.swm.wall.model.post;

import javax.validation.constraints.NotNull;

public class PostCreation {

    @NotNull
    private String topicId;

    @NotNull
    private String personId;

    public PostCreation() {
    }

    public PostCreation(String topicId, String personId) {
        this.topicId = topicId;
        this.personId = personId;
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

}
