package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "places"
})
public class Includes {
    private ArrayList<Place> places;

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Includes{" +
                "places=" + places +
                '}';
    }
}
