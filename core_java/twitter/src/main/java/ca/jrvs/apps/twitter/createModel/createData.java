package ca.jrvs.apps.twitter.createModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({
        "edit_history_tweet_ids",
        "id",
        "text"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
public class createData {
    @JsonProperty("edit_history_tweet_ids")
    private ArrayList<String> editHistoryTweetIds;
    @JsonProperty("id")
    private String id;
    @JsonProperty("text")
    private String text;

    public ArrayList<String> getEditHistoryTweetIds() {
        return editHistoryTweetIds;
    }

    public void setEditHistoryTweetIds(ArrayList<String> editHistoryTweetIds) {
        this.editHistoryTweetIds = editHistoryTweetIds;
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

    @Override
    public String toString() {
        return "createData{" +
                "editHistoryTweetIds=" + editHistoryTweetIds +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
