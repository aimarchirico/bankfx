package bank.persistence;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bank.core.User;

public class BankPersistence {
    
    private ObjectMapper om;

    public BankPersistence() {
        om = new ObjectMapper();
        om.registerModule(new SimpleModule().addDeserializer(User.class, new UserDeserializer()));
    }

    public void writeToFile(File file, List<User> users) {
        try {
            om.writeValue(file, users);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
    public List<User> readUserData(File file, TypeReference<List<User>> reference) {
        try {
            return om.readValue(file, reference);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
        
    }



}

