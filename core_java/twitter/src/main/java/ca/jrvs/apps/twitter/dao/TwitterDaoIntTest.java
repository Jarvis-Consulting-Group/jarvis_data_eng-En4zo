package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Root;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TwitterDaoIntTest {
    private TwitterDao dao;

    public void setup(){
        HttpHelper httpHelper = new TwitterHttpHelper();
        this.dao = new TwitterDao(httpHelper);
    }

    public static void main(String[] args) {
        TwitterDaoIntTest twitterDaoIntTest = new TwitterDaoIntTest();
        twitterDaoIntTest.setup();
        Root tweet = twitterDaoIntTest.dao.findById("1631058476355993605");
        try {
            System.out.println(JsonUtil.toJson(tweet,true,false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
