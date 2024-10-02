package bank.persistence;

import bank.core.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testklasse for {@link UserDeserializer} for å validere at
 * deserialisering av {@link User} objekter fra JSON fungerer korrekt.
 */
public class UserDeserializerTest {
  private UserDeserializer userDeserializer;
  private ObjectMapper objectMapper;

  /**
   * Setter opp testmiljøet før hver test ved å initialisere
   * {@link ObjectMapper} og {@link UserDeserializer} instanser.
   */
  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();
    userDeserializer = new UserDeserializer();
  }

  /**
   * Tester deserialisering av en gyldig JSON-streng til en {@link User} instans.
   * Forventer at brukerobjektet ikke er null og har de riktige feltene.
   *
   * @throws Exception hvis deserialisering feiler
   */
  @Test
  void testDeserialize() throws Exception {
    String json = "{\"ssn\":\"01010000000\",\"name\":\"Sindre\",\"password\":\"Hei123\"}";
    JsonParser parser = objectMapper.getFactory().createParser(json);
    DeserializationContext ctxt = objectMapper.getDeserializationContext();

    User user = userDeserializer.deserialize(parser, ctxt);

    assertNotNull(user);
    assertEquals("01010000000", user.getSsn());
    assertEquals("Sindre", user.getName());
    assertEquals("Hei123", user.getPassword());
  }

  /**
   * Tester deserialisering når {@link JsonParser} er null.
   * Forventer at en {@link NullPointerException} blir kastet.
   */
  @Test
  void testDeserialize_with_Null_parser() {
    JsonParser parser = null;
    DeserializationContext ctxt = objectMapper.getDeserializationContext();
    assertThrows(NullPointerException.class, () -> {
      userDeserializer.deserialize(parser, ctxt);
    });
  }
}

