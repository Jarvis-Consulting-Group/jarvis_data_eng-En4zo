package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class TwitterControllerIntTest {
    public static void main(String[] args) {
        HttpHelper httpHelper = new TwitterHttpHelper();
        TwitterDao dao = new TwitterDao(httpHelper);
        Service newSer = new TwitterService(dao);
        Controller newCon = new TwitterController(newSer);
        String[] showArgs = {"show", "1632810193737207809","id,text"};
        String[] postArgs = {"post", "twitterController test"};
        String[] deleteArgs = {"delete","1632444147385638912,1631848755140935682,1631375394656886786"};
        Root tweet = newCon.showTweet(showArgs);
        createRoot newC = newCon.postTweet(postArgs);
        List<Object> newL = newCon.deleteTweet(deleteArgs);

        try {
            System.out.println(JsonUtil.toJson(tweet,true,false));
            System.out.println("-------------------------------------");
            for (int i = 0; i < newL.size(); i++){
                if (i %2 == 0){
                    System.out.println(JsonUtil.toJson(newL.get(i),true,false));
                }else {
                    System.out.println(JsonUtil.toJson(newL.get(i),true,false));
                }
                System.out.println("<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");

            }
            System.out.println("-------------------------------------");
            System.out.println(JsonUtil.toJson(newC,true,false));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
