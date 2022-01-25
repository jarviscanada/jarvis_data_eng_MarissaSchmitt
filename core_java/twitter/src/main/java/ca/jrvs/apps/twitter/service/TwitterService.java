package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service{

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) { this.dao = dao; }

    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet){
        int textLength = tweet.getText().length();
        Double latitude = tweet.getCoordinates().getCoordinates().get(0);
        Double longitude =  tweet.getCoordinates().getCoordinates().get(1);

        //checking length of tweet text
        if(textLength > 140){
            throw new RuntimeException("Max text length exceeded");
        }

        //checking range of latitude in coordinates
        if(latitude < -90 && latitude > 90)
        {
            throw new RuntimeException("Latitude value out of range");
        }

        //checking range of longitude in coordinates
        if(longitude < -180 && longitude > 180)
        {
            throw new RuntimeException("Longitude value out of range");
        }
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateIdFormat(id);
        return(Tweet) dao.findById(id);
    }

    private void validateIdFormat(String strId){
        String regex = "\\d+";
        if(!strId.matches(regex))
        {
            throw new RuntimeException("Not correct format for ID");
        }
    }

    private void validateManyIds(String ...ids){
        for(String id : ids){
            validateIdFormat(id);
        }
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        validateManyIds(ids);
        List<Tweet> tweets = new ArrayList<>();
        for(String id : ids)
        {
            tweets.add((Tweet)dao.deleteById(id));
        }
        return tweets;
    }
}
