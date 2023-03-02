package ca.jrvs.apps.twitter.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "place_id",
        "full_name",
        "id",
        "type",
        "bbox",
        "properties",

})
public class Geo {
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;
    @JsonProperty("bbox")
    private float[] bbox;
    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("properties")
    private String properties;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float[] getBbox() {
        return bbox;
    }

    public void setBbox(float[] bbox) {
        this.bbox = bbox;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "geo{" +
                "fullName='" + fullName + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", bbox=" + Arrays.toString(bbox) +
                ", placeId='" + placeId + '\'' +
                ", properties='" + properties + '\'' +
                '}';
    }
}
