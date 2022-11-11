/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

// VISITOR PATTERN -- for User Group Total Button
public class UserGroupTotalVisitor implements UserComponentVisitor {
    @Override
    public int visit(User visitUserCount) {
        return 0;
    }

    @Override
    public int visit(UserGroup userGroupVisitor) {
        return userGroupVisitor.getUserGroupCount();
    }
}