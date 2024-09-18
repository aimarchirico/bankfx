package bank.core;


import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    String ssn = "23069456293";
    String name = "Kari Nordmann";
    String password = "passWord123";
    User user1;

    @BeforeEach
	public void setup() {
        user1 = new User(ssn, name, password);
	}

    @Test
    public void testConstructorSetAndGet() {
        Assertions.assertEquals(user1.getName(), name);
        Assertions.assertEquals(user1.getSsn(), ssn);
        Assertions.assertEquals(user1.getPassword(), password);

        String newName = "Kari Franskmann";
        String newPassword = "baNaNa124";

        user1.setName(newName);
        Assertions.assertEquals(user1.getName(), newName);

        user1.setPassword(newPassword);
        Assertions.assertEquals(user1.getPassword(), newPassword);
    }

    @Test
    public void testIllegalArguments() {
        assertThrows(IllegalStateException.class,
        () -> new User("32070043672", name, password)); //Illegal birthdate

        assertThrows(IllegalArgumentException.class,
        () -> new User("220700432672", name, password)); //Too long social security number

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, "Mar0tin Hansen", password)); //Illegal name

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, "J", password)); //Illegal name

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, name, "Password")); //Illegal password

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, name, "Pass6")); //Illegal password

        assertThrows(IllegalArgumentException.class,
        () -> new User(ssn, name, null)); //Illegal password
    }
    

    
}
