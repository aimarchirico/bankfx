package bank.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
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

public class DepositControllerTest extends ApplicationTest {

  @Mock
  private UserAccess userAccess;

  @InjectMocks
  private DepositController depositController;

  @BeforeAll
  public static void setupHeadless() {
    UiUtils.supportHeadless();
  }

  private long accountNumber1;

  /**
   * Sets up mock objects and initializes test accounts before each test.
   * <p>
   * Configures user access and test accounts for deposit functionality.
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    depositController.setUserAccess(userAccess);
    Account testAccount1 = new Account(100.0, "testAccountOne", "Savings Account");
    Account testAccount2 = new Account(100.0, "testAccountTwo", "Savings Account");
    Account testAccount3 = new Account(100.0, "testAccountThree", "Savings Account");
    accountNumber1 = testAccount1.getAccountNumber();
    List<Account> testAccounts = List.of(testAccount1, testAccount2, testAccount3);
    User testUser = new User("10101000000", "Admin", "A12345z", testAccounts);
    doReturn(testUser).when(userAccess).getUser();
  }

  /**
   * Loads initial scene.
   *
   * @param stage the top level JavaFX container
   * @throws IOException when file is invalid
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Deposit.fxml"));
    Parent parent = fxmlLoader.load();
    this.depositController = fxmlLoader.getController();
    MockitoAnnotations.openMocks(this);
    setup();
    depositController.update();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  /**
   * Tests the logout functionality by clicking the logout icon and verifying that the overview screen
   * loads.
   */
  @Test
  @DisplayName("Test logout function")
  public void testLogoutButton() {
    clickOn("#logoutIcon");
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(UiUtils.findSceneRootWithId("overviewRoot"));
  }

  /**
   * Tests a successful deposit by selecting an account and amount, confirming the deposit, and
   * verifying the request was processed.
   */
  @Test
  @DisplayName("Test succesful transfer between accounts")
  public void testSuccessfulDeposit() {
    clickOn("#depositTargetField");
    clickOn("testAccountOne");
    clickOn("#depositAmountField");
    write("10");
    clickOn("#confirmDepositButton");
    WaitForAsyncUtils.waitForFxEvents();
    verify(userAccess, times(1)).depositOrWithdrawRequest("deposit", accountNumber1, 10.0);;

  }
}
