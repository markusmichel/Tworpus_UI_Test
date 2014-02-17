/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tweet;

import com.thoughtworks.xstream.XStream;

/**
 * Representation of a twitter tweet.
 * 
 * @TODO: Not used yet
 */
public class Tweet {
    private String id;
    private User user;
    private String date;
    private int retweets;
    private int favoured;
    private Message message;
    
    /**
     * Preconfigured XStream instance to perisist/unpersist tweet objects.
     * @return Configured XStream instance
     */
    public static XStream getXStream() {
        XStream xstream = new XStream();
        xstream.alias("tweets", TweetContainer.class);
        xstream.addImplicitCollection(TweetContainer.class, "tweets");
        
        xstream.alias("tweet", Tweet.class);
        xstream.alias("user", User.class);
        xstream.alias("text", Message.class);
        
        xstream.aliasField("text", Tweet.class, "message");
        xstream.aliasAttribute(Tweet.class, "id", "id");
        xstream.aliasAttribute(User.class, "userId", "id");      
        xstream.registerConverter(new MessageConverter());
        
        return xstream;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the retweets
     */
    public int getRetweets() {
        return retweets;
    }

    /**
     * @param retweets the retweets to set
     */
    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    /**
     * @return the favoured
     */
    public int getFavoured() {
        return favoured;
    }

    /**
     * @param favoured the favoured to set
     */
    public void setFavoured(int favoured) {
        this.favoured = favoured;
    }

    /**
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Message message) {
        this.message = message;
    }
}
