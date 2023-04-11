package ca.jrvs.apps.twitter.deleteModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "data"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
public class deleteRoot {
    @JsonProperty("data")
    private deleteData data;

    public deleteData getData() {
        return data;
    }

    public void setData(deleteData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "deleteRoot{" +
                "data=" + data +
                '}';
    }
}
