package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({

        "id",
        "text",
        "public_metrics",
        "edit_history_tweet_ids",
        "created_at",
        "geo",
        "entities",


})
public class Tweet {

    @JsonProperty("edit_history_tweet_ids")
    private ArrayList<String> editHistoryTweetIds;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("id")
    private String id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("entities")
    private Entities entities;
    @JsonProperty("geo")
    private Geo geo;
    @JsonProperty("public_metrics")
    private PublicMetrics publicMetrics;

    public ArrayList<String> getEditHistoryTweetIds() {
        return editHistoryTweetIds;
    }

    public void setEditHistoryTweetIds(ArrayList<String> editHistoryTweetIds) {
        this.editHistoryTweetIds = editHistoryTweetIds;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
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
                "editHistoryTweetIds=" + editHistoryTweetIds +
                ", createdAt=" + createdAt +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", entities=" + entities +
                ", geo=" + geo +
                ", publicMetrics=" + publicMetrics +
                '}';
    }
}
