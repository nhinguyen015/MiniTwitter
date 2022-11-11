/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

// VISITOR PATTERN -- for User Total Button
public class UserTotalVisitor implements UserComponentVisitor {
    @Override
    public int visit(User visitUserCount) {
        return visitUserCount.getUserCount();
    }

    @Override
    public int visit(UserGroup userGroupVisitor) {
        return 0;
    }
}