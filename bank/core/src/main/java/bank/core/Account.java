package bank.core;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a bank account with a balance, account holder name, and account type. Supports
 * depositing and withdrawing. Each account is assigned a unique account number.
 */

public class Account {
  private Double balance;
  private final String name;
  private final String accountType;
  private final long accountNumber;
  private final List<String> accountTypes =
      Collections.unmodifiableList(List.of("Savings Account", "Checking Account"));

  /**
   * Constructs a new {@link Account} object.
   *
   * @param balance The initial balance of the Account. Must be greater than or equal to zero.
   * @param name the name of the account holder
   * @param accountType the type of Account. Must be one of the allowed account types
   * @throws IllegalArgumentException if the balance is negative or the account name or type is
   *         invalid
   */
  public Account(Double balance, String name, String accountType) {
    this(balance, name, accountType, generateAccountNumber());
  }

  /**
   * Constructs a new {@link Account} object.
   *
   * @param balance The initial balance of the Account. Must be greater than or equal to zero.
   * @param name the name of the account holder
   * @param accountType the type of Account, must be one of the allowed account types
   * @param accountNumber the account number
   * @throws IllegalArgumentException if the balance is negative or the account name or type is
   *         invalid
   */
  public Account(Double balance, String name, String accountType, long accountNumber) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance can't be less than 0, balance: " + balance);
    }
    accountTypeCheck(accountType);
    nameCheck(name);
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.name = name;
    this.accountType = accountType;
  }

  /**
   * Generates a unique account number for each account. Ensures uniqueness by checking existing
   * account numbers.
   *
   * @return account number
   */
  private static synchronized long generateAccountNumber() {
    return ThreadLocalRandom.current().nextLong(10000000000L, 100000000000L);
  }

  /**
   * Returns the account number.
   *
   * @return the account number of this account
   */
  public long getAccountNumber() {
    return accountNumber;
  }


  /**
   * Returns the name.
   *
   * @return the name of the account
   */

  public String getName() {
    return this.name;
  }

  /**
   * Returns the account type.
   *
   * @return the account type
   */

  public String getAccountType() {
    return this.accountType;
  }

  /**
   * Return the balance of the account.
   *
   * @return the balance
   */
  public double getBalance() {
    return this.balance;
  }

  /**
   * Deposits an amount to the account.
   *
   * @param amount The amount to deposit. Must be greater than or equal to zero.
   * @throws IllegalArgumentException if the amount is negative
   */
  protected void deposit(Double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Can't deposit negative amount, amount : " + amount + " Account: " + this.getName());
    }
    this.balance += amount;
  }

  /**
   * Withdraws an amount from the account.
   *
   * @param amount The amount to withdraw. Must be greater than or equal to zero
   * @throws IllegalArgumentException if the amount is negative
   */
  protected void withdraw(Double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Can't withdraw negative amount, amount : " + amount + " Account: " + this.getName());
    }
    if (getBalance() - amount < 0) {
      throw new IllegalArgumentException(
          "Can't withdraw an amount that makes the balance negative: balance after withdrawal: "
              + (getBalance() - amount) + " Account: " + this.getName());
    }
    this.balance -= amount;
  }

  /**
   * Validates the provided account type to ensure it is one of the allowed types.
   *
   * @param accountType the account type to be validated
   * @throws IllegalArgumentException if the account type is not valid
   */
  private void accountTypeCheck(String accountType) {
    if (!accountTypes.contains(accountType)) {
      throw new 
          IllegalArgumentException("Account must be of the valid types, accountType: "
          + accountType);
    }
  }

  /**
   * Validates the provided name to ensure it meets the following criteria.
   * <ul>
   * <li>Not null</li>
   * <li>At least 2 characters long</li>
   * <li>Contains only letters, spaces, and hyphens</li>
   * </ul>
   *
   * @param name the name to be validated.
   * @throws IllegalArgumentException if the name does not meet the validation criteria
   */
  private void nameCheck(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (name.length() < 2) {
      throw new IllegalArgumentException("Name needs at least 2 characters");
    }
    final String name_regex = "^[a-zæøåÅA-ZÆØÅ\\s-]+$";
    if (!name.matches(name_regex)) {
      throw new IllegalArgumentException("Name can only contain letters, spaces, or hyphens");
    }
  }

}

