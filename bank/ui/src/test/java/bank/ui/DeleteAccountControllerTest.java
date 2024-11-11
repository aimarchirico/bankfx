package bank.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import bank.core.Account;
import bank.core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteAccountControllerTest extends ApplicationTest {
  @Mock
  private UserAccess userAccess;

  @InjectMocks
  private DeleteAccountController deleteAccountController;
  private long accountNumber1;
  private long accountNumber2;

  /**
   * Setup headless test support.
   */
  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  /**
   * Sets up mock objects before each test.
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    deleteAccountController.setUserAccess(userAccess);
    Account testAccount1 = new Account(0.0, "testAccountOne", "Savings Account");
    Account testAccount2 = new Account(100.0, "testAccountTwo", "Savings Account");
    List<Account> testAccounts = List.of(testAccount1, testAccount2);
    accountNumber1 = testAccount1.getAccountNumber();
    accountNumber2 = testAccount2.getAccountNumber();

    User testUser = new User("10101000000", "Admin", "A12345z", testAccounts);
    doReturn(testUser).when(userAccess).getUser();

    // Mock oppførselen for en vellykket sletting
    doNothing().when(userAccess).deleteAccountRequest(accountNumber1);

    // Mock oppførselen for en konto som ikke kan slettes     
    doThrow(new IllegalArgumentException("Balance must be 0.0")).when(userAccess).deleteAccountRequest(accountNumber2);

    
    

  }
  
  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("DeleteAccount.fxml"));
    Parent parent = fxmlLoader.load();
    this.deleteAccountController = fxmlLoader.getController();
    MockitoAnnotations.openMocks(this);
    setup();
    deleteAccountController.update();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Test that <code>backIcon</code> switches scene to <code>Overview.fxml</code>.
   */
  @Test
  @DisplayName("Test the backIcon")
  public void testBackIcon() {
    clickOn("#backIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
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

  /**
   * Test opening payment screen.
   */
  @Test
  @DisplayName("Test payment button")
  public void testPayment() {
    clickOn("#paymentIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("paymentRoot"));
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
   * Test opening withdrawal screen.
   */
  @Test
  @DisplayName("Test withdrawal button")
  public void testWithdrawalButton() {
    clickOn("#withdrawalIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("withdrawalRoot"));
  }


  @Test
  @DisplayName("Test succesful deletion of account")
  public void testSuccesfullDeleteAccount() {
    clickOn("#deleteChoiceBox");
    clickOn("testAccountOne");
    clickOn("#confirmButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
    verify(userAccess, times(1)).deleteAccountRequest(accountNumber1);
   }

  @Test
  @DisplayName("Test failed deletion of account")
  public void testfailedDeleteAccount() {
    clickOn("#deleteChoiceBox");
    clickOn("testAccountTwo");
    clickOn("#confirmButton");
    WaitForAsyncUtils.waitForFxEvents();
    Button errorButton = lookup("#errorButton").query();
    assertNotNull(errorButton, "Error button should be present.");
    assertEquals("Balance must be 0.0", errorButton.getText());
   }
}
