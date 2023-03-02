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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Mention{" +
                "start=" + start +
                ", end=" + end +
                ", tag='" + tag + '\'' +
                '}';
    }
}
