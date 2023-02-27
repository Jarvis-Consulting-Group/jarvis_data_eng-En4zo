package ca.jrvs.apps.twitter.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coordinates",
        "type"
})
public class Coordinates {
    @JsonProperty("coordinates")
    private float coordinates;
    @JsonProperty("type")
    private String type;

    public float getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
