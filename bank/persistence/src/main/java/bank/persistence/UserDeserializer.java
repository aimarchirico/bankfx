package bank.persistence;

import bank.core.Account;
import bank.core.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The JSON deserializer class for {@link User} objects.
 */
public class UserDeserializer extends JsonDeserializer<User> {
  @Override
  public User deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Method for deserializing and returning a {@link User} instance.
   *
   * @param treeNode the {@link JsonNode}
   */
  User deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode ssnNode = objectNode.get("ssn");
      String ssn = ssnNode.asText();
      JsonNode nameNode = objectNode.get("name");
      String name = nameNode.asText();
      JsonNode passwordNode = objectNode.get("password");
      String password = passwordNode.asText();
      JsonNode accountsNode = objectNode.get("accounts");
      List<Account> accounts = new ArrayList<>();
      if (accountsNode != null && accountsNode.isArray()) {
        for (JsonNode accountNode : accountsNode) {
          String accountName = accountNode.get("name").asText(); // Get account name
          String accountType = accountNode.get("accountType").asText(); // Get account type
          double balance = accountNode.get("balance").asDouble(); // Get balance field

          // Create an Account object and add to list
          accounts.add(new Account(balance, accountName, accountType));
        }
      }

      if (ssn == null || name == null || password == null) {
        throw new IllegalArgumentException("Missing required field");
      }

      return new User(ssn, name, password, accounts);
    }
    return null;
  }
}
