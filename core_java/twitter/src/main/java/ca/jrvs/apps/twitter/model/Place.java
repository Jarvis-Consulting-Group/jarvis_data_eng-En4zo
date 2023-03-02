package ca.jrvs.apps.twitter.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "geo"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {
    private Geo place;

    public Geo getPlace() {
        return place;
    }

    public void setPlace(Geo place) {
        this.place = place;
    }


}
