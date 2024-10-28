package bank.persistence;

import bank.core.Account;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link AccountDeserializer}.
 */
class AccountDeserializerTest {

  private AccountDeserializer deserializer;
  private ObjectMapper objectMapper;

  /**
   * Instantiates {@link AccountDeserializer}.
   */
  @BeforeEach
  void setUp() {
    deserializer = new AccountDeserializer();
    objectMapper = new ObjectMapper();
  }

  
  /**
   * Tests deserializing valid {@link Account}.
   */
  @Test
  @DisplayName("Test deserializing valid account")
  void deserializeValidAccountJson() throws Exception {
    String json = """
        {
          "name": "Savings",
          "accountType": "Savings Account",
          "balance": 1000.0,
          "accountNumber": 10000000000
        }
        """;

    JsonNode jsonNode = objectMapper.readTree(json);
    Account account = deserializer.deserialize(jsonNode);

    assertNotNull(account);
    assertEquals("Savings", account.getName());
    assertEquals("Savings Account", account.getAccountType());
    assertEquals(10000000000L, account.getAccountNumber());
    assertEquals(1000.0, account.getBalance());

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
              "name": "Savings",
              "balance": 1000.0,
              "accountNumber": 10000000000
          }
            """;

      JsonNode jsonNode = objectMapper.readTree(json);
      assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(jsonNode));
    });

  }

}
