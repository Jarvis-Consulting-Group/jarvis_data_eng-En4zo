package ca.jrvs.apps.twitter.cli;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class TwitterCLIApp {
    public static final String USAGE = "USAGE: TwitterCLIApp post|show|delete [options]";

    private Controller controller;

    public TwitterCLIApp(Controller controller) {this.controller = controller;}
    final private Logger logger = LoggerFactory.getLogger(TwitterCLIApp.class);

    public static void main(String[] args) {
        String  CONSUMER_KEY = System.getenv("consumerKey");
        String  CONSUMER_SECRET = System.getenv("consumerSecret");
        String  ACCESS_TOKEN = System.getenv("accessToken");
        String  TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
        TwitterDao dao = new TwitterDao(httpHelper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        app.run(args);
    }

    public void run(String[] args){
        if (args.length == 0){
            logger.error(USAGE);
        }
        switch (args[0].toLowerCase()){
            case "post":
                postTweet(controller.postTweet(args));
                break;
            case "show":
                showTweet(controller.showTweet(args));
                break;
            case "delete":
                List<Object> deleteTweet = controller.deleteTweet(args);
                for (int i = 0; i < deleteTweet.size(); i++){
                    if (i % 2 == 0){
                        System.out.println("--------------------------------------------------");
                    }
                    try {
                        System.out.println(JsonUtil.toJson(deleteTweet.get(i),true,false));
                    } catch (JsonProcessingException e) {
                        logger.error("Unable to convert deleteRoot object to string");
                    }
                }

        }
    }

    private void postTweet(createRoot newTweet){
        try {
            System.out.println(JsonUtil.toJson(newTweet,true,false));
        } catch (JsonProcessingException e) {
            logger.error("Unable to convert createRoot object to string");
        }
    }
    private void showTweet(Root tweet){
        try {
            System.out.println(JsonUtil.toJson(tweet,true,false));
        } catch (JsonProcessingException e) {
            logger.error("Unable to convert Root object to string");
        }
    }
}
