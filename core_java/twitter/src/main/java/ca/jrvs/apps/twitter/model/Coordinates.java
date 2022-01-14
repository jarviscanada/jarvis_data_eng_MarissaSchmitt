package ca.jrvs.apps.twitter.model;

import java.util.Collection;

public class Coordinates {
    Collection<Float> coordinates;

    String type;


    public Collection<Float> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Collection<Float> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
