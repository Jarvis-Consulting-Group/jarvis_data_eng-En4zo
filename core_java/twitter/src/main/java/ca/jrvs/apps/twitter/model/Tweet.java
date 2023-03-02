package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "created_at",
        "id",
        "text",
        "entities",
        "coordinates",
        "public_metrics",
})
public class Tweet {
    @JsonProperty("create_at")
    private Date createAt;
    @JsonProperty("id")
    private String id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("entities")
    private Entities entities;
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("public_metrics")
    private PublicMetrics publicMetrics;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public PublicMetrics getPublicMetrics() {
        return publicMetrics;
    }

    public void setPublicMetrics(PublicMetrics publicMetrics) {
        this.publicMetrics = publicMetrics;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "createAt=" + createAt +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", entities=" + entities +
                ", coordinates=" + coordinates +
                ", publicMetrics=" + publicMetrics +
                '}';
    }
}
