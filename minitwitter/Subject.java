/*
 * Nhi Nguyen
 * CS3650 
 * Mini Twitter
 */

package minitwitter;

import java.util.ArrayList;
import java.util.List;

// OBSERVER PATTERN -- abstract class for Subject (User)
public abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers){
            observer.update(this);
        }
    }
}