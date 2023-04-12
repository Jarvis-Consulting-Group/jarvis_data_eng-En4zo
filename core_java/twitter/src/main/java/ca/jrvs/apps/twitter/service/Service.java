package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;

public interface Service {

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet tweet to be created
     * @return created tweet
     *
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long out of range
     */
    createRoot postTweet(Root tweet);


    /**
     * Search a tweet by ID
     *
     * @param id tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     *
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    Root showTweet(String id, String[] fields);

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     *
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    List<Object> deleteTweets(String[] ids);

}