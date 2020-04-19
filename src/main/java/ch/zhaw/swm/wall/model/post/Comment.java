package ch.zhaw.swm.wall.model.post;

public class Comment extends Post {

    public static final String ENTITY_NAME = "comment";

    private String message;

    public Comment(CommentCreation comment) {
        super(comment.getTopicId(), comment.getPersonId(), PostType.COMMENT);
        this.message = comment.getMessage();
    }

    public Comment() {
        super(PostType.COMMENT);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
