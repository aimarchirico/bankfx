package bank.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Bank class represents a central entity managing accounts. It uses the Singleton pattern to
 * ensure only one instance exists.
 */
public class Bank {
  private final List<User> users = new ArrayList<>();

  /**
   * Private constructor to prevent instantiation from outside the class. Initializes the Bank with an
   * empty account registry.
   */
  private Bank() {}

  /**
   * A static inner class that holds the single instance of the Bank class.
   * <p>
   * This inner class is not loaded until it is referenced
   */
  private static class SingletonHolder {
    private static final Bank INSTANCE = new Bank();
  }

  /**
   * @return the singleton instance of Bank.
   */
  public static Bank getInstance() {
    return SingletonHolder.INSTANCE;
  }

  /**
 * Retrieves a list of all registered users in the bank.
 *
 * @return a list containing all User objects in the bank
 */
  public List<User> getUsers() {
    return new ArrayList<>(users); 
  }
  
  /**
 * Retrieves a user by their social security number (SSN).
 *
 * @param ssn the social security number of the user to retrieve
 * @return the User object associated with the given SSN
 * @throws IllegalArgumentException if no user is found with the given SSN
 */
  public User getUser(String ssn){
    return users.stream()
    .filter(user -> user.getSsn().equals(ssn))
    .findFirst()
    .orElse(null);
  }

  /**
   * Adds a user to the bank's records.
   * 
   * @param user the user to add.
   */
  public void addUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("user can not be null");
    }
    users.add(user);
  }

  /**
   * Sets a new list of users for the bank.
   * 
   * @param newUsers the list of users to be set.
   * @throws IllegalArgumentException if any user in the list is null.
   */
  public void setUsers(List<User> newUsers) {
    for (User user : newUsers) {
      if (user == null) {
        throw new IllegalArgumentException("user can not be null");
      }
      users.add(user);
    }
  }

  /**
   * Removes a user.
   *
   * @param user The user to be removed.
   * @throws IllegalArgumentException if the user is not registered.
   */
  public void removeUser(User user) {
    if (!users.contains(user)) {
      throw new IllegalArgumentException("The user is not registered");
    }
    users.remove(user);
  }

  /**
   * Retrieves an account by its account number.
   *
   * @param accountNumber The account number of the account to retrieve.
   * @return The account associated with the specified account number.
   * @throws IllegalArgumentException if the account number is not registered.
   */
  public Account getAccountByNumber(long accountNumber) {
    return users.stream()
        .flatMap(user -> user.getAccounts().stream())
        .filter(account -> account.getAccountNumber() == accountNumber)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Account with number " + accountNumber + " not found"));
  }


  /**
   * Transfers money from one account to another account the user does not have access to.
   *
   * @param user the user initiating the transfer.
   * @param sourceAccountNumber the account to withdraw from
   * @param targetAccountNumber the account to deposit to
   * @param amount the amount to transfer
   * @throws IllegalArgumentException if the user is not registered or if the user 
   * doesn't own the source account
   */

  public void payment(User user, long sourceAccountNumber, long targetAccountNumber, double amount){
    userCheck(user);
    Account sourceAccount = getAccountByNumber(sourceAccountNumber);
    Account targetAccount = getAccountByNumber(targetAccountNumber);
    isOwnerOfAccountCheck(user, sourceAccount);

    sourceAccount.withDraw(amount);
    targetAccount.deposit(amount);
  }

  /**
   * Transfers money from one account to another within the same user's accounts.
   * 
   * @param user the user initiating the transfer.
   * @param sourceAccountNumber the account to withdraw from.
   * @param targetAccountNumber the account to deposit to.
   * @param amount the amount to transfer.
   * @throws IllegalArgumentException if the user is not registered or if the user 
   * doesn't own either the source or target account.
   */
  public void transfer(User user, long sourceAccountNumber, long targetAccountNumber, double amount){
    userCheck(user);
    Account sourceAccount = getAccountByNumber(sourceAccountNumber);
    Account targetAccount = getAccountByNumber(targetAccountNumber);
    isOwnerOfAccountCheck(user, sourceAccount);
    isOwnerOfAccountCheck(user, targetAccount);

    sourceAccount.withDraw(amount);
    targetAccount.deposit(amount);
  }

  /**
   * Withdraws a specified amount from a user's account.
   *
   * @param user the user initiating the withdrawal.
   * @param sourceAccountNumber the account to withdraw from.
   * @param amount the amount to withdraw.
   * @throws IllegalArgumentException if the user is not registered or doesn't own the source account.
   */
  public void withdraw(User user, long sourceAccountNumber, double amount){
    userCheck(user);
    Account account = getAccountByNumber(sourceAccountNumber);
    isOwnerOfAccountCheck(user, account);

    account.withDraw(amount);
  }

  /**
   * Deposits a specified amount into a user's account.
   *
   * @param user the user initiating the deposit.
   * @param sourceAccountNumber the account to deposit into.
   * @param amount the amount to deposit.
   * @throws IllegalArgumentException if the user is not registered or doesn't own the source account.
   */
  public void deposit(User user, long sourceAccountNumber, double amount){
    userCheck(user);
    Account account = getAccountByNumber(sourceAccountNumber);
    isOwnerOfAccountCheck(user, account);

    account.deposit(amount);
  }

  /**
   * Checks if the user owns the given account.
   *
   * @param account the account to check
   * @throws IllegalArgumentException if the user doesn't own the account
   */
  public void isOwnerOfAccountCheck(User user, Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Account can not be null");
    }
    if (!user.getAccounts().contains(account)) {
      throw new IllegalArgumentException("You don't have access to this " 
          + account.getAccountType() + " account.");
    }
  }

  /**
   * Verifies if a user is registered in the bank's records.
   * 
   * @param user the user to verify.
   * @throws IllegalArgumentException if the user is null or not registered.
   */
  public void userCheck(User user){
    if (user == null){
      throw new IllegalArgumentException("user can not be null");
    }
    if(!users.contains(user)){
      throw new IllegalArgumentException("user is not registered");
    }
  }
}
