package bank.ui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import bank.core.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for <code>Payment.fxml</code>.
 */
public class PaymentController extends Controller{
  @FXML
  private ImageView homeIcon;
  @FXML
  private ImageView transferIcon;
  @FXML
  private ImageView withdrawalIcon;
  @FXML
  private ImageView depositIcon;
  @FXML
  private ChoiceBox<String> paymentSourceChoiceBox;
  @FXML
  private TextField paymentTargetField;
  @FXML
  private TextField amountField;
  @FXML
  private Button confirmPaymentButton;
  @FXML
  private Button errorButton;

  
  /**
   * Updates the source account field with the user's account names.
   */
  public void update() {
    ObservableList<String> accounts = FXCollections.observableArrayList();
    List<String> accountNames =
        userAccess.getUser().getAccounts().stream().map(Account::getName).collect(Collectors.toList());
    accounts.setAll(accountNames);
    paymentSourceChoiceBox.setItems(accounts);

  }

  /**
   * Open Overview scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openOverview(Node node) throws IOException {
    FXMLLoader fxmlLoader = newScene(this, node, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();

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
   * Opens the {@link TransferController} scene for making a transfer.
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
   * Opens the {@link Withdrawal} scene for making a withdrawal.
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
   * Starts the openOverview method with right input
   */
  @FXML
  private void goHome() throws IOException {
    openOverview(homeIcon);
  }

  
  /**
   * Handles the payment of funds between accounts based on user input. Validates the input and
   * initiates the payment request.
   */
  @FXML
  private void handlePayment() {
    Optional<Account> account = Optional.empty();
    Double amount = 0.0;
    Long toAccountNumber = null;
    try {
      String accName = paymentSourceChoiceBox.getValue();
      List<Account> userAccounts = userAccess.getUser().getAccounts();
      account = userAccounts.stream().filter(Account -> accName.equals(Account.getName())).findFirst();
    } catch (Exception e) {
      showError("You must choose an account to pay from");
      return;
    }
    try {
      toAccountNumber = Long.parseLong(paymentTargetField.getText());
    } catch (NumberFormatException e) {
      showError("To-field is not on the right format, it should be the account number");
      return;
    }
    try {
      amount = Double.parseDouble(amountField.getText());
    } catch (NumberFormatException e) {
      showError("Amount is not in the right format");
      return;
    }
    try {
      userAccess.paymentRequest(account.get().getAccountNumber(), toAccountNumber, amount);

    } catch (Exception e) {
      showError(e.getMessage());
      return;
    }

    try {
      openOverview(confirmPaymentButton);
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }
}
