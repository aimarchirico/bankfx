package bank.persistence;

import bank.core.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserDataStorage, verifying the functionality of user data management methods.
 */
public class UserDataStorageTest {
  private UserDataStorage userDataStorage;

  @TempDir
  Path tempDir;

  /**
   * Sets up the UserDataStorage instance before each test.
   */
  @BeforeEach
  void setup() {
    userDataStorage = new UserDataStorage(TestUtils.path);
  }

  /**
   * Tests if writing and fetching user data works correctly. Verifies that the user list is not empty
   * and contains the correct number of users after fetching.
   */
  @Test
  void fetchAndWriteUserDataTest() {
    User user = new User("03030000000", "Sindre", "Hei123");

    userDataStorage.writeUserData(user);

    userDataStorage.fetchUserData();
    assertFalse(userDataStorage.users.isEmpty());
    assertEquals(1, userDataStorage.users.size());
  }


  /**
   * Tests if getUser() returns the correct user when the user exists. Verifies that the fetched user
   * is not null and that the name matches.
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
   * Tests if getUser() returns null for a nonexistent user. Verifies that the fetched user is null
   * when the user does not exist in the storage.
   */

  @Test
  void testGetUserReturnsNullForNonexistentUser() {
    User fetchedUser = userDataStorage.getUser("99999999999");
    assertNull(fetchedUser, "Fetched user should be null for nonexistent user.");
  }

  @AfterEach
  void tearDown() throws Exception {
    // Slett testfilen etter hver test
    Files.deleteIfExists(Paths.get(TestUtils.path));
  }

}
