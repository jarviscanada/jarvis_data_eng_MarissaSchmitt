package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceTest {

    private CrdDao dao;
    private TwitterService twitterService;
    @Before
    public void setUp() {
        //this.dao = new
        //this.dao = new CrdDao() {};
        this.twitterService = new TwitterService(this.dao);
    }

    @Test
    public void postTweet() {
        Tweet tweet = new Tweet();
        tweet.setText("Test message");
        Coordinates coords = new Coordinates();
        List<Double> latitudeLongitude = new ArrayList<>();
        latitudeLongitude.add(50.0);
        latitudeLongitude.add(0.0);
        coords.setCoordinates(latitudeLongitude);
        tweet.setCoordinates(coords);

        Tweet returnedTweet = twitterService.postTweet(tweet);
        assertEquals(tweet.getText(), returnedTweet.getText());
        assertNotNull(returnedTweet);
    }

    @Test
    public void showTweet() {
        String[] fields = {};
        Tweet tweet = twitterService.showTweet("1234", fields);

        assertEquals("1234", tweet.getTweetId());
        assertNotNull(tweet);
    }

    @Test
    public void deleteTweets() {
        String[] ids = {"129302832"};
        List<Tweet> tweet = twitterService.deleteTweets(ids);

        assertEquals("129302832", tweet.get(0).getTweetId());
        assertNotNull(tweet);
    }
}