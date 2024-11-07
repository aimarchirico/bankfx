package bank.persistence;

import static org.junit.jupiter.api.Assertions.*;
import bank.core.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class UserPersistenceTest {
  private UserPersistence userPersistence;

  /**
   * Resets userPersistence. 
   */
  @BeforeEach
  void setup() {
    assertDoesNotThrow(() -> {
      userPersistence = new UserPersistence("testUsers.json");
      userPersistence.initializeFile();
    });
  }

  /**
   * Deletes file. 
   */
  @AfterEach
  void tearDown() {
    assertTrue(userPersistence.getFile().delete());
  }
  

  /**
   * Tests that a {@link List} of {@link User} objects is written to a file using
   * {@link UserPersistence#writeToFile(List)}
   * <p>
   * Verifies correct data in file after creation.
   *
   */
  @Test
  @DisplayName("Write to file test")
  void writeToFileTest() {
    List<User> users = Arrays.asList(new User("01010000001", "Sindre", "Hei123"));
    assertDoesNotThrow(() -> {
      userPersistence.writeToFile(users);
      List<User> readUsers = userPersistence.readFromFile();
      assertNotNull(readUsers);
      assertEquals(1, readUsers.size());
      assertEquals("Sindre", readUsers.get(0).getName());
      assertEquals("Hei123", readUsers.get(0).getPassword());
      assertEquals("01010000001", readUsers.get(0).getSsn());
    });

  }

  /**
   * Tests reading a {@link List} of {@link User} objects from a file using
   * {@link UserPersistence#readFromFile()}.
   * <p>
   * Verifies that the data is correctly read from the file and matches the expected values.
   *
   */
  @Test
  @DisplayName("Read from file test")
  void readFromFileTest() {
    assertDoesNotThrow(() -> {
      List<User> readUsers = userPersistence.readFromFile();
      assertNotNull(readUsers);
      assertEquals(1, readUsers.size());
      assertEquals("admin", readUsers.get(0).getName());
      assertEquals("A123456z", readUsers.get(0).getPassword());
      assertEquals("01010000000", readUsers.get(0).getSsn());
    });

  }


}
