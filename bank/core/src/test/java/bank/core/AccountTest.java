package bank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Account} class.
 */
public class AccountTest {
    Account account1;
    Account account2;

    /**
     * Initializes test {@link Account}s before each test.
     */
    @BeforeEach
    public void setup() {
        account1 = new Account(100.0, "Savings", "Savings Account");
        account2 = new Account(100.0, "Checking", "Checking Account");
    }

    /**
     * Tests the {@link Account} constructor for valid and invalid inputs.
     */
    @DisplayName("Test constructor")
    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Account(-1.0, "MyAccount", "Savings Account"));
        assertThrows(IllegalArgumentException.class, () -> new Account(10.0, "MyOtherAccount", "Loan Account"));
        assertThrows(IllegalArgumentException.class, () -> new Account(10.0, "-*¨^", "Savings Account"));
    }

    /**
     * Tests deposit and withdrawal methods of the {@link Account} class.
     */
    @DisplayName("Test withdrawal and depsit")
    @Test
    public void testWithdrawDeposit() {
        assertEquals(100.0, account1.getBalance());
        account1.deposit(100.0);
        assertEquals(200.0, account1.getBalance());
        account1.withdraw(50.0);
        assertEquals(150, account1.getBalance());
        assertThrows(IllegalArgumentException.class, () -> account1.withdraw(200.0));
    }

}
