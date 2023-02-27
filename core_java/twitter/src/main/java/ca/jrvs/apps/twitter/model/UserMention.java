package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "id_str",
        "indices",
        "name",
        "screen_name"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMention {
    @JsonProperty("id")
    private long id;
    @JsonProperty("id_str")
    private String idStr;
    @JsonProperty("indices")
    private int[] indices;
    @JsonProperty("name")
    private String name;
    @JsonProperty("screen_name")
    private String screenName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
