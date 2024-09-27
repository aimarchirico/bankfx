package bank.persistence;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import bank.core.User;

/**
 * Handles persistence of User data, allowing for reading and writing user lists to files.
 */
public class BankPersistence {

  private ObjectMapper om;

  /**
   * Initializes the BankPersistence object with a custom deserializer for User objects.
   */
  public BankPersistence() {
    om = new ObjectMapper();
    om.registerModule(new SimpleModule().addDeserializer(User.class, new UserDeserializer()));
  }


  /**
   * Writes a list of User objects to a file.
   * 
   * @param file  the file to write the user data to
   * @param users the list of users to write to the file
   */
  public void writeToFile(File file, List<User> users) {
    try {
      om.writeValue(file, users);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  /**
   * Reads a list of User objects from a file.
   * 
   * @param file      the file to read user data from
   * @param reference type reference indicating the expected data format
   * @return a list of User objects or null if an error occurs
   */
  public List<User> readUserData(File file, TypeReference<List<User>> reference) {
    try {
      return om.readValue(file, reference);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;

  }



}

