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
 * Controller class for handling deposit functionality in the banking application. Manages the user
 * interface for depositing funds to an {@link Account}.
 */
public class DepositController extends Controller {
  @FXML
  private ImageView homeIcon;
  @FXML
  private ComboBox<String> depositTargetField;
  @FXML
  private TextField depositAmountField;
  @FXML
  private Button errorButton;
  @FXML
  private Button confirmDepositButton;
  @FXML
  private ImageView transferIcon;
  @FXML
  private ImageView withdrawalIcon;
  @FXML
  private ImageView depositIcon;
  @FXML
  private ImageView paymentIcon;

  /**
   * Opens the {@link OverviewController} scene.
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
   * Opens the {@link TransferController} scene.
   *
   * @throws IOException if the scene file is invalid
   */
  @FXML
  private void openTransfer() throws IOException {
    FXMLLoader fxmlLoader = newScene(this, homeIcon, "Transfer.fxml");
    TransferController controller = fxmlLoader.getController();
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
   * Handles the deposit action, depositing a specified amount into the chosen {@link Account}.
   * Displays error messages if there is an issue with the amount or the deposit operation.
   */
  @FXML
  private void handleDeposit() {
    if (isFieldEmpty(depositTargetField)) {
      showError("Target account is not selected.");
      return;
    }


    List<Account> userAccounts = userAccess.getUser().getAccounts();
    String targetAccName = depositTargetField.getValue();
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(depositAmountField.getText());
      Optional<Account> targetAccount =
          userAccounts.stream().filter(account -> targetAccName
              .equals(account.getName())).findFirst();
      if (targetAccount.isPresent()) {
        try {
          userAccess.depositOrWithdrawRequest("deposit", targetAccount
              .get().getAccountNumber(), amount);
        } catch (Exception e) {
          showError(e.getMessage());
          return;
        }
      }
      try {
        openOverview();
      } catch (Exception e) {
        showError(e.getMessage());
      }

    } catch (NumberFormatException e) {
      showError("Amount is not in the right format.");
    }

  }

  /**
   * Updates the available {@link Account}s in the deposit target field. Populates the
   * {@link ChoiceBox} with account names associated with the user.
   */
  public void update() {
    List<Account> accounts = userAccess.getUser().getAccounts();
    List<String> accountNames = accounts.stream()
        .map(Account::getName).collect(Collectors.toList());
    depositTargetField.getItems().addAll(accountNames);
  }

  /**
   * Checks if choicebox field is chosen.
   */
  private boolean isFieldEmpty(ComboBox<String> choiceBox) {
    return choiceBox.getValue() == null;
  }
}
