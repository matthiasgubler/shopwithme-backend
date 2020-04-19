package ch.zhaw.swm.wall.model.post;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Location extends Post {

    public static final String ENTITY_NAME = "location";

    @GeoSpatialIndexed(name = "location")
    private Double[] location;

    public Location() {
        super(PostType.LOCATION);
    }

    public Double[] getLocation() {
        return location;
    }

    public void setLocation(Double[] location) {
        this.location = location;
    }
}
