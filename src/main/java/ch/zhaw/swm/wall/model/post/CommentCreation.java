package ch.zhaw.swm.wall.model.post;

import javax.validation.constraints.NotNull;

public class CommentCreation extends PostCreation {

    @NotNull
    private String message;

    public CommentCreation() {
        super();
    }

    public CommentCreation(String message) {
        super();
        this.message = message;
    }

    public CommentCreation(String topicId, String personId, String message) {
        super(topicId, personId);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
