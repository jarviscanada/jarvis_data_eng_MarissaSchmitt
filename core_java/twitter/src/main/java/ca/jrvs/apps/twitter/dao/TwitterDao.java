package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import ca.jrvs.apps.twitter.example.JsonParser;


public class TwitterDao implements CrdDao<Tweet, String>{

    //URI Constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show/";
    private static final String DELETE_PATH = "/1.1/statuses/destroy/";
    private static final String JSON_URI = ".json";
    //URI Symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;
    private JsonParser JsonUtil;

    @Autowired
    public TwitterDao(HttpHelper httpHelper){
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet entity) {
        URI uri;
        try{
            uri = getPostUri(entity);
        } catch(URISyntaxException | UnsupportedEncodingException e){
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        //executing the http request
        HttpResponse httpResponse = httpHelper.httpPost(uri);

        return null;
    }

    private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode)
    {
        Tweet tweet = null;

        //Check status code in response
        int status = response.getStatusLine().getStatusCode();
        if(status != expectedStatusCode){
            try{
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch(IOException e){
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        if(response.getEntity() == null){
            throw new RuntimeException("Empty response body");
        }

        //Convert response entity to string
        String jsonStr;
        try{
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch(IOException e) {
            throw new RuntimeException("Failed to convert entity to string");
        }

        try{
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        } catch(IOException e) {
            throw new RuntimeException("Unable to convert JSON string to Object", e);
        }

        return tweet;
    }

    private URI getPostUri(Tweet entity) throws URISyntaxException, UnsupportedEncodingException{
        URI uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status=" + entity.getText());
        return uri;
    }

    @Override
    public Tweet findById(String s) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        try{
            URI uri = new URI(API_BASE_URI + SHOW_PATH + s + JSON_URI);
            HttpGet request = new HttpGet(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }
}
