package ca.jrvs.apps.twitter.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
        "properties"
})
@JsonPropertyOrder({
        "type",
        "bbox",
        "properties",
        "place_id"

})
public class Geo {

    @JsonProperty("type")
    private String type;
    @JsonProperty("bbox")
    private ArrayList<Double> bbox;
    @JsonProperty("properties")
    private Properties properties;
    @JsonProperty("place_id")
    private String placeId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Double> getBbox() {
        return bbox;
    }

    public void setBbox(ArrayList<Double> bbox) {
        this.bbox = bbox;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "type='" + type + '\'' +
                ", bbox=" + bbox +
                ", properties=" + properties +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
