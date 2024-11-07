package bank.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import bank.core.User;

/**
 * Test class for {@link OverviewController}.
 */
public class OverviewControllerTest extends ApplicationTest {

  @Mock
  private UserAccess userAccess;

  private OverviewController overviewController;

  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /**
   * Sets up the mock dependencies for userAccess and prepares the environment for testing.
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    User testUser = new User("10101000000", "Admin", "A12345z");
    doReturn(testUser).when(userAccess).getUser();
    overviewController.setUserAccess(userAccess);
    doNothing().when(userAccess).createAccountRequest(any());
  }

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
    this.overviewController = fxmlLoader.getController();
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

  /**
   * Test opening deposit screen.
   */
  @Test
  @DisplayName("Test deposit button")
  public void testDepositButton() {
    clickOn("#depositIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("depositRoot"));
  }

  /**
   * Test opening transfer screen.
   */
  @Test
  @DisplayName("Test transfer button")
  public void testTransferButton() {
    clickOn("#transferIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("transferRoot"));
  }


}


