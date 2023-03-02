package ca.jrvs.apps.twitter.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "full_name",
        "id"
})
public class Coordinates {
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("id")
    private String id;

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

    @Override
    public String toString() {
        return "Coordinates{" +
                "fullName='" + fullName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
