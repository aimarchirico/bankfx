package bank.ui;


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
import javafx.stage.Stage;

/*
 * Test class for {@link LoginController}
 */
public class TransferControllerTest extends ApplicationTest {

  @Mock
  private UserAccess userAccess;

  @InjectMocks
  private TransferController transferController;

  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  private long accountNumber1;
  private long accountNumber2;
  private long accountNumber3;

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
    transferController.setUserAccess(userAccess);
    Account testAccount1 = new Account(100.0, "testAccountOne", "Savings Account");
    Account testAccount2 = new Account(100.0, "testAccountTwo", "Savings Account");
    Account testAccount3 = new Account(100.0, "testAccountThree", "Savings Account");
    accountNumber1 = testAccount1.getAccountNumber();
    accountNumber2 = testAccount2.getAccountNumber();
    List<Account> testAccounts = List.of(testAccount1, testAccount2, testAccount3);
    User testUser = new User("10101000000", "Admin", "A12345z", testAccounts);
    doReturn(testUser).when(userAccess).getUser();
    // Simulert unntak
    doThrow(new IllegalArgumentException("Account balance would be negative")).when(userAccess)
        .transferRequest(accountNumber2, accountNumber3, 2000.0);
  }

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Transfer.fxml"));
    Parent parent = fxmlLoader.load();
    this.transferController = fxmlLoader.getController();
    MockitoAnnotations.openMocks(this);
    setup();
    transferController.update();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Tests the home functionality by simulating a click on the home icon and verifying that the
   * user is returned to the overview screen.
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
   * Tests a successful transfer between two accounts by simulating interactions with UI fields for
   * source account, target account, and transfer amount. Verifies that the transfer request is called
   * once with the specified parameters.
   *
   * @throws Exception if any interaction with the UI components fails or if the transfer request
   *         verification fails.
   */
  @Test
  @DisplayName("Test succesful transfer between accounts")
  public void testSuccessfulTransfer() {
    clickOn("#transferSourceField");
    clickOn("testAccountOne");
    clickOn("#transferTargetField");
    clickOn("testAccountTwo");
    clickOn("#transferAmountField");
    write("10");
    clickOn("#confirmTransferButton");
    WaitForAsyncUtils.waitForFxEvents();
    verify(userAccess, times(1)).transferRequest(accountNumber1, accountNumber2, 10.0);

  }
}

