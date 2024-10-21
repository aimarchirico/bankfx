package bank.core;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link User} class, ensuring that user management functionalities work as
 * expected.
 */
public class UserTest {
    String ssn = "23069456293";
    String name = "Kari Nordmann";
    String password = "passWord123";
    User user1;
    User user2;
    Account account1;
    Account account2;
    Account account3;
    List<Account> accounts;

    /**
   * Sets up the test environment by initializing two users, three accounts and adding some of the accounts to each user. 
   */
    @BeforeEach
	public void setup() {
        user1 = new User(ssn, name, password);
        account1 = new Account(20000.00, "minBrukskonto", "Brukskonto");
        account2 = new Account(100000.00, "minSparekonto", "Sparekonto");
        account3 = new Account(0.0, "minAndreBrukskonto", "Brukskonto");
        accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        user2 = new User(ssn, name, password, accounts);
        user1.addAccount(account1);
        user1.addAccount(account3);
	}

    /**
   * Tests that the constructor works by retrieving the getters, also tests changing objects with setters 
   */
    @Test
    public void testConstructorSetAndGet() {
        Assertions.assertEquals(name, user1.getName());
        Assertions.assertEquals(ssn, user1.getSsn());
        Assertions.assertEquals(password, user1.getPassword());

        Assertions.assertEquals(name, user2.getName());
        Assertions.assertEquals(ssn, user2.getSsn());
        Assertions.assertEquals(password, user2.getPassword());
        Assertions.assertEquals(accounts, user2.getAccounts());


        String newName = "Kari Franskmann";
        String newPassword = "baNaNa124";

        user1.setName(newName);
        Assertions.assertEquals(user1.getName(), newName);

        user1.setPassword(newPassword);
        Assertions.assertEquals(user1.getPassword(), newPassword);
    }

    /**
   * Tests that illegal arguments in the constructor throws an exception
   */
    @Test
    public void testIllegalArguments() {
        assertThrows(IllegalStateException.class,
        () -> new User("32070043672", name, password)); //Illegal birthdate

        assertThrows(IllegalArgumentException.class,
        () -> new User("220700432672", name, password)); //Illegal social security number

        assertThrows(IllegalArgumentException.class,
        () -> new User(null, name, password)); //Illegal social security number

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, "Mar0tin Hansen", password)); //Illegal name

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, "J", password)); //Illegal name

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, null, password)); //Illegal name

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, name, "Password")); //Illegal password

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, name, "Pass6")); //Illegal password

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, name, null)); //Illegal password

        
    }

    /**
   * Tests the addition or the absence of addition of an account to the user. Also checks that you can not add an already 
   * added account, that you maximum can add three accounts to an user, and checks for proper handling of adding null accounts
   */
    @Test
    public void testAddRemoveAndCreateAccountWithIsOwnerOfAccount() {
        user1.deleteAccount(account3);
        Account account4 = user1.createAccount("Sparekonto", "nySparekonto");
        Account account5 = new Account(2000.0, "nyBrukskonto", "Brukskonto");


        assertDoesNotThrow(()-> user1.isOwnerOfAccountCheck(account1));
        assertDoesNotThrow(()-> user1.isOwnerOfAccountCheck(account4));
        assertThrows(IllegalArgumentException.class,
        () -> user1.isOwnerOfAccountCheck(account2));
        assertThrows(IllegalArgumentException.class,
        () -> user1.isOwnerOfAccountCheck(account3));
        assertThrows(IllegalArgumentException.class,
        () -> user1.isOwnerOfAccountCheck(null));
        assertThrows(IllegalArgumentException.class,
        () -> user1.addAccount(account1));
        assertThrows(IllegalArgumentException.class,
        () -> user1.addAccount(null));
        assertThrows(IllegalArgumentException.class,
        () -> user1.addAccount(account5));

    }
    
    /**
   * Tests that different inputs returns the right values
   */
    @Test
    public void testSsnEquals() {
        int i = 2221;
        User user3 = new User(ssn, "Ola Nordmann", "heLLo123");

        Assertions.assertTrue(user1.ssnEquals(user1));
        Assertions.assertFalse(user1.ssnEquals(i));
        Assertions.assertTrue(user1.ssnEquals(user3));

       
    }
}


