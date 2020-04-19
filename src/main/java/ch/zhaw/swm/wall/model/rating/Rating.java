package ch.zhaw.swm.wall.model.rating;

import ch.zhaw.swm.wall.model.AbstractDocument;
import org.springframework.data.mongodb.core.index.Indexed;

public class Rating extends AbstractDocument {

    @Indexed
    private String personId;

    @Indexed
    private String postId;

    private RatingType ratingType;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
