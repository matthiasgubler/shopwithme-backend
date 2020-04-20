package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.CommentCreation;

public class CommentCreationBuilder {

    private String topicId;
    private String personId;
    private String message;

    private CommentCreationBuilder() {
    }

    public static CommentCreationBuilder aCommentCreation() {
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

    public CommentCreationBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public CommentCreation build() {
        CommentCreation commentCreation = new CommentCreation();
        commentCreation.setTopicId(topicId);
        commentCreation.setPersonId(personId);
        commentCreation.setMessage(message);
        return commentCreation;
    }

}
