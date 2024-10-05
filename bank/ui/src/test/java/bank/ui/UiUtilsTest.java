package bank.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
 * Test class for {@link UiUtils}.
 */
public class UiUtilsTest extends ApplicationTest {
  
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
   * Test switching scenes using {@link UiUtils#newScene(Object, javafx.scene.Node, String)}.
   *
   * @throws IOException when file is invalid
   */
  @Test
  @DisplayName("Test switching scene")
  public void testSwitchingScene() {
    interact(() -> {
      try {
        UiUtils.newScene(this, lookup("#loginButton").queryAs(Button.class), "CreateUser.fxml");
      } catch (IOException e) {
        assertTrue(false);
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("createUserRoot"));
  }

}


