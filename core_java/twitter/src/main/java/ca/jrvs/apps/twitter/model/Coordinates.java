package ca.jrvs.apps.twitter.model;

import java.util.Collection;
import java.util.List;

public class Coordinates {
    //Collection<Float> coordinates;
    List<Float> coordinates;

    String type;


    public List<Float> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Float> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
