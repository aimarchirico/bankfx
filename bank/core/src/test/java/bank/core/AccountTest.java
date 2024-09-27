package bank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
    Account account1;

    @BeforeEach
    public void setup() {
        account1 = new Account(100.0, "test", "Sparekonto");
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Account(-1.0, "MinKonto", "Sparekonto")); // Illegal
                                                                                                         // balance
        assertThrows(IllegalArgumentException.class, () -> new Account(10.0, "MinAndreKonto", "Lån")); // Illegal
                                                                                                       // accounttype
        assertThrows(IllegalArgumentException.class, () -> new Account(10.0, "-*¨^", "Sparekonto")); // Illegal name
    }

    @Test
    public void testWithdrawDeposit() {
        assertEquals(100.0, account1.getBalance());
        account1.deposit(100.0);
        assertEquals(200.0, account1.getBalance());
        account1.withDraw(50.0);
        assertEquals(150, account1.getBalance());
        assertThrows(IllegalArgumentException.class, () -> account1.withDraw(200.0));
    }

    @Test
    public void testNameChange() {
        assertEquals("test", account1.getName());
        account1.changeName("bob");
        assertEquals("bob", account1.getName());
        assertThrows(IllegalArgumentException.class, () -> account1.changeName("1234"));
        assertThrows(IllegalArgumentException.class, () -> account1.changeName("p"));
        assertThrows(IllegalArgumentException.class, () -> account1.changeName(""));

    }

    @Test
    public void testAccountTypeChange() {
        assertEquals("Sparekonto", account1.getAccountType());
        account1.changeAccountType("Brukskonto");
        assertEquals("Brukskonto", account1.getAccountType());
        assertThrows(IllegalArgumentException.class, () -> account1.changeAccountType("Ikke gyldig"));

    }
}
