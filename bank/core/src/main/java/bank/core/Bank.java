package bank.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Bank class represents a central entity managing accounts. 
 * It uses the Singleton pattern to ensure only one instance exists.
 */
public class Bank {
  private List<User> users = new ArrayList<>();
  private static final User ADMIN_TEMPLATE = new User("01010000000", "admin", "A123456z",
      List.of(new Account(0.0, "admin", "Checking Account", 10000000000L)));

  /**
   * Private constructor to prevent instantiation from outside the class. 
   * Initializes the Bank with an empty account registry.
   */
  private Bank() {}

  /**
   * A static inner class that holds the single instance of the Bank class. This inner class is not
   * loaded until it is referenced.
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
   * Returns an admin.
   *
   * @return the bank admin.
   */
  public static User getAdminTemplate() {
    return new User(ADMIN_TEMPLATE.getSsn(), ADMIN_TEMPLATE.getName(), ADMIN_TEMPLATE.getPassword(),
        ADMIN_TEMPLATE.getAccounts());
  }

  /**
   * Verifies that SSN and password matches.
   *
   * @throws IllegalAccessException if SSN and password do not match
   */
  public void verifyCredentials(String ssn, String password) throws IllegalAccessException {
    User user = getUser(ssn);
    if (user != null && !user.getPassword().equals(password)) {
      throw new IllegalAccessException("SSN or password is incorrect.");
    }
  }



  /**
   * Retrieves a list of all registered {@link User}s in the Bank.
   *
   * @return a list containing all User objects in the Bank
   */
  public List<User> getUsers() {
    return new ArrayList<>(users);
  }

  /**
   * Retrieves a {@link User} by their social security number (SSN).
   *
   * @param ssn the social security number of the user to retrieve
   * @return the User associated with the given SSN, or null if not found
   */
  public User getUser(String ssn) {
    return users.stream().filter(user -> user.getSsn().equals(ssn)).findFirst().orElse(null);
  }

