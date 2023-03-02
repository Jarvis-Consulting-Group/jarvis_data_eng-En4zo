package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import javax.validation.groups.Default;
import java.io.IOException;
import java.net.URI;
import ca.jrvs.apps.twitter.example.JsonParser;

public class TwitterHttpHelper implements HttpHelper{
    private OAuthConsumer consumer;
    private HttpClient httpClient;
    private static final URI postUri = URI.create("https://api.twitter.com/2/tweets");
    private static final URI getUri = URI.create("https://api.twitter.com/2/tweets");

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
        HttpResponse response = httpHelper.httpGet(URI.create("https://api.twitter.com/2/tweets?ids=1212092628029698048&tweet.fields=attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,public_metrics,referenced_tweets,source,text"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
