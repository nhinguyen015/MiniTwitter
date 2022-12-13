/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

import java.util.ArrayList;
import java.util.List;

// OBSERVER PATTERN -- User is both the Subject and Observer (followers/followings)
// COMPOSITE PATTERN -- User is the leaf
// VISITOR PATTERN -- accept method
public class User extends Subject implements UserComponent, Observer {
    private int userCount = 0;
    private int messageCount = 0;
    private int positiveCount = 0;
    private String userID;
    private List<User> followers;
    private List<User> followings;
    private String messages;
    private List<String> feed;
    private long creationTime; // HW 3 #2
    private long lastUpdateTime; // HW 3 #3

    public User(){
        userCount++;
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        messages = "";
        feed = new ArrayList<>();
        this.creationTime = System.currentTimeMillis(); // HW 3 #2 -- new User created
    }

    public User(String newID){
        userCount++;
        userID = newID;
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        messages = "";
        feed = new ArrayList<>();
        this.creationTime = System.currentTimeMillis(); // HW 3 #2 -- new User created
    }

    public int getUserCount(){
        return userCount;
    }

    public int getPositiveCount(){
        return positiveCount;
    }

    public int getMessageCount(){
        return messageCount;
    }

    // COMPOSITE PATTERN -- method from interface
    @Override
    public String getID() {
        return userID;
    }

    public List<User> getFollowers(){
        return followers;
    }

    public void setFollowers(User newFollower){
        followers.add(newFollower);
    }

    public List<User> getFollowings(){
        return followings;
    }

    public void setFollowings(User newFollowing){
        followings.add(newFollowing);
    }

    public String getMessages(){
        return messages;
    }

    public void setMessages(String myMessages){
        lastUpdateTime = System.currentTimeMillis(); // HW 3 #3 -- new tweet posted
        messages = myMessages;
        notifyObservers();
        if(messages.contains("good") || messages.contains("great") || messages.contains("positive") || messages.contains(":)") ){
            positiveCount++;
        }
        messageCount++;
    }

    public List<String> getFeed(){
        return feed;
    }

    public void setFeed(String message){
        lastUpdateTime = System.currentTimeMillis(); // HW 3 #3 -- new tweet posted (user and followers)
        feed.add(message);
    }

    // OBSERVER PATTERN -- observe feed of followings
    public void update(Subject subject) {
        if(subject instanceof User){
            System.out.println(((User) subject).getID() + "'s New Message: " + ((User) subject).getMessages());
            for(int i = 0 ; i < ((User) subject).getFollowers().size() ; i++){ // for all followers, update feed
                ((User) subject).getFollowers().get(i).setFeed(((User) subject).getMessages() + " Updated: " + lastUpdateTime);

            }
        }
    }

    // VISITOR PATTERN -- accept method
    @Override
    public int accept(UserComponentVisitor visitor) {
        return visitor.visit(this);
    }

    // HW 3
    public long getCreationTime(){
        return creationTime;
    }

    public long getUpdateTime(){
        return lastUpdateTime;
    }
}