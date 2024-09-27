package bank.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import bank.core.User;

/**
 * Class responsible for managing user data storage and retrieval. It interacts with the
 * BankPersistence class to read and write user data to a specified file.
 */
public class UserDataStorage {
  File file;
  BankPersistence bankPers = new BankPersistence();
  List<User> users = new ArrayList<>();

  /**
   * Constructs a UserDataStorage instance with the specified file path.
   *
   * @param path the path to the user data file
   */
  public UserDataStorage(String path) {
    file = new File(path);
  }

  /**
   * Checks if the user data file exists.
   *
   * @return true if the user data file exists, false otherwise
   */
  public boolean userDataExists() {
    return file.exists();
  }

  /**
   * Fetches user data from the file if it exists and populates the users list. If the file does not
   * exist or cannot be read, an empty list is initialized.
   */
  public void fetchUserData() {
    if (userDataExists()) {
      users = bankPers.readUserData(file, new TypeReference<List<User>>() {});
    }
    if (users == null) {
      users = new ArrayList<>();
    }
  }

  /**
   * Writes a user to the data file if the user does not already exist. It first fetches existing user
   * data and checks for duplicates before writing.
   *
   * @param user the user to be added
   */
  public void writeUserData(User user) {
    boolean exists = false;
    fetchUserData();
    for (User i : users) {
      if (i.ssnEquals(user)) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      users.add(user);
      bankPers.writeToFile(file, users);
    }

  }

  /**
   * Retrieves a user by their social security number (SSN).
   *
   * @param ssn the social security number of the user to retrieve
   * @return the User object if found, or null if not found
   */
  public User getUser(String ssn) {
    fetchUserData();
    for (User i : users) {
      if (i.getSsn().equals(ssn)) {
        return i;
      }
    }
    return null;
  }

}
