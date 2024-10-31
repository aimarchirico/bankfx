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

public class TransferController {
  @FXML
  private ImageView logoutIcon;
  @FXML
  private UserAccess userAccess;
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


  @FXML
  private void openOverview() throws IOException {
    FXMLLoader fxmlLoader = UiUtils.newScene(this, logoutIcon, "Overview.fxml");
    OverviewController controller = fxmlLoader.getController();
    controller.setUserAccess(userAccess);
  }

  @FXML
  private void handleTransfer() {
    List<Account> userAccounts = userAccess.getUser().getAccounts();
    String sourceAccName = transferSourceField.getValue();
    String targetAccName = transferTargetField.getValue();
    Double amount = 0.0;
    try {
      amount = Double.parseDouble(transferAmountField.getText());
    } catch (NumberFormatException e) {
      UiUtils.showError(errorButton, "Amount is not in the right format");
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
        UiUtils.showError(errorButton, e.getMessage());
      }
    } else {
      UiUtils.showError(errorButton, "Something went wrong trying to select account");
    }
  }
  /**
   * Dismiss error message. Delegates to UiUtils.
   */
  @FXML
  public void dismissError() {
    UiUtils.dismissError(errorButton);
  }

  public void update() {
    List<Account> accounts = userAccess.getUser().getAccounts();
    List<String> accountNames = accounts.stream().map(Account::getName).collect(Collectors.toList());
    transferTargetField.getItems().addAll(accountNames);
    transferSourceField.getItems().addAll(accountNames);
  }

  public void setUserAccess(UserAccess userAccess) {
    this.userAccess = userAccess;
  }

  public UserAccess getUserAccess() {
    return userAccess;
  }

}
