package ca.jrvs.apps.twitter.deleteModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "deleted"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
public class deleteData {
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "deleteData{" +
                "deleted=" + deleted +
                '}';
    }
}
