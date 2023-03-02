package ca.jrvs.apps.twitter.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "id",
        "text",
        "public_metrics",
        "edit_history_tweet_ids",
        "create_at",
        "geo",
        "entities"

})
public class Data {
    private String id;
    private String text;
    private PublicMetrics publicMetrics;
    private List<String> edit_history_tweet_ids;
    private String createAt;
    private Entities entities;
    private Geo geo;

}

