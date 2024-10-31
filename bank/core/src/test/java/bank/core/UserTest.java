package bank.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link User} class.
 */
public class UserTest {
  String ssn = "23069456293";
  String name = "Kari Nordmann";
  String password = "passWord123";
  User user;
  Account account1;
  Account account2;
  List<Account> accounts;

  /**
   * Sets up the test environment by initializing two {@link USer}s and three {@link Account}s.
   */
  @BeforeEach
  public void setup() {
    user = new User(ssn, name, password);
    account1 = new Account(20000.00, "myChecking", "Checking Account");
    account2 = new Account(100000.00, "mySavings", "Savings Account");
    user.addAccount(account1);
    user.addAccount(account2);
  }

  /**
   * Tests that the constructor works by retrieving the getters.
   */
  @Test
  @DisplayName("Test constructor")
  public void testConstructor() {
    Assertions.assertEquals(name, user.getName());
    Assertions.assertEquals(ssn, user.getSsn());
    Assertions.assertEquals(password, user.getPassword());
    Assertions.assertTrue(user.getAccounts().contains(account1) & user.getAccounts().contains(account2));
  }

  /**
   * Tests that illegal arguments in the constructor throws an exception.
   */
  @Test
  @DisplayName("Test illegal arguments")
  public void testIllegalArguments() {
    assertThrows(IllegalStateException.class, () -> new User("32070043672", name, password)); // Illegal birthdate

    assertThrows(IllegalArgumentException.class, () -> new User("220700432672", name, password)); // Illegal social
                                                                                                  // security number

    assertThrows(IllegalArgumentException.class, () -> new User(null, name, password)); // Illegal social security
                                                                                        // number

    assertThrows(IllegalArgumentException.class, () -> new User(ssn, "Mar0tin Hansen", password)); // Illegal name

    assertThrows(IllegalArgumentException.class, () -> new User(ssn, "J", password)); // Illegal name

    assertThrows(IllegalArgumentException.class, () -> new User(ssn, null, password)); // Illegal name

    assertThrows(IllegalArgumentException.class, () -> new User(ssn, name, "Password")); // Illegal password

    assertThrows(IllegalArgumentException.class, () -> new User(ssn, name, "Pass6")); // Illegal password

    assertThrows(IllegalArgumentException.class, () -> new User(ssn, name, null)); // Illegal password


  }

  /**
   * Tests adding and removing an Account.
   */
  @DisplayName("Test add and remove Account")
  @Test
  public void testAddAndRemoveAccount() {
    account2.withdraw(account2.getBalance());
    user.removeAccount(account2);
    Account account3 = new Account(2000.0, "New Checking", "Checking Account");
    user.addAccount(account3);
    Assertions.assertFalse(user.getAccounts().contains(account2));
    Assertions.assertTrue(user.getAccounts().contains(account3));
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      user.addAccount(null);
    });


  }

}


