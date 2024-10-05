package bank.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Test class for {@link LoginController}.
 */
public class LoginControllerTest extends ApplicationTest {
  
  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test that <code>createUserButton</code> switches scene to <code>CreateUser.fxml</code>.
   */
  @Test
  @DisplayName("Test createUserButton")
  public void testCreateUserButton() {
    clickOn("#createUserButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("createUserRoot"));
  }

  /**
   * Test logging in with correct ssn and password.
   */
  @Test
  @DisplayName("Test successful user login")
  public void testSuccessfulUserLogin() {
    clickOn("#ssnField");
    write("01010000000");
    clickOn("#passwordField");
    write("A123456z");
    clickOn("#loginButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
  }

  /**
   * Test logging in with incorrect ssn or password.
   */
  @Test
  @DisplayName("Test failed user login")
  public void testFailedUserLogin() {
    clickOn("#ssnField");
    write("01010100000");
    clickOn("#passwordField");
    write("B123456z");
    clickOn("#loginButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals("Incorrect social security number or password", lookup("#errorButton").queryAs(Button.class).getText());
  }

}


