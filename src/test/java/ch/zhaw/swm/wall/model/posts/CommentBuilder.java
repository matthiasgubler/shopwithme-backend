package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.Comment;

public class CommentBuilder {

    private String postId;
    private String topicId;
    private String personId;
    private String message;

    private CommentBuilder() {
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder withPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public CommentBuilder withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    public CommentBuilder withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public CommentBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setId(postId);
        comment.setTopicId(topicId);
        comment.setPersonId(personId);
        comment.setMessage(message);
        return comment;
    }

}
