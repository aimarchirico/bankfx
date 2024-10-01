package bank.persistence;

import bank.core.User;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for managing user data storage and retrieval. It interacts with the
 * {@link BankPersistence} class to read and write user data to a specified {@link File}.
 */
public class UserDataStorage {
  private File file;
  private BankPersistence bankPers = new BankPersistence();
  private List<User> users = new ArrayList<>();

  /**
   * Constructs a {@link UserDataStorage} instance with the specified file path.
   *
   * @param path the path to the user data file
   */
  public UserDataStorage(String path) {
    file = new File(path);
  }

  /**
   * Checks if the user data {@link File} exists.
   *
   * @return <code>true</code> if the user data file exists, <code>false</code> otherwise
   */
  public boolean userDataExists() {
    return file.exists();
  }

  /**
   * Fetches user data from the {@link File} if it exists and 
   * populates the users {@link List}. If the file does not
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
   * Writes a {@link User} to the data {@link File} if the user does not already exist.
   *
   * @param user the user to be added
   */
  public void writeUserData(User user) {
    fetchUserData();
    if (getUser(user.getSsn()) == null) {
      users.add(user);
      bankPers.writeToFile(file, users);
    }

  }

  /**
   * Deletes a {@link User} from the data {@link File}.
   *
   * @param user the user to be removed
   */
  public void deleteUserData(User user) {
    fetchUserData();
    users.removeIf(u -> u.getSsn().equals(user.getSsn()));
    bankPers.writeToFile(file, users);
  }

  /**
   * Resets the data {@link File}.
   */
  public void resetUserData() {
    fetchUserData();
    users.clear();
    users.add(new User("01010000000", "admin", "A123456z"));
    bankPers.writeToFile(file, users);
  }

  /**
   * Retrieve a {@link List} of {@link User}s.
   *
   * @return list of registered users
   */
  public List<User> getUsers() {
    fetchUserData();
    return Collections.unmodifiableList(users);
  }



  /**
   * Retrieves a {@link User} by their social security number (SSN).
   *
   * @param ssn the social security number of the user to retrieve
   * @return the user if found, or <code>null</code> if not found
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
