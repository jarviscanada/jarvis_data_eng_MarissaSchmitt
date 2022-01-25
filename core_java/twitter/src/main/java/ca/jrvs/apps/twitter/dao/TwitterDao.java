package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.jrvs.apps.twitter.example.JsonParser;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String>{

    //URI Constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy.json";
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
        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode)
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
            tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
        } catch(IOException e) {
            throw new RuntimeException("Unable to convert JSON string to Object", e);
        }

        return tweet;
    }

    private URI getPostUri(Tweet entity) throws URISyntaxException, UnsupportedEncodingException{
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        Double lat = entity.getCoordinates().getCoordinates().get(0);
        Double lon = entity.getCoordinates().getCoordinates().get(1);
        URI uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(entity.getText())
                + AMPERSAND + "lat" + EQUAL + lat.toString() + AMPERSAND + "lon" + EQUAL + lon.toString());
        return uri;
    }

    @Override
    public Tweet findById(String s) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        try{
            URI uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s );
            HttpResponse response = retrieveResponse(uri, HttpMethod.GET);
            String jsonStr = EntityUtils.toString(response.getEntity());
            Tweet tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
            return tweet;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        try{
            URI uri = new URI(API_BASE_URI + DELETE_PATH + QUERY_SYM + "id" + EQUAL + s );
            HttpResponse response = retrieveResponse(uri, HttpMethod.POST);
            String jsonStr = EntityUtils.toString(response.getEntity());
            Tweet tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
            return tweet;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpResponse retrieveResponse(URI uri, HttpMethod method)
    {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        HttpClient httpClient = HttpClientBuilder.create().build();

        if(method == HttpMethod.GET)
        {
            HttpGet getRequest = new HttpGet(uri);
            try{
                consumer.sign(getRequest);
                return httpClient.execute(getRequest);
            } catch(OAuthMessageSignerException | OAuthExpectationFailedException |
                    OAuthCommunicationException | IOException e) {
                e.printStackTrace();
            }
        } else if (method == HttpMethod.POST){
            HttpPost postRequest = new HttpPost(uri);
            try{
                consumer.sign(postRequest);
                return httpClient.execute(postRequest);
            } catch(OAuthMessageSignerException | OAuthExpectationFailedException |
                    OAuthCommunicationException | IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    public static class TweetUtil
    {
        public static Tweet buildTweet(String text, Double lon, Double lat)
        {
            Tweet tweet = new Tweet();
            Coordinates coordinatesObj = new Coordinates();
            List<Double> coordList = new ArrayList<>();
            coordList.add(lat);
            coordList.add(lon);
            tweet.setText(text);
            coordinatesObj.setCoordinates(coordList);
            tweet.setCoordinates(coordinatesObj);
            return tweet;
        }
    }
}
