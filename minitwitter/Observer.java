/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

// OBSERVER PATTERN -- User will implement this (followers)
public interface Observer {
    public void update(Subject subject);
}