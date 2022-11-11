/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

// VISITOR PATTERN -- UserComponent will extend Visitable (UserComponent accepts visitors)
public interface Visitable {
    public int accept(UserComponentVisitor visitor);
}