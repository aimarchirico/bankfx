package bank.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import bank.core.User;

public class UserDataStorage {
    File file = new File("../persistence/src/main/resources/bank/persistence/UserData.json");
    BankPersistence bankPers = new BankPersistence();
    List<User> users = new ArrayList<>();

    public boolean userDataExists() {
        return file.exists();
    }
    
    public void fetchUserData() {
        if (userDataExists()) {
            users = bankPers.readUserData(file, new TypeReference<List<User>>() {});
        }
        if (users == null) {
            users = new ArrayList<>();
        }
    }
   
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