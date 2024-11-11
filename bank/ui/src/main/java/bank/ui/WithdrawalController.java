package bank.ui;

import bank.core.Account;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for <code>Withdrawal.fxml</code>.
 */
public class WithdrawalController extends Controller {

  @FXML
  private ImageView homeIcon;
  @FXML
  private ImageView paymentIcon;
  @FXML
  private ImageView transferIcon;
  @FXML
  private ImageView depositIcon;
  @FXML
  private ComboBox<String> withdrawalSourceField;
  @FXML
  private TextField withdrawalAmountField;
  @FXML
  private Button errorButton;
  @FXML
  private Button confirmWithdrawalButton;

  /**
   * Open Overview scene.
   *
   * @throws IOException when file is invalid
   */
  @FXML
  private void openOverview() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, homeIcon, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Opens the {@link PaymentController} scene for making a payment.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openPayment() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, paymentIcon, "Payment.fxml");
    PaymentController controller = fxmlLoader.getController();
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
   * Handles the withdrawal of funds from accounts based on user input. Validates the input and
   * initiates the withdrawal request.
   */
  @FXML
  private void handleWithdrawal() {
    if (isFieldEmpty(withdrawalSourceField)) {
      showError("Source account is not selected.");
      return;
    }
    List<Account> userAccounts = userAccess.getUser().getAccounts();
    String targetAccName = withdrawalSourceField.getValue();
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(withdrawalAmountField.getText());
    } catch (NumberFormatException e) {
      showError("Amount is not in the right format.");
      return;
    }
    Optional<Account> targetAccount =
        userAccounts.stream().filter(account -> targetAccName
            .equals(account.getName())).findFirst();
    if (targetAccount.isPresent()) {
      try {
        userAccess.withdrawRequest(targetAccount.get().getAccountNumber(), amount);
      } catch (Exception e) {
        showError(e.getMessage());
        return;
      }
    } else {
      showError("You must choose an account.");
      return;
    }
    try {
      openOverview();
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }

  /**
   * Updates the source account field with the user's account names.
   */
  public void update() {
    List<Account> accounts = userAccess.getUser().getAccounts();
    List<String> accountNames = accounts.stream()
        .map(Account::getName).collect(Collectors.toList());
    withdrawalSourceField.getItems().addAll(accountNames);
  }

  /**
   * Checks if choicebox field is chosen.
   */
  private boolean isFieldEmpty(ComboBox<String> choiceBox) {
    return choiceBox.getValue() == null;
  }
}
