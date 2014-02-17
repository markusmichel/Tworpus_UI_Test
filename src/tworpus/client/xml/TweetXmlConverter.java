/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.xml;

import com.thoughtworks.xstream.XStream;
import tweet.Tweet;
import tweet.TweetContainer;

/**
 * Static class for converting: 
 * Tweets -> XML/String
 * XML -> Tweets[]
 */
public class TweetXmlConverter {
    
    public static String XmlToTweets(String xml) {
        
        return "";
    }
    
    public static String tweetsToXml(Tweet[] tweets) {
        XStream xstream = Tweet.getXStream();
               
        TweetContainer tweetCon = new TweetContainer();
        tweetCon.tweets = tweets;
        
        String out = xstream.toXML(tweetCon);
        
        return out;
    }
    
}
