package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "start",
        "end",
        "tag"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mention {
    @JsonProperty("start")
    private int start;
    @JsonProperty("end")
    private int end;
    @JsonProperty("tag")
    private String tag;

}
