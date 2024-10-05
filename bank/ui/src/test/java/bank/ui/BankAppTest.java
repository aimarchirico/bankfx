package bank.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Test class for {@link BankApp}.
 */
public class BankAppTest extends ApplicationTest {

  private LoginController controller;

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
    this.controller = fxmlLoader.getController();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test that initial controller is not <code>null</code>.
   */
  @Test
  @DisplayName("Test initial controller")
  public void testInitialController() {
    assertNotNull(this.controller);
  }

  /**
   * Test that initial scene is <code>Login.fxml</code>.
   */
  @Test
  @DisplayName("Test initial scene")
  public void testInitialScene() {
    assertNotNull(UiUtils.findSceneRootWithId("loginRoot"));
  }

}


