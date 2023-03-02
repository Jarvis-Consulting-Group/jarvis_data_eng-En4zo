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


}
