package bank.persistence;

import bank.core.Account;
import bank.core.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link UserDeserializer}.
 */
class UserDeserializerTest {

  private UserDeserializer deserializer;
  private ObjectMapper objectMapper;

/**
 * Instantiates {@link UserDeserializer}.
 */
  @BeforeEach
  void setUp() {
    deserializer = new UserDeserializer();
    objectMapper = new ObjectMapper();
  }


  /**
   * Tests deserializing valid {@link User}.
   */
  @Test
  @DisplayName("Test deserializing valid User")
  void deserializeValidUserJson() {
    assertDoesNotThrow(() -> {

      String json = """
        {
            "ssn": "01010000000",
            "name": "admin",
            "password": "A123456z",
            "accounts": [
                {
                    "name": "Savings",
                    "accountType": "Savings Account",
                    "balance": 1000.0,
                    "accountNumber": 10000000000
                }
            ]
        }
        """;

      JsonNode jsonNode = objectMapper.readTree(json);
      User user = deserializer.deserialize(jsonNode);

      assertNotNull(user);
      assertEquals("01010000000", user.getSsn());
      assertEquals("admin", user.getName());
      assertEquals("A123456z", user.getPassword());

      List<Account> accounts = user.getAccounts();
      assertEquals(1, accounts.size());

      Account account = accounts.get(0);
      assertEquals("Savings", account.getName());
      assertEquals("Savings Account", account.getAccountType());
      assertEquals(1000.0, account.getBalance());
      assertEquals(10000000000L, account.getAccountNumber());

    });
    

  }

  /**
   * Tests missing required field. 
   */
  @Test
  @DisplayName("Test missing required field")
  void deserializeMissingRequiredField() {
    assertDoesNotThrow(() -> {
      String json = """
          {
              "name": "admin",
              "password": "A123456z",
              "accounts": []
          }
          """;

      JsonNode jsonNode = objectMapper.readTree(json);
      assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(jsonNode));
    });

  }

}
