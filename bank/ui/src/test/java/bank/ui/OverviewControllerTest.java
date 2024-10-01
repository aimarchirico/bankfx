package bank.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Test class for {@link OverviewController}.
 */
public class OverviewControllerTest extends ApplicationTest {

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Overview.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test that <code>logoutIcon</code> switches scene to <code>Login.fxml</code>.
   */
  @Test
  @DisplayName("Test logoutIcon")
  public void testLogoutButton() {
    clickOn("#logoutIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("loginRoot"));
  }


}


