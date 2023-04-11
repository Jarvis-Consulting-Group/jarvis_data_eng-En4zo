package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public class TwitterDaoIntTest {
    private TwitterDao dao;

    public void setup(){
        HttpHelper httpHelper = new TwitterHttpHelper();
        this.dao = new TwitterDao(httpHelper);
    }

    public static void main(String[] args) {
        TwitterDaoIntTest twitterDaoIntTest = new TwitterDaoIntTest();
        twitterDaoIntTest.setup();
        Root tweet = twitterDaoIntTest.dao.findById("1631746850054352897");

//        deleteRoot deleteTweet = twitterDaoIntTest.dao.deleteById("1631747183832694786");


//        Tweet newTweet = new Tweet();
//        newTweet.setText("This is a test of  create tweet method from Twitter Dao5");
//        ArrayList<Tweet> newArrayTweet = new ArrayList<>();
//        newArrayTweet.add(newTweet);
//        Root newRoot = new Root();
//        newRoot.setTweets(newArrayTweet);
//        createRoot createTweet = twitterDaoIntTest.dao.create(newRoot);

        try {
            System.out.println(JsonUtil.toJson(tweet,true,false));

            //System.out.println(JsonUtil.toJson(deleteTweet,true,false));

            //System.out.println(JsonUtil.toJson(createTweet,true,false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
