package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({
        "hashtags",
        "Mentions"
})
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Entities {
    @JsonProperty("hashtags")
    private List<Hashtag> hashtags;
    @JsonProperty("Mentions")
    private List<Mention> mentions;

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    @Override
    public String toString() {
        return "Entities{" +
                "hashtags=" + hashtags +
                ", mentions=" + mentions +
                '}';
    }
}


