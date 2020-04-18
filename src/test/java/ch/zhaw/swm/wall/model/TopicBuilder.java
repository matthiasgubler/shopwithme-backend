package ch.zhaw.swm.wall.model;

import ch.zhaw.swm.wall.model.topic.Topic;

public class TopicBuilder {

    private String topicId;
    private String title;
    private String personId;

    private TopicBuilder() {
    }

    public static TopicBuilder aTopic() {
        return new TopicBuilder();
    }

    public TopicBuilder withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    public TopicBuilder withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public TopicBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public Topic build() {
        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setPersonId(personId);
        topic.setTitle(title);
        return topic;
    }

}
