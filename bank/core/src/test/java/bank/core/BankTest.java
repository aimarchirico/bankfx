package bank.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Bank} class, ensuring that account management functionalities work as
 * expected.
 */

public class BankTest {
  private Bank bank;
  private Account testAccount1;

  /**
   * Sets up the test environment by initializing the Bank instance and creating a test account.
   */
  @BeforeEach
  void setUp() {
    bank = Bank.getInstance();

    testAccount1 = new Account(10.0, "Sindre", "Brukskonto");
  }

  /**
   * Cleans up after each test by removing the test account created for the test.
   */
  @AfterEach
  void tearDown() {
    if (bank.accountNumberInAccounts(testAccount1.getAccountNumber())) {
      bank.removeAccount(testAccount1.getAccountNumber());
    }
  }

  /**
   * Tests the addition of an account to the bank and checks for proper handling of null accounts.
   */
  @Test
  void testAddAccount() {
    bank.addAccount(testAccount1);
    assertTrue(bank.accountNumberInAccounts(testAccount1.getAccountNumber()));
    assertThrows(IllegalArgumentException.class, () -> bank.addAccount(null));
  }

  /**
   * Tests retrieving an account by its account number and checks for proper handling of invalid
   * account numbers.
   */
  @Test
  void testGetAccountByNumber() {
    bank.addAccount(testAccount1);
    long invalidAccountNumber = 5;
    Account retrievedAccount = bank.getAccountByNumber(testAccount1.getAccountNumber());
    assertEquals(testAccount1, retrievedAccount);
    assertThrows(IllegalArgumentException.class, () -> bank.getAccountByNumber(invalidAccountNumber));
  }

  /**
   * Tests the removal of an account from the bank and checks for proper handling of non-existent
   * accounts.
   */
  @Test
  void testRemoveAccount() {
    bank.addAccount(testAccount1);
    bank.removeAccount(testAccount1.getAccountNumber());
    assertFalse(bank.accountNumberInAccounts(testAccount1.getAccountNumber()));
    assertThrows(IllegalArgumentException.class, () -> bank.removeAccount(testAccount1.getAccountNumber()));
  }
}
