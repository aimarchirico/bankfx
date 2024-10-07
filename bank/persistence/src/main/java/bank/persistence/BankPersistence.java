package bank.persistence;

import bank.core.Account;
import bank.core.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistence of {@link User} data, allowing for reading and writing user lists to files.
 */
public class BankPersistence {

  private ObjectMapper om;

  /**
   * Initializes the {@link BankPersistence} object with a custom deserializer 
   * for {@link User} objects.
   */
  public BankPersistence() {
    om = new ObjectMapper();
    om.registerModule(new SimpleModule().addDeserializer(User.class, new UserDeserializer()));
  }


  /**
   * Writes a {@link List} of {@link User} objects to a {@link File}.
   *
   * @param file the file to write the user data to
   * @param users the list of users to write to the file
   */
  public void writeToFile(File file, List<User> users) {
    try {
      om.writerWithDefaultPrettyPrinter().writeValue(file, users);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  /**
   * Reads a {@link List} of {@link User} objects from a {@link File}.
   *
   * @param file the file to read user data from
   * @param reference type reference indicating the expected data format
   * @return a list of user objects or <code>null</code> if an error occurs
   */
  public List<User> readUserData(File file, TypeReference<List<User>> reference) {
    try {
      return om.readValue(file, reference);
    } catch (IOException e) {
      return null;
    }
  }

}

