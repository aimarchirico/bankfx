package bank.ui;

import bank.core.Account;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;

/**
 * Controller class for <code>deleteAccount.fxml</code>.
 */
public class DeleteAccountController extends Controller {
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
  private Button errorButton;
  @FXML
  private ChoiceBox<String> deleteChoiceBox;

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
   * Updates the source account field with the user's account names.
   */
  public void update() {
    ObservableList<String> accounts = FXCollections.observableArrayList();
    List<String> accountNames =
        userAccess.getUser().getAccounts().stream()
            .map(Account::getName).collect(Collectors.toList());
    accounts.setAll(accountNames);
    deleteChoiceBox.getItems().addAll(accountNames);

  }

  /**
   * Handles the deletion of an account based on user input. Validates the input and initiates the
   * deleteAccount request.
   */
  @FXML
  private void confirmDeleteAccount() {
    Optional<Account> targetAccount = Optional.empty();
    try {
      List<Account> userAccounts = userAccess.getUser().getAccounts();
      String targetAccName = deleteChoiceBox.getValue();
      targetAccount = userAccounts.stream()
          .filter(account -> targetAccName.equals(account.getName())).findFirst();
    } catch (Exception e) {
      showError("You must choose an account to delete");
      return;
    }

    try {
      userAccess.deleteAccountRequest(targetAccount.get().getAccountNumber());
    } catch (Exception e) {
      showError(e.getMessage());
      return;
    }

    try {
      openOverview(confirmButton);
    } catch (Exception e) {
      showError(e.getMessage());
    }
  }


}
