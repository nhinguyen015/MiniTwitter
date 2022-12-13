/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

// OBSERVER PATTERN -- attach observer to new following
// GUI for each User
public class UserView {
    private String userViewID;
    private Admin admin = Admin.getInstance();
    private ListView newsFeedListView = new ListView();

    public UserView(User openID) {
        userViewID = openID.getID();
    }
    public String getUserViewID(){
        return userViewID;
    }

    // Launch the User View GUI
    public void display(User selectedTreeItem, List<User> userList, List<UserView> userViewList) {
        TextArea userIDTextArea = new TextArea();
        TextArea tweetMessageTextArea = new TextArea();
        userIDTextArea.setPrefSize(200,50);
        ListView currentFollowingListView = new ListView();
        Button followUserButton = new Button("Follow User");
        Button postTweetButton = new Button("Post Tweet");
        HBox hBox = new HBox(10, userIDTextArea, followUserButton);
        hBox.setAlignment(Pos.CENTER);

        // HW 3 #2 -- Display Creation Time in User View GUI
        Label creationTime = new Label("Creation Time: " + selectedTreeItem.getCreationTime());
        tweetMessageTextArea.setPrefSize(200,50);
        HBox hBox1 = new HBox(10, tweetMessageTextArea, postTweetButton);
        hBox1.setAlignment(Pos.CENTER);
        System.out.println("User Display For: " + selectedTreeItem.getID());

        // To populate GUI with current followings and feed
        for (int i = 0 ; i < selectedTreeItem.getFollowings().size() ; i++){
            currentFollowingListView.getItems().add(selectedTreeItem.getFollowings().get(i).getID());
        }
        for (int i = 0 ; i < selectedTreeItem.getFeed().size() ; i++){
            newsFeedListView.getItems().add(selectedTreeItem.getFeed().get(i));
        }

        // BUTTON FUNCTIONS
        followUserButton.setOnAction(event -> {
            User newFollowing = new User(userIDTextArea.getText());
            for (int i = 0 ; i < userList.size() ; i++){
                if ((userList.get(i).getID()).equals(newFollowing.getID())){
                    newFollowing = userList.get(i);
                }
            }

            selectedTreeItem.setFollowings(newFollowing); // add to followings list
            newFollowing.setFollowers(selectedTreeItem);
            newFollowing.attach(selectedTreeItem); // OBSERVER PATTERN -- attach to observer
            System.out.println(selectedTreeItem.getID() + " Now following: " + newFollowing.getID());
            currentFollowingListView.getItems().add(newFollowing.getID());
        });
        postTweetButton.setOnAction(event -> {
            selectedTreeItem.setMessages(tweetMessageTextArea.getText());

            // HW 3 #3 -- Include last updated in GUI
            newsFeedListView.getItems().add(selectedTreeItem.getID() + ": "+ tweetMessageTextArea.getText() + ",   Updated: " + selectedTreeItem.getUpdateTime());


            // add new message for followers
            // HW 3 #3 -- Display last updated for followers as well
            for (int i = 0 ; i < selectedTreeItem.getFollowers().size() ; i++){
                if(admin.userViewList.equals(selectedTreeItem.getFollowers().get(i))){
                    selectedTreeItem.getFollowers().get(i).setMessages(tweetMessageTextArea.getText() + ",   Updated: " + selectedTreeItem.getUpdateTime());
                }
            }

            // refresh the feed for followers
            for(int j = 0 ; j < selectedTreeItem.getFollowers().size() ; j++){
                for(int k = 0 ; k < userViewList.size() ; k++) {
                    if(selectedTreeItem.getFollowers().get(j).getID().equals(userViewList.get(k).getUserViewID())){
                        userViewList.get(k).newsFeedListView.getItems().add(selectedTreeItem.getID() + ": " + tweetMessageTextArea.getText() + ",   Updated: " + selectedTreeItem.getUpdateTime());
                        userViewList.get(k).newsFeedListView.refresh();
                    }
                }
            }
            selectedTreeItem.setFeed(selectedTreeItem.getID() + ": "+ tweetMessageTextArea.getText() + ",   Updated: " + selectedTreeItem.getUpdateTime());
        });

        VBox vBox = new VBox(10,creationTime, hBox, currentFollowingListView, hBox1, newsFeedListView);
        vBox.setStyle("-fx-background-color: #E1E8ED;");
        vBox.setAlignment(Pos.CENTER);
        Scene scene2 = new Scene(vBox, 350,400);
        scene2.getStylesheets().add("styles.css"); // button styles
        Stage secondStage = new Stage();
        secondStage.setScene(scene2);
        secondStage.setTitle(selectedTreeItem.getID());
        secondStage.show();
    }
}