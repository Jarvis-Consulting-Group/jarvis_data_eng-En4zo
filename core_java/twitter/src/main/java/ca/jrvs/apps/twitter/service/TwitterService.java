package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service{
    private TwitterDao dao;
    // Max Text length
    private static final int MAX_TEXT_LENGTH = 140;
    final private Logger logger = LoggerFactory.getLogger(TwitterService.class);

    public TwitterService(TwitterDao dao) {this.dao = dao;}


    @Override
    public createRoot postTweet(Root tweet) {
        //Get tweet text length
        int tweetTextLength = tweet.getTweets().get(0).getText().length();
        if (tweetTextLength >  MAX_TEXT_LENGTH){
            logger.error("Tweet text length must less than 140 characters.");
        }
        return dao.create(tweet);
    }

    @Override
    public Root showTweet(String id, String[] fields) {
        ArrayList<String> correct = new ArrayList<>(
                Arrays.asList(
                "id",
                "text",
                "public_metrics",
                "edit_history_tweet_ids",
                "created_at",
                "geo",
                "entities"
        ));
        ArrayList<String> incorrect = new ArrayList<>();

        if (fields != null){
            for(String f : fields){
                if (!correct.contains(f)){
                    incorrect.add(f);
                }
            }
        }
        if (incorrect.size() >0){
            StringBuilder errorMessage = new StringBuilder();
            for (String s : incorrect){
                errorMessage.append(s).append(",");
            }
            logger.error("Incorrect fields:" + errorMessage);
        }

        if (!id.matches("[0-9]+")){
            logger.error("Illegal tweet Id.");
        }

        return dao.findById(id);
    }

    @Override
    public List<Object> deleteTweets(String[] ids) {
        List<Object> deletedRoots = new ArrayList<>();
        for (String id : ids){
            if (!id.matches("[0-9]+")){
                logger.error("Illegal tweet id.");
            }else {
                deletedRoots.add(dao.findById(id));
                deletedRoots.add(dao.deleteById(id));
            }
        }
        return deletedRoots;
    }
}
