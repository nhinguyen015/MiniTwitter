/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter Assignment 2
 */

package minitwitter;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Admin extends Application {
    Button showMessageTotal = new Button("Show Message Total");
    TreeView<UserComponent> treeView = new TreeView<>();
    private int userTotalSum = 0;
    private int groupTotalInt = 0;
    private int currMessageInt = 0;
    private double positiveInt = 0;
    List<UserView> userViewList = new ArrayList<>();

    // Singleton Pattern -- limit to only one Admin
    private static Admin pointer;
    public static Admin getInstance() {
        if (pointer == null){
            synchronized (Admin.class) {
                if(pointer == null) {
                    pointer = new Admin();
                }
            }
        }
        return pointer;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<User> userList = new ArrayList<>();
        List<UserGroup> userGroupList = new ArrayList<>();

        UserGroup root = new UserGroup("Root");
        root.setEnteries(root);
        userGroupList.add(root);
        Label treeRootLabel = new Label("Root");
        treeRootLabel.setStyle("-fx-font-weight: bold;");
        TreeItem treeRoot = new TreeItem(treeRootLabel);
        treeView.setRoot(treeRoot);

        TextArea userID = new TextArea();
        userID.setPrefSize(125,20);
        TextArea groupID = new TextArea();
        groupID.setPrefSize(125,20);
        Button addUser = new Button("Add User");
        Button addGroup = new Button("Add Group");
        Button openUserView = new Button("Open User View");

        GridPane gridPane = new GridPane();
        gridPane.add(userID, 1, 0);
        gridPane.add(groupID, 1, 1);
        gridPane.add(addUser, 2, 0);
        gridPane.add(addGroup, 2, 1);
        gridPane.add(openUserView,1, 2, 2, 1 );
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        GridPane.setHalignment(openUserView, HPos.CENTER);

        Button showUserTotal = new Button("Show User Total");
        Button showGroupTotal = new Button("Show Group Total");
        Button showPositivePercentage = new Button ("Show Positive Percentage");
        GridPane gridPane1 = new GridPane();
        gridPane1.add(showUserTotal,0,0);
        gridPane1.add(showMessageTotal,0,1);
        gridPane1.add(showGroupTotal,1,0);
        gridPane1.add(showPositivePercentage,1,1);
        gridPane1.setVgap(5);
        gridPane1.setHgap(5);
        VBox vBox = new VBox(50, gridPane,gridPane1);
        HBox hBox = new HBox(10, treeView, vBox);

        // BUTTON ACTIONS
        addUser.setOnAction(event -> {
            for(int i = 0 ; i < userList.size() ; i++) {
                if(userList.get(i).getID().equals(userID.getText())) {
                    Alert duplicateUser = new Alert(Alert.AlertType.INFORMATION);
                    duplicateUser.setTitle("Error");
                    duplicateUser.setContentText("Error: User Already Added");
                    duplicateUser.setHeaderText("");
                    duplicateUser.show();
                    return;
                }
            }
            TreeItem<UserComponent> selectedTreeItem = (TreeItem) treeView.getSelectionModel().getSelectedItem();
            TreeItem<UserComponent> userIDTreeItem = new TreeItem(userID.getText());
            selectedTreeItem.getChildren().add(userIDTreeItem);
            User addedUser = new User(userID.getText());
            userList.add(addedUser);
        });

        addGroup.setOnAction(event -> {
            for(int i = 0 ; i < userGroupList.size() ; i++) {
                if(userGroupList.get(i).getID().equals(groupID.getText())) {
                    Alert duplicateGroup = new Alert(Alert.AlertType.INFORMATION);
                    duplicateGroup.setTitle("Error");
                    duplicateGroup.setContentText("Error: Group Already Created");
                    duplicateGroup.setHeaderText("");
                    duplicateGroup.show();
                    return;
                }
            }
            Label groupIDString = new Label(groupID.getText());
            groupIDString.setStyle("-fx-font-weight: bold;");
            TreeItem<UserGroup> usersNewTreeItem = new TreeItem(groupIDString);
            Object selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
            int row = treeView.getRow((TreeItem) selectedTreeItem);
            treeRoot.getChildren().add( row, usersNewTreeItem);
            groupTotalInt++;
            UserGroup newUserGroup = new UserGroup(groupID.getText());
            newUserGroup.setEnteries(newUserGroup);
            userGroupList.add(newUserGroup);
        });

        openUserView.setOnAction(event -> {
            TreeItem<UserComponent> selectedTreeItem = treeView.getSelectionModel().getSelectedItem();
            for (int i = 0 ; i < userList.size() ; i++) {
                if ((userList.get(i).getID()).equals(selectedTreeItem.getValue())) {
                    openUserView(userList.get(i), userList, userViewList);
                }
            }
        });

        showUserTotal.setOnAction(event -> {
            userTotalSum = 0;
            Alert userTotalAlert = new Alert(Alert.AlertType.INFORMATION);
            userTotalAlert.setTitle("User Total");
            userTotalAlert.setHeaderText("");
            System.out.println("Visiting User Total...");
            for(int i = 0 ; i < userList.size() ; i++) {
                userTotalSum = userTotalSum + userList.get(i).accept(new UserTotalVisitor());
            }
            userTotalAlert.setContentText("User Total: " + userTotalSum);
            userTotalAlert.show();
        });

        showGroupTotal.setOnAction(event -> {
            groupTotalInt = 0;
            Alert groupTotalAlert = new Alert(Alert.AlertType.INFORMATION);
            groupTotalAlert.setTitle("Group Total");
            groupTotalAlert.setHeaderText("");
            System.out.println("Visiting Group Total...");
            for(int i = 0 ; i < userGroupList.size() ; i++) {
                groupTotalInt = groupTotalInt + userGroupList.get(i).accept(new UserGroupTotalVisitor());
            }
            groupTotalAlert.setContentText("Group Total: " + groupTotalInt);
            groupTotalAlert.show();
        });
        
        showMessageTotal.setOnAction(event -> {
            currMessageInt = 0;
            Alert messageTotalAlert = new Alert(Alert.AlertType.INFORMATION);
            messageTotalAlert.setTitle("Message Total");
            messageTotalAlert.setHeaderText("");
            System.out.println("Visiting Message Total...");
            for(int i = 0 ; i < userViewList.size() ; i++) {
                currMessageInt = currMessageInt + userList.get(i).accept(new MessageTotalVisitor());
            }
            messageTotalAlert.setContentText("Message Total: " + currMessageInt);
            messageTotalAlert.show();
        });

        showPositivePercentage.setOnAction(event -> {
            currMessageInt = 0;
            for(int i = 0 ; i < userViewList.size() ; i++) {
                currMessageInt = currMessageInt + userList.get(i).accept(new MessageTotalVisitor());
            }
            positiveInt = 0;
            Alert positiveAlert = new Alert(Alert.AlertType.INFORMATION);
            positiveAlert.setTitle("Positive Messages Total");
            positiveAlert.setHeaderText("");
            System.out.println("Visiting Positive Messages Total...");
            for(int i = 0 ; i < userList.size() ; i++) {
                positiveInt = positiveInt + userList.get(i).accept(new PositiveTotalVisitor());
            }
            positiveAlert.setContentText("Positive Messages Total: " + String.format("%.02f", positiveInt/currMessageInt * 100) +"%");
            positiveAlert.show();
        });
        
        primaryStage.setTitle("Admin Control");
        hBox.setBackground(new Background(new BackgroundFill(Color.BEIGE,null,null)));
        Scene scene1 = new Scene(hBox, 550, 250);
        scene1.getStylesheets().add("styles.css");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    private void openUserView(User openID, List<User> userList, List<UserView> uvList) {
        UserView openView = new UserView(openID);
        userViewList.add(openView);
        openView.display(openID, userList, userViewList); // launch GUI
    }
}