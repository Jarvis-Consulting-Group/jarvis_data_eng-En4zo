//package ca.jrvs.apps.twitter.dao;
//
//import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
//import ca.jrvs.apps.twitter.model.Tweet;
//import com.google.gdata.util.common.base.PercentEscaper;
//import org.apache.http.HttpResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public class TwitterDao implements CrdDao<Tweet, String>{
//    //URI constants
//    private static final String API_BASE_URI = "https://api.twitter.com";
//    private static final String POST_PATH = "/1.1/statuses/update.json";
//    private static final String SHOW_PATH = "/1,1/statuses/show.json";
//    private static final String DELETE_PATH = "1.1/statuses/destroy";
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
//        try {
//            uri = getPostUri(entity);
//        } catch (URISyntaxException e) {
//            throw new IllegalArgumentException("Invalid tweet input", e);
//        }
//        HttpResponse response = httpHelper.httpPost(uri);
//        return parseResponseBody(response,HTTP_OK);
//    }
//
//    /**
//     * Purpose:
//     * @param entity
//     * @return
//     */
//    private URI getPostUri(Tweet entity) throws URISyntaxException {
//        PercentEscaper percentEscaper = new PercentEscaper("",false);
//        String CoordinateLong = String.valueOf(entity.getCoordinates().getCoordinates()[0]);
//        String CoordinateLat = String.valueOf(entity.getCoordinates().getCoordinates()[1]);
//        String text = entity.getText();
//        //"https://api.twitter.com/1.1/statuses/update.json?status=first_tweet2"
//        return new URI(API_BASE_URI +  POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(text)
//                + AMPERSAND + "long" + EQUAL + CoordinateLong + AMPERSAND + "lat" + EQUAL + CoordinateLat);
//    }
//
//    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){
//
//    }
//
//
//    @Override
//    public Tweet findById(String s) {
//        return null;
//    }
//
//    @Override
//    public Tweet deleteById(String s) {
//        return null;
//    }
//}
