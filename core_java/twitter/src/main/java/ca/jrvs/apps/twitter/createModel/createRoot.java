package ca.jrvs.apps.twitter.createModel;

import ca.jrvs.apps.twitter.deleteModel.deleteData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "data"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
public class createRoot {
    @JsonProperty("data")
    private createData data;

    public createData getData() {
        return data;
    }

    public void setData(createData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "createRoot{" +
                "data=" + data +
                '}';
    }
}