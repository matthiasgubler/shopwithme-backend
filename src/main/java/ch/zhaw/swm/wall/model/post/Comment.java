package ch.zhaw.swm.wall.model.post;

public class Comment extends Post {

    public static final String ENTITY_NAME = "comment";

    private String comment;

    public Comment() {
        super(PostType.COMMENT);
    }

    public Comment(String postId) {
        super(postId, PostType.COMMENT);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
