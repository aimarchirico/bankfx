package bank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Account class, verifying its behavior and error handling.
 */
public class AccountTest {
    Account account1;
    Account account2;

    /**
     * Initializes test accounts before each test.
     */
    @BeforeEach
    public void setup() {
        account1 = new Account(100.0, "sparekonto", "Sparekonto");
        account2 = new Account(100.0, "brukskonto", "Brukskonto");
    }

    /**
     * Tests the Account constructor for valid and invalid inputs.
     */
    @Test
    public void testConstructor() {
        // Illegal balance
        assertThrows(IllegalArgumentException.class, () -> new Account(-1.0, "MinKonto", "Sparekonto"));
        // Illegal account type
        assertThrows(IllegalArgumentException.class, () -> new Account(10.0, "MinAndreKonto", "Lån"));
        // Illegal name
        assertThrows(IllegalArgumentException.class, () -> new Account(10.0, "-*¨^", "Sparekonto")); // Illegal name
    }

    /**
     * Tests deposit and withdrawal methods of the Account class.
     */
    @Test
    public void testWithdrawDeposit() {
        assertEquals(100.0, account1.getBalance());
        account1.deposit(100.0);
        assertEquals(200.0, account1.getBalance());
        account1.withDraw(50.0);
        assertEquals(150, account1.getBalance());
        assertThrows(IllegalArgumentException.class, () -> account1.withDraw(200.0));
    }

    /**
     * Tests changing the name of the account.
     */
    @Test
    public void testNameChange() {
        assertEquals("sparekonto", account1.getName());
        account1.changeName("bob");
        assertEquals("bob", account1.getName());
        assertThrows(IllegalArgumentException.class, () -> account1.changeName("1234"));
        assertThrows(IllegalArgumentException.class, () -> account1.changeName("p"));
        assertThrows(IllegalArgumentException.class, () -> account1.changeName(""));

    }

    /**
     * Tests changing the account type.
     */
    @Test
    public void testAccountTypeChange() {
        assertEquals("Sparekonto", account1.getAccountType());
        account1.changeAccountType("Brukskonto");
        assertEquals("Brukskonto", account1.getAccountType());
        assertThrows(IllegalArgumentException.class, () -> account1.changeAccountType("Ikke gyldig"));

    }

    /**
     * Tests the transfer functionality between accounts.
     */
    @Test
    public void testTransfer() {
        assertThrows(IllegalArgumentException.class, () -> account1.transferTo(50.0, account1.getAccountNumber()));
        account1.transferTo(50.0, account2.getAccountNumber());
        assertEquals(150.0, account2.getBalance());
        assertEquals(50.0, account1.getBalance());
        account1.transferFrom(50.0, account2.getAccountNumber());
        assertEquals(100.0, account2.getBalance());
        assertEquals(100.0, account1.getBalance());
    }
}
