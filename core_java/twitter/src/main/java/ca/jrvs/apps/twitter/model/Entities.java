package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({
        "hashtags",
        "Mentions"
})
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Entities {
    private List<Hashtag> hashtags;
    private List<Mention> Mentions;

}


