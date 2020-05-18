package ch.zhaw.swm.wall.model.post;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

public class Location extends Post {

    public static final String ENTITY_NAME = "location";

    @GeoSpatialIndexed(name = "coordinates")
    private Double[] coordinates;

    public Location() {
        super(PostType.LOCATION);
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
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
