package bank.rest;

import bank.core.Account;
import bank.core.Bank;
import bank.core.User;
import bank.persistence.UserPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Test class for {@link BankController}.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BankController.class)
public class BankControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BankService bankService;

  @Mock
  private Bank bank;

  private User user;

  private Account account1;
  private Account account2;

  @InjectMocks
  private BankController bankController;

  private String userJson;
  private String accountJson;
  private ObjectMapper objectMapper;
  private String endpoint;

  /**
   * Setup mock classes for bank, user, bankService etc.
   */
  @BeforeEach
  public void setup() throws JsonProcessingException {
    account1 = new Account(100.0, "Test", "Checking Account", 10000000000L);
    account2 = new Account(100.0, "OtherTest", "Checking Account", 20000000000L);
    user = new User("01010000001", "Test", "Password123", List.of(account1));
    objectMapper = UserPersistence.createObjectMapper();
    endpoint = "/bank/users/" + user.getSsn();
    userJson = objectMapper.writeValueAsString(user);
    accountJson = objectMapper.writeValueAsString(account1);


    when(bankService.getBank()).thenReturn(bank);
    when(bank.getUser(user.getSsn())).thenReturn(user);
    when(bank.getAccountByNumber(account1.getAccountNumber())).thenReturn(account1);
    when(bank.getAccountByNumber(account2.getAccountNumber())).thenReturn(account2);


  }

  /**
   * Test getUser method.
   */
  @Test
  @DisplayName("Test getUser")
  public void testGetUser() throws Exception {
    MvcResult result = mockMvc.perform(get(endpoint).param("password", user.getPassword())).andExpect(status().isOk())
        .andExpect(content().json(userJson)).andReturn();
    System.out.println(result);

    doThrow(new IllegalAccessException("Invalid credentials.")).when(bank).verifyCredentials(user.getSsn(),
        user.getPassword());
    mockMvc.perform(get(endpoint).param("password", user.getPassword())).andExpect(status().isForbidden());
  }

  /**
   * Test addUser method.
   */
  @Test
  @DisplayName("Test addUser")
  public void testAddUser() throws Exception {
    mockMvc.perform(post(endpoint).content(userJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andExpect(content().json(objectMapper.writeValueAsString(user)));

    doThrow(new IllegalStateException("User already exists.")).when(bank).addUser(any());
    mockMvc.perform(post(endpoint).content(userJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  /**
   * Test removeUser method.
   */
  @Test
  @DisplayName("Test removeUser")
  public void testRemoveUser() throws Exception {
    mockMvc.perform(delete(endpoint).param("password", user.getPassword())).andExpect(status().isOk());

    doThrow(new NoSuchElementException("User not found")).when(bank).removeUser(user);
    mockMvc.perform(delete(endpoint).param("password", user.getPassword())).andExpect(status().isNotFound());
  }

  /**
   * Test addAccount method.
   */
  @Test
  @DisplayName("Test addAccount")
  public void testAddAccount() throws Exception {
    mockMvc
        .perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()))
            .param("password", user.getPassword()).param("password", user.getPassword()).content(accountJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated()).andExpect(content().json(userJson));

    doThrow(new IllegalStateException("Account already exists")).when(bank).addAccount(any(), any());
    mockMvc.perform(post(endpoint + "/accounts/" + account1.getAccountNumber()).param("password", user.getPassword())
        .content(accountJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
  }

  /**
   * Test removeAccount method.
   */
  @Test
  @DisplayName("Test removeAccount")
  public void testRemoveAccount() throws Exception {
    mockMvc.perform(delete(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber())).param("password",
        user.getPassword())).andExpect(status().isOk());

    doThrow(new NoSuchElementException("Account not found")).when(bank).removeAccount(user, account1);
    mockMvc.perform(delete(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber())).param("password",
        user.getPassword())).andExpect(status().isNotFound());
  }

  /**
   * Test transfer method.
   */
  @Test
  @DisplayName("Test transfer")
  public void testTransfer() throws Exception {
    mockMvc.perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/transfer")
        .param("password", user.getPassword()).param("targetAccount", Long.toString(account2.getAccountNumber()))
        .param("amount", "100.0")).andExpect(status().isOk()).andExpect(content().json(userJson));

    doThrow(new IllegalStateException("Insufficient funds")).when(bank).transfer(user, account1, account2, 100.0);
    mockMvc.perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/transfer")
        .param("password", user.getPassword()).param("targetAccount", Long.toString(account2.getAccountNumber()))
        .param("amount", "100")).andExpect(status().isConflict());
  }

  /**
   * Test payment method.
   */
  @Test
  @DisplayName("Test payment")
  public void testPayment() throws Exception {
    mockMvc.perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/payment")
        .param("password", user.getPassword()).param("targetAccount", Long.toString(account2.getAccountNumber()))
        .param("amount", "100")).andExpect(status().isOk()).andExpect(content().json(userJson));

    doThrow(new IllegalStateException("Insufficient funds")).when(bank).payment(user, account1, account2, 100.0);
    mockMvc.perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/payment")
        .param("password", user.getPassword()).param("targetAccount", Long.toString(account2.getAccountNumber()))
        .param("amount", "100")).andExpect(status().isConflict());
  }

  /**
   * Test withdraw method.
   */
  @Test
  @DisplayName("Test withdraw")
  public void testWithdraw() throws Exception {
    mockMvc
        .perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/withdraw")
            .param("password", user.getPassword()).param("amount", "100"))
        .andExpect(status().isOk()).andExpect(content().json(userJson));

    doThrow(new IllegalAccessException("User does not own account")).when(bank).withdraw(user, account2, 100.0);
    mockMvc.perform(post(endpoint + "/accounts/" + Long.toString(account2.getAccountNumber()) + "/withdraw")
        .param("password", user.getPassword()).param("amount", "100")).andExpect(status().isForbidden());
  }

  /**
   * Test deposit method.
   */
  @Test
  @DisplayName("Test deposit")
  public void testDeposit() throws Exception {
    mockMvc
        .perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/deposit")
            .param("password", user.getPassword()).param("amount", "100"))
        .andExpect(status().isOk()).andExpect(content().json(userJson));

    doThrow(new IllegalArgumentException("Invalid amount")).when(bank).deposit(user, account1, 100.0);
    mockMvc.perform(post(endpoint + "/accounts/" + Long.toString(account1.getAccountNumber()) + "/deposit")
        .param("password", user.getPassword()).param("amount", "100")).andExpect(status().isBadRequest());
  }

}
