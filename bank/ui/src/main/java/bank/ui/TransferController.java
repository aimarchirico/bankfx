package bank.ui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import bank.core.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for managing fund transfers between accounts in the banking application.
 */
public class TransferController extends Controller{
  @FXML
  private ImageView logoutIcon;
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
    FXMLLoader fxmlLoader = newScene(this, logoutIcon, "Overview.fxml");
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
    FXMLLoader fxmlLoader = newScene(this, logoutIcon, "Deposit.fxml");
    DepositController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
    controller.update();
  }

  /**
   * Handles the transfer of funds between accounts based on user input.
   * Validates the input and initiates the transfer request.
   */
  @FXML
  private void handleTransfer() {
    List<Account> userAccounts = userAccess.getUser().getAccounts();
    String sourceAccName = transferSourceField.getValue();
    String targetAccName = transferTargetField.getValue();
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(transferAmountField.getText());
    } catch (NumberFormatException e) {
      showError("Amount is not in the right format");
    }

    Optional<Account> targetAccount =
        userAccounts.stream().filter(Account -> targetAccName.equals(Account.getName())).findFirst();
    Optional<Account> sourceAccount =
        userAccounts.stream().filter(Account -> sourceAccName.equals(Account.getName())).findFirst();
    if (targetAccount.isPresent() && sourceAccount.isPresent()) {
      try {
        userAccess.transferRequest(sourceAccount.get().getAccountNumber(), targetAccount.get().getAccountNumber(),
            amount);
      } catch (Exception e) {
        showError(e.getMessage());
      }
    } else {
      showError("Something went wrong trying to select account");
    }
    try {
      openOverview();
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }

  /**
   * Updates the source and target account fields with the user's account names.
   */
  public void update() {
    List<Account> accounts = userAccess.getUser().getAccounts();
    List<String> accountNames = accounts.stream().map(Account::getName).collect(Collectors.toList());
    transferTargetField.getItems().addAll(accountNames);
    transferSourceField.getItems().addAll(accountNames);
  }

}
