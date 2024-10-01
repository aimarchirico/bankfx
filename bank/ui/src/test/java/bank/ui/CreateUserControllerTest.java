package bank.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import bank.core.User;
import bank.persistence.Utils;
import bank.persistence.UserDataStorage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Test class for {@link CreateUserController}.
 */
public class CreateUserControllerTest extends ApplicationTest {

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreateUser.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test that <code>backIcon</code> switches scene to <code>Login.fxml</code>.
   */
  @Test
  @DisplayName("Test backIcon")
  public void testReturnButton() {
    clickOn("#backIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("loginRoot"));
  }

  /**
   * Test registration with valid arguments.
   */
  @Test
  @DisplayName("Test successful user registration")
  public void testSuccessfulUserRegistration() {
    User testUser = new User("01010100001", "test", "A123456z");
    clickOn("#nameField");
    write(testUser.getName());
    clickOn("#passwordField");
    write(testUser.getPassword());
    clickOn("#ssnField");
    write(testUser.getSsn());
    clickOn("#registerButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
    UserDataStorage storage = new UserDataStorage(Utils.path);
    storage.deleteUserData(testUser);
  }

  /**
   * Test registration with invalid arguments.
   */
  @Test
  @DisplayName("Test failed user registration")
  public void testFailedUserRegistration() {
    clickOn("#nameField");
    write("test");
    clickOn("#passwordField");
    write("A123456z");
    clickOn("#ssnField");
    write("00000000000");
    clickOn("#registerButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals("The first 6 numbers needs to be a date", lookup("#errorButton").queryAs(Button.class).getText());
  }

  /**
   * Test attempted registration with exisiting {@link User}.
   */
  @Test
  @DisplayName("Test existing user registration")
  public void testExistingUserRegistration() {
    User testUser = new User("01010000000", "admin", "A123456z");
    clickOn("#nameField");
    write(testUser.getName());
    clickOn("#passwordField");
    write(testUser.getPassword());
    clickOn("#ssnField");
    write(testUser.getSsn());
    clickOn("#registerButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals("User already registered", lookup("#errorButton").queryAs(Button.class).getText());
  }


}


