package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({
        "hashtags",
        "userMentions"
})
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Entities {
    private List<Hashtag> hashtags;
    private List<UserMention> userMentions;

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }
}
