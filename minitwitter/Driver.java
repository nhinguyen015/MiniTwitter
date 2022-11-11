/*
 * Nhi Nguyen
 * CS3650
 * Mini Twitter
 */

package minitwitter;

public class Driver{

    public static void main(String[] args) {
        //SINGLETON PATTERN -- trigger Admin
        Admin.launch(Admin.class, args);
    }
}