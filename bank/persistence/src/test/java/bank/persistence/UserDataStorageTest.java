package bank.persistence;

import static org.junit.jupiter.api.Assertions.*;
import bank.core.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;

/**
 * Test class for {@link UserDataStorage}, verifying the functionality of user data management methods.
 */
public class UserDataStorageTest {
  private UserDataStorage userDataStorage;

  @TempDir
  Path tempDir;

  /**
   * Sets up the {@link UserDataStorage} instance before each test.
   */
  @BeforeEach
  void setup() {
    userDataStorage = new UserDataStorage(Utils.testPath);
  }

  /**
   * Tests if writing and fetching {@link User} data works correctly. Verifies that the {@link List} is not empty
   * and contains the correct number of users after fetching.
   */
  @Test
  void fetchAndWriteUserDataTest() {
    User user = new User("03030000000", "Sindre", "Hei123");

    userDataStorage.writeUserData(user);

    userDataStorage.fetchUserData();
    assertFalse(userDataStorage.getUsers().isEmpty());
    assertEquals(2, userDataStorage.getUsers().size());
  }

  /**
   * Tests if deleting and fetching {@link User} data works correctly. Verifies that the {@link List} contains
   * the correct number of users after fetching.
   */
  @Test
  void fetchAndDeleteUserDataTest() {
    User user = new User("01010000000", "admin", "A123456z");
    userDataStorage.deleteUserData(user);
    userDataStorage.fetchUserData();
    assertTrue(userDataStorage.getUsers().isEmpty());
  }

  /**
   * Tests if {@link UserDataStorage#getUser(String)} returns the correct {@link User} when the user exists. Verifies that the
   * fetched user is not <code>null</code> and that the name matches.
   */
  @Test
  void testGetUserReturnsCorrectUser() {
    User user = new User("03030000000", "Sindre", "Hei123");
    userDataStorage.writeUserData(user);

    User fetchedUser = userDataStorage.getUser("03030000000");
    assertNotNull(fetchedUser, "Fetched user should not be null.");
    assertEquals("Sindre", fetchedUser.getName(), "Fetched user name should match.");
  }

  /**
   * Tests if {@link UserDataStorage#getUser(String)} returns <code>null</code> for a nonexistent {@link User}. Verifies that the fetched
   * user is <code>null</code> when the user does not exist in the storage.
   */

  @Test
  void testGetUserReturnsNullForNonexistentUser() {
    User fetchedUser = userDataStorage.getUser("99999999999");
    assertNull(fetchedUser, "Fetched user should be null for nonexistent user.");
  }

  /**
   * Resets test file.
   */
  @AfterEach
  void reset() throws Exception {
    userDataStorage.resetUserData();
  }

}
