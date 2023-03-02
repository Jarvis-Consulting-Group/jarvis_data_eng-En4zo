//package ca.jrvs.apps.twitter.dao;
//
//import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
//import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
//import ca.jrvs.apps.twitter.model.Tweet;
//import com.google.gdata.util.common.base.PercentEscaper;
//import io.gsonfire.util.JsonUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import ca.jrvs.apps.twitter.JsonUtil;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public class TwitterDao implements CrdDao<Tweet, String>{
//    //URI constants
//    private static final String API_BASE_URI = "https://api.twitter.com";
//    //Path for Post, get and delete
//    private static final String PATH = "/2/tweets";
//    //URI symbols
//    private static final String QUERY_SYM = "?";
//    private static final String AMPERSAND = "&";
//    private static final String EQUAL = "=";
//
//    //Response code
//    private static final int HTTP_OK = 200;
//
//    private HttpHelper httpHelper;
//
//    private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);
//
//
//    public TwitterDao(HttpHelper httpHelper) {
//        this.httpHelper = httpHelper;
//    }
//
//    @Override
//    public Tweet create(Tweet entity) {
//        URI uri;
//
//        try {
//            uri = new URI(API_BASE_URI + PATH);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        HttpResponse response = httpHelper.httpPost(uri,entity.getText());
//        return parseResponseBody(response,HTTP_OK);
//
//
//    }
//
//
//
//
//    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){
//        Tweet tweet;
//        int status = response.getStatusLine().getStatusCode();
//        if (status != expectedStatusCode){
//            try {
//                logger.debug(EntityUtils.toString(response.getEntity()));
//            } catch (IOException e) {
//                logger.error("Unexpected HTTP status" + status);
//            }
//        }
//        if (response.getEntity() == null){
//            logger.error("Empty response body");
//        }
//        String jsonStr;
//        try {
//            jsonStr = EntityUtils.toString(response.getEntity());
//        } catch (IOException e) {
//            logger.error("Fail to convert entity to String");
//        }
//        tweet = JsonUtils.
//    }
//
//
//    @Override
//    public Tweet findById(String s) {
//        URI uri;
//        try {
//            uri = new URI(API_BASE_URI + PATH + QUERY_SYM + "ids" + EQUAL + s + AMPERSAND + "tweet.field" + EQUAL +
//                    "id,created_at,text,entities,public_metrics" + AMPERSAND +"expansions" + EQUAL + "geo.place_id" +
//                    AMPERSAND + "place.fields" + EQUAL + "geo");
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        HttpResponse response = httpHelper.httpGet(uri);
//        return parseResponseBody(response,HTTP_OK);
//    }
//
//    @Override
//    public Tweet deleteById(String s) {
//        URI uri;
//        try {
//            uri = new URI(API_BASE_URI + PATH + "/" + s);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        HttpResponse response = httpHelper.httpDelete(uri);
//        return parseResponseBody(response,HTTP_OK);
//    }
//}
