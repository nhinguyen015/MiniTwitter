/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

// VISITOR PATTERN -- for any added visitors, implement this interface
public interface UserComponentVisitor {
    public int visit(User userVisitor);
    public int visit(UserGroup userGroupVisitor);
}