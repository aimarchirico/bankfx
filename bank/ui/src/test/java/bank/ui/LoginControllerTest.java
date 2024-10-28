package bank.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import bank.core.User;


/**
 * Test class for {@link LoginController}.
 */
public class LoginControllerTest extends ApplicationTest {
  @Mock
  private UserAccess userAccess;

  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /*
   * Injects mocks into controller
   */
  @InjectMocks
  private LoginController loginController;

  /*
   * Sets up UserAccess mock, to avoid dependance on server
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    // Mocks specific behaviors of UserAccess
    doNothing().when(userAccess).getUserRequest("01010000000", "A123456z");
    doReturn(new User("01010000000", "mock", "A123456z")).when(userAccess).getUser();
    doThrow(new IllegalArgumentException("Invalid credentials")).when(userAccess).getUserRequest("01010100000",
        "B123456z");

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
    loginController = fxmlLoader.getController();
    loginController.setUserAccess(userAccess);
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
   * Test logging in with incorrect ssn or password. Tests if error message is displayed correctly
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
    assertEquals("Invalid credentials", lookup("#errorButton").queryAs(Button.class).getText());
  }

}


