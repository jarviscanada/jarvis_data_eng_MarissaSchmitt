package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private TwitterDao dao;
    private JsonParser JsonUtil;

    @Before
    public void setUp() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        //Set up dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        //pass dependency
        this.dao = new TwitterDao(httpHelper);
        this.JsonUtil = new JsonParser();
    }

    @Test
    public void create() throws Exception{
        String hashtag = "#abcd";
        String text = "@marissaschmitt6 Twitter Test " + hashtag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;

        Tweet postTweet = TwitterDao.TweetUtil.buildTweet(text, lon, lat);
        //System.out.println(JsonUtil.toPrettyJson(postTweet));
        System.out.println(JsonUtil.toJson(postTweet, true, false));
        Tweet tweet = dao.create(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    }
}