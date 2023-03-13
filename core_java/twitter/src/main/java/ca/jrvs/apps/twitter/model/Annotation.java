package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "start",
        "end",
        "probability",
        "type",
        "normalized_text"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Annotation {
    @JsonProperty("start")
    private int start;
    @JsonProperty("end")
    private int end;
    @JsonProperty("probability")
    private double probability;
    @JsonProperty("type")
    private String type;
    @JsonProperty("normalized_text")
    private String normalizedText;

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

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNormalizedText() {
        return normalizedText;
    }

    public void setNormalizedText(String normalizedText) {
        this.normalizedText = normalizedText;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "start=" + start +
                ", end=" + end +
                ", probability=" + probability +
                ", type='" + type + '\'' +
                ", normalizedText='" + normalizedText + '\'' +
                '}';
    }
}
