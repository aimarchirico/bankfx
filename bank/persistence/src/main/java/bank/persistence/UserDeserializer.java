package bank.persistence;

import bank.core.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

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
 */
  User deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode ssnNode = objectNode.get("ssn");
      String ssn = ssnNode.asText();
      JsonNode nameNode = objectNode.get("name");
      String name = nameNode.asText();
      JsonNode passwordNode = objectNode.get("password");
      String password = passwordNode.asText();
      return new User(ssn, name, password);
    }
    return null;
  }
}
