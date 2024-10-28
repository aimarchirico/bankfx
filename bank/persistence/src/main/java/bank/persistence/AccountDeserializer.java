package bank.persistence;

import bank.core.Account;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

/**
 * The JSON deserializer class for {@link Account} objects.
 */
public class AccountDeserializer extends JsonDeserializer<Account> {
  @Override
  public Account deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Method for deserializing and returning an {@link Account} instance.
   *
   * @param treeNode the {@link JsonNode}
   * @throws IllegalArgumentException if node is missing required field
   */
  Account deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode accountNameNode = objectNode.get("name");
      JsonNode accountTypeNode = objectNode.get("accountType");
      JsonNode balanceNode = objectNode.get("balance");
      JsonNode accountNumberNode = objectNode.get("accountNumber");

      if (accountNameNode == null || accountTypeNode == null 
          || balanceNode == null || accountNumberNode == null) {
        throw new IllegalArgumentException("Missing required field.");
      }

      return new Account(balanceNode.asDouble(), accountNameNode.asText(), accountTypeNode.asText(),
          accountNumberNode.asLong());
    }
    return null;
  }
}
