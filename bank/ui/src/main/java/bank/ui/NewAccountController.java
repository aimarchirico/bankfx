package bank.ui;

import bank.core.Account;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for <code>NewAccount.fxml</code>.
 */
public class NewAccountController extends Controller {

  @FXML
  private ImageView backIcon;
  @FXML
  private Button confirmButton;
  @FXML
  private ImageView paymentIcon;
  @FXML
  private ImageView transferIcon;
  @FXML
  private ImageView withdrawalIcon;
  @FXML
  private ImageView depositIcon;
  @FXML
  private TextField nameField;
  @FXML
  private ChoiceBox<String> accountTypes;
  @FXML
  private Button errorButton;

  /**
   * Open overview scene.
   *
   * @throws IOException when file is invalid
   */

  private void openOverview(Node node) throws IOException {
    FXMLLoader fxmlLoader = newScene(this, node, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Uses the openOverview function with the right input.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void goBack() throws IOException {
    openOverview(backIcon);
  }

  /**
   * Open payment scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openPayment() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, paymentIcon, "Payment.fxml");
    PaymentController paymentController = fxmlLoader.getController();
    paymentController.setUserAccess(userAccess);
    paymentController.update();
  }

  /**
   * Opens the {@link DepositController} scene for making a deposit.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openDeposit() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, depositIcon, "Deposit.fxml");
    DepositController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Opens the {@link WithdrawalController} scene for making a withdrawal.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openWithdrawal() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, withdrawalIcon, "Withdrawal.fxml");
    WithdrawalController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }


  /**
   * Opens the {@link TransferController} scene for initiating a transfer.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openTransfer() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, transferIcon, "Transfer.fxml");
    TransferController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Handles the creation of an account based on user input,
   * initiates the createAccount request.
   */
  @FXML
  private void newAccount() {
    try {
      userAccess.createAccountRequest(new 
          Account(0.0, nameField.getText(), accountTypes.getValue()));
      openOverview(confirmButton);
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }

}
