package minitwitter;

import java.util.ArrayList;

// HW 3 #1 -- validates IDs, 1 is valid and 0 is invalid
public class ValidVisitor implements UserComponentVisitor {
    int validation = 1;

    @Override
    public int visit(User userVisitor) {
        if(userVisitor.getID().contains(" ")){
            validation = 0;
        }
        return validation;
    }

    @Override
    public int visit(UserGroup userGroupVisitor) {
        if(userGroupVisitor.getID().contains(" ")){
            validation = 0;
        }
        return validation;
    }
}