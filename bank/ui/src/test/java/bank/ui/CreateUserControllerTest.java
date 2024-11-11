package bank.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Test class for {@link CreateUserController}.
 */
public class CreateUserControllerTest extends ApplicationTest {

  @Mock
  private UserAccess userAccess;

  @InjectMocks
  private CreateUserController createUserController;

  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /**
   * Sets up UserAccess mock.
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    doNothing().when(userAccess).createUserRequest(Mockito.argThat(user -> 
        user.getSsn().equals("01010000000")
    ));

    doThrow(new IllegalArgumentException("User already exists.")).when(userAccess)
        .createUserRequest(Mockito.argThat(user -> 
            user.getSsn().equals("01010100000")
        ));
}

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
    createUserController = fxmlLoader.getController();
    createUserController.setUserAccess(userAccess);
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test registering a new user successfully.
   */
  @Test
  @DisplayName("Test successful user registration")
  public void testSuccessfulUserRegistration() {
    clickOn("#ssnField");
    write("01010000000");
    clickOn("#nameField");
    write("Test User");
    clickOn("#passwordField");
    write("A123456z");
    clickOn("#registerButton");
    WaitForAsyncUtils.waitForFxEvents();

    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
  }

  /**
   * Test registering a user with existing SSN.
   */
  @Test
  @DisplayName("Test failed user registration due to existing SSN")
  public void testFailedUserRegistration() {
    clickOn("#ssnField");
    write("01010100000");
    clickOn("#nameField");
    write("Another User");
    clickOn("#passwordField");
    write("B123456z");
    clickOn("#registerButton");
    WaitForAsyncUtils.waitForFxEvents();

    Button errorButton = lookup("#errorButton").query();
    assertNotNull(errorButton, "Error button should be present.");
    assertEquals("User already exists.", errorButton.getText());

  }

  /**
   * Test navigating back to the login scene.
   */
  @Test
  @DisplayName("Test back to login scene")
  public void testBackToLoginScene() {
    clickOn("#backIcon");
    WaitForAsyncUtils.waitForFxEvents();

    assertNotNull(UiUtils.findSceneRootWithId("loginRoot"));
  }
}


