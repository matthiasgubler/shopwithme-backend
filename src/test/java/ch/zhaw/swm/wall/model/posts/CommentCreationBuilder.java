package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.CommentCreation;

public class CommentCreationBuilder {

    private String topicId;
    private String personId;
    private String content;

    private CommentCreationBuilder() {
    }

    public static CommentCreationBuilder aComment() {
        return new CommentCreationBuilder();
    }

    public CommentCreationBuilder withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    public CommentCreationBuilder withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public CommentCreationBuilder withComment(String content) {
        this.content = content;
        return this;
    }

    public CommentCreation build() {
        CommentCreation comment = new CommentCreation();
        comment.setTopicId(topicId);
        comment.setPersonId(personId);
        comment.setMessage(content);
        return comment;
    }

}
