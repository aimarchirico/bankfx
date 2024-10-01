package bank.persistence;

import static org.junit.jupiter.api.Assertions.*;
import bank.core.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class BankPersistenceTest {
  private BankPersistence bankPersistence;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setup() {
    bankPersistence = new BankPersistence();
  }

  /**
   * Tests that a {@link List} of {@link User} objects is written to a {@link File} using
   * {@link BankPersistence#writeToFile(File, List)}
   * <p>
   * Verifies that the file is created after writing.
   *
   * @throws IOException if the operation fails
   */
  @Test
  void writeToFileTest() throws IOException {
    File file = tempDir.resolve("user.json").toFile();
    List<User> users =
        Arrays.asList(new User("01010000000", "Sindre", "Hei123"), new User("02020000000", "Aimar", "Nei123"));

    bankPersistence.writeToFile(file, users);
    assertTrue(file.exists());
  }

  /**
   * Tests reading a {@link List} of {@link User} objects from a {@link File} using
   * {@link BankPersistence#readUserData(File, TypeReference)}.
   * <p>
   * Verifies that the data is correctly read from the file and matches the expected values.
   *
   * @throws IOException if file operations fail.
   */
  @Test
  void readUserDataTest() throws IOException {
    File file = tempDir.resolve("user.json").toFile();
    List<User> users =
        Arrays.asList(new User("01010000000", "Sindre", "Hei123"), new User("02020000000", "Aimar", "Nei123"));

    bankPersistence.writeToFile(file, users);
    List<User> readUsers = bankPersistence.readUserData(file, new TypeReference<List<User>>() {});

    assertNotNull(readUsers);
    assertEquals(2, readUsers.size());
    assertEquals("Sindre", readUsers.get(0).getName());
    assertEquals("Aimar", readUsers.get(1).getName());
    assertEquals("Hei123", readUsers.get(0).getPassword());
    assertEquals("Nei123", readUsers.get(1).getPassword());
    assertEquals("01010000000", readUsers.get(0).getSsn());
    assertEquals("02020000000", readUsers.get(1).getSsn());
  }

  /**
   * Tests reading from an invalid or non-existent {@link File} using
   * {@link BankPersistence#readUserData(File, TypeReference)}.
   * <p>
   * Verifies that the method returns <code>null</code> when attempting to read from an invalid file.
   */
  @Test
  void readInvalidFileTest() {
    File invalidFile = tempDir.resolve("invalid.json").toFile();
    List<User> users = bankPersistence.readUserData(invalidFile, new TypeReference<List<User>>() {});
    assertNull(users);
  }

}
