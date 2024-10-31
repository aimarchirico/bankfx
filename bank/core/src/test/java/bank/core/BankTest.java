package bank.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Bank} class.
 */
public class BankTest {

  private Bank bank;
  private User user1;
  private User user2;
  private Account account1;
  private Account account2;
  private Account account3;

  /**
   * Sets up the test environment before each test. Initializes a {@link Bank} instance, two
   * {@link User} objects, and their {@link Account} objects.
   */
  @BeforeEach
  public void setUp() {
    bank = Bank.getInstance();

    user1 = new User("01010044556", "Test", "Hei123");
    user2 = new User("01010044557", "Test", "Hei123");
    account1 = new Account(500.0, "Test", "Savings Account");
    account2 = new Account(100.0, "Test", "Checking Account");
    account3 = new Account(100.0, "Test", "Savings Account");

    user1.addAccount(account1);
    user1.addAccount(account2);
    user2.addAccount(account3);

    bank.addUser(user1);
    bank.addUser(user2);
  }

  /**
   * Cleans up the test environment after each test. Removes the {@link User} objects added during the
   * setup.
   */
  @AfterEach
  public void tearDown() {
    bank.getUsers().forEach(u -> u.getAccounts().forEach(a -> a.withdraw(a.getBalance())));
    bank.removeUser(user1);
    bank.removeUser(user2);
  }

  /**
   * Tests successful credential match.
   */
  @Test
  @DisplayName("Test successful verifyCredentials")
  public void testSuccessfulVerifyCredentials() {
    assertDoesNotThrow(() -> {
      bank.verifyCredentials("01010044556", "Hei123");
    });
  }

  /**
   * Tests failed credential match.
   */
  @Test
  @DisplayName("Test failed verifyCredentials")
  public void testFailedVerifyCredentials() {
    assertThrows(IllegalAccessException.class, () -> {
      bank.verifyCredentials("01010044556", "Hei1234");
    });
  }

  /**
   * Tests that a {@link User} is successfully added to the {@link Bank}.
   */
  @Test
  @DisplayName("Test addUser")
  public void testAddUser() {
    assertTrue(bank.getUsers().contains(user1), "User should be added to the bank");
  }

  /**
   * Tests that an {@link Account} can be retrieved by its number.
   */
  @Test
  @DisplayName("Test getAccountByNumber")
  public void testGetAccountByNumber() {
    Account foundAccount = bank.getAccountByNumber(account1.getAccountNumber());
    assertEquals(account1, foundAccount, "Should return the correct account by number");
  }

  /**
   * Tests the payment functionality between two {@link Account} objects.
   */
  @Test
  @DisplayName("Test payment")
  public void testPayment() {
    assertDoesNotThrow(() -> {
      bank.payment(user1, account1, account2, 100.0);
      assertEquals(400, account1.getBalance(), "Source account balance should be decreased");
      assertEquals(200, account2.getBalance(), "Target account balance should be increased");
    });
      }

  /**
   * Tests the transfer functionality between two {@link Account} objects.
   */
  @Test
  @DisplayName("Test transfer")
  public void testTransfer() {
    assertDoesNotThrow(() -> {
      bank.payment(user1, account1, account3, 100.0);
      assertEquals(400, account1.getBalance(), "Source account balance should be decreased");
      assertEquals(200, account3.getBalance(), "Target account balance should be increased");
    });
  }

  /**
   * Tests the withdrawal functionality of an {@link Account}.
   */
  @Test
  @DisplayName("Test withdrawal")
  public void testWithdraw() {
    assertDoesNotThrow(() -> {
      bank.withdraw(user1, account1, 200);
      assertEquals(300, account1.getBalance(), "Account balance should be decreased by the withdrawal amount");
    });
   }

  /**
   * Tests the deposit functionality of an {@link Account}.
   */
  @Test
  @DisplayName("Test deposit")
  public void testDeposit() {
    assertDoesNotThrow(() -> {
      bank.deposit(user1, account1, 200);
      assertEquals(700, account1.getBalance(), "Account balance should be increased by the deposit amount");
    });
  }

  /**
   * Tests the ownership verification of an {@link Account}.
   */

  @Test
  @DisplayName("Test isOwnerOfAccountCheck")
  public void testIsOwnerOfAccountCheck() {
    assertDoesNotThrow(() -> bank.isOwnerOfAccountCheck(user1, account1), "User should own the account");
    assertThrows(IllegalAccessException.class, () -> bank.isOwnerOfAccountCheck(user2, account1),
        "User2 should not own the account");
  }

  /**
   * Tests the {@link User} registration verification.
   */
  @Test
  @DisplayName("Test userCheck")
  public void testUserCheck() {
    User user3 = new User("01010044557", "Test", "Hei123");
    assertDoesNotThrow(() -> bank.userCheck(user1), "User should be registered in the bank");
    assertThrows(IllegalStateException.class, () -> bank.userCheck(user3), "User3 should not be registered");
  }
}
