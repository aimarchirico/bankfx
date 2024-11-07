package bank.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ControllerTest extends ApplicationTest {

  private Controller controller;

  @Mock
  private UserAccess userAccess;

  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /**
   * Sets up mock objects and initializes the test before each test.
   */
  @BeforeEach
  public void setup() {
    this.controller = new Controller();
    controller.errorButton = new Button();
    userAccess = mock(UserAccess.class);
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
   * Test switching scenes using {@link UiUtils#newScene(Object, javafx.scene.Node, String)}.
   *
   * @throws IOException when file is invalid
   */
  @Test
  @DisplayName("Test switching scene")
  public void testSwitchingScene() {
    interact(() -> {
      try {
        controller.newScene(this, lookup("#loginButton").queryAs(Button.class), "CreateUser.fxml");
      } catch (IOException e) {
        assertTrue(false);
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("createUserRoot"));
  }

  /**
   * Tests showing error message.
   */
  @Test
  @DisplayName("Test showing error message")
  public void testShowError() {
    String errorMessage = "An error occurred";
    controller.showError(errorMessage);
    assertTrue(controller.errorButton.isVisible(), "Error button should be visible");
    assertEquals(errorMessage, controller.errorButton.getText(), "Error message should match");
  }

  /**
   * Tests dismissing error message.
   */
  @Test
  @DisplayName("Test dismissing error message")
  public void testDismissError() {
    controller.showError("An error occurred");
    controller.dismissError();
    assertFalse(controller.errorButton.isVisible(), "Error button should not be visible after dismissal");
  }

  /**
   * Tests setting and getting userAccess.
   */
  @Test
  @DisplayName("Test setting and getting userAccess")
  public void testSetAndGetUserAccess() {
    controller.setUserAccess(userAccess);
    assertEquals(userAccess, controller.getUserAccess(), "UserAccess should be set and retrieved correctly");
  }
}
