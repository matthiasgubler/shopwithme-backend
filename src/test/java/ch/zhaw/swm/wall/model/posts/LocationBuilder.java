package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.Location;

public class LocationBuilder {

    private String topicId;
    private String personId;
    private String title;

    private LocationBuilder() {
    }

    public static LocationBuilder aLocation() {
        return new LocationBuilder();
    }

    public LocationBuilder withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    public LocationBuilder withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public LocationBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public Location build() {
        Location location = new Location();
        location.setTopicId(topicId);
        location.setPersonId(personId);
        location.setTitle(title);
        return location;
    }

}