  /**
   * Adds a {@link User} to the Bank's records.
   *
   * @param user the User to add
   * @throws IllegalArgumentException if user is null
   * @throws IllegalStateException if ssn or an account is already registered
   */
  public void addUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null.");
    }
    if (getUser(user.getSsn()) != null) {
      throw new IllegalStateException("User with specified ssn already exists.");
    }
    for (Account account : user.getAccounts()) {
      if (getAccountByNumber(account.getAccountNumber()) != null) {
        throw new 
        IllegalStateException("User has a an account with already registered account number.");
      }
    }
    users.add(user);
  }

  /**
   * Adds an {@link Account} to the Bank's records.
   *
   * @param user the {@link User} of the Account.
   * @param account the Account to add.
   * @throws IllegalStateException if account number is already registered or User does not exist
   * @throws IllegalArgumentException if User or Account is null
   */
  public void addAccount(User user, Account account) {
    userCheck(user);
    if (account == null) {
      throw new IllegalArgumentException("Account cannot be null.");
    }
    if (getAccountByNumber(account.getAccountNumber()) != null) {
      throw new IllegalStateException("Account with specified account number already exists.");
    }
    if (user.getAccounts().stream()
        .anyMatch(acc -> acc.getName().equals(account.getName()))) {
      throw new IllegalArgumentException("This name is already in use.");
    }
    user.addAccount(account);
  }


  /**
   * Remove an {@link Account} from the Bank's records.
   *
   * @param user the{@link User} of the Account
   * @param account the Account to add
   * @throws IllegalAccessException if the User does not own the Account
   * @throws IllegalStateException if the User is not registered
   * @throws IllegalArgumentException if User or Account is null
   */
  public void removeAccount(User user, Account account) throws IllegalAccessException {
    userCheck(user);
    isOwnerOfAccountCheck(user, account);
    user.removeAccount(account);
  }

  /**
   * Sets a new {@link List} of {@link User}s for the Bank. Admin is always added.
   *
   * @param newUsers the list of users to be set
   * @throws IllegalArgumentException if any User in the list is null
   */
  public void setUsers(List<User> newUsers) {
    users.clear();
    for (User user : newUsers) {
      addUser(user);
    }
    if (getUser(ADMIN_TEMPLATE.getSsn()) == null) {
      addUser(ADMIN_TEMPLATE);
    }
  }

  /**
   * Removes a {@link User}.
   *
   * @param user The User to be removed
   * @throws IllegalArgumentException if the User is not registered or is admin
   * @throws IllegalStateException if balance is not zero
   */
  public void removeUser(User user) {
    userCheck(user);
    if (user.getSsn().equals(ADMIN_TEMPLATE.getSsn())) {
      throw new IllegalArgumentException("Cannot remove admin.");
    }
    if (user.getAccounts().stream().anyMatch(account -> account.getBalance() != 0)) {
      throw new IllegalStateException("Balance must be zero.");
    }
    users.remove(user);
  }


  /**
   * Retrieves an account by its account number.
   *
   * @param accountNumber The account number of the account to retrieve
   * @return The Account associated with the specified account number
   */
  public Account getAccountByNumber(long accountNumber) {
    return users.stream().flatMap(user -> user.getAccounts().stream())
        .filter(account -> account.getAccountNumber() == accountNumber).findFirst().orElse(null);
  }


  /**
   * Transfers money from one {@link Account} to another that the {@link User} does not have access
   * to.
   *
   * @param user the user initiating the transfer
   * @param sourceAccount the Account to withdraw from
   * @param targetAccount the Account to deposit to
   * @param amount the amount to transfer
   * @throws IllegalAccessException if the User does not own the source Account
   * @throws IllegalStateException if the User is not registered
   * @throws IllegalArgument if User or Account is null
   */

  public void payment(User user, Account sourceAccount, Account targetAccount, double amount)
      throws IllegalAccessException {
    userCheck(user);
    isOwnerOfAccountCheck(user, sourceAccount);
    if (targetAccount == null) {
      throw new IllegalArgumentException("Target account is invalid.");
    }
    sourceAccount.withdraw(amount);
    targetAccount.deposit(amount);
  }

  /**
   * Transfers money from one {@link Account} to another within the same {@link User}'s Accounts.
   *
   * @param user the user initiating the transfer.
   * @param sourceAccount the account to withdraw from.
   * @param targetAccount the account to deposit to.
   * @param amount the amount to transfer.
   * @throws IllegalAccessException if the User does not own the Accounts
   * @throws IllegalStateException if the User is not registered
   * @throws IllegalArgument if User or Account is null
   */
  public void transfer(User user, Account sourceAccount, Account targetAccount, double amount)
      throws IllegalAccessException {
    userCheck(user);
    isOwnerOfAccountCheck(user, sourceAccount);
    isOwnerOfAccountCheck(user, targetAccount);
    sourceAccount.withdraw(amount);
    targetAccount.deposit(amount);
  }

  /**
   * Withdraws a specified amount from a {@link User}s {@link Account}.
   *
   * @param user the User initiating the withdrawal
   * @param account the Account to withdraw from
   * @param amount the amount to withdraw
   * @throws IllegalAccessException if the User does not own the Account
   * @throws IllegalStateException if the User is not registered
   * @throws IllegalArgument if User or Account is null
   */
  public void withdraw(User user, Account account, double amount) throws IllegalAccessException {
    userCheck(user);
    isOwnerOfAccountCheck(user, account);
    account.withdraw(amount);
  }

  /**
   * Deposits a specified amount into a {@link User}s {@link Account}.
   *
   * @param user the User initiating the deposit
   * @param account the Account to deposit into
   * @param amount the amount to deposit
   * @throws IllegalAccessException if the User does not own the Account
   * @throws IllegalStateException if the User is not registered
   * @throws IllegalArgument if User or Account is null
   */
  public void deposit(User user, Account account, double amount) throws IllegalAccessException {
    userCheck(user);
    isOwnerOfAccountCheck(user, account);
    account.deposit(amount);
  }

  /**
   * Checks if the {@link User} owns the given {@link Account}.
   *
   * @param account the Account to check
   * @throws IllegalAccessException if User does not own Account
   * @throws IllegalArgumentException if the User is null
   */
  public void isOwnerOfAccountCheck(User user, Account account) throws IllegalAccessException {
    if (account == null) {
      throw new IllegalArgumentException("Account cannot be null.");
    }
    if (!user.getAccounts().contains(account)) {
      throw new 
          IllegalAccessException("You don't have access to this account.");
    }
  }

  /**
   * Verifies if a user is registered in the bank's records.
   *
   * @param user the user to verify.
   * @throws IllegalArgumentException if the user is null.
   * @throws IllegalStateException if the user is not registered.
   */
  public void userCheck(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null.");
    }
    if (!users.contains(user)) {
      throw new IllegalStateException("User is not registered.");
    }
  }
}
