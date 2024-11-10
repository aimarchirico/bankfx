package bank.ui;

import bank.core.Account;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for managing fund transfers between accounts in the banking application.
 */
public class TransferController extends Controller {
  @FXML
  private ImageView homeIcon;
  @FXML
  private ImageView transferIcon;
  @FXML
  private ImageView withdrawalIcon;
  @FXML
  private ImageView depositIcon;
  @FXML
  private ImageView paymentIcon;
  @FXML
  private ChoiceBox<String> transferTargetField;
  @FXML
  private ChoiceBox<String> transferSourceField;
  @FXML
  private TextField transferAmountField;
  @FXML
  private Button errorButton;
  @FXML
  private Button confirmTransferButton;

  /**
   * Opens the {@link OverviewController} scene to view account overview.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openOverview() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, homeIcon, "Overview.fxml");
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
    FXMLLoader fxmlLoader = newScene(this, homeIcon, "Deposit.fxml");
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
   * Handles the transfer of funds between accounts based on user input. Validates the input and
   * initiates the transfer request.
   */
  @FXML
  private void handleTransfer() {
    if (isFieldEmpty(transferSourceField) || isFieldEmpty(transferTargetField)) {
      showError("All fields must be selected.");
      return;
    }
    List<Account> userAccounts = userAccess.getUser().getAccounts();
    String sourceAccName = transferSourceField.getValue();
    String targetAccName = transferTargetField.getValue();
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(transferAmountField.getText());
    } catch (NumberFormatException e) {
      showError("Amount is not in the right format.");
      return;
    }

    Optional<Account> targetAccount =
        userAccounts.stream().filter(account -> targetAccName
            .equals(account.getName())).findFirst();
    Optional<Account> sourceAccount =
        userAccounts.stream().filter(account -> sourceAccName
            .equals(account.getName())).findFirst();
    if (targetAccount.isPresent() && sourceAccount.isPresent()) {
      try {
        userAccess.transferRequest(sourceAccount.get()
            .getAccountNumber(), targetAccount.get().getAccountNumber(),
            amount);

      } catch (Exception e) {
        showError(e.getMessage());
        return;
      }
    } else {
      showError("Something went wrong trying to select account.");
    }
    try {
      openOverview();
    } catch (Exception e) {
      showError(e.getMessage());
      return;
    }

  }

  /**
   * Updates the source and target account fields with the user's account names.
   */
  public void update() {
    List<Account> accounts = userAccess.getUser().getAccounts();
    List<String> accountNames = accounts.stream()
        .map(Account::getName).collect(Collectors.toList());
    transferTargetField.getItems().addAll(accountNames);
    transferSourceField.getItems().addAll(accountNames);
  }

  /**
   * Checks if choicebox field is chosen.
   */
  private boolean isFieldEmpty(ChoiceBox<String> choiceBox) {
    return choiceBox.getValue() == null;
  }
}
