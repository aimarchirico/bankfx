package bank.persistence;

import bank.core.Account;
import bank.core.Bank;
import bank.core.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Handles persistence of {@link User} data, allowing for reading and writing user lists to files.
 */
public class UserPersistence {

  private ObjectMapper om;
  private File file;
  private String fileName;
  private static final String ROOT_DIRECTORY = ".gr2422/bank/";



  /**
   * Initializes the {@link UserPersistence} object with a custom deserializer for {@link User}
   * objects.
   * 
   */
  public UserPersistence(String fileName) {
    om = createObjectMapper();
    this.fileName = fileName;

  }

  /**
   * Initializes returns a {@link ObjectMapper} with the custom modules registered. objects.
   *
   * @returns the ObjectMapper
   */
  public static ObjectMapper createObjectMapper() {
    return new ObjectMapper().registerModule(createModule());
  }

  /**
   * Returns a Module with the custom {@link UserDeserializer} and {@link AccountDeserializer}
   * registered.
   *
   * @returns the Module
   */
  public static SimpleModule createModule() {
    return new SimpleModule().addDeserializer(User.class, 
        new UserDeserializer()).addDeserializer(Account.class,
            new AccountDeserializer());

  }

  /**
   * Writes a {@link List} of {@link User} objects to file.
   *
   * @param users the list of users to write to the file
   * @throws IOException if it fails to write
   */
  public void writeToFile(List<User> users) throws IOException {
    checkFile();
    om.writerWithDefaultPrettyPrinter().writeValue(file, users);
  }

  /**
   * Sets the file to write data to. 
   * If file does not exist it will attempt to create and initialize it.
   *
   * @throws IOException if it fails to create file
   */
  public void setSaveFile(String fileName) throws IOException {
    this.fileName = fileName;
    Path path = Paths.get(System.getProperty("user.home"), ROOT_DIRECTORY, fileName);
    file = path.toFile();
    if (!file.exists()) {
      if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
        if (file.createNewFile()) {
          initializeFile();
        } else {
          throw new IOException("Failed to create file: " + file.getName());
        }
      } else {
        throw new IOException("Failed to create directories for: " + file.getParent());
      }
    }
  }

  /**
   * Checks if file is null or does not exist. If so it will call
   * {@link UserPersistence#setSaveFile(String)}.
   *
   * @throws IOException if it fails to create file
   */
  public void checkFile() throws IOException {
    if (file == null || !file.exists()) {
      setSaveFile(fileName);
    }
  }

  /**
   * Writes a list including just the admin to file.
   *
   * @throws IOException if it fails to write to file
   */
  public void initializeFile() throws IOException {
    writeToFile(List.of(Bank.getAdminTemplate()));
  }

  /**
   * Reads a {@link List} of {@link User} objects from a file.
   *
   * @return a list of User instances
   * @throws IOException if it fails to read
   */
  public List<User> readFromFile() throws IOException {
    checkFile();
    return om.readValue(file, new TypeReference<List<User>>() {});
  }

}
