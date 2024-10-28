package bank.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Represents a user in the bank system. A user has an SSN, name, password, and a {@link List} of
 * {@link Account}s.
 */
public class User {
  private final String ssn;
  private final String name;
  private final String password;
  private List<Account> accounts;

  /**
   * Constructs a new User with the given SSN, name, and password.
   *
   * @param ssn the User's social security number
   * @param name the User's name
   * @param password the User's password
   */
  public User(String ssn, String name, String password) {
    ssnCheck(ssn);
    passwordCheck(password);
    nameCheck(name);
    this.ssn = ssn;
    this.name = name;
    this.password = password;
    this.accounts = new ArrayList<>();
    this.addAccount("Checking Account", name);
  }

  /**
   * Constructs a new User with the given SSN, name, password, and {@link List} of {@link Account}s.
   *
   * @param ssn the user's social security number
   * @param name the user's name
   * @param password the user's password
   * @param accounts list of user accounts
   */
  public User(String ssn, String name, String password, List<Account> accounts) {
    ssnCheck(ssn);
    passwordCheck(password);
    nameCheck(name);
    this.ssn = ssn;
    this.name = name;
    this.password = password;
    this.accounts = accounts;

  }


  /**
   * Gets the list of accounts.
   *
   * @return the list of accounts
   */
  public List<Account> getAccounts() {
    return this.accounts;
  }

  /**
   * Gets the user's social security number.
   *
   * @return the user's SSN
   */
  public String getSsn() {
    return ssn;
  }

  /**
   * Gets the user's name.
   *
   * @return the user's name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the user's password.
   *
   * @return the user's password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Creates a new account for the user with the given account type.
   *
   * @param accountType the type of the new account
   * @param accountName the name of the account
   */
  protected Account addAccount(String accountType, String accountName) {
    Account account = new Account(0.0, accountName, accountType);
    addAccount(account);
    return account;
  }

  /**
   * Adds an account to the user's list of accounts.
   *
   * @param account the account to add
   * @throws IllegalArgumentException if the account is null or already exists
   */
  protected void addAccount(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Account can not be null");
    }
    if (accounts.contains(account)) {
      throw new IllegalArgumentException("Account already exists");
    }
    if (accounts.size() == 3) {
      throw new IllegalArgumentException("User can have a maximum of 3 accounts");
    }
    accounts.add(account);
  }

  /**
   * Deletes an account from the user's list of accounts.
   *
   * @param account the account to delete
   * @throws IllegalArgumentException if the user doesn't own the account
   */
  protected void removeAccount(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Account can not be null");
    }
    if (account.getBalance() != 0) {
      throw new IllegalArgumentException("The balance has to be 0");
    }
    accounts.remove(account);
  }

  /**
   * Checks if the given password is valid.
   *
   * @param password the password to check
   * @throws IllegalArgumentException if the password is invalid
   */
  protected void passwordCheck(String password) {
    if (password == null) {
      throw new IllegalArgumentException("Password can not be null");
    }
    if (password.length() < 6) {
      throw new IllegalArgumentException("Password needs at least 6 charachters");
    }
    final String password_regex = "^(?=.*[a-zæøå])(?=.*[A-ZÆØÅ])(?=.*\\d).+$";
    Pattern pattern = Pattern.compile(password_regex);
    if (!pattern.matcher(password).matches()) {
      throw new 
          IllegalArgumentException(
          "Password needs at least 1 small letter, 1 capital letter and 1 number");
    }
  }

  /**
   * Validates the user's social security number.
   *
   * @param ssn the SSN to check
   * @throws IllegalArgumentException if the SSN is invalid
   */
  protected void ssnCheck(String ssn) {
    if (ssn == null) {
      throw new IllegalArgumentException("Social security number can not be null");
    }
    if (ssn.length() != 11 || !ssn.matches("\\d+")) {
      throw new IllegalArgumentException("Social security number has to be 11 numbers");
    }
    int day = Integer.parseInt(ssn.substring(0, 2));
    int month = Integer.parseInt(ssn.substring(2, 4));
    int year = Integer.parseInt(ssn.substring(4, 6));

    year += (year >= 24 ? 1900 : 2000);

    String dateStr = String.format("%02d%02d%04d", day, month, year);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    try {
      LocalDate.parse(dateStr, formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalStateException("The first 6 numbers needs to be a date");
    }

  }

  /**
   * Validates the user's name.
   *
   * @param name the name to check
   * @throws IllegalArgumentException if the name is invalid
   */
  protected void nameCheck(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name can not be null");
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

