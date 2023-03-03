package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.createModel.createRoot;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.deleteModel.deleteRoot;
import ca.jrvs.apps.twitter.model.Root;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.jrvs.apps.twitter.JsonUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements readDao<Root, String>,deleteDao<deleteRoot,String>,createDao<createRoot,String> {
    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    //Path for Post, get and delete
    private static final String PATH = "/2/tweets";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATE = 201;

    private HttpHelper httpHelper;

    private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);


    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }



    @Override
    public createRoot create(Root entity) {
        URI uri;

        try {
            uri = new URI(API_BASE_URI + PATH);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("this is the create tweet test: "+ entity.getTweets().get(0).getText());
        HttpResponse response = httpHelper.httpPost(uri,entity.getTweets().get(0).getText());
        return parseCreateBody(response,HTTP_CREATE);

    }




    public createRoot parseCreateBody(HttpResponse response, Integer expectedStatusCode){

        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode){
            try {
                logger.debug(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                logger.error("Unexpected HTTP status" + status);
            }
        }
        if (response.getEntity() == null){
            logger.error("Empty response body");
        }
        try {
            String jsonStr = EntityUtils.toString(response.getEntity());
            System.out.println(jsonStr);
            createRoot root = JsonUtil.toObjectFromJson(jsonStr, createRoot.class);
            return root;

        } catch (IOException e) {
            logger.debug("Unable to covert Create JSON str to Object");
        }
        return null;
    }





    public Root parseResponseBody(HttpResponse response, Integer expectedStatusCode){

        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode){
            try {
                logger.debug(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                logger.error("Unexpected HTTP status" + status);
            }
        }
        if (response.getEntity() == null){
            logger.error("Empty response body");
        }
        try {
                Root data = JsonUtil.toObjectFromJson(EntityUtils.toString(response.getEntity()),Root.class);
                return data;

        } catch (IOException e) {
            logger.debug("Unable to convert JSON str to Object");
        }
        return null;
    }




    @Override
    public Root findById(String s) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + PATH + QUERY_SYM + "ids" + EQUAL + s + AMPERSAND + "tweet.fields" + EQUAL +
                    "id,created_at,text,entities,public_metrics" + AMPERSAND +"expansions" + EQUAL + "geo.place_id" +
                    AMPERSAND + "place.fields" + EQUAL + "geo");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponseBody(response,HTTP_OK);
    }

    @Override
    public deleteRoot deleteById(String s) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + PATH + "/" + s);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpResponse response = httpHelper.httpDelete(uri);
        return parseDeleteBody(response,HTTP_OK);
    }

    public deleteRoot parseDeleteBody(HttpResponse response, Integer expectedStatusCode){

        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode){
            try {
                logger.debug(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                logger.error("Unexpected HTTP status" + status);
            }
        }
        if (response.getEntity() == null){
            logger.error("Empty response body");
        }
        try {
            deleteRoot data = JsonUtil.toObjectFromJson(EntityUtils.toString(response.getEntity()),deleteRoot.class);
            return data;

        } catch (IOException e) {
            logger.debug("Unable to convert JSON str to Object");
        }
        return null;
    }

}
