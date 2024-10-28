package bank.rest;

import bank.core.Bank;
import bank.persistence.UserPersistence;
import org.springframework.stereotype.Service;

/**
 * Configures the bank service.
 */
@Service
public class BankService {

  private Bank bank;
  private UserPersistence userPersistence;
  private static final String SAVE_FILE = "users.json";

  /**
   * Initializes the service with the singleton {@link Bank} instance.
   * 
   */
  public BankService() {
    this.bank = Bank.getInstance();
    setUserPersistence(new UserPersistence(SAVE_FILE));
    retrieveUsers();
  }

  /**
   * Setter for userPersistence.
   * 
   */
  public void setUserPersistence(UserPersistence userPersistence) {
    this.userPersistence = userPersistence;
  }

  /**
   * Sets the users of the {@link Bank} by reading from file. 
   * Catches and prints exception if thrown.
   * 
   */
  public void retrieveUsers() {
    try {
      bank.setUsers(userPersistence.readFromFile());
    } catch (Exception e) {
      System.out.println("Failed retrieving user data from file: " + e.getMessage());
    }
  }

  /**
   * Saves the users of the {@link Bank} by writing to file. Catches and prints exception if thrown.
   * 
   */
  public void saveUsers() {
    try {
      userPersistence.writeToFile(bank.getUsers());
    } catch (Exception e) {
      System.out.println("Failed saving user data to file: " + e.getMessage());
    }
  }

  /**
   * returns the {@link Bank} instance.
   *
   * @return the Bank instance
   */
  public Bank getBank() {
    return bank;
  }

}
