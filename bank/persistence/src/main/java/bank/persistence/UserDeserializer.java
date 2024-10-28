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
  public User deserialize(JsonParser parser, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Method for deserializing and returning a {@link User} instance.
   *
   * @param treeNode the {@link JsonNode}
   * @throws IllegalArgumentException if node is missing required field
   */
  User deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode ssnNode = objectNode.get("ssn");
      JsonNode nameNode = objectNode.get("name");
      JsonNode passwordNode = objectNode.get("password");
      JsonNode accountsNode = objectNode.get("accounts");
      List<Account> accounts = new ArrayList<>();
      if (accountsNode != null && accountsNode.isArray()) {
        for (JsonNode accountNode : accountsNode) {
          JsonNode accountNameNode = accountNode.get("name");
          JsonNode accountTypeNode = accountNode.get("accountType");
          JsonNode balanceNode = accountNode.get("balance");
          JsonNode accountNumberNode = accountNode.get("accountNumber");

          if (accountNameNode == null || accountTypeNode == null  
              || balanceNode == null || accountNumberNode == null) {
            throw new IllegalArgumentException("Missing required field.");
          }

          accounts.add(new Account(balanceNode.asDouble(), accountNameNode.asText(), 
              accountTypeNode.asText(), accountNumberNode.asLong()));
        }
      }

      if (ssnNode == null || nameNode == null || passwordNode == null) {
        throw new IllegalArgumentException("Missing required field.");
      }

      return new User(ssnNode.asText(), nameNode.asText(), passwordNode.asText(), accounts);
    }
    return null;
  }
}
