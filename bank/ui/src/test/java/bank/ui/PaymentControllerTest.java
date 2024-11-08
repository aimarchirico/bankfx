package bank.ui;

import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.Assert.assertEquals;
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
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testfx.util.WaitForAsyncUtils;
import bank.core.Account;
import bank.core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Test class for {@link PaymentController}.
 */
public class PaymentControllerTest extends ApplicationTest{
 
  @Mock
  private UserAccess userAccess;

  @InjectMocks
  private PaymentController paymentController;

  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  private long accountNumber1;
  private long targetAccountNumber;

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
    paymentController.setUserAccess(userAccess);
    Account testAccount1 = new Account(100.0, "testAccountOne", "Savings Account");
    Account testAccount2 = new Account(100.0, "testAccountTwo", "Savings Account");
    accountNumber1 = testAccount1.getAccountNumber();
    targetAccountNumber = Long.parseLong("12345678901");
    List<Account> testAccounts = List.of(testAccount1, testAccount2);
    User testUser = new User("10101000000", "Admin", "A12345z", testAccounts);
    doReturn(testUser).when(userAccess).getUser();
    doThrow(new IllegalArgumentException("Account balance would be negative")).when(userAccess)
        .paymentRequest(accountNumber1, targetAccountNumber, 150.0);
  }

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Payment.fxml"));
    Parent parent = fxmlLoader.load();
    this.paymentController = fxmlLoader.getController();
    MockitoAnnotations.openMocks(this);
    setup();
    paymentController.update();
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
   * Test opening withdrawal screen.
   */
  @Test
  @DisplayName("Test Withdrawal button")
  public void testWithdrawalButton() {
    clickOn("#withdrawalIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("withdrawalRoot"));
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
   * Tests a successful payment between two accounts by simulating interactions with UI fields for
   * source account, target account, and transfer amount. Verifies that the payment request is called
   * once with the specified parameters.
   *
   * @throws Exception if any interaction with the UI components fails or if the payment request
   *         verification fails.
   */
  @Test
  @DisplayName("Test successful payment between accounts")
  public void testSuccessfullPayment() {
    clickOn("#paymentSourceChoiceBox");
    clickOn("testAccountOne");
    clickOn("#paymentTargetField");
    write("12345678901");
    clickOn("#amountField");
    write("50");
    clickOn("#confirmPaymentButton");
    WaitForAsyncUtils.waitForFxEvents();
    verify(userAccess, times(1)).paymentRequest(accountNumber1, targetAccountNumber, 50.0);

  }
  
  /**
   * Tests a failed payment between two accounts by simulating interactions with UI fields for
   * source account, target account, and transfer amount. Asserts that the payment request is denied,
   *  and that the errorbutton shows the right message
   *
   * @throws Exception if any interaction with the UI components fails or if the errorbutton not shows up
   */
  @Test
  @DisplayName("Test failed payment between two accounts")
  public void testFailedPayment() throws IOException {
    clickOn("#paymentSourceChoiceBox");
    clickOn("testAccountOne");
    clickOn("#paymentTargetField");
    write("12345678901");
    clickOn("#amountField");
    write("150");
    clickOn("#confirmPaymentButton");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals("Account balance would be negative", lookup("#errorButton").queryAs(Button.class).getText());
  }

}
