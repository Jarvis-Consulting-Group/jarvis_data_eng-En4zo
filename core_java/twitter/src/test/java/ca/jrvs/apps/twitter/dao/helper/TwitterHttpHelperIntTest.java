package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.JsonUtil;
import ca.jrvs.apps.twitter.model.Root;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class TwitterHttpHelperIntTest {
    public static void main(String[] args) throws Exception{
        String  CONSUMER_KEY = System.getenv("consumerKey");
        String  CONSUMER_SECRET = System.getenv("consumerSecret");
        String  ACCESS_TOKEN = System.getenv("accessToken");
        String  TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println(CONSUMER_KEY + "|" + CONSUMER_SECRET + "|" + ACCESS_TOKEN + "|" + TOKEN_SECRET);

        //HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
        HttpHelper httpHelper = new TwitterHttpHelper();
        String text = "Hello World7790099";
        HttpResponse response = httpHelper.httpPost(URI.create("https://api.twitter.com/2/tweets"), text);
//        HttpResponse response = httpHelper.httpGet(URI.create("https://api.twitter.com/2/tweets?ids=1632973093122588673&tweet.fields=id,created_at,text,entities,public_metrics,geo&expansions=geo.place_id&place.fields=geo"));
//        HttpResponse response = httpHelper.httpDelete(URI.create("https://api.twitter.com/2/tweets/1631375617609400320"));
//        HttpResponse response = httpHelper.httpGet(URI.create("https://api.twitter.com/2/tweets?ids=1631058476355993605&tweet.fields=created_at,entities,public_metrics,geo"));
        String jsonStr;
        jsonStr = EntityUtils.toString(response.getEntity());
        System.out.println(jsonStr);
        Root root = JsonUtil.toObjectFromJson(jsonStr, Root.class);
        //       System.out.println("this is tweet tostring method" + tweet.toString());
        System.out.println(JsonUtil.toJson(root,true,false));

//        System.out.println(tweet.getText());

        //      System.out.println(response.getStatusLine().getStatusCode());

    }
}
