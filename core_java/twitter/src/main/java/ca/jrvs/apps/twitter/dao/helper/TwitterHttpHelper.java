package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import ca.jrvs.apps.twitter.model.*;
import javax.validation.groups.Default;
import java.io.IOException;
import java.net.URI;
import ca.jrvs.apps.twitter.example.JsonParser;

public class TwitterHttpHelper implements HttpHelper{
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret){
        consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecret);
        consumer.setTokenWithSecret(accessToken,tokenSecret);

        httpClient = new DefaultHttpClient();

    }

    @Override
    public HttpResponse httpPost(URI uri, String s) {
        try{
            return executeHttpRequest(HttpMethod.POST,uri, s);
        }catch (OAuthException | IOException e){
            throw new RuntimeException("Failed to execute", e);
        }
    }


    @Override
    public HttpResponse httpGet(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.GET,uri,null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }
    @Override
    public HttpResponse httpDelete(URI uri){
        try {
            return executeHttpRequest(HttpMethod.DELETE, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse executeHttpRequest(HttpMethod method, URI uri, String s) throws OAuthException, IOException{
        if(method == HttpMethod.GET){
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);
        } else if (method == HttpMethod.POST) {
            HttpPost request = new HttpPost(uri);
            if (s != null){
                request.setHeader("Content-Type","application/json");
                String requestBody = "{\"text\": \"" + s + "\"}";
                request.setEntity(new StringEntity(requestBody));
            }
            consumer.sign(request);
            return httpClient.execute(request);

        }else if(method == HttpMethod.DELETE){
            HttpDelete request = new HttpDelete(uri);
            consumer.sign(request);
            return httpClient.execute(request);

        }else {
            throw new IllegalArgumentException("Unknown HTTP method" + method.name());
        }

    }

    public static void main(String[] args) throws Exception{
        String  CONSUMER_KEY = System.getenv("consumerKey");
        String  CONSUMER_SECRET = System.getenv("consumerSecret");
        String  ACCESS_TOKEN = System.getenv("accessToken");
        String  TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + "|" + CONSUMER_SECRET + "|" + ACCESS_TOKEN + "|" + TOKEN_SECRET);

        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
        String text = "Hello World2233";
//        HttpResponse response = httpHelper.httpPost(TwitterHttpHelper.uri, text);
        HttpResponse response = httpHelper.httpGet(URI.create("https://api.twitter.com/2/tweets?ids=1631058476355993605&tweet.fields=id,created_at,text,entities,public_metrics&expansions=geo.place_id&place.fields=geo"));
//        HttpResponse response = httpHelper.httpDelete(URI.create("https://api.twitter.com/2/tweets/1631026412621627392"));
        String jsonStr;
        jsonStr = EntityUtils.toString(response.getEntity());
        Tweet tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        System.out.println(JsonUtil.toJson(tweet,true,false));

        System.out.println(response.getStatusLine().getStatusCode());

    }
}
