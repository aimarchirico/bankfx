package bank.core;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a bank account with a balance, account holder name, and account type. Supports
 * depositing, withdrawing, and transferring funds. Each account is assigned a unique account
 * number.
 */
public class Account {
    private Double balance;
    private String name;
    private String accountType;
    private final long accountNumber;
    private final List<String> accountTypes = Collections.unmodifiableList(List.of("Sparekonto", "Brukskonto"));

    /**
     * Constructs a new `Account` object.
     *
     * @param balance The initial balance of the account. Must be greater than or equal to zero.
     * @param name The name of the account holder.
     * @param accountType The type of account. Must be one of the allowed account types
     * @throws IllegalArgumentException if the balance is negative or the account type is invalid.
     */
    public Account(Double balance, String name, String accountType) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance can't be less than 0, balance: " + balance);
        }
        accountTypeCheck(accountType);
        nameCheck(name);
        this.accountNumber = generateAccountNumber();
        this.balance = balance;
        this.name = name;
        this.accountType = accountType;
    }
    accountTypeCheck(accountType);
    nameCheck(name);
    bank = Bank.getInstance();
    this.accountNumber = generateAccountNumber();
    this.balance = balance;
    this.name = name;
    this.accountType = accountType;
    bank.addAccount(this);
  }

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
    accountTypeCheck(accountType);
    nameCheck(name);
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.name = name;
    this.accountType = accountType;
    bank = Bank.getInstance();
    bank.addAccount(this);
  }

    /**
     * Generates a unique account number for each account.
     * Ensures uniqueness by checking existing account numbers.
     * 
     * @return Account number.
     */
    private synchronized long generateAccountNumber() {
        return ThreadLocalRandom.current().nextLong(10000000000L, 100000000000L);
    }
    this.balance += amount;
  }

  /**
   * Withdraws an amount from the account.
   *
   * @param amount The amount to withdraw. Must be greater than or equal to zero
   * @throws IllegalArgumentException if the amount is negative
   */
  public void withDraw(Double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Can't withdraw negative amount, amount : " + amount + " Account: " + this.getName());
    }
    if (getBalance() - amount < 0) {
      throw new IllegalArgumentException(
          "Can't withdraw an amount that makes the balance negative: balance after withdrawl: "
              + (getBalance() - amount) + " Account: " + this.getName());
    }
    this.balance -= amount;
  }



  /**
   * Transfers an amount from this account to a given account.
   *
   * @param amount amount to transfer
   * @param accountNumber account number to destination account
   */
  public void transferTo(Double amount, long accountNumber) {
    Account recepientAccount = bank.getAccountByNumber(accountNumber);
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Can't transfer negative amount, amount : " + amount + " Account: " 
              + this.getName());
    }
    if (this.equals(recepientAccount)) {
      throw new IllegalArgumentException("Can't transfer to same account, Account: " 
          + this.getName());
    }
    this.withDraw(amount);
    recepientAccount.deposit(amount);
  }

  /**
   * Transfers an amount from another account to this account.
   *
   * @param amount amount to transfer
   * @param accountNumber account number to withdraw from
   */
  public void transferFrom(Double amount, long accountNumber) {
    Account senderAccount = bank.getAccountByNumber(accountNumber);
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Can't transfer negative amount, amount : " + amount + " Account: " 
              + this.getName());
    }
    if (this.equals(senderAccount)) {
      throw new IllegalArgumentException("Can't transfer to same account, Account: " 
          + this.getName());
    }
    senderAccount.withDraw(amount);
    this.deposit(amount);
  }

    /**
     * Changes the name of the account
     * 
     * @param name
     * @throws IllegalArgumentException if the name is not valid
     */
    public void changeName(String name) {
        nameCheck(name);
        this.name = name;
    }
    final String Name_regex = "^[a-zæøåÅA-ZÆØÅ\\s-]+$";
    if (!name.matches(Name_regex)) {
      throw new IllegalArgumentException("Name can only contain letters, spaces, or hyphens");
    }
  }

}
