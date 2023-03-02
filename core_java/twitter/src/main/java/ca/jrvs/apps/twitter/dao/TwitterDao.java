package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet, String>{
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

    private HttpHelper httpHelper;

    private final Logger logger = LoggerFactory.getLogger(TwitterDao.class);


    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet entity) {
        URI uri;

        try {
            uri = new URI(API_BASE_URI + PATH);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpResponse response = httpHelper.httpPost(uri,entity.getText());
        return parseResponseBody(response,HTTP_OK);


    }




    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){

        return null;
    }


    @Override
    public Tweet findById(String s) {

    }

    @Override
    public Tweet deleteById(String s) {
        URI uri;
        try {
            uri = new URI(API_BASE_URI + PATH + "/" + s);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
