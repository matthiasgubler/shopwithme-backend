package ch.zhaw.swm.wall.model.person;

public class AcceptFriendshipStatus {

    public AcceptFriendshipStatus() {
    }

    private RelationshipStatus status;

    public AcceptFriendshipStatus(RelationshipStatus status) {
        this.status = status;
    }

    public RelationshipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }

}
