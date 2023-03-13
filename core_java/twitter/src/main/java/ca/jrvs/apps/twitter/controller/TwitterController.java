package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TwitterController implements Controller{
    private static final String COMMA = ",";

    private Service service;

    public TwitterController(Service service){ this.service = service;}

    final private Logger logger = LoggerFactory.getLogger(TwitterController.class);
    @Override
    public createRoot postTweet(String[] args) {
        if (args.length !=2 ){
            logger.error("USAGE: TwitterCLIApp post \" tweet_text \"");
        }
        Tweet tweet = new Tweet();
        tweet.setText(args[1]);
        ArrayList<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        Root newRoot = new Root();
        newRoot.setTweets(tweets);
        return service.postTweet(newRoot);

    }

    @Override
    public Root showTweet(String[] args) {
        String id = args[1];
        if (args.length > 3){
            logger.error("USAGE: TwitterCLIApp show id \"fields, field2\"");
        }

        String[] fields = null;
        if (args.length == 3 && args[2] != null){
            fields = args[2].split(COMMA);
        }

        return service.showTweet(id,fields);
    }

    @Override
    public List<Object> deleteTweet(String[] args) {
        if (args.length != 2){
            logger.error("USAGE: TwitterCLIApp delete \"id1,id2,id3,etc.\"");
        }

        String[] ids = args[1].split(COMMA);
        return service.deleteTweets(ids);
    }
}
