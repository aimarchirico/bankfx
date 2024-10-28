package bank.rest;

import bank.core.Bank;
import bank.core.User;
import bank.persistence.UserPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link BankService}.
 */
class BankServiceTest {

  @Mock
  private UserPersistence userPersistence;

  @InjectMocks
  private BankService bankService;

  /**
   * Set up mock objects.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    bankService.setUserPersistence(userPersistence); 
  }

  /**
   * Test that BankService is initialized correctly.
   */
  @Test
  @DisplayName("Test BankService initialization")
  public void testBankServiceInitialization() {
    assertNotNull(bankService
        .getBank(), "The Bank instance in BankService should not be null.");
  }

  /**
   * Test that saving and retrieving of {@link User}s.
   */
  @Test
  @DisplayName("Test save and retrieve users")
  public void testSaveRetrieveUsers() throws IOException {
    List<User> users = Arrays.
        asList(new User("01010000000", "admin", "A123456z"));
    when(userPersistence.readFromFile()).thenReturn(users);
    bankService.retrieveUsers();
    List<User> readUsers = bankService.getBank().getUsers();
    assertNotNull(readUsers);
    assertEquals(1, readUsers.size());
    assertEquals("admin", readUsers.get(0).getName());
    assertEquals("A123456z", readUsers.get(0).getPassword());
    assertEquals("01010000000", readUsers.get(0).getSsn());
    assertDoesNotThrow(() -> bankService.saveUsers());
  }

  /**
   * Test getting {@link Bank} instance.
   */
  @Test
  @DisplayName("Test getBank")
  public void testGetBank() {
    Bank result = bankService.getBank();
    assertNotNull(result, "getBank() should return a non-null Bank instance.");
  }
}
