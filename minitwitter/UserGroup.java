/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

import java.util.ArrayList;
import java.util.List;

// VISITOR PATTERN -- accept method
// COMPOSITE PATTERN -- UserGroup is the Composite
public class UserGroup implements UserComponent {
    private int userGroupCount = 0;
    private String groupID;
    private List<UserComponent> enteries; //COMPOSITE PATTERN -- list of UserComponent (interface)
    private long creationTime; // HW 3 #2

    public UserGroup(){
        userGroupCount++;
        enteries = new ArrayList<UserComponent>();
        creationTime = this.creationTime = System.currentTimeMillis(); // HW 3 #2 -- new user group created
    }

    public UserGroup(String newID){
        userGroupCount++;
        enteries = new ArrayList<UserComponent>();
        groupID = newID;
        creationTime = this.creationTime = System.currentTimeMillis(); // HW 3 #2 -- new user group created
    }

    public int getUserGroupCount(){
        return userGroupCount;
    }

    public void setEnteries(UserComponent newUser){
        enteries.add(newUser);
    }

    public List<UserComponent> getEnteries(){
        return enteries;
    }

    // COMPOSITE PATTERN -- method from interface
    @Override
    public String getID() {
        return groupID;
    }

    // VISITOR PATTERN -- accept method
    @Override
    public int accept(UserComponentVisitor userGroupVisitor) {
        return userGroupVisitor.visit(this);
    }

    // HW 3
    public long getCreationTime(){
        return creationTime;
    }
}