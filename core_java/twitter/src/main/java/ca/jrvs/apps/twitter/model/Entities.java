package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder({
        "hashtags",
        "mentions",
        "annotations"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities {
    @JsonProperty("hashtags")
    private List<Hashtag> hashtags;
    @JsonProperty("mentions")
    private List<Mention> mentions;
    @JsonProperty("annotations")
    private ArrayList<Annotation> annotations;

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

    public ArrayList<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ArrayList<Annotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "Entities{" +
                "hashtags=" + hashtags +
                ", mentions=" + mentions +
                ", annotations=" + annotations +
                '}';
    }
}


