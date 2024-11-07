package bank.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import bank.core.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


/**
 * Test class for {@link NewAccountController}.
 */
public class NewAccountControllerTest extends ApplicationTest {

  @Mock
  private UserAccess userAccess;

  private NewAccountController newAccountController;


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
    when(userAccess.getUser()).thenReturn(new User("11111111111", "Test", "Hei123"));
    newAccountController.setUserAccess(userAccess);
    doNothing().when(userAccess).createAccountRequest(any());
  }

  /**
   * Loads initial scene.
   *
   * @param stage the top-level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewAccount.fxml"));
    Parent parent = fxmlLoader.load();
    newAccountController = fxmlLoader.getController();
    newAccountController.setUserAccess(userAccess);
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test that clicking the back button navigates to the overview scene.
   */
  @Test
  @DisplayName("Test goBack Button Navigation")
  public void testGoBack() {
    clickOn("#backIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
  }

  /**
   * Test creating a new account with valid data.
   */
  @Test
  @DisplayName("Test newAccount with valid data")
  public void testNewAccount_Success() throws IOException {
    clickOn("#nameField");
    write("Saving");
    clickOn("#typeField");
    write("Savings Account");
    clickOn("#confirmButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
  }

  /**
   * Test creating a new account with invalid data, verifying error message.
   */
  @Test
  @DisplayName("Test newAccount with invalid data")
  public void testNewAccount_Error() throws IOException {
    doThrow(new IllegalStateException("Account creation failed.")).when(userAccess).createAccountRequest(any());
    clickOn("#nameField");
    write("Saving");
    clickOn("#typeField");
    write("Savings Account");
    clickOn("#confirmButton");
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals("Account creation failed.", lookup("#errorButton").queryAs(Button.class).getText());
  }
}
