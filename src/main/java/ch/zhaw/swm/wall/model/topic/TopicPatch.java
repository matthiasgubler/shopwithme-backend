package ch.zhaw.swm.wall.model.topic;

import ch.zhaw.swm.wall.model.Status;
import org.apache.commons.lang3.StringUtils;


public class TopicPatch {

    private String title;

    private String description;

    private Status status = Status.ACTIVE;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic apply(Topic topicToApply) {
        if (!StringUtils.isEmpty(this.getTitle())) {
            topicToApply.setTitle(this.getTitle());
        }

        if (!StringUtils.isEmpty(this.getDescription())) {
            topicToApply.setDescription(this.getDescription());
        }

        if (this.getStatus() != null) {
            topicToApply.setStatus(this.getStatus());
        }
        return topicToApply;
    }

}
