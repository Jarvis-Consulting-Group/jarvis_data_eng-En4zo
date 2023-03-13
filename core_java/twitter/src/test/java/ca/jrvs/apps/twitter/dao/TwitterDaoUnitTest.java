package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ca.jrvs.apps.twitter.createModel.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
    @Mock
    HttpHelper mockHelper;
    @InjectMocks
    TwitterDao dao;
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

    @Test
    public void testShowTweet() throws Exception{
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.findById("1632973093122588673");
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }
        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spydao = Mockito.spy(dao);
        Root expectedTweet = JsonUtil.toObjectFromJson(tweetJasonStr,Root.class);

        doReturn(expectedTweet).when(spydao).parseResponseBody(any(),anyInt());
        Root root = spydao.findById("1632973093122588673");
        assertNotNull(root);
        assertNotNull(root.getTweets().get(0).getText());

    }
    @Test
    public void testPostTweet() throws IOException {

        Tweet newTweet = new Tweet();
        newTweet.setText("This is a test of  create tweet method from Twitter Dao5");
        ArrayList<Tweet> newArrayTweet = new ArrayList<>();
        newArrayTweet.add(newTweet);
        Root newRoot = new Root();
        newRoot.setTweets(newArrayTweet);


        when(mockHelper.httpPost(isNotNull(),isNotNull())).thenThrow(new RuntimeException("mock"));
        try {
            dao.create(newRoot);
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }
        when(mockHelper.httpPost(isNotNull(),isNotNull())).thenReturn(null);
        TwitterDao spydao = Mockito.spy(dao);
        createRoot expectedRoot = JsonUtil.toObjectFromJson(createJasonStr, createRoot.class);

        doReturn(expectedRoot).when(spydao).parseCreateBody(any(),anyInt());
        createRoot createTweet = spydao.create(newRoot);

        assertNotNull(createTweet);
        assertNotNull(createTweet.getData().getText());
    }
    @Test
    public void testDeleteTweet() throws IOException {
        when(mockHelper.httpDelete(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.deleteById("1634748971926052865");
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }
        when(mockHelper.httpDelete(isNotNull())).thenReturn(null);
        TwitterDao spydao = Mockito.spy(dao);
        deleteRoot expectedRoot = JsonUtil.toObjectFromJson(deleteJasonStr, deleteRoot.class);

        doReturn(expectedRoot).when(spydao).parseDeleteBody(any(),anyInt());
        deleteRoot deleteTweet =spydao.deleteById("1634748971926052865");

        assertNotNull(deleteTweet);
        assertTrue(deleteTweet.getData().isDeleted());
    }



}
