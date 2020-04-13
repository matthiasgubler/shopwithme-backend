package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.Comment;

public class CommentBuilder {

    private String topicId;
    private String personId;
    private String title;
    private String content;

    private CommentBuilder() {
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    public CommentBuilder withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public CommentBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CommentBuilder withComment(String content) {
        this.content = content;
        return this;
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setTopicId(topicId);
        comment.setPersonId(personId);
        comment.setTitle(title);
        comment.setComment(content);
        return comment;
    }

}
