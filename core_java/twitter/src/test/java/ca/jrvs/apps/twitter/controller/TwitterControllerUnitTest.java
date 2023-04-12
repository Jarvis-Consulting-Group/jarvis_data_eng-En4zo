package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
    @Mock
    Service Mockservice;
    @Mock
    TwitterController mockController;




    @Test
    public void postTweet() throws IOException {
        String[] postArgs = {"post", "this tweet create by docker program running on springboot"};

        Tweet newTweet = new Tweet();
        newTweet.setText("this tweet create by docker program running on springboot");
        ArrayList<Tweet> newArrayTweet = new ArrayList<>();
        newArrayTweet.add(newTweet);
        Root newRoot = new Root();
        newRoot.setTweets(newArrayTweet);

        when(Mockservice.postTweet(isNotNull())).thenThrow(new RuntimeException("mock service"));
        try{
            Mockservice.postTweet(newRoot);
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }

        when(Mockservice.postTweet(isNotNull())).thenReturn(null);
        TwitterController spyController = Mockito.spy(mockController);
        createRoot expectedRoot = JsonUtil.toObjectFromJson(createJasonStr, createRoot.class);

        doReturn(expectedRoot).when(spyController).postTweet(isNotNull());
        createRoot createTweet = spyController.postTweet(postArgs);

        assertNotNull(createTweet);
        assertEquals(newRoot.getTweets().get(0).getText(),createTweet.getData().getText());

    }

    @Test
    public void showTweet() throws IOException {
        String[] showArgs = {"show", "1632973093122588673","id,text"};
        when(Mockservice.showTweet(isNotNull(),isNotNull())).thenThrow(new RuntimeException("mock service"));
        try{
            Mockservice.showTweet("1632973093122588673" , new String[]{"id,text"});
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }

        when(Mockservice.showTweet(isNotNull(),isNotNull())).thenReturn(null);
        TwitterController spyController = Mockito.spy(mockController);
        Root expectedRoot = JsonUtil.toObjectFromJson(tweetJasonStr, Root.class);

        doReturn(expectedRoot).when(spyController).showTweet(isNotNull());
        Root showTweet = spyController.showTweet(showArgs);

        assertNotNull(showTweet);

    }
    @Test
    public void deleteTweet() throws IOException {
        String[] deleteArgs = {"delete","1632973093122588673"};
        when(Mockservice.deleteTweets(isNotNull())).thenThrow(new RuntimeException("mock service"));
        try{
            Mockservice.deleteTweets(new String[]{"1632973093122588673"});
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }

        when(Mockservice.deleteTweets(isNotNull())).thenReturn(null);
        TwitterController spyController = Mockito.spy(mockController);
        Root expectedRoot1 = JsonUtil.toObjectFromJson(tweetJasonStr,Root.class);
        deleteRoot expectedRoot2 = JsonUtil.toObjectFromJson(deleteJasonStr, deleteRoot.class);

        List<Object> expectedList = new ArrayList<>();
        expectedList.add(expectedRoot1);
        expectedList.add(expectedRoot2);
        doReturn(expectedList).when(spyController).deleteTweet(isNotNull());

        List<Object> deleteTweetList = spyController.deleteTweet(deleteArgs);
        assertNotNull(deleteTweetList);
        assertEquals(2,deleteTweetList.size());

    }

    String createJasonStr = "{\n" +
            "  \"data\" : {\n" +
            "    \"edit_history_tweet_ids\" : [ \"1634748971926052865\" ],\n" +
            "    \"id\" : \"1634748971926052865\",\n" +
            "    \"text\" : \"this tweet create by docker program running on springboot\"\n" +
            "  }\n" +
            "}\n";
    String deleteJasonStr = "{\n" +
            "  \"data\" : {\n" +
            "    \"deleted\" : true\n" +
            "  }\n" +
            "}\n";
    String tweetJasonStr = "{\n" +
            "  \"data\" : [ {\n" +
            "    \"id\" : \"1632973093122588673\",\n" +
            "    \"text\" : \"this tweet create by docker program running on springboot\",\n" +
            "    \"public_metrics\" : {\n" +
            "      \"retweet_count\" : 0,\n" +
            "      \"reply_count\" : 0,\n" +
            "      \"like_count\" : 0,\n" +
            "      \"quote_count\" : 0,\n" +
            "      \"impression_count\" : 11\n" +
            "    },\n" +
            "    \"edit_history_tweet_ids\" : [ \"1632973093122588673\" ],\n" +
            "    \"created_at\" : 1678166099000,\n" +
            "    \"entities\" : {\n" +
            "      \"annotations\" : [ {\n" +
            "        \"start\" : 21,\n" +
            "        \"end\" : 26,\n" +
            "        \"probability\" : 0.4809,\n" +
            "        \"type\" : \"Organization\",\n" +
            "        \"normalized_text\" : \"docker\"\n" +
            "      }, {\n" +
            "        \"start\" : 47,\n" +
            "        \"end\" : 56,\n" +
            "        \"probability\" : 0.6718,\n" +
            "        \"type\" : \"Other\",\n" +
            "        \"normalized_text\" : \"springboot\"\n" +
            "      } ]\n" +
            "    }\n" +
            "  } ]\n" +
            "}";



}
