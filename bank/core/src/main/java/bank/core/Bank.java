package bank.core;

import java.util.HashMap;
import java.util.Map;

/**
 * The Bank class represents a central entity managing accounts.
 * It uses the Singleton pattern to ensure only one instance exists.
 */
public class Bank {
  private static Bank instance;
  private Map<Long, Account> accounts = new HashMap<>();

  /**
   * Private constructor to prevent instantiation from outside the class. Initializes the Bank with an
   * empty account registry.
   */
  private Bank() {}

  /**
   * Returns the single instance of the Bank class. If no instance exists, it creates one.
   *
   * @return the singleton instance of Bank.
   */
  public static synchronized Bank getInstance() {
    if (instance == null) {
      instance = new Bank();
    }
    return instance;
  }

  /**
   * Adds an account to the bank's records.
   * 
   * @param account the account to add.
   */
  public void addAccount(Account account) {
    accounts.put(account.getAccountNumber(), account);
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

  public boolean accountNumberInAccounts(long accountNumber){
    return accounts.containsKey(accountNumber);
  }
}
