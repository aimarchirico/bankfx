package bank.core;

import java.util.HashMap;
import java.util.Map;

/**
 * The Bank class represents a central entity managing accounts. 
 * It uses the Singleton pattern to ensure only one instance exists.
 */
public class Bank {
  private final Map<Long, Account> accounts = new HashMap<>();

  /**
   * Private constructor to prevent instantiation from outside the class. 
   * Initializes the Bank with an empty account registry.
   */
  private Bank() {}

  /**
   * A static inner class that holds the single instance of the Bank class.
   * This inner class is not loaded until it is referenced.
   */
  private static class SingletonHolder {
    private static final Bank INSTANCE = new Bank();
  }

  /**
   * Returns the Bank instance.
   *
   * @return the singleton instance of Bank.
   */
  public static Bank getInstance() {
    return SingletonHolder.INSTANCE;
  }

  /**
   * Adds an account to the bank's records.
   *
   * @param account the account to add.
   */
  public void addAccount(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("account can not be null");
    }
    accounts.put(account.getAccountNumber(), account);
  }

  /**
   * Removes an account based on the account number.
   *
   * @param accountNumber The account number of the account to be removed.
   * @throws IllegalArgumentException if the account number is not registered.
   */
  public void removeAccount(long accountNumber) {
    if (!accountNumberInAccounts(accountNumber)) {
      throw new IllegalArgumentException("The account is not registered");
    }
    accounts.remove(accountNumber);
  }

  /**
   * Retrieves an account by its account number.
   *
   * @param accountNumber The account number of the account to retrieve.
   * @return The account associated with the specified account number.
   * @throws IllegalArgumentException if the account number is not registered.
   */
  public Account getAccountByNumber(long accountNumber) {
    if (!accounts.containsKey(accountNumber)) {
      throw new IllegalArgumentException("The account number is not registered");
    }
    return accounts.get(accountNumber);
  }

  public boolean accountNumberInAccounts(long accountNumber) {
    return accounts.containsKey(accountNumber);
  }
}
