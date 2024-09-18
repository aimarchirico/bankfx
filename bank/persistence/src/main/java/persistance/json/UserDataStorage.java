package persistance.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import bank.core.User;

public class UserDataStorage {
    File file = new File("UserData");
    BankPersistance bankPers = new BankPersistance();
    List<User> users = new ArrayList<>();

    public boolean userDataExists() {
        return file.exists();
    }
    //Fetches user deata
    public void fetchUserData() {
        if (userDataExists()) {
            users = bankPers.readUserData(file, new TypeReference<List<User>>() {});
        } 
    }
    /*
     * Adds user to the existing user database
     * Checks if user already is in the database
     *
     */
    public void writeUserData(User user) {
        boolean exists = false;
        fetchUserData();
        for(User i : users) {
            if(i.ssnEquals(user)) {
                exists = true;
                break;
            }
        }
        if(!exists) {
            users.add(user);
            bankPers.writeToFile(file, users);
        }

    }
    /*
     * Gets User data from database by ssn
     * returns null if no user found
     */
    public User getUser(String ssn) {
        fetchUserData();
        for(User i : users) {
            if(i.getSsn().equals(ssn)) {
                return i;
            }
        }
        return null;
    }

}
