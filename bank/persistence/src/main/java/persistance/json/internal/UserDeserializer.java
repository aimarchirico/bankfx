package persistance.json.internal;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import bank.core.User;

public class UserDeserializer extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser parser, DeserializationContext ctxt) 
    throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }
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
