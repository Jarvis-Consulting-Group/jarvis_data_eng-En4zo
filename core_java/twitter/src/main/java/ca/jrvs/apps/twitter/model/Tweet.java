package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({

        "data",
        "includes"

})
public class Tweet {
    @JsonProperty("Data")
    private Data data;
    private Includes includes;

//    @JsonProperty("editHistoryTweetIds")
//    private String editHistoryTweetIds;
//    @JsonProperty("create_at")
//    private Date createAt;
//    @JsonProperty("id")
//    private String id;
//    @JsonProperty("text")
//    private String text;
//    @JsonProperty("entities")
//    private Entities entities;
//    @JsonProperty("coordinates")
//    private geo geo;
//    @JsonProperty("public_metrics")
//    private PublicMetrics publicMetrics;


}
