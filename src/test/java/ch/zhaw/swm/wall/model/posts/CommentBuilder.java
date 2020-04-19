package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.Comment;

import java.time.LocalDateTime;

public class CommentBuilder {

    private String commentId;
    private LocalDateTime createDateTime;
    private String topicId;
    private String personId;
    private String message;

    private CommentBuilder() {
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder withCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public CommentBuilder withCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
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
        comment.setId(commentId);
        comment.setCreateDateTime(createDateTime);
        comment.setTopicId(topicId);
        comment.setPersonId(personId);
        comment.setMessage(message);
        return comment;
    }

}
