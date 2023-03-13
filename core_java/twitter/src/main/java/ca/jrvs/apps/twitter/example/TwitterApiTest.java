//package ca.jrvs.apps.twitter.example;
//
//import com.google.gdata.util.common.base.PercentEscaper;
//import oauth.signpost.OAuthConsumer;
//import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import java.util.Arrays;
//
//public class TwitterApiTest {
//    private static String  CONSUMER_KEY = System.getenv("consumerKey");
//    private static String  CONSUMER_SECRET = System.getenv("consumerSecret");
//    private static String  ACCESS_TOKEN = System.getenv("accessToken");
//    private static String  TOKEN_SECRET = System.getenv("tokenSecret");
//
//    public static void main(String[] args) throws Exception{
//        // Setup authentication
//        OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
//        oAuthConsumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
//
//        // Create a HTTP GET request
//        String Id = "1212092628029698048";
//        String text = "today is a good day";
//        PercentEscaper percentEscaper = new PercentEscaper("", false);
//        HttpPost httpGetRequest = new HttpPost(
//                "https://api.twitter.com/2/tweets");
//        String tweetText = "Hello, world! This is my first tweet using the Twitter v2 API. #twitterapi #java";
//        String requestBody = "{\"text\": \"" + tweetText + "\"}";
//        httpGetRequest.setHeader("Content-Type","application/json");
//        httpGetRequest.setEntity(new StringEntity(requestBody));
//        System.out.println(httpGetRequest.getEntity());
//
//        // sign/add headers to request
//        oAuthConsumer.sign(httpGetRequest);
//
//        System.out.println("Http Request Headers:");
//        Arrays.stream(httpGetRequest.getAllHeaders()).forEach(System.out::println);
//
//        // Send http GET request
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        HttpResponse httpResponse = httpClient.execute(httpGetRequest);
//        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
//
//    }
//}
