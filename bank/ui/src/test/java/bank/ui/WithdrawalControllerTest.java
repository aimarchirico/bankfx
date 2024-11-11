package bank.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

/**
 * Test class for {@link WithdrawalController}.
 */
public class WithdrawalControllerTest extends ApplicationTest{
  @Mock
  private UserAccess userAccess;

  @InjectMocks
  private WithdrawalController withdrawalController;

  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  private long accountNumber1;

  /**
   * Initializes mock objects and test data before each test.
   *
   * <p>
   * Mocks user access, sets up three test accounts, and assigns two accounts for use in tests. A
   * simulated user is returned by {@code userAccess.getUser()}
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    withdrawalController.setUserAccess(userAccess);
    Account testAccount1 = new Account(100.0, "testAccountOne", "Savings Account");
    Account testAccount2 = new Account(100.0, "testAccountTwo", "Savings Account");
    accountNumber1 = testAccount1.getAccountNumber();
    List<Account> testAccounts = List.of(testAccount1, testAccount2);
    User testUser = new User("10101000000", "Admin", "A12345z", testAccounts);
    doReturn(testUser).when(userAccess).getUser();
    doThrow(new IllegalArgumentException("Account balance would be negative")).when(userAccess)
        .withdrawRequest(accountNumber1, 150);
  }

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Withdrawal.fxml"));
    Parent parent = fxmlLoader.load();
    this.withdrawalController = fxmlLoader.getController();
    MockitoAnnotations.openMocks(this);
    setup();
    withdrawalController.update();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Tests the home functionality by simulating a click on the home icon and verifying that the user
   * is returned to the overview screen.
   *
   * @throws Exception if the home process fails or the root element is not found.
   */
  @Test
  @DisplayName("Test home function")
  public void testHomeButton() {
    clickOn("#homeIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
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
   * Test opening payment screen.
   */
  @Test
  @DisplayName("Test payment button")
  public void testPaymentButton() {
    clickOn("#paymentIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("paymentRoot"));
  }

  /**
   * Test opening deposit screen.
   */
  @Test
  @DisplayName("Test Transfer button")
  public void testTransferButton() {
    clickOn("#transferIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("transferRoot"));
  }

  /**
   * Tests a successful withdrawal from an account by simulating interactions with UI fields for
   * source account and transfer amount. Verifies that the withdrawal request is called
   * once with the specified parameters.
   *
   * @throws Exception if any interaction with the UI components fails or if the withdrawal request
   *         verification fails.
   */
  @Test
  @DisplayName("Test successful withdrawal from account")
  public void testSuccessfullWithdrawal() {
    clickOn("#withdrawalSourceField");
    clickOn("testAccountOne");
    clickOn("#withdrawalAmountField");
    write("50");
    clickOn("#confirmWithdrawalButton");
    WaitForAsyncUtils.waitForFxEvents();
    verify(userAccess, times(1)).withdrawRequest(accountNumber1, 50.0);

  }
  
  /**
   * Tests a failed withdrawal from an account by simulating interactions with UI fields for
   * source account and transfer amount. Asserts that the withdrawal request is denied,
   *  and that the errorbutton shows the right message
   *
   * @throws Exception if any interaction with the UI components fails or if the errorbutton not shows up
   */
  @Test
  @DisplayName("Test failed withdrawal between two accounts")
  public void testFailedWithdrawal() throws IOException {
    clickOn("#withdrawalSourceField");
    clickOn("testAccountOne");
    clickOn("#withdrawalAmountField");
    write("150");
    clickOn("#confirmWithdrawalButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals("Account balance would be negative", lookup("#errorButton").queryAs(Button.class).getText());
  }
}
