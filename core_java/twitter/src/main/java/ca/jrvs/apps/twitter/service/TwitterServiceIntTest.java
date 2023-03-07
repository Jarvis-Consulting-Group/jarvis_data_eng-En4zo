//package ca.jrvs.apps.twitter.service;
//
//import ca.jrvs.apps.twitter.JsonUtil;
//import ca.jrvs.apps.twitter.createModel.createRoot;
//import ca.jrvs.apps.twitter.dao.TwitterDao;
//import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
//import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
//import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
//import ca.jrvs.apps.twitter.model.Root;
//import ca.jrvs.apps.twitter.model.Tweet;
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TwitterServiceIntTest {
//    private TwitterDao dao;
//    private TwitterService tweetService;
//
//    public void setup(){
//        HttpHelper httpHelper = new TwitterHttpHelper();
//        this.dao = new TwitterDao(httpHelper);
//        this.tweetService = new TwitterService(this.dao);
//    }
//
//    public static void main(String[] args) {
//        TwitterServiceIntTest twitterServiceIntTest = new TwitterServiceIntTest();
//        twitterServiceIntTest.setup();
//
//        Tweet newTweet = new Tweet();
//        newTweet.setText("This is a test of  create tweet method from Twitter Dao555444444");
//        ArrayList<Tweet> newArrayTweet = new ArrayList<>();
//        newArrayTweet.add(newTweet);
//        Root newRoot = new Root();
//        newRoot.setTweets(newArrayTweet);
//        //createRoot createTweet = twitterServiceIntTest.tweetService.postTweet(newRoot);
//        //Root tweet = twitterServiceIntTest.tweetService.showTweet("1631026072627077120", new String[]{"id","name","what"});
//        List<Object> deleteRoots = twitterServiceIntTest.tweetService.deleteTweets(new String[]{"1631026072627077120"});
//
//        try {
//            //System.out.println(JsonUtil.toJson(tweet,true,false));
//            System.out.println("-------------------------------------");
//            for (int i = 0; i < deleteRoots.size(); i++){
//                if (i %2 == 0){
//                    System.out.println(JsonUtil.toJson(deleteRoots.get(i),true,false));
//                }else {
//                    System.out.println(JsonUtil.toJson(deleteRoots.get(i),true,false));
//                }
//                System.out.println("<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
//
//            }
//            System.out.println("-------------------------------------");
//            //System.out.println(JsonUtil.toJson(createTweet,true,false));
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}
